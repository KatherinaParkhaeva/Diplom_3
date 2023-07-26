import PageObject.HeaderPage;
import PageObject.MainPage;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static config.Constants.LOGIN_PAGE_URL;
import static config.Constants.MAIN_PAGE_URL;
import static config.WebDriverCreator.*;

public class HeaderLinksTest {
    private WebDriver driver;
    private HeaderPage objHeaderPage;
    private String expectedText;
    private String expectedURL;

    @Before
    public void setUp() {
        driver = createWebDriver();
        objHeaderPage = new HeaderPage(driver);
    }

    @Test
    @DisplayName("go to MainPage by constructor link")
    @Description("переход из личного кабинета в конструктор по клику на «Конструктор»")
    public void goToMainPageByConstructorLinkTest() {
        expectedText = "Соберите бургер";
        expectedURL = MAIN_PAGE_URL;
        driver.get(LOGIN_PAGE_URL);
        MainPage objMainPage = objHeaderPage.clickConstructorLink();
        String actualText = objMainPage.getBurgerIngredientsHeader();
        Assert.assertEquals(String.format("Ожидаемый URL: %s", expectedURL),
                expectedURL,
                driver.getCurrentUrl());
        Assert.assertEquals(String.format("Ожидаемый текст: %s", expectedText),
                expectedText,
                actualText);
    }

    @Test
    @DisplayName("go to MainPage by logo link")
    @Description("переход из личного кабинета в конструктор по клику на логотип «Stellar Burgers»")
    public void goToMainPageByLogoLinkTest() {
        expectedText = "Соберите бургер";
        expectedURL = MAIN_PAGE_URL;
        driver.get(LOGIN_PAGE_URL);
        MainPage objMainPage = objHeaderPage.clickLogoLink();
        String actualText = objMainPage.getBurgerIngredientsHeader();
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
    }

}