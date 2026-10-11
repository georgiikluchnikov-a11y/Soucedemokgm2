package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private By cartItemName = By.className("inventory_item_name");
    private By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Получить название первого товара в корзине")
    public String getItemNameText() {
        return el(cartItemName).getText();
    }

    @Step("Нажать кнопку Checkout")
    public void clickCheckout() {
        click(checkoutButton);
    }
}