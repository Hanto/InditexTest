package com.inditex.test.product.application;// Created by jhant on 05/06/2022.

import com.inditex.test.configuration.SpringBeans;
import com.inditex.test.product.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static com.inditex.test.product.domain.model.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SameParameterValue")
public class ProductServiceTest extends BDDMockito
{
    private final PersistenceDAO dao = mock(PersistenceDAO.class);
    private final MemoryDAO memDao = mock(MemoryDAO.class);
    private final ProductServiceI service = new SpringBeans().getProductService(dao, memDao);

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: the application service: ProductService is used")
    class Service
    {
        @Test @DisplayName("THEN: can create a new Product and save it")
        void test0()
        {
            givenAProductIdSequenceAt(1L);

            service.createProduct("short", "long");

            Product product = thenProductSaved(1).get(0);

            assertThat(product.getProductId().getId()).isEqualTo(1L);
            assertThat(product.getProductName().getShortName()).isEqualTo("short");
            assertThat(product.getProductName().getLongName()).isEqualTo("long");
            assertThat(product.getPrices().getPriceList()).hasSize(0);
        }

        @Test @DisplayName("THEN: can retrieve a Product")
        void test1()
        {
            givenAnAccountWithId(1L, "shortName", "longName");

            Product product = service.getProduct(1L);

            assertThat(product.getProductId().getId()).isEqualTo(1L);
            assertThat(product.getProductName().getShortName()).isEqualTo("shortName");
            assertThat(product.getProductName().getLongName()).isEqualTo("longName");
        }

        @Test @DisplayName("THEN: can retrieve all the Products")
        void test2()
        {
            givenAnAccountWithId(1L, "shortName", "longName");

            Collection<Product> products = service.getProducts(1, 10);

            assertThat(products).hasSize(1);
        }

        @Test @DisplayName("THEN: can modify a product")
        void test3()
        {
            givenAnAccountWithId(1L, "shortName", "longName");

            service.modifyShortName(1L, "newName");

            Product product = thenProductSaved(1).get(0);

            assertThat(product.getProductName().getShortName()).isEqualTo("newName");
        }

        @Test @DisplayName("THEN: can retrieve a Price")
        void test4()
        {
            givenAPriceeWithId(1L);

            Price price = service.getPrice(1L);

            assertThat(price.getPriceId().getId()).isEqualTo(1L);
            assertThat(price.getBrandId().getId()).isEqualTo(1L);
        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    void givenAProductIdSequenceAt(long nextSequence)
    {
        given(memDao.generateUniqueProductId())
            .willReturn(nextSequence);
    }

    void givenAnAccountWithId(long id, String shortName, String longName)
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
            .should(times(numberOfProductsSaved))
            .saveProduct(accountCaptor.capture());

        return accountCaptor.getAllValues();
    }

    void givenAPriceeWithId(long id)
    {
        PriceId priceId     = new PriceId(id);
        ProductId productId = new ProductId(1L);
        BrandId brandId     = new BrandId(1L);
        PriceListId priceLId= new PriceListId(1L);
        DateInterval dates  = new DateInterval(LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        int priority        = 0;
        Money money         = new Money(100.0f, EUR);
        Price price         = new Price(priceId, productId, brandId, priceLId, dates, priority, money);


        given(dao.loadPrice(priceId))
            .willReturn(price);
    }
}
