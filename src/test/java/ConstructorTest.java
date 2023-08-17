import PageObject.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static config.Constants.MAIN_PAGE_URL;
import static config.WebDriverCreator.createWebDriver;

@RunWith(Parameterized.class)
public class ConstructorTest {
    private WebDriver driver;
    private MainPage objMainPage;
    private final String expectedSectionName;
    private final boolean isSectionDisplayed;

    public ConstructorTest(String expectedSectionName, boolean isSectionDisplayed) {
        this.expectedSectionName = expectedSectionName;
        this.isSectionDisplayed = isSectionDisplayed;
    }

    @Parameterized.Parameters(name = "Название раздела {0}, видимость {1}")
    public static Object[][] getSectionName() {
        return new Object[][]{
                {"Булки", true},
                {"Соусы", true},
                {"Начинки", true},
        };
    }

    @Before
    public void setUp() {
        driver = createWebDriver();
        driver.get(MAIN_PAGE_URL);
        objMainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("go to ingredient type section of constructor")
    @Description("переход в конструкторе к разделам: «Булки», «Соусы», «Начинки»")
    public void goToIngredientTypeSectionTest() {
        List<By> section = objMainPage.getSectionSelectors(expectedSectionName);
        objMainPage.clickSectionButton(section);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assert.assertTrue(driver.findElement(section.get(0)).getAttribute("class").contains("current"));
        Assert.assertTrue(String.format("Раздел %s отображается: %b", expectedSectionName, isSectionDisplayed),
                objMainPage.isSectionTitleDisplayed(section));
    }


    @After
    public void teardown() {
        driver.quit();
    }
}