package com.example.playwrightjava.tests;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.LoggedInPage;
import pages.LoginPage;

import java.util.stream.Stream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

class LoginTests extends PlaywrightTestBase {

    @Test
    @DisplayName("Login with valid credentials")
    void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateTo();
        LoggedInPage loggedInPage = loginPage.doLogin("student", "Password123");
        assertThat(loggedInPage.getPage()).hasURL(loggedInPage.getEndpoint());
        assertThat(loggedInPage.getPostTitle()).hasText("Logged In Successfully");
        assertThat(loggedInPage.getPostContent()).hasText("Congratulations student. You successfully logged in!");
    }

    private static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of("Login with invalid password", "student", "wrongPassword", "Your password is invalid!"),
                Arguments.of("Login with invalid username","wrongUsername", "Password123", "Your username is invalid!"),
                Arguments.of("Login with invalid username and password","wrongUsername", "wrongPassword", "Your username is invalid!"),
                Arguments.of("Intentionally wrong test","student", "wrongPassword", "Intentionally wrong error message")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidUsers")
    void loginWithInvalidCredentials(String testName, String username, String password, String errorMessage) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateTo();
        loginPage.doLogin(username, password);
        assertThat(loginPage.getErrorMessage()).isVisible();
        assertThat(loginPage.getErrorMessage()).containsText(errorMessage);
    }
}
