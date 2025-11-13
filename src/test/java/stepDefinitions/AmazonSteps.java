package stepDefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailPage;
import pages.SearchResultsPage;
import utils.DriverManager;
import utils.TestDataReader;

import java.util.ArrayList;
import java.util.List;

public class AmazonSteps {
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;

    private String productTitle;
    private String productPrice;

    @Given("I am on Amazon homepage")
    public void i_am_on_amazon_homepage() {
        homePage = new HomePage(DriverManager.getDriver());
        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Amazon homepage is not displayed");
        System.out.println("✓ Amazon homepage loaded successfully");
    }

    @When("I search for a product")
    public void i_search_for_a_product() {
        String searchKeyword = TestDataReader.getSearchKeyword();
        homePage.searchForProduct(searchKeyword);
        System.out.println("✓ Searched for product: " + searchKeyword);
    }

    @When("I click on the first non-sponsored product")
    public void i_click_on_the_first_non_sponsored_product() {
        searchResultsPage = new SearchResultsPage(DriverManager.getDriver());
        Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(),
                "Search results are not displayed");

        searchResultsPage.clickFirstNonSponsoredProduct();
        System.out.println("✓ Clicked on first non-sponsored product");
    }

    @Then("I should see the product title and price")
    public void i_should_see_the_product_title_and_price() {
        List<String> windows = new ArrayList<>(DriverManager.getDriver().getWindowHandles());
        String currentWindow = DriverManager.getDriver().getWindowHandle();

        for (String w : windows) {
            if (!w.equals(currentWindow)) {   // <- this is what you actually meant
                DriverManager.getDriver().switchTo().window(w);
                break;
            }
        }
        productDetailPage = new ProductDetailPage(DriverManager.getDriver());
        Assert.assertTrue(productDetailPage.isProductTitleVisible(),
                "Product title is not visible");
        Assert.assertTrue(productDetailPage.isProductPriceVisible(),
                "Product price is not visible");
        productTitle = productDetailPage.getProductTitle();
        productPrice = productDetailPage.getProductPrice();

        System.out.println("✓ Product Title: " + productTitle);
        System.out.println("✓ Product Price: " + productPrice);
    }

    @When("I click on Add to Cart button")
    public void i_click_on_add_to_cart_button() {
        productDetailPage.clickAddToCart();
        System.out.println("✓ Clicked on Add to Cart button");
    }

    @Then("I should see {string} confirmation message")
    public void i_should_see_confirmation_message(String expectedMessage) {
        Assert.assertTrue(productDetailPage.isAddedToCartConfirmationDisplayed(),
                "Add to Cart confirmation message is not displayed");

        String actualMessage = productDetailPage.getConfirmationMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected message: " + expectedMessage + ", but got: " + actualMessage);

        System.out.println("✓ Confirmation message displayed: " + actualMessage);
    }
    @When("I click on Add to Cart button again")
    public void i_click_on_add_to_cart_button_again() {
        DriverManager.getDriver().navigate().back();
        productDetailPage.clickAddToCart();
        System.out.println("✓ Clicked on Add to Cart button");
    }

    @When("I navigate to cart page")
    public void i_navigate_to_cart_page() {
        try {
            Thread.sleep(2000); // Wait for cart update
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        productDetailPage.navigateToCart();
        cartPage = new CartPage(DriverManager.getDriver());
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Cart page is not displayed");
        System.out.println("✓ Navigated to cart page");
    }
    @Then("I should verify the product is not duplicated")
    public void i_should_verify_the_product_is_not_duplicated() {
        Assert.assertTrue(cartPage.verifyProductNotDuplicated(),
                "Product is duplicated in cart");

        int itemCount = cartPage.getCartItemsCount();
        System.out.println("✓ Cart items count: " + itemCount);
        System.out.println("✓ Product is not duplicated");

    }
    @Then("I should verify the quantity is incremented to {int}")
    public void i_should_verify_the_quantity_is_incremented_to(Integer expectedQuantity) {
        Assert.assertTrue(cartPage.verifyQuantityIncremented(expectedQuantity),
                "Quantity is not incremented to " + expectedQuantity);
        int actualQuantity = cartPage.getProductQuantity();
        System.out.println("✓ Quantity incremented to: " + actualQuantity);

    }
    @Then("I should verify the subtotal equals price times quantity")
    public void i_should_verify_the_subtotal_equals_price_times_quantity() {
        int quantity = cartPage.getProductQuantity();
        Assert.assertTrue(cartPage.verifySubtotal(productPrice, quantity),
                "Subtotal does not match price × quantity");
        String subtotal = cartPage.getSubtotal();
        System.out.println("✓ Price per item: " + productPrice);
        System.out.println("✓ Quantity: " + quantity);
        System.out.println("✓ Subtotal: " + subtotal);
        System.out.println("✓ Subtotal calculation verified");
    }
    @When("I search for {string}")
    public void i_search_for(String productName) {
        homePage.searchForProduct(productName);
        System.out.println("✓ Searched for product: " + productName);
    }


}
