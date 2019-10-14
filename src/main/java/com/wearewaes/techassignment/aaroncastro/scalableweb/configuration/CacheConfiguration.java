package com.wearewaes.techassignment.aaroncastro.scalableweb.configuration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Creates the Cache instances to store the items to be compare (base on the id from the request) and the result of the
 * comparison operation (also base on the id from the request)
 */
@Configuration
public class CacheConfiguration {

    /**
     * Creates the cache persistence layer to keep the Items to be compare + the results of the comparison
     * @param cacheMaxSize cache max size
     * @param cacheInitialSize cache initial size
     * @param cacheExpirationInMin cache expiration time in minutes
     * @return a cache instance using the Google Guava Cache Instance
     */
    @Bean
    public Cache<String, PersistenceModel> cache(@Value("${cache.maxSize}") int cacheMaxSize,
                                                 @Value("${cache.initialSize}") int cacheInitialSize,
                                                 @Value("${cache.expirationInMin}") int cacheExpirationInMin) {
        return CacheBuilder
                .newBuilder()
                .maximumSize(cacheMaxSize * 3) // it's x 3 to keep the left, right and result instances
                .expireAfterWrite(cacheExpirationInMin, TimeUnit.MINUTES)
                .initialCapacity(cacheInitialSize)
                .build();
    }
}
