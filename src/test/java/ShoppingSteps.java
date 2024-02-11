import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import um.cps3230.UltimatePageObject;

public class ShoppingSteps {

    static WebDriver driver = new FirefoxDriver();
    UltimatePageObject ultimateSite;


    @Given("I am a user of the website")
    public void iAmAUserOfTheWebsite() {
        driver.get("https://www.ultimate.com.mt/");
        ultimateSite = new UltimatePageObject(driver);
    }

    @When("I visit the Computing website")
    public void iVisitTheComputingWebsite() {
        ultimateSite.clickNthElement(1, "top-level-link");
    }

    @And("I click on the {string} category")
    public void iClickOnTheCategory(String arg0) {
        ultimateSite.clickButton("View " + arg0, "a");
    }

    @Then("I should be taken to {string} category")
    public void iShouldBeTakenToCategory(String arg0) {
        Assertions.assertTrue(ultimateSite.checkCategory(arg0));
    }

    @And("The category should show at least {int} products")
    public void theCategoryShouldShowAtLeastProducts(int arg0) {
        int productCount = ultimateSite.getResultCount("product");
        Assertions.assertTrue(productCount>=arg0);
    }

    @When("I click on the first product in the results")
    public void iClickOnTheFirstProductInTheResults() {
        ultimateSite.clickNthElement(0, "products");

    }

    @Then("I should be taken to the details page for that product")
    public void iShouldBeTakenToTheDetailsPageForThatProduct() {
        Assertions.assertTrue(ultimateSite.compareFirstProduct());

    }

    @When("I search for a product using the term {string}")
    public void iSearchForAProductUsingTheTerm(String arg0) {
        ultimateSite.search(arg0, "s", "search-icon");
    }

    @Then("I should see the search results")
    public void iShouldSeeTheSearchResults() {
        Assertions.assertTrue(ultimateSite.checkSearch());
    }

    @And("There should be at least {int} products in the search results")
    public void thereShouldBeAtLeastProductsInTheSearchResults(int arg0) {
        int productCount = ultimateSite.getResultCount("product");
        Assertions.assertTrue(productCount>=arg0);
    }
}
