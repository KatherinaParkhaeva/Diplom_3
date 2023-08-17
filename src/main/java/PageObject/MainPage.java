package PageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MainPage implements BasePage {
    private final WebDriver driver;
    //заголовок секции "Собери бургер"
    private final By burgerIngredientsHeader = By.xpath(".//h1[text()= 'Соберите бургер']");
    //вкладка "Булки"
    private final By bunsButton = By.xpath(".//span[text()= 'Булки']/parent::div");
    //вкладка "Соусы"
    private final By souseButton = By.xpath(".//span[text()= 'Соусы']/parent::div");
    //вкладка "Начинки"
    private final By toppingButton = By.xpath(".//span[text()= 'Начинки']/parent::div");
    //заголовок раздела "Булки"
    private final By bunsTitle = By.xpath(".//h2[text()= 'Булки']");
    //заголовок раздела "Соусы"
    private final By souseTitle = By.xpath(".//h2[text()= 'Соусы']");
    //заголовок раздела "Начинки"
    private final By toppingTitle = By.xpath(".//h2[text()= 'Начинки']");
    //ссылка на ингредиенты
    private final By ingredientItem = By.xpath(".//ul['BurgerIngredients_ingredients__list__2A-mT']/a");
    //кнопка Войти в аккаунт
    private final By loginButton = By.xpath(".//button[text()= 'Войти в аккаунт']");
    //кнопка Оформить заказ
    private final By orderButton = By.xpath(".//button[text()= 'Оформить заказ']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("ожидание видимости и получение заголовка секции \"Соберите бургер\"")
    public String getBurgerIngredientsHeader() {
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(burgerIngredientsHeader)).isDisplayed();
        return driver.findElement(burgerIngredientsHeader).getText();
    }

    @Step("получение локаторов кнопки и заголовка для раздела sectionName")
    public List<By> getSectionSelectors(String sectionName) {
        By sectionButton;
        By sectionTitle;
        switch (sectionName) {
            case "Булки":
                sectionButton = bunsButton;
                sectionTitle = bunsTitle;
                break;
            case "Соусы":
                sectionButton = souseButton;
                sectionTitle = souseTitle;
                break;
            case "Начинки":
                sectionButton = toppingButton;
                sectionTitle = toppingTitle;
                break;
            default:
                throw new RuntimeException(String.format("Раздел с названием %s не найден", sectionName));
        }
        return List.of(sectionButton, sectionTitle);
    }

    @Step("поиск кнопки sectionName и переход в раздел")
    public void clickSectionButton(List<By> section) {
        By sectionButton = section.get(0);
        try {
            driver.findElement(sectionButton).click();
        } catch (Exception e) {
            scrollDown();
            driver.findElement(sectionButton).click();
        }
    }

    @Step("ожидание видимости заголовка раздела sectionName")
    public boolean isSectionTitleDisplayed(List<By> section) {
        By sectionTitle = section.get(1);
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(sectionTitle)).isDisplayed();
        return driver.findElement(sectionTitle).isDisplayed();
    }

    @Step("скрол до конца секции \"Соберите бургер\"")
    public void scrollDown() {
        int lastIndex = driver.findElements(ingredientItem).size() - 1;
        new Actions(driver).moveToElement(driver.findElements(ingredientItem).get(lastIndex)).perform();
    }

    @Step("ожидание видимости и получение текста кнопки \"Оформить заказ\"")
    public String getOrderButtonText() {
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(orderButton)).isDisplayed();
        return driver.findElement(orderButton).getText();
    }

    @Override
    @Step("клик по кнопке \"Войти в аккаунт\"")
    public LoginPage transitionToLoginPage() {
        new WebDriverWait(driver, 3)
                .until(visibilityOfElementLocated(loginButton)).click();
        return new LoginPage(driver);
    }


}