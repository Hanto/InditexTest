package com.inditex.test.product.application.service;// Created by jhant on 04/06/2022.

import com.inditex.test.common.annotations.UseCase;
import com.inditex.test.product.application.port.in.ModifyPriceCommand;
import com.inditex.test.product.application.port.in.PriceUseCase;
import com.inditex.test.product.application.port.in.QueryPriceCommand;
import com.inditex.test.product.application.port.out.EventBus;
import com.inditex.test.product.application.port.out.ProductRepository;
import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.PriceId;
import com.inditex.test.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.StaleStateException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@UseCase
@RequiredArgsConstructor
@Log4j2
public class PriceService implements PriceUseCase
{
    private final ProductRepository productRepository;
    private final EventBus eventBus;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price getPrice(PriceId priceId)
    {   return productRepository.loadPrice(priceId); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price assignedPriceFor(QueryPriceCommand command)
    {
        Product product = productRepository.loadProduct(command.getProductId());
        return product.getPriceAt(command.getLocalDateTime(), command.getBrandId());
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Retryable(
        value = {StaleStateException.class},
        maxAttempts = 10,
        backoff = @Backoff(delay = 5))
    @Transactional(
        propagation = REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void modifyPrice(ModifyPriceCommand command)
    {
        Product product = productRepository.loadProduct(command.getProductId());
        Price price = product.getPrice(command.getPriceId());

        price.addCost(command.getMoney());

        productRepository.saveProduct(product);
        eventBus.publish(product.pullAllDomainEvents());
    }
}