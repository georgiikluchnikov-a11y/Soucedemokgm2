package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessageContainer = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу Saucedemo")
    public void open() {
        driver.get("https://www.saucedemo.com/");
    }

    @Step("Авторизоваться с логином: {username} и паролем: {password}")
    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    @Step("Получить текст ошибки при входе")
    public String getErrorMessageText() {
        return el(errorMessageContainer).getText();
    }
}