package com.inditex.test.product.application.service;// Created by jhant on 07/06/2022.

import com.inditex.test.product.application.port.in.PaginationCommand;
import com.inditex.test.product.application.port.in.ProductUseCase;
import com.inditex.test.product.application.port.out.MemoryRepository;
import com.inditex.test.product.application.port.out.PersistenceRepository;
import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;
import com.inditex.test.product.domain.model.ProductName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SameParameterValue")
public class ProductServiceTest extends BDDMockito
{
    private final PersistenceRepository dao = Mockito.mock(PersistenceRepository.class);
    private final MemoryRepository memDao = Mockito.mock(MemoryRepository.class);
    private final ProductUseCase service = new ProductService(dao, memDao);

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("WHEN: the application service: ProductService is used")
    class Service
    {
        @Test
        @DisplayName("THEN: can create a new Product and save it")
        void test0()
        {
            givenAProductIdSequenceAt(1L);

            service.createProduct(new ProductName("short", "long"));

            Product product = thenProductSaved(1).get(0);

            assertThat(product.getProductId().getId()).isEqualTo(1L);
            assertThat(product.getShortName()).isEqualTo("short");
            assertThat(product.getLongName()).isEqualTo("long");
            assertThat(product.getPriceList()).hasSize(0);
        }

        @Test @DisplayName("THEN: can retrieve a Product")
        void test1()
        {
            givenAProductWithId(1L, "shortName", "longName");

            Product product = service.getProduct(new ProductId(1L));

            assertThat(product.getProductId().getId()).isEqualTo(1L);
            assertThat(product.getShortName()).isEqualTo("shortName");
            assertThat(product.getLongName()).isEqualTo("longName");
        }

        @Test @DisplayName("THEN: can retrieve all the Products")
        void test2()
        {
            givenAProductWithId(1L, "shortName", "longName");

            Collection<Product> products = service.getProducts(new PaginationCommand(1, 10));

            assertThat(products).hasSize(1);
        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    void givenAProductIdSequenceAt(long nextSequence)
    {
        given(memDao.generateUniqueProductId())
            .willReturn(nextSequence);
    }

    void givenAProductWithId(long id, String shortName, String longName)
    {
        ProductId productId = new ProductId(id);
        ProductName name    = new ProductName(shortName, longName);
        Product product     = new Product(productId, name);

        given(dao.loadProduct(productId))
            .willReturn(product);

        given(dao.loadProducts(any(Integer.class), any(Integer.class)))
            .willReturn(List.of(product));
    }

    List<Product> thenProductSaved(int numberOfProductsSaved)
    {
        ArgumentCaptor<Product> accountCaptor = ArgumentCaptor.forClass(Product.class);
        then(dao)
            .should(Mockito.times(numberOfProductsSaved))
            .saveNewProduct(accountCaptor.capture());

        return accountCaptor.getAllValues();
    }
}
