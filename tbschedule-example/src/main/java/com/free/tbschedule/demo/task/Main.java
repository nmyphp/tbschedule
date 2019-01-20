package com.free.tbschedule.demo.task;

import java.io.IOException;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws IOException {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("classpath:schedule.xml");
        context.start();
        System.out.println("Tbschedule is started!");
        System.in.read();
    }
}
