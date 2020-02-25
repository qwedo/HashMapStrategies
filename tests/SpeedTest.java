package com.javarush.task.Projects.HashMapStrategies.tests;

import com.javarush.task.Projects.HashMapStrategies.Helper;
import com.javarush.task.Projects.HashMapStrategies.Shortener;
import com.javarush.task.Projects.HashMapStrategies.strategy.HashBiMapStorageStrategy;
import com.javarush.task.Projects.HashMapStrategies.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SpeedTest {

    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids){
        Date startGetIds = new Date();
        ids = strings.stream()
                .map(shortener::getId)
                .collect(Collectors.toSet());
        Date endGetIds = new Date();
        return endGetIds.getTime() - startGetIds.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings){
        Date startGetIds = new Date();
        strings = ids.stream()
                .map(shortener::getString)
                .collect(Collectors.toSet());
        Date endGetIds = new Date();
        return endGetIds.getTime() - startGetIds.getTime();
    }

    @Test
    public void testHashMapStorage(){
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> ids = new HashSet<>();
        long timeToGetIds1 = getTimeToGetIds(shortener1, origStrings, ids);
        long timeToGetIds2 = getTimeToGetIds(shortener2, origStrings, ids);
        Assert.assertTrue (timeToGetIds1 > timeToGetIds2);

        Set<String> expStrings = new HashSet<>();
        long timeToGetStrings1 = getTimeToGetStrings(shortener1, ids, expStrings);
        long timeToGetStrings2 = getTimeToGetStrings(shortener2, ids, expStrings);
        Assert.assertEquals(timeToGetStrings1, timeToGetStrings2, 30);
    }
}
