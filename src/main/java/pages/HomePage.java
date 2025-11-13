package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    // Locators using data-testid, id, and other unique attributes
    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;

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
}