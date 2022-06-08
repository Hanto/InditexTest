package com.inditex.test.product.application.service;// Created by jhant on 04/06/2022.

import com.inditex.test.common.UseCase;
import com.inditex.test.product.application.port.in.ModifyPriceCommand;
import com.inditex.test.product.application.port.in.PriceUseCase;
import com.inditex.test.product.application.port.in.QueryPriceCommand;
import com.inditex.test.product.application.port.out.PersistenceRepository;
import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.PriceId;
import com.inditex.test.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleStateException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@UseCase
@RequiredArgsConstructor
public class PriceService implements PriceUseCase
{
    private final PersistenceRepository persistenceRepository;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price getPrice(PriceId priceId)
    {   return persistenceRepository.loadPrice(priceId); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price assignedPriceFor(QueryPriceCommand command)
    {
        Product product = persistenceRepository.loadProduct(command.getProductId());
        return product.getPrices().getPriceAt(command.getLocalDateTime(), command.getBrandId());
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