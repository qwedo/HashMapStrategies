package com.javarush.task.Projects.HashMapStrategies;

public class ExceptionHandler {
    public static void log(Exception e){
        Helper.printMessage(e.getLocalizedMessage());
    }
}
