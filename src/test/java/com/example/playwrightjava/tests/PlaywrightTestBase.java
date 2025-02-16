package com.example.playwrightjava.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class PlaywrightTestBase {
    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void beforeAll() {
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
}