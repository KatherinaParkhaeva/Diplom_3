package PageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class HeaderPage implements BasePage {
    private WebDriver driver;
    //ссылка "Конструктор"
    private By constructorLink = By.linkText("Конструктор");
    //ссылка Лого
    private By logoLink = By.xpath(".//div[@class = 'AppHeader_header__logo__2D0X2']/a");
    //ссылка "Личный кабинет"
    private By accountLink = By.linkText("Личный Кабинет");

    public HeaderPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("клик по ссылке \"Конструктор\"")
    public MainPage clickConstructorLink() {
        driver.findElement(constructorLink).click();
        return new MainPage(driver);
    }

    @Step("клик по логотипу \"Stellar Burgers\"")
    public MainPage clickLogoLink() {
        driver.findElement(logoLink).click();
        return new MainPage(driver);
    }

    @Step("клик по ссылке \"Личный кабинет\"")
    public void clickAccountLink() {
        driver.findElement(accountLink).click();
    }

    @Override
    @Step("клик по ссылке \"Личный кабинет\"")
    public LoginPage transitionToLoginPage() {
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(accountLink)).click();
        return new LoginPage(driver);
    }
}