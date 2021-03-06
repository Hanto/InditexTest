package com.inditex.test;// Created by jhant on 06/06/2022.

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.RestAssured;
import lombok.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SameParameterValue")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class InditexMainTest
{
    @Nested @DisplayName("WHEN: performing end to end tests")
    class EndToEnd
    {
        @Test @DisplayName("THEN: return price between the time interval with the highest priority")
        public void endToEnd()
        {
            PriceDTO dto = retrievePriceFor(35455L, 1L, LocalDateTime.parse("2020-06-14T10:00:00"));

            assertThat(dto.getBrandId()).isEqualTo(1L);
            assertThat(dto.getPriceId()).isEqualTo(1L);

            dto = retrievePriceFor(35455L, 1L, LocalDateTime.parse("2020-06-14T16:00:00"));

            assertThat(dto.getBrandId()).isEqualTo(1L);
            assertThat(dto.getPriceId()).isEqualTo(2L);

            dto = retrievePriceFor(35455L, 1L, LocalDateTime.parse("2020-06-14T21:00:00"));

            assertThat(dto.getBrandId()).isEqualTo(1L);
            assertThat(dto.getPriceId()).isEqualTo(1L);

            dto = retrievePriceFor(35455L, 1L, LocalDateTime.parse("2020-06-15T10:00:00"));

            assertThat(dto.getBrandId()).isEqualTo(1L);
            assertThat(dto.getPriceId()).isEqualTo(3L);

            dto = retrievePriceFor(35455L, 1L, LocalDateTime.parse("2020-06-16T21:00:00"));

            assertThat(dto.getBrandId()).isEqualTo(1L);
            assertThat(dto.getPriceId()).isEqualTo(4L);
        }

        @Test @DisplayName("THEN: prices can be modified concurrently")
        public void concurrentTest() throws InterruptedException
        {
            ExecutorService executor = Executors.newCachedThreadPool();

            for (int i=0; i<10; i++)
                executor.submit(InditexMainTest.this::modifyPrice);

            Thread.sleep(2000); //give it some time to process all the requests

            PriceDTO dto = retrievePriceFor(35455L, 1L, LocalDateTime.parse("2020-06-14T10:00:00"));

            assertThat(dto.getPrice()).isEqualByComparingTo("135.50");
        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private void modifyPrice()
    {
        RestAssured
            .given()
                .contentType("application/json")
            .when()
                .basePath("/api/price/{productId}/{priceId}/{amount}/{currency}")
                .pathParam("productId", 35455L)
                .pathParam("priceId", 1L)
                .pathParam("amount", 10f)
                .pathParam("currency", "EUR")
                .log().all()
            .patch()
            .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK);
    }

    private PriceDTO retrievePriceFor(Long productId, Long brandId, LocalDateTime date)
    {
        return RestAssured
            .given()
                .contentType("application/json")
            .when()
                .basePath("/api/price/{productId}/{brandId}/{dateTime}")
                .pathParam("productId", productId)
                .pathParam("brandId", brandId)
                .pathParam("dateTime", date.toString())
                .log().all()
            .get()
            .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getObject(".", PriceDTO.class);
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    @Setter @Getter @EqualsAndHashCode(callSuper = false) @NoArgsConstructor @AllArgsConstructor @Builder
    static class PriceDTO extends RepresentationModel<PriceDTO>
    {
        private long priceId;
        private long productId;
        private long brandId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int priority;
        private String currency;
        private BigDecimal price;

        @JsonProperty("_links") @SuppressWarnings("unused")
        public void setLinks(final Map<String, Link> links)
        {   links.forEach((label, link) ->  add(link.withRel(label)) ); }
    }
}