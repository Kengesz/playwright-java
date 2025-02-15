package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoggedInPage extends BasePage {

    private final String endpoint;
    private final Locator postTitle;
    private final Locator postContent;

    public LoggedInPage(Page page) {
        super(page);
        this.endpoint = "/logged-in-successfully/";
        this.postTitle = page.locator("//h1[@class='post-title']");
        this.postContent = page.locator("//div/p/strong");
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Locator getPostTitle() {
        return postTitle;
    }

    public Locator getPostContent() {
        return postContent;
    }
}
