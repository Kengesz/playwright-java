package utils;

import com.microsoft.playwright.Page;

import java.nio.file.Path;

public class ScreenshotUtil {

    private ScreenshotUtil() {}

    public static void takeScreenshot(Page page, Path path) {
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(path));
    }
}
