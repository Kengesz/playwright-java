package com.example.playwrightjava.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlaywrightConfig {
    private String baseUrl = "https://practicetestautomation.com";
    private String browserType = "chromium";
    private boolean headless = true;
    private int viewportWidth = 1920;
    private int viewportHeight = 1080;
    private int slowmo = 0;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getBrowserType() {
        return browserType;
    }

    public boolean isHeadless() {
        return headless;
    }

    public int getViewportWidth() {
        return viewportWidth;
    }

    public int getViewportHeight() {
        return viewportHeight;
    }

    public int getSlowmo() {
        return slowmo;
    }

    public static PlaywrightConfig loadConfig(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        PlaywrightConfig playwrightConfig = new PlaywrightConfig();

        try {
            if (Files.exists(Path.of(filePath))) {
                playwrightConfig = objectMapper.readerForUpdating(playwrightConfig).readValue(new File(filePath));
                if (!(playwrightConfig.getBrowserType().equals("chromium")
                        || playwrightConfig.getBrowserType().equals("firefox")
                        || playwrightConfig.getBrowserType().equals("webkit"))) {
                    System.err.printf("Unknown browser type detected: %s%n", playwrightConfig.getBrowserType());
                    System.exit(1);
                }
            } else {
                System.out.println("Cannot found 'playwrightConfig.json' configuration file. Using default values.");
            }
        } catch (IOException e) {
            System.err.println("Error reading configuration file" + e.getMessage());
        }

        System.out.println("Playwright configuration:");
        System.out.println("Base URL: " + playwrightConfig.getBaseUrl());
        System.out.println("Browser type: " + playwrightConfig.getBrowserType());
        System.out.println("Headless: " + playwrightConfig.isHeadless());
        System.out.println("Viewport width: " + playwrightConfig.getViewportWidth());
        System.out.println("Viewport height: " + playwrightConfig.getViewportHeight());
        System.out.println("Slowmo: " + playwrightConfig.getSlowmo());

        return playwrightConfig;
    }
}
