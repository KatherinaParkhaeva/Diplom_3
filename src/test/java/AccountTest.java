import PageObject.AccountPage;
import PageObject.HeaderPage;
import PageObject.LoginPage;
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

import static config.Constants.ACCOUNT_PAGE_URL;
import static config.Constants.LOGIN_PAGE_URL;
import static config.WebDriverCreator.createWebDriver;

public class AccountTest {
    private WebDriver driver;
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
        userClient.createUser(user);
        driver.get(LOGIN_PAGE_URL);
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.setLoginForm(UserCreds.credsFrom(user));
    }

    @Test
    @DisplayName("go to Account by account link")
    @Description("переход в личный кабинет по клику на «Личный кабинет»")
    public void goToAccountByAccountLinkTest() {
        String expectedText = "В этом разделе вы можете изменить свои персональные данные";
        String expectedURL = ACCOUNT_PAGE_URL;
        HeaderPage objHeaderPage = new HeaderPage(driver);
        objHeaderPage.clickAccountLink();
        AccountPage objAccountPage = new AccountPage(driver);
        String actualText = objAccountPage.getAccountText();
        Assert.assertEquals(String.format("Ожидаемый URL: %s", expectedURL),
                expectedURL,
                driver.getCurrentUrl());
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