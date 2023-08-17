package PageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AccountPage {
    private final WebDriver driver;
    //кнопка "Выход"
    private final By logoutButton = By.xpath(".//button[text() = 'Выход']");
    //текст "В этом разделе вы можете изменить свои персональные данные"
    private final By accountText = By.xpath(".//p[text() = 'В этом разделе вы можете изменить свои персональные данные']");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("ожидание видимости и получение текста \"В этом разделе вы можете изменить свои персональные данные\"")
    public String getAccountText() {
        new WebDriverWait(driver, 10)
                .until(visibilityOfElementLocated(accountText)).isDisplayed();
        return driver.findElement(accountText).getText();
    }

    @Step("ожидание загрузки формы логина")
    public void waitLoadingAccountPage() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(accountText)).isDisplayed();
    }

    @Step("клик по кнопке Выйти")
    public void clickLogoutButton() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).click();
    }
}