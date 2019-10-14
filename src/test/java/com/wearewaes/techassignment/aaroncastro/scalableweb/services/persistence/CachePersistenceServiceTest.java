package com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CachePersistenceServiceTest {

    private final Cache<String, PersistenceModel> cache = CacheBuilder
            .newBuilder()
            .maximumSize(10) // it's x 3 to keep the left, right and result instances
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .initialCapacity(10)
            .build();

    private CachePersistenceService cachePersistenceService;

    @Before
    public void setUp() {
        cachePersistenceService = new CachePersistenceService(cache);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorOnPersistWithNullIdTest() {
        cachePersistenceService.set(null, TwoItemsContainer.newInstance("id", "body"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnPersistWithEmptyIdTest() {
        cachePersistenceService.set(" ", TwoItemsContainer.newInstance("id", "body"));
    }

    @Test(expected = NullPointerException.class)
    public void getErrorOnPersistWithNullBodyTest() {
        cachePersistenceService.set("id", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnPersistWithDuplicatedBodyTest() {
        cachePersistenceService.set("duplicated", TwoItemsContainer.newInstance("duplicated", "body"));
        cachePersistenceService.set("duplicated", TwoItemsContainer.newInstance("duplicated", "body"));
    }

    @Test
    public void persistValidInputTest() {
        cachePersistenceService.set("id-persist-test", TwoItemsContainer.newInstance("id-persist-test", "body"));

        assertNotNull(cache.getIfPresent("id-persist-test"));
    }

    @Test(expected = NullPointerException.class)
    public void getErrorOnRetrieveWithNullIdTest() {
        cachePersistenceService.get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnRetrieveWithEmptyIdTest() {
        cachePersistenceService.get(" ");
    }

    @Test
    public void getValueOnRetrieveWithValidIdTest() {
        cachePersistenceService.set("id-get-test", TwoItemsContainer.newInstance("id-get-test", "body"));
        Optional<PersistenceModel> model = cachePersistenceService.get("id-get-test");

        assertTrue(model.isPresent(), "optional model must contains the model");
        assertNotNull(model.get(), "model must not be null");
    }

    @Test(expected = NullPointerException.class)
    public void getErrorOnCheckIdWithNullIdTest() {
        cachePersistenceService.hasId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnCheckIdWithEmptyIdTest() {
        cachePersistenceService.hasId(" ");
    }

    @Test
    public void getFalseOnCheckIdWithTest() {
        boolean actual = cachePersistenceService.hasId("id-hasId-false-test");

        assertFalse(actual, "id should not be on the storage");
    }

    @Test
    public void getTrueOnCheckIdWithTest() {
        cachePersistenceService.set("id-hasId-true-test", TwoItemsContainer.newInstance("id-hasId-true-test", "body"));

        boolean actual = cachePersistenceService.hasId("id-hasId-true-test");

        assertTrue(actual, "id should be on the storage");
    }
}
