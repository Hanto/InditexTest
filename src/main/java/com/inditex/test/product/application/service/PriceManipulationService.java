package com.inditex.test.product.application.service;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.port.in.ModifyPriceCommand;
import com.inditex.test.product.application.port.in.PriceManipulationUseCase;
import com.inditex.test.product.application.port.out.MemoryRepository;
import com.inditex.test.product.application.port.out.PersistenceRepository;
import com.inditex.test.product.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleStateException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@RequiredArgsConstructor
public class PriceManipulationService implements PriceManipulationUseCase
{
    private final PersistenceRepository persistenceRepository;
    private final MemoryRepository memoryRepository;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price getPrice(long priceId)
    {   return persistenceRepository.loadPrice(new PriceId(priceId)); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price assignedPriceFor(long productId, long brandId, LocalDateTime time)
    {
        Product product = persistenceRepository.loadProduct(new ProductId(productId));
        return product.getPrices().getPriceAt(time, new BrandId(brandId));
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    @Retryable(
        value = {StaleStateException.class},
        maxAttempts = 10,
        backoff = @Backoff(delay = 5))
    @Transactional(
        propagation = REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void modifyPrice(ModifyPriceCommand command)
    {
        Product product = persistenceRepository.loadProduct(command.getProductId());
        Price price = product.getPrices().getPrice(command.getPriceId());

        price.addCost(command.getMoney());

        persistenceRepository.saveProduct(product);
    }
}