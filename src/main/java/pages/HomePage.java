package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.DriverManager;

import java.util.List;

public class HomePage extends BasePage {

    // Locators using data-testid, id, and other unique attributes
    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;
@FindBy(xpath="//button[text()='Continue shopping']")
private WebElement continueShopingButton;
    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;

    @FindBy(id = "nav-logo-sprites")
    private WebElement amazonLogo;

    @FindBy(id = "nav-cart-count")
    private WebElement cartCount;

    @FindBy(id = "nav-cart")
    private WebElement cartIcon;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHomePageDisplayed() {
        return isElementDisplayed(amazonLogo);
    }

    public void searchForProduct(String searchKeyword) {
        enterText(searchBox, searchKeyword);
        clickElement(searchButton);
    }

    public void navigateToCart() {
        clickElement(cartIcon);
    }

    public String getCartCount() {
        return getElementText(cartCount);
    }
    public void handleContinueShopping() {
waitForPageLoad();
        List<WebElement> elements = DriverManager.getDriver()
                .findElements(By.xpath("//button[text()='Continue shopping']")); // your locator

        if (!elements.isEmpty()) {
            WebElement continueShoppingButton = elements.get(0);
            wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
            clickElement(continueShoppingButton);
        }
    }



}