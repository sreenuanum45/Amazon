package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



public class ProductDetailPage extends BasePage {

    @FindBy(xpath = "//span[@id='productTitle']")
    private WebElement productTitle;

    @FindBy(xpath = "//div[@class='a-section a-spacing-none aok-align-center aok-relative']//span[@class='a-price-whole']")
    private WebElement productPrice;

    @FindBy(css = "span.a-price span.a-price-whole")
    private WebElement priceWhole;

    @FindBy(css = "span.a-price span.a-price-fraction")
    private WebElement priceFraction;

    @FindBy(xpath = "//input[@name='submit.add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(xpath = "//input[@id='add-to-cart-button']")
    private WebElement addToCartBtn;

    // Multiple possible confirmation messages
    @FindBy(xpath = "//h1[contains(text(),'Added to cart')] | " +
            "//span[contains(text(),'Added to Cart')] | " +
            "//*[@id='NATC_SMART_WAGON_CONF_MSG_SUCCESS']")
    private WebElement addedToCartConfirmation;

    @FindBy(xpath = "(//a[normalize-space(text())='Go to Cart'])[2]")
    private WebElement viewCartButton;

    @FindBy(xpath= "//a[@id='nav-cart']")
    private WebElement cartIcon;

    @FindBy(id = "attach-close_sideSheet-link")
    private WebElement closeCartSideSheet;

    private String productTitleText;
    private String productPriceText;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductTitleVisible() {
        return isElementDisplayed(productTitle);
    }

    public boolean isProductPriceVisible() {
        try {
            return isElementDisplayed(productPrice); //|| //isElementDisplayed(priceWhole);
        } catch (Exception e) {
            return false;
        }
    }

    public String getProductTitle() {
        waitForElementToBeVisible(productTitle);
        productTitleText = getElementText(productTitle);
        return productTitleText;
    }

    public String getProductPrice() {
        try {
            if (isElementDisplayed(productPrice)) {
                productPriceText = getElementText(productPrice);
            } else if (isElementDisplayed(priceWhole)) {
                String whole = getElementText(priceWhole);
                String fraction = getElementText(priceFraction);
                productPriceText = "$" + whole + fraction;
            }
        } catch (Exception e) {
            productPriceText = "Price not available";
        }
        return productPriceText;
    }

    public void clickAddToCart() {
        scrollToElement(addToCartButton);
        clickElement(addToCartButton);
    }

    public boolean isAddedToCartConfirmationDisplayed() {
        try {
            waitForElementToBeVisible(addedToCartConfirmation);
            return true;
        } catch (Exception e) {
            // Try alternative confirmation
            try {
                WebElement altConfirmation = driver.findElement(
                        By.xpath("//*[contains(text(),'Added to Cart')]")
                );
                return altConfirmation.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public String getConfirmationMessage() {
        try {
            waitForElementToBeVisible(addedToCartConfirmation);
            return getElementText(addedToCartConfirmation);
        } catch (Exception e) {
            try {
                WebElement altConfirmation = driver.findElement(
                        By.xpath("//*[contains(text(),'Added to Cart')]")
                );
                return altConfirmation.getText();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    public void closeSideSheetIfPresent() {
        try {
            if (isElementDisplayed(closeCartSideSheet)) {
                clickElement(closeCartSideSheet);
            }
        } catch (Exception e) {
            // Side sheet not present, continue
        }
    }

    public void navigateToCart() {
        try {
            if (isElementDisplayed(viewCartButton)) {
                clickElement(viewCartButton);
            } else {
                clickElement(cartIcon);
            }
        } catch (Exception e) {
            clickElement(cartIcon);
        }
    }

}