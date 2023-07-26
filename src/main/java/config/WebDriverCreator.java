package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverCreator {

    //Переменные окружения, прописанные в системе:
    //путь к папке с драйверами для браузеров
    public static final String BROWSER_DRIVERS = "src/test/resources";
    //имя файла драйвера Яндекс браузера
    public static final String YANDEX_BROWSER_DRIVER_FILENAME = "yandexdriver.exe";
    //путь к исполняемому файлу Яндекс браузера в системе
    public static final String YANDEX_BROWSER_PATH = "C:\\Program Files (x86)\\WebDrivers\\bin";

    public static WebDriver createWebDriver() {
        String browser = System.getProperty("browser");
        if (browser == null) {
            return createChromeDriver();
        }

        switch (browser) {
            case "yandex":
                return createYandexDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }

    public static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        return new ChromeDriver(options);
    }

    public static WebDriver createYandexDriver() {
        System.setProperty("webdriver.chrome.driver", String.format("%s/%s", BROWSER_DRIVERS, YANDEX_BROWSER_DRIVER_FILENAME));
        ChromeOptions options = new ChromeOptions();
        options.setBinary(YANDEX_BROWSER_PATH);
        return new ChromeDriver(options);
    }
}