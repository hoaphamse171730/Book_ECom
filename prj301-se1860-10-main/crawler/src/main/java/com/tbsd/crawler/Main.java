package com.tbsd.crawler;

import com.tbsd.crawler.task.BookGenerateTask;
import com.tbsd.crawler.task.CrawlTask;
import com.tbsd.crawler.task.NormalizeTask;
import com.tbsd.crawler.task.UserGenerateTask;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static final File DATA_DIR = new File("data");

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) {
        DATA_DIR.mkdirs();

        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                showHelp();
                String line = scanner.nextLine().trim();
                switch (line) {
                    case "crawl":
                        CrawlTask.start();
                        break;
                    case "normalize":
                        NormalizeTask.start();
                        break;
                    case "genuser":
                        UserGenerateTask.start();
                        break;
                    case "genbook":
                        BookGenerateTask.start();
                        break;
                    case "quit":
                        System.exit(0);
                        break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void showHelp() {
        System.out.println("Usage:");
        System.out.println(" >  crawl: start or resume the work");
        System.out.println(" >  normalize: normalize the data");
        System.out.println(" >  genbook: generate category + book data");
        System.out.println(" >  genuser: generate user + order data");
        System.out.println(" >  quit: quit the CLI");
    }
}
