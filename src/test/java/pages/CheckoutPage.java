package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private By firstNameInput = By.id("first-name");
    private By lastNameInput = By.id("last-name");
    private By postalCodeInput = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By finishButton = By.id("finish");
    private By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнить информацию о покупателе: {firstName} {lastName}, Индекс: {postalCode}")
    public void fillInformation(String firstName, String lastName, String postalCode) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
        click(continueButton);
    }

    @Step("Завершить заказ (нажать Finish)")
    public void clickFinish() {
        click(finishButton);
    }

    @Step("Получить итоговый заголовок подтверждения заказа")
    public String getCompleteHeaderText() {
        return el(completeHeader).getText();
    }
}