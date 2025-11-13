package stepDefinitions;

import com.aventstack.extentreports.Status;
import io.cucumber.java.en.*;
import io.qameta.allure.Step;
import org.testng.Assert;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailPage;
import pages.SearchResultsPage;

import utils.DriverManager;
import utils.TestDataReader;

import java.util.ArrayList;
import java.util.List;

import static utils.AllureManager.*;
import static utils.ExtentManager.getExtentTest;

public class AmazonSteps {
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;

    private String productTitle;
    private String productPrice;

    @Step("Navigate to Amazon homepage")
    @Given("I am on Amazon homepage")
    public void i_am_on_amazon_homepage() {
        homePage = new HomePage(DriverManager.getDriver());
        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Amazon homepage is not displayed");
        System.out.println("✓ Amazon homepage loaded successfully");
        addStep("Amazon homepage loaded successfully");
    }

    @When("I search for a product")
    public void i_search_for_a_product() {
        String searchKeyword = TestDataReader.getSearchKeyword();
        homePage.searchForProduct(searchKeyword);
        System.out.println("✓ Searched for product: " + searchKeyword);
        getExtentTest().log(Status.INFO, "Searched for: " + searchKeyword);
        addParameter("Search Keyword", searchKeyword);
        addStep("Product searched: " + searchKeyword);
    }

    @Step("Click on first non-sponsored product")
    @When("I click on the first non-sponsored product")
    public void i_click_on_the_first_non_sponsored_product() {
        searchResultsPage = new SearchResultsPage(DriverManager.getDriver());
        Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(),
                "Search results are not displayed");

        searchResultsPage.clickFirstNonSponsoredProduct();
        System.out.println("✓ Clicked on first non-sponsored product");
        getExtentTest().log(Status.PASS, "✓ Non-sponsored product clicked");
        addStep("First non-sponsored product clicked");
    }

    @Step("Validate product title and price are visible")
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
        getExtentTest().log(Status.INFO, "Product: " + productTitle);
        getExtentTest().log(Status.INFO, "Price: " + productPrice);

        addParameter("Product Title", productTitle);
        addParameter("Product Price", productPrice);
        addStep("Product details validated");
    }

    @Step("Click Add to Cart button")
    @When("I click on Add to Cart button")
    public void i_click_on_add_to_cart_button() {
        productDetailPage.clickAddToCart();
        System.out.println("✓ Clicked on Add to Cart button");
        getExtentTest().log(Status.PASS, "✓ Add to Cart clicked");
        addStep("Add to Cart button clicked");
    }

    @Step("Verify confirmation message: {0}")
    @Then("I should see {string} confirmation message")
    public void i_should_see_confirmation_message(String expectedMessage) {
        Assert.assertTrue(productDetailPage.isAddedToCartConfirmationDisplayed(),
                "Add to Cart confirmation message is not displayed");

        String actualMessage = productDetailPage.getConfirmationMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected message: " + expectedMessage + ", but got: " + actualMessage);

        System.out.println("✓ Confirmation message displayed: " + actualMessage);

        getExtentTest().log(Status.PASS, "✓ Confirmation: " + actualMessage);
        addStep("Confirmation message verified: " + actualMessage);
    }

    @Step("Click Add to Cart button again")
    @When("I click on Add to Cart button again")
    public void i_click_on_add_to_cart_button_again() {
        DriverManager.getDriver().navigate().back();
        productDetailPage.clickAddToCart();
        System.out.println("✓ Clicked on Add to Cart button");
        getExtentTest().log(Status.PASS, "✓ Add to Cart clicked again");
        addStep("Add to Cart clicked second time");
    }

    @Step("Navigate to cart page")
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
        getExtentTest().log(Status.PASS, "✓ Cart page opened");
        addStep("Navigated to cart page");
    }

    @Step("Verify product is not duplicated in cart")
    @Then("I should verify the product is not duplicated")
    public void i_should_verify_the_product_is_not_duplicated() {
        Assert.assertTrue(cartPage.verifyProductNotDuplicated(),
                "Product is duplicated in cart");

        int itemCount = cartPage.getCartItemsCount();
        System.out.println("✓ Cart items count: " + itemCount);
        System.out.println("✓ Product is not duplicated");
        getExtentTest().log(Status.PASS, "✓ Product not duplicated");
        addParameter("Cart Items Count", String.valueOf(itemCount));
        addStep("Product not duplicated - verified");
    }

    @Step("Verify quantity is incremented to {0}")
    @Then("I should verify the quantity is incremented to {int}")
    public void i_should_verify_the_quantity_is_incremented_to(Integer expectedQuantity) {
        Assert.assertTrue(cartPage.verifyQuantityIncremented(expectedQuantity),
                "Quantity is not incremented to " + expectedQuantity);
        int actualQuantity = cartPage.getProductQuantity();
        System.out.println("✓ Quantity incremented to: " + actualQuantity);
        getExtentTest().log(Status.PASS, "✓ Quantity: " + actualQuantity);
        addParameter("Expected Quantity", String.valueOf(expectedQuantity));
        addParameter("Actual Quantity", String.valueOf(actualQuantity));
        addStep("Quantity verified: " + actualQuantity);
    }

    @Step("Verify subtotal equals price × quantity")
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
        getExtentTest().log(Status.PASS,
                "✓ Subtotal verified: " + subtotal + " = " + productPrice + " × " + quantity);

        addParameter("Price per Item", productPrice);
        addParameter("Quantity", String.valueOf(quantity));
        addParameter("Subtotal", subtotal);
        addStep("Subtotal calculation verified successfully");
    }

    @Step("Search for product: {0}")
    @When("I search for {string}")
    public void i_search_for(String productName) {
        homePage.searchForProduct(productName);
        System.out.println("✓ Searched for product: " + productName);
        getExtentTest().log(Status.INFO, "Searched for: " + productName);
        addParameter("Search Keyword", productName);
        addStep("Product searched: " + productName);
    }


}
