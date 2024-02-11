package um.cps3230;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class SiteModel {


    WebDriver driver;
    UltimatePageObject ultimateSite;



    //booleans for each of the states of the FSA
    private boolean
    home = false,
    searching = false,
    viewingProduct = false,
    viewingCart = false,
    checkingOut = false;

    //boolean to check if the cart has items or is empty
    private int cartItems = 0;

    public SiteModel() {
        driver = new FirefoxDriver();
        driver.get("https://www.ultimate.com.mt/");
        ultimateSite = new UltimatePageObject(driver);
    }

    public void closeDriver() {
        if (driver!=null) driver.close();
    }


    //searches the same string every time
    public boolean search() {
        ultimateSite.search("laptop", "s", "search-icon");
        home = false;
        searching = true;
        viewingProduct = false;
        viewingCart = false;
        checkingOut = false;
        return true;
    }

    public boolean viewProduct() {
        if (searching) {
            //view the first product
            ultimateSite.clickNthElement(0, "products");
            home = false;
            searching = false;
            viewingProduct = true;
            viewingCart = false;
            checkingOut = false;
            return true;
        }
        else {
            throw new IllegalStateException();
        }
    }

    public boolean addToCart() {
        if (viewingProduct && cartItems==0) {
            ultimateSite.clickButton("Add to basket", "button");
            cartItems++;
            return true;
        }
        else {
            throw new IllegalStateException();
        }
    }

    public boolean viewCart() {
        ultimateSite.clickElementClass("ultimate-cart");
        home = false;
        searching = false;
        viewingProduct = false;
        viewingCart = true;
        checkingOut = false;
        return true;
    }

    public boolean removeFromCart() {
        if (viewingCart && cartItems>0) {
            ultimateSite.clickElementClass("remove");
            cartItems=0;
            return true;
        }
        else {
            throw new IllegalStateException();
        }
    }

    public boolean checkOut() {
        if (viewingCart && cartItems>0) {
            ultimateSite.clickButton("\n" + "\tProceed to checkout", "a");
            home = false;
            searching = false;
            viewingProduct = false;
            viewingCart = false;
            checkingOut = true;
            return true;
        }
        else {
            throw new IllegalStateException();
        }
    }

    public boolean goHome() {
        ultimateSite.clickElementClass("ultimate-logo");

        home = true;
        searching = false;
        viewingProduct = false;
        viewingCart = false;
        checkingOut = false;
        return true;
    }

    public boolean isHome() { return home; }
    public boolean isSearching() { return searching; }
    public boolean isViewingProduct() { return viewingProduct; }
    public boolean isViewingCart() { return viewingCart; }
    public boolean isCheckingOut() { return checkingOut; }

    public int getCartItems() { return cartItems; }

}

