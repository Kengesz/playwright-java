package com.example.playwrightjava.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;

@UsePlaywright(BaseTest.CustomOptions.class)
public class BaseTest {

    public static class CustomOptions implements OptionsFactory {
        @Override
        public Options getOptions() {
            return new Options()
                    .setHeadless(false)
                    .setContextOptions(
                            new Browser.NewContextOptions()
                                    .setBaseURL("https://new.aldi.us/")
                                    .setViewportSize(1920, 1080)
                    );
        }
    }
}
