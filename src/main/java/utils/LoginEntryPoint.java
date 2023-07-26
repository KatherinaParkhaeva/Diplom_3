package utils;

import PageObject.*;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static config.Constants.*;

public class LoginEntryPoint {
    @Step("создание экземпляра нужного класс в зависимости от entryPoint")
    public static BasePage getEntryPointPage(String entryPoint, WebDriver driver) {
        switch (entryPoint) {
            case "Main":
                return new MainPage(driver);
            case "Header":
                return new HeaderPage(driver);
            case "Register":
                return new RegisterPage(driver);
            case "ForgotPassword":
                return new ForgotPasswordPage(driver);
            default: throw new RuntimeException(String.format("Точка входа с названием %s не найдена", entryPoint));
        }
    }
    @Step("получение URL в зависимости от entryPoint")
    public static String getEntryPointURL(String entryPoint) {
        switch (entryPoint) {
            case "Main":
            case "Header":
                return MAIN_PAGE_URL;
            case "Register":
                return REGISTER_PAGE_URL;
            case "ForgotPassword":
                return FORGOT_PASSWORD_PAGE_URL;
            default: throw new RuntimeException(String.format("Точка входа с названием %s не найдена", entryPoint));
        }
    }
}