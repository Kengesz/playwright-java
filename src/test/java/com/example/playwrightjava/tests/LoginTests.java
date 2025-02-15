package com.example.playwrightjava.tests;


import com.microsoft.playwright.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

class LoginTests extends BaseTest {

    @Test
    @DisplayName("Login with valid credentials")
    void loginWithValidCredentials(Page page) {
        page.navigate("/practice-test-login/");
        page.locator("//input[@id='username']").fill("student");
        page.locator("//input[@id='password']").fill("Password123");
        page.locator("//button[@id='submit']").click();
        assertThat(page).hasURL("/logged-in-successfully/");
        assertThat(page.locator("//div/p/strong"))
                .containsText("Congratulations student. You successfully logged in!");
    }

    private static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of("student", "wrongPassword", "Your password is invalid!"),
                Arguments.of("wrongUsername", "Password123", "Your username is invalid!"),
                Arguments.of("wrongUsername", "wrongPassword", "Your username is invalid!")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidUsers")
    @DisplayName("Login with invalid credentials")
    void loginWithInvalidCredentials(String username, String password, String errorMessage, Page page) {
        page.navigate("/practice-test-login/");
        page.locator("//input[@id='username']").fill(username);
        page.locator("//input[@id='password']").fill(password);
        page.locator("//button[@id='submit']").click();
        assertThat(page.locator("//div[@id='error' and @class='show']")).isVisible();
        assertThat(page.locator("//div[@id='error' and @class='show']")).containsText(errorMessage);
    }
}
