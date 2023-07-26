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

import static config.Constants.LOGIN_PAGE_URL;
import static config.WebDriverCreator.createWebDriver;

public class LogoutTest {
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
        HeaderPage objHeaderPage = new HeaderPage(driver);
        objHeaderPage.clickAccountLink();
    }

    @Test
    @DisplayName("logout from Account page link")
    @Description("выход по кнопке «Выйти» в личном кабинете")
    public void logoutFromAccountPageTest() {
        String expectedURL = LOGIN_PAGE_URL;
        AccountPage objAccountPage = new AccountPage(driver);
        objAccountPage.clickLogoutButton();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.waitLoadingLoginPage();
        Assert.assertEquals(String.format("Ожидаемый URL: %s", expectedURL),
                expectedURL,
                driver.getCurrentUrl());
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