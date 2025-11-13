@AmazonAutomation @RegressionTest
Feature: Amazon Add to Cart Functionality
  As a customer
  I want to search for products and add them to cart
  So that I can verify the cart behavior when adding same product multiple times

  Background:
    Given I am on Amazon homepage

  @AddToCart @SmokeTest
  Scenario: Search product and add to cart once
    When I search for a product
    And I click on the first non-sponsored product
    Then I should see the product title and price
    When I click on Add to Cart button
    Then I should see "Added to cart" confirmation message

  @AddToCart @QuantityIncrement
  Scenario: Add same product to cart twice and verify quantity increment
    When I search for a product
    And I click on the first non-sponsored product
    Then I should see the product title and price
    When I click on Add to Cart button
    Then I should see "Added to cart" confirmation message
    When I click on Add to Cart button again
    And I navigate to cart page
    Then I should verify the product is not duplicated
    And I should verify the quantity is incremented to 2
    And I should verify the subtotal equals price times quantity

  @DataDriven
  Scenario Outline: Search different products and add to cart
    When I search for "<productName>"
    And I click on the first non-sponsored product
    Then I should see the product title and price
    When I click on Add to Cart button
    Then I should see "Added to cart" confirmation message
    Examples:
      | productName      |
      | wireless mouse   |
#      | laptop stand     |
#      | usb cable        |
#      | bluetooth speaker|