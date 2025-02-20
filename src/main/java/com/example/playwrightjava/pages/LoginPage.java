package com.example.playwrightjava.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

    private final String endpoint;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator submitButton;
    private final Locator errorMessage;


    public LoginPage(Page page) {
        super(page);
        this.endpoint = "/practice-test-login/";
        this.usernameInput = page.locator("//input[@id='username']");
        this.passwordInput = page.locator("//input[@id='password']");
        this.submitButton = page.locator("//button[@id='submit']");
        this.errorMessage = page.locator("//div[@id='error' and @class='show']");
    }

    public void navigateTo() {
        page.navigate(endpoint);
    }

    public Locator getErrorMessage() {
        return errorMessage;
    }

    private LoginPage enterUsername(String username) {
        usernameInput.fill(username);
        return this;
    }

    private LoginPage enterPassword(String password) {
        passwordInput.fill(password);
        return this;
    }

    private LoginPage clickSubmit() {
        submitButton.click();
        return this;
    }

    public LoggedInPage doLogin(String username, String password) {
        enterUsername(username)
                .enterPassword(password)
                .clickSubmit();
        return new LoggedInPage(page);
    }
}
