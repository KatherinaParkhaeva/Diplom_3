package PageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.UserCreds;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LoginPage {
    private WebDriver driver;
    //заголовок "Вход"
    private By loginTitle = By.xpath(".//h2[text()= 'Вход']");
    //поле Email
    private By emailInput = By.xpath(".//label[text()= 'Email']/parent::div/input");
    //поле Пароль
    private By passwordInput = By.xpath(".//label[text()= 'Пароль']/parent::div/input");
    //кнопка "Войти"
    private By loginButton = By.xpath(".//button[text()= 'Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("ожидание видимости и получение заголовка \"Вход\"")
    public String getLoginTitle() {
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(loginTitle)).isDisplayed();
        return driver.findElement(loginTitle).getText();
    }

    @Step("ожидание загрузки формы логина")
    public void waitLoadingLoginPage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginTitle)).isDisplayed();
    }

    @Step("заполнение поля Email")
    public void setEmailInput(String userEmail) {
        driver.findElement(emailInput).sendKeys(userEmail);
    }

    @Step("заполнение поля Password")
    public void setPasswordInput(String userPassword) {
        driver.findElement(passwordInput).sendKeys(userPassword);
    }

    @Step("клик по кнопке Войти")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("заполнение и отправка формы логина")
    public MainPage setLoginForm(UserCreds userCreds) {
        waitLoadingLoginPage();
        setEmailInput(userCreds.getEmail());
        setPasswordInput(userCreds.getPassword());
        try {
            clickLoginButton();
        } catch (Exception e){
            System.out.println("Некорректно заполнены поля формы");
            throw e;
        }
        return new MainPage(driver);
    }

}