package PageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.User;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class RegisterPage implements BasePage {
    private final WebDriver driver;
    //заголовок формы регистрации
    private final By registerTitle = By.xpath(".//h2[text()= 'Регистрация']");
    //поле Имя
    private final By nameInput = By.xpath(".//label[text()= 'Имя']/parent::div/input");
    //поле Email
    private final By emailInput = By.xpath(".//label[text()= 'Email']/parent::div/input");
    //поле Пароль
    private final By passwordInput = By.xpath(".//label[text()= 'Пароль']/parent::div/input");
    //ошибка ввода Пароля "Некорректный пароль"
    private final By passwordErrorText = By.xpath(".//p[text()= 'Некорректный пароль']");
    //кнопка "Зарегистрироваться"
    private final By registerButton = By.xpath(".//button[text()= 'Зарегистрироваться']");
    //ссылка "Войти"
    private final By loginLink = By.linkText("Войти");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("ожидание загрузки формы регистрации")
    public void waitLoadingRegisterPage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(registerTitle)).isDisplayed();
    }

    @Step("заполнение поля Имя")
    public void setNameInput(String userName) {
        driver.findElement(nameInput).sendKeys(userName);
    }

    @Step("заполнение поля Email")
    public void setEmailInput(String userEmail) {
        driver.findElement(emailInput).sendKeys(userEmail);
    }

    @Step("заполнение поля Password")
    public void setPasswordInput(String userPassword) {
        driver.findElement(passwordInput).sendKeys(userPassword);
    }

    @Step("клик по кнопке Зарегистрироваться")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("заполнение и отправка формы регистрации")
    public LoginPage setRegistrationForm(User user) {
        waitLoadingRegisterPage();
        setNameInput(user.getName());
        setEmailInput(user.getEmail());
        setPasswordInput(user.getPassword());
        try {
            clickRegisterButton();
        } catch (Exception e) {
            System.out.println("Некорректно заполнены поля формы");
            throw e;
        }
        return new LoginPage(driver);
    }

    @Step("ожидание видимости и получение текста ошибки \"Некорректный пароль\"")
    public String getPasswordErrorText() {
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(passwordErrorText)).isDisplayed();
        return driver.findElement(passwordErrorText).getText();
    }

    @Override
    @Step("клик по ссылке \"Войти\"")
    public LoginPage transitionToLoginPage() {
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(loginLink)).click();
        return new LoginPage(driver);
    }
}