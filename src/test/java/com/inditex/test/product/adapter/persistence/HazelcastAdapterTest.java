package com.inditex.test.product.adapter.persistence;// Created by jhant on 06/06/2022.

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.inditex.test.common.Try;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collection;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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

    @Nested @DisplayName("WHEN: generating uniqueIDs concurrently")
    class ConcurrentIdGeneration
    {
        @Test @Sql("/test.sql") @DisplayName("THEN: productIds are unique")
        public void productIdSequenceTest()
        {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Queue<Future<Long>> generated = new ConcurrentLinkedDeque<>();

            for (int i=0; i<100; i++)
            {
                generated.add(executor.submit(() -> adapter.generateUniqueProductId()));
                generated.add(CompletableFuture.completedFuture(adapter.generateUniqueProductId()));
            }

            Set<Long> notDuplicated = toNotDuplicatedSet(generated);

            assertThat(generated).hasSameSizeAs(notDuplicated);
        }

        @Test @Sql("/test.sql") @DisplayName("THEN: priceIds are unique")
        public void priceIdSequenceTest()
        {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Queue<Future<Long>> generated = new ConcurrentLinkedDeque<>();

            for (int i=0; i<100; i++)
            {
                generated.add(executor.submit(() -> adapter.generateUniquePriceId()));
                generated.add(CompletableFuture.completedFuture(adapter.generateUniquePriceId()));
            }

            Set<Long> notDuplicated = toNotDuplicatedSet(generated);

            assertThat(generated).hasSameSizeAs(notDuplicated);
        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private <T>Set<T> toNotDuplicatedSet(Collection<Future<T>>collection)
    {
        return collection.stream()
            .map(Try.function(Future::get))
            .map(Try::getSuccess)
            .collect(Collectors.toSet());
    }
}