Feature: Exploring products in website
  In order to be able to make wise purchasing decisions
  As a user of this website
  I want to be able to look through the various categories of products, as well as search for specific products

  Scenario Outline: Reachability of product categories
    Given I am a user of the website
    When I visit the Computing website
    And I click on the <category-name> category
    Then I should be taken to <category-name> category
    And The category should show at least <num-products> products
    When I click on the first product in the results
    Then I should be taken to the details page for that product

    Examples:
    |category-name    |num-products|
    |"Laptops"        |3           |
    |"Tablets"        |5           |
    |"Monitors"       |5           |
    |"Printers"       |5           |
    |"Gaming Headsets"|5           |

  Scenario Outline: Search functionality
    Given I am a user of the website
    When I search for a product using the term <search-term>
    Then I should see the search results
    And There should be at least <num> products in the search results
    When I click on the first product in the results
    Then I should be taken to the details page for that product

    Examples:
    |search-term|num|
    |"laptop"   |2  |