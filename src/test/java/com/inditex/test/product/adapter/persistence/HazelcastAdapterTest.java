package com.inditex.test.product.adapter.persistence;// Created by jhant on 06/06/2022.

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest @ActiveProfiles("H2Hazel")
@Import({JpaH2Adapter.class, ProductMapper.class, PriceMapper.class, HazelcastAdapter.class, HazelcastConfig.class})
class HazelcastAdapterTest
{
    @Autowired private HazelcastAdapter adapter;
    private static HazelcastInstance hazelcast;

    // HAZELCAST INIT:
    //--------------------------------------------------------------------------------------------------------

    @BeforeAll
    static void beforeAll()
    {
        Config config = new Config("HazelcastTest");
        config.setProperty( "hazelcast.logging.type", "log4j2" );
        hazelcast = Hazelcast.newHazelcastInstance(config); }

    @AfterAll
    static void afterall()
    {   hazelcast.shutdown(); }

    // TESTS:
    //--------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: generating uniqueIDs")
    class IdGeneration
    {
        @Test @Sql("/test.sql") @DisplayName("THEN: productIds are generated consecutively")
        public void productIdSequenceTest()
        {
            List<Long>expectedResult = LongStream.iterate(1, i -> i +1).limit(1000).boxed().toList();
            List<Long> results = new ArrayList<>();

            for (int i=0; i<1000; i++)
                results.add(adapter.generateUniqueProductId());

            assertThat(results).hasSize(1000);
            assertThat(results).isEqualTo(expectedResult);
        }

        @Test @Sql("/test.sql") @DisplayName("THEN: priceIds are generated consecutively")
        public void priceIdSequenceTest()
        {
            List<Long>expectedResult = LongStream.iterate(1, i -> i +1).limit(1000).boxed().toList();
            List<Long> results = new ArrayList<>();

            for (int i=0; i<1000; i++)
                results.add(adapter.generateUniquePriceId());

            assertThat(results).hasSize(1000);
            assertThat(results).isEqualTo(expectedResult);
        }
    }
}