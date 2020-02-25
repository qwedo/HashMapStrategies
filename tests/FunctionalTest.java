package com.javarush.task.Projects.HashMapStrategies.tests;

import com.javarush.task.Projects.HashMapStrategies.Shortener;
import com.javarush.task.Projects.HashMapStrategies.strategy.*;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class FunctionalTest {

    public void testStorage(Shortener shortener){
        String s1Expected = "aaa1";
        String s2Expected = "bbb2";
        String s3Expected = "aaa1";

        Long id1 = shortener.getId(s1Expected);
        Long id2 = shortener.getId(s2Expected);
        Long id3 = shortener.getId(s3Expected);
        Assert.assertNotEquals(id1, id2);
        Assert.assertNotEquals(id3, id2);
        Assert.assertEquals(id1, id3);

        String s1Actual = shortener.getString(id1);
        String s2Actual = shortener.getString(id2);
        String s3Actual = shortener.getString(id3);
        Assert.assertEquals(s1Expected, s1Actual);
        Assert.assertEquals(s2Expected, s2Actual);
        Assert.assertEquals(s3Expected, s3Actual);
    }

    @Test
    public void testHashMapStorageStrategy(){
        StorageStrategy storageStrategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy(){
        StorageStrategy storageStrategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy(){
        StorageStrategy storageStrategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy(){
        StorageStrategy storageStrategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy(){
        StorageStrategy storageStrategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy(){
        StorageStrategy storageStrategy = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
}
