package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(css = "div[data-component-type='s-search-result']")
    private List<WebElement> searchResults;

    @FindBy(xpath = "//span[@data-component-type='s-search-results']")
    private WebElement searchResultsContainer;

    @FindBy(css = "span.a-size-medium.a-color-base.a-text-normal")
    private List<WebElement> productTitles;

    // Locator for non-sponsored products
    private By nonSponsoredProduct = By.xpath(
            "//div[@data-component-type='s-search-result'][not(descendant::span[contains(text(),'Sponsored')])]"
    );

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean areSearchResultsDisplayed() {
        waitForElementsToBeVisible(searchResults);
        return searchResults.size() > 0;
    }

    public void clickFirstNonSponsoredProduct() {
        // Wait for search results to load
        waitForElementToBeVisible(searchResultsContainer);

        // Find first non-sponsored product
        List<WebElement> products = driver.findElements(nonSponsoredProduct);

        if (products.isEmpty()) {
            // Fallback to first product if no non-sponsored found
            WebElement firstProduct = searchResults.get(0).findElement(By.xpath(".//a[@class='a-link-normal s-no-outline']"));
            scrollToElement(firstProduct);
            clickElement(firstProduct);
        } else {
            WebElement firstNonSponsored_Target = products.get(0).findElement(By.xpath(".//a[@class='a-link-normal s-no-outline']"));
            firstNonSponsored_Target.getText();
            scrollToElement(firstNonSponsored_Target);
            clickElement(firstNonSponsored_Target);

        }
    }

    public int getSearchResultsCount() {
        return searchResults.size();
    }
}