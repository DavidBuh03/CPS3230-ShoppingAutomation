package um.cps3230;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UltimatePageObject {

    WebDriver driver;
    String lastSearchQuery;
    String lastProductClicked;

    public UltimatePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void clickButton(String button, String elementType) {
        WebElement hyperlink = driver.findElement(By.xpath("//" + elementType +"[text() = '" + button + "']"));
        hyperlink.click();
    }

    public boolean checkCategory(String category) {
        WebElement text = driver.findElement(By.xpath("//h2[text() = '" + category + "']"));
        return text!=null;
    }
    public boolean checkSearch() {
        WebElement text = driver.findElement(By.xpath("//h2[text() = 'Search Results for: " + lastSearchQuery + "']"));
        return text!=null;
    }

    public boolean compareFirstProduct() {
        String currentProductName = driver.findElement(By.className("product_title")).getText();
        return lastProductClicked.equals(currentProductName);
    }

    public void clickNthElement(int index, String idName) {
        List<WebElement> elements;
        if (idName.equals("products")) {
            elements = driver.findElement(By.className(idName)).findElements(By.className(idName.substring(0, idName.length()-1)));
            lastProductClicked = elements.get(index).findElement(By.tagName("h2")).getText();
        }
        else {
            elements = driver.findElements(By.className(idName));
        }
        elements.get(index).click();
    }
    public void clickElementClass(String className) {
        WebElement button = driver.findElement((By.className(className)));
        button.click();
    }


    public void search(String query, String fieldName, String buttonId) {
        lastSearchQuery = query;
        WebElement search = driver.findElement(By.name(fieldName));
        search.clear();
        search.sendKeys(query);
        WebElement searchButton = driver.findElement(By.id(buttonId));
        searchButton.click();

    }
    public int getResultCount(String elementId) {
        List<WebElement> elements = driver.findElements(By.className(elementId));
        return elements.size();
    }

}
