package com.tbsd.crawler.task;

import com.tbsd.crawler.generator.UserGenerator;

public class UserGenerateTask {
    public static void start() {
        System.out.println("Generating user");
        System.out.println(UserGenerator.generate() + " users generated");
    }
}
