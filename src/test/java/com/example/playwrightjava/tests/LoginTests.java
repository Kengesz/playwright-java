package com.example.playwrightjava.tests;


import com.microsoft.playwright.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.LoggedInPage;
import pages.LoginPage;

import java.util.stream.Stream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

class LoginTests extends BaseTest {

    @Test
    @DisplayName("Login with valid credentials")
    void loginWithValidCredentials(Page page) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateTo();
        LoggedInPage loggedInPage = loginPage.doLogin("student", "Password123");
        assertThat(loggedInPage.getPage()).hasURL(loggedInPage.getEndpoint());
        assertThat(loggedInPage.getPostTitle()).hasText("Logged In Successfully");
        assertThat(loggedInPage.getPostContent()).hasText("Congratulations student. You successfully logged in!");
    }

    private static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of("student", "wrongPassword", "Your password is invalid!"),
                Arguments.of("wrongUsername", "Password123", "Your username is invalid!"),
                Arguments.of("wrongUsername", "wrongPassword", "Your username is invalid!"),
                Arguments.of("student", "wrongPassword", "Intentionally wrong error message")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidUsers")
    @DisplayName("Login with invalid credentials")
    void loginWithInvalidCredentials(String username, String password, String errorMessage, Page page) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateTo();
        loginPage.doLogin(username, password);
        assertThat(loginPage.getErrorMessage()).isVisible();
        assertThat(loginPage.getErrorMessage()).containsText(errorMessage);
    }
}
