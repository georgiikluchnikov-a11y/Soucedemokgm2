package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    private By titleElement = By.className("title");
    private By addToCartBackpackButton = By.id("add-to-cart-sauce-labs-backpack");
    private By removeBackpackButton = By.id("remove-sauce-labs-backpack");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");
    private By sortDropdown = By.className("product_sort_container");
    private By itemNameList = By.className("inventory_item_name");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Получить заголовок страницы каталога")
    public String getTitleText() {
        return el(titleElement).getText();
    }

    @Step("Добавить рюкзак (Sauce Labs Backpack) в корзину")
    public void addBackpackToCart() {
        click(addToCartBackpackButton);
    }

    @Step("Удалить рюкзак из корзины со страницы каталога")
    public void removeBackpackFromCart() {
        click(removeBackpackButton);
    }

    @Step("Получить значение счетчика корзины")
    public String getCartBadgeText() {
        return el(cartBadge).getText();
    }

    @Step("Проверить, пуста ли корзина (отсутствует счетчик)")
    public boolean isCartBadgePresent() {
        return driver.findElements(cartBadge).size() > 0;
    }

    @Step("Перейти в корзину")
    public void openCart() {
        click(cartLink);
    }

    @Step("Выбрать сортировку: {optionValue}")
    public void selectSortOption(String optionValue) {
        Select select = new Select(el(sortDropdown));
        select.selectByValue(optionValue);
    }

    @Step("Получить список наименований товаров")
    public List<String> getItemNames() {
        return driver.findElements(itemNameList).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}