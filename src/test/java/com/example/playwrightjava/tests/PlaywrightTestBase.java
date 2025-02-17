package com.example.playwrightjava.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;
import utils.ScreenshotUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaywrightTestBase {
    static Playwright playwright;
    static Browser browser;
    static String timeStamp;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void beforeAll() {
        timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(150));
    }

    @AfterAll
    static void afterAll() {
        playwright.close();
    }

    @BeforeEach
    void beforeEach() {
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setBaseURL("https://practicetestautomation.com")
                        .setViewportSize(1920, 1080));
        page = context.newPage();
    }

    @AfterEach
    void afterEach() {
        context.close();
    }


    @RegisterExtension
    private final AfterTestExecutionCallback afterTest = extensionContext -> {
        extensionContext.getExecutionException().ifPresent(exception -> {
            Path timestampedDir = Paths.get("attachments", timeStamp);
            timestampedDir.toFile().mkdirs();

            String screenshotName = extensionContext.getDisplayName().replace(" ", "").toLowerCase() + ".png";
            Path screenshotPath = timestampedDir.resolve(screenshotName);

            ScreenshotUtil.takeScreenshot(page, screenshotPath);
        });
    };
}