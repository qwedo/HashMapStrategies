package com.javarush.task.Projects.HashMapStrategies;

import com.javarush.task.Projects.HashMapStrategies.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        return strings.stream()
                .map(shortener::getId)
                .collect(Collectors.toSet());
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        return keys.stream()
                .map(shortener::getString)
                .collect(Collectors.toSet());
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> stringSet = new HashSet<>();
        for (int i = 0; i < elementsNumber; i++) {
            stringSet.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);
        Date startGetIds = new Date();
        Set<Long> getIdsResult = getIds(shortener, stringSet);
        Date endGetIds = new Date();
        long timeGetIds = endGetIds.getTime() - startGetIds.getTime();
        Helper.printMessage(String.valueOf(timeGetIds));

        Date startGetStrings = new Date();
        Set<String> getStringsResult = getStrings(shortener, getIdsResult);
        Date endGetStrings = new Date();
        long timeGetStrings = endGetStrings.getTime() - startGetStrings.getTime();
        Helper.printMessage(String.valueOf(timeGetStrings));

        if (stringSet.containsAll(getStringsResult) && getStringsResult.containsAll(stringSet))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");
    }

    public static void main(String[] args) {

        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);
    }
}
