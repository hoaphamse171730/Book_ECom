package com.tbsd.crawler.task;

import com.tbsd.crawler.generator.BookGenerator;
import com.tbsd.crawler.generator.CategoryGenerator;

public class BookGenerateTask {
    public static void start() throws Exception {
        System.out.println("Generating category");
        System.out.println(CategoryGenerator.generate() + " categories generated");
        System.out.println("Generating book");
        System.out.println(BookGenerator.generate() + " books generated");
    }
}
