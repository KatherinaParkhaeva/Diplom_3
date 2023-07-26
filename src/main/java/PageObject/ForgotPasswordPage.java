package PageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ForgotPasswordPage implements BasePage {
    private WebDriver driver;
    //ссылка Войти
    private By loginLink = By.linkText("Войти");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    @Step("клик по ссылке \"Войти\"")
    public LoginPage transitionToLoginPage() {
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(loginLink)).click();
        return new LoginPage(driver);
    }
}