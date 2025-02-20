package com.example.playwrightjava.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;
import com.example.playwrightjava.utils.PlaywrightConfig;
import com.example.playwrightjava.utils.ScreenshotUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaywrightTestBase {
    static Playwright playwright;
    static Browser browser;
    static String timeStamp;
    static PlaywrightConfig config;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void beforeAll() {
        config = PlaywrightConfig.loadConfig("playwrightConfig.json");
        timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(config.isHeadless())
                .setSlowMo(config.getSlowmo());
        switch (config.getBrowserType()) {
            case "firefox" -> browser = playwright.firefox().launch(launchOptions);
            case "webkit" -> browser = playwright.webkit().launch(launchOptions);
            default -> browser = playwright.chromium().launch(launchOptions);
        }

    }

    @AfterAll
    static void afterAll() {
        playwright.close();
    }

    @BeforeEach
    void beforeEach() {
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setBaseURL(config.getBaseUrl())
                        .setViewportSize(config.getViewportWidth(), config.getViewportHeight()));
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
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

            String attachmentName = extensionContext.getDisplayName().replace(" ", "").toLowerCase();

            Path screenshotPath = timestampedDir.resolve(attachmentName + ".png");
            ScreenshotUtil.takeScreenshot(page, screenshotPath);

            Path tracePath = timestampedDir.resolve(attachmentName + ".zip");
            context.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        });
    };
}