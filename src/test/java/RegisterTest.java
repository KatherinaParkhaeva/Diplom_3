import PageObject.LoginPage;
import PageObject.RegisterPage;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import utils.RandomUserGenerator;
import utils.User;
import utils.UserClient;
import utils.UserCreds;

import static config.Constants.LOGIN_PAGE_URL;
import static config.Constants.REGISTER_PAGE_URL;
import static config.WebDriverCreator.createWebDriver;

public class RegisterTest {
    private WebDriver driver;
    private RegisterPage objRegPage;
    private User user;
    private UserClient userClient = new UserClient();
    private UserCreds userCreds;
    private RandomUserGenerator userGenerator = new RandomUserGenerator();

    @Before
    public void setUp() {
        driver = createWebDriver();
        user = new User()
                .setEmail(userGenerator.getEmail())
                .setPassword(userGenerator.getPassword())
                .setName(userGenerator.getName());
        driver.get(REGISTER_PAGE_URL);
        objRegPage = new RegisterPage(driver);
    }

    @Test
    @DisplayName("success registration test")
    @Description("проверка успешной регистрации пользователя")
    public void successRegistrationTest() {
        String expectedURL = LOGIN_PAGE_URL;
        String expectedText = "Вход";
        LoginPage objLoginPage = objRegPage.setRegistrationForm(user);
        String actualText = objLoginPage.getLoginTitle();
        Assert.assertEquals(String.format("Ожидаемый текст: %s", expectedText),
                expectedText,
                actualText);
        Assert.assertEquals(String.format("Ожидаемый URL: %s", expectedURL),
                expectedURL,
                driver.getCurrentUrl());
    }

    @Test
    @DisplayName("registration with incorrect password test")
    @Description("проверка регистрации с некорректным паролем")
    public void registrationWithIncorrectPasswordTest() {
        String expectedText = "Некорректный пароль";
        String incorrectPassword = user.getPassword().substring(0,5);
        user = user.setPassword(incorrectPassword);
        objRegPage.setRegistrationForm(user);
        String actualText = objRegPage.getPasswordErrorText();
        Assert.assertEquals(String.format("Ожидаемый текст: %s", expectedText),
                expectedText,
                actualText);
    }

    @After
    public void teardown() {
        driver.quit();
        ValidatableResponse loginResponse = userClient.loginUser(userCreds.credsFrom(user));
        if (loginResponse.extract().statusCode() == 200) {
            String accessTokenBearer = loginResponse.extract().path("accessToken");
            String accessToken = accessTokenBearer.split(" ")[1];
            userClient.deleteUser(accessToken);
        }
    }
}