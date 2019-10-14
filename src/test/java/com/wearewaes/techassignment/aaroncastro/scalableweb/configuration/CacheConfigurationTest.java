package com.wearewaes.techassignment.aaroncastro.scalableweb.configuration;

import com.google.common.cache.Cache;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CacheConfigurationTest {

    @Test
    public void createConfigurationTest() {
        Cache<String, PersistenceModel> cache = new CacheConfiguration().cache(1, 1, 1);
        assertNotNull(cache, "cache must not be null");

        cache.put("test", TwoItemsContainer.newInstance("id", "test"));
        assertEquals(1, cache.size(), "cache size must be 1");
    }
}
