package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.AllureAttachments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class BaseTest {
    protected WebDriver driver;

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, @Optional("false") String headless) {
        boolean isHeadless = Boolean.parseBoolean(headless);

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fo = new FirefoxOptions();
                if (isHeadless) fo.addArguments("-headless");
                driver = new FirefoxDriver(fo);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions eo = new EdgeOptions();
                if (isHeadless) eo.addArguments("--headless=new");
                driver = new EdgeDriver(eo);
                break;
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                co.addArguments("--remote-allow-origins=*");
                co.addArguments("--log-level=3");
                if (isHeadless) co.addArguments("--headless=new");
                driver = new ChromeDriver(co);
                break;
        }

        driver.manage().window().maximize();
        generateEnvironmentProperties(browser);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            if (!result.isSuccess()) {
                AllureAttachments.screenshot(driver, "Скриншот ошибки");
                AllureAttachments.pageSource(driver, "Исходный код страницы ошибки");
            }
            driver.quit();
        }
    }

    private void generateEnvironmentProperties(String browser) {
        File resultsDir = new File("target/allure-results");
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }
        File envFile = new File(resultsDir, "environment.properties");
        try (FileWriter writer = new FileWriter(envFile)) {
            writer.write("OS=" + System.getProperty("os.name") + "\n");
            writer.write("JDK.Version=" + System.getProperty("java.version") + "\n");
            writer.write("Browser=" + browser.toUpperCase() + "\n");
            writer.write("Selenium.Version=4.28.0\n");
            writer.write("Project=Soucedemokgm2\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}