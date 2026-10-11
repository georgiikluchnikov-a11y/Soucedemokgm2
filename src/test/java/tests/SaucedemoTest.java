package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.util.List;

@Epic("UI Автотесты Saucedemo (Swag Labs)")
public class SaucedemoTest extends BaseTest {

    @Test(description = "1. Позитивный тест: Вход под стандартным пользователем")
    @Feature("Авторизация")
    @Story("Позитивная авторизация")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Владыкин М. Р.")
    public void testPositiveLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertEquals(inventoryPage.getTitleText(), "Products", "Заголовок страницы не совпадает!");
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "Неверный URL после входа");
    }

    @Test(description = "2. Негативный тест: Авторизация заблокированного пользователя")
    @Feature("Авторизация")
    @Story("Негативная авторизация")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Владыкин М. Р.")
    public void testNegativeLoginLockedUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.getErrorMessageText().contains("Epic sadface: Sorry, this user has been locked out."),
                "Сообщение о блокировке не найдено!");
    }

    @Test(description = "3. Позитивный тест: Добавление товара в корзину")
    @Feature("Корзина")
    @Story("Управление товарами в корзине")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Владыкин М. Р.")
    public void testAddToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        Assert.assertEquals(inventoryPage.getCartBadgeText(), "1", "Счетчик корзины не равен 1!");

        inventoryPage.openCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getItemNameText(), "Sauce Labs Backpack", "Наименование товара в корзине не совпадает!");
    }

    @Test(description = "4. Позитивный тест: Удаление товара из корзины на странице каталога")
    @Feature("Корзина")
    @Story("Управление товарами в корзине")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Владыкин М. Р.")
    public void testRemoveFromCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        Assert.assertEquals(inventoryPage.getCartBadgeText(), "1");

        inventoryPage.removeBackpackFromCart();
        Assert.assertFalse(inventoryPage.isCartBadgePresent(), "Счетчик корзины должен исчезнуть после удаления!");
    }

    @Test(description = "5. Позитивный тест: Сортировка товаров Z to A")
    @Feature("Каталог")
    @Story("Сортировка товаров")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Владыкин М. Р.")
    public void testSortProductsZtoA() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.selectSortOption("za");

        List<String> itemNames = inventoryPage.getItemNames();
        Assert.assertEquals(itemNames.get(0), "Test.allTheThings() T-Shirt (Red)", "Первым должен идти товар на T!");
    }

    @Test(description = "6. Позитивный тест: Сквозное оформление заказа (Checkout)")
    @Feature("Оформление заказа")
    @Story("Сквозной процесс покупки")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Владыкин М. Р.")
    public void testCompleteCheckoutProcess() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        inventoryPage.openCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInformation("Maxim", "Vladykin", "101000");
        checkoutPage.clickFinish();

        Assert.assertEquals(checkoutPage.getCompleteHeaderText(), "Thank you for your order!",
                "Заказ не оформлен!");
    }
}