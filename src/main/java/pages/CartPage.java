package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "div.sc-list-item-content")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//span[@class='a-truncate-cut']")
    private List<WebElement> productTitlesInCart;

    @FindBy(css = "span.sc-product-title")
    private List<WebElement> productTitles;

    @FindBy(css = "select.a-dropdown-prompt")
    private WebElement quantityDropdown;

    @FindBy(xpath = "//span[@data-a-selector='value']")
    private WebElement quantityLabel;

    @FindBy(xpath = "//span[@class='a-dropdown-prompt' and @aria-labelledby]")
    private List<WebElement> quantitySelectors;

    @FindBy(id = "sc-subtotal-label-activecart")
    private WebElement subtotalLabel;

    @FindBy(id = "sc-subtotal-amount-activecart")
    private WebElement subtotalAmount;

    @FindBy(css = "span.sc-price")
    private List<WebElement> itemPrices;

    @FindBy(css = "input[data-action='delete']")
    private List<WebElement> deleteButtons;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartPageDisplayed() {
        try {
            return cartItems.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartItemsCount() {
        return cartItems.size();
    }

    public String getProductTitleInCart(int index) {
        waitForElementsToBeVisible(productTitles);
        return getElementText(productTitles.get(index));
    }

    public int getProductQuantity() {
        try {
            waitForElementToBeVisible(quantityLabel);
            String quantityText = getElementText(quantityLabel);
            return Integer.parseInt(quantityText.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 1;
        }
    }

    public String getSubtotal() {
        waitForElementToBeVisible(subtotalAmount);
        return getElementText(subtotalAmount).replace("₹","");
    }

    public String getItemPrice(int index) {
        waitForElementsToBeVisible(itemPrices);
        return getElementText(itemPrices.get(index));
    }

    public boolean verifyProductNotDuplicated() {
        // Should only have one item in cart for same product
        return cartItems.size() == 1;
    }

    public boolean verifyQuantityIncremented(int expectedQuantity) {
        int actualQuantity = getProductQuantity();
        return actualQuantity == expectedQuantity;
    }

    public boolean verifySubtotal(String pricePerItem, int quantity) {
        try {
            // Extract numeric value from price
            double price = extractPriceValue(pricePerItem);
            double expectedSubtotal = price * quantity;

            // Extract numeric value from subtotal
            String subtotalText = getSubtotal();
            double actualSubtotal = extractPriceValue(subtotalText);

            // Allow small difference for rounding
            return Math.abs(expectedSubtotal - actualSubtotal) < 0.01;
        } catch (Exception e) {
            return false;
        }
    }

    private double extractPriceValue(String priceText) {
        // Remove currency symbols and commas, extract number
        String numericValue = priceText.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numericValue);
    }
}