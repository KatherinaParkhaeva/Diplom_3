import PageObject.BasePage;
import PageObject.LoginPage;
import PageObject.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import utils.*;

import static config.WebDriverCreator.createWebDriver;

@RunWith(Parameterized.class)
public class LoginTest {
    private WebDriver driver;
    private User user;
    private final UserClient userClient = new UserClient();
    private UserCreds userCreds;
    private final RandomUserGenerator userGenerator = new RandomUserGenerator();
    private String accessToken;
    private final String entryPoint;

    public LoginTest(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Parameterized.Parameters(name = "Точка входа: кнопка на странице {0}")
    public static Object[][] getEntryPoint() {
        return new Object[][]{
                {"Main"}, //1. вход по кнопке «Войти в аккаунт» на главной,
                {"Header"}, //2. вход через кнопку «Личный кабинет»,
                {"Register"}, //3. вход через кнопку в форме регистрации,
                {"ForgotPassword"}, //4. вход через кнопку в форме восстановления пароля.
        };
    }

    @Before
    public void setUp() {
        driver = createWebDriver();
        user = new User()
                .setEmail(userGenerator.getEmail())
                .setPassword(userGenerator.getPassword())
                .setName(userGenerator.getName());
        userClient.createUser(user);
    }

    @Test
    @DisplayName("login from different entry points")
    @Description("вход из разных точек входа")
    public void loginFromDifferentEntryPointsTest() {
        String expectedText = "Оформить заказ";
        BasePage objEntryPage = LoginEntryPoint.getEntryPointPage(entryPoint, driver);
        driver.get(LoginEntryPoint.getEntryPointURL(entryPoint));
        objEntryPage.transitionToLoginPage();
        LoginPage objLoginPage = new LoginPage(driver);
        MainPage objMainPage = objLoginPage.setLoginForm(UserCreds.credsFrom(user));
        String actualText = objMainPage.getOrderButtonText();
        Assert.assertEquals(String.format("Ожидаемый текст: %s", expectedText),
                expectedText,
                actualText);
    }

    @After
    public void teardown() {
        driver.quit();
        ValidatableResponse loginResponse = userClient.loginUser(UserCreds.credsFrom(user));
        if (loginResponse.extract().statusCode() == 200) {
            String accessTokenBearer = loginResponse.extract().path("accessToken");
            String accessToken = accessTokenBearer.split(" ")[1];
            userClient.deleteUser(accessToken);
        }
    }

}