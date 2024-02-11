import enums.SiteStates;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import um.cps3230.SiteModel;

import java.util.Random;

import static enums.SiteStates.*;

public class SiteFSMTester implements FsmModel {

    SiteModel sut = new SiteModel();
    SiteStates state = HOME;
    //booleans for each of the states of the FSA
    boolean
            home = false,
            searching = false,
            viewingProduct = false,
            viewingCart = false,
            checkingOut = false;

    //boolean to check if the cart has items or is empty
    int cartItems = 0;

    boolean delayEnabled = true;

    public void delay() {
        //i did this to slow down requests, reduce the number of concurrent requests and reduce load on the site's server
        try {
            if (delayEnabled) Thread.sleep(7000);
        }
        catch (InterruptedException e) {
            e.getMessage();
            System.out.println("Finished before delay");
        }

    }

    public @Action void search() {
        delay();
        sut.search();

        state = SEARCHING;

        home = false;
        searching = true;
        viewingProduct = false;
        viewingCart = false;
        checkingOut = false;

        Assertions.assertTrue(sut.isSearching());

    }

    public boolean searchGuard() {
        return state == HOME && cartItems==0;
    }

    public @Action void viewProduct() {
        delay();
        sut.viewProduct();

        state = VIEWINGPRODUCT;

        home = false;
        searching = false;
        viewingProduct = true;
        viewingCart = false;
        checkingOut = false;

        Assertions.assertTrue(sut.isViewingProduct());
    }
    public boolean viewProductGuard() {
        return state == SEARCHING;
    }

    public @Action void addToCart() {
        delay();
        sut.addToCart();
        cartItems++;

        Assertions.assertEquals(cartItems, sut.getCartItems());
    }
    public boolean addToCartGuard() {
        return state == VIEWINGPRODUCT && cartItems==0;
    }

    public @Action void viewCart() {
        delay();
        sut.viewCart();

        state = VIEWINGCART;

        home = false;
        searching = false;
        viewingProduct = false;
        viewingCart = true;
        checkingOut = false;

        Assertions.assertTrue(sut.isViewingCart());
    }
    public boolean viewCartGuard() {
        return (state == VIEWINGPRODUCT || state == HOME) && cartItems>0;
    }

    public @Action void removeFromCart() {
        delay();
        sut.removeFromCart();
        cartItems=0;
        Assertions.assertTrue(sut.isViewingCart());
        Assertions.assertEquals(cartItems, sut.getCartItems());
    }

    public boolean removeFromCartGuard() {
        return (state == VIEWINGCART && cartItems>0);
    }

    public @Action void checkOut() {
        delay();
        sut.checkOut();

        state = CHECKINGOUT;

        home = false;
        searching = false;
        viewingProduct = false;
        viewingCart = false;
        checkingOut = true;

        Assertions.assertTrue(sut.isCheckingOut());
    }

    public boolean checkOutGuard() {
        return (state == VIEWINGCART && cartItems>0);
    }

    public @Action void goHome() {
        delay();
        sut.goHome();

        state = HOME;

        home = true;
        searching = false;
        viewingProduct = false;
        viewingCart = false;
        checkingOut = false;

        Assertions.assertTrue(sut.isHome());
    }

    public boolean goHomeGuard() {
        return (state!=HOME && state!=SEARCHING) && (state != VIEWINGCART && cartItems>0);
    }






    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean b) {
        state = SiteStates.HOME;
        cartItems=0;
        home = true;
        searching = false;
        viewingProduct = false;
        viewingCart = false;
        checkingOut = false;

        if (b) {
            sut.closeDriver();
            sut = new SiteModel();
        }
    }

    @Test
    public void SiteModelTestRunner() throws InterruptedException {
        final GreedyTester tester = new GreedyTester(new SiteFSMTester());
        tester.setRandom(new Random());
        //tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new ActionCoverage());


        tester.generate(25);
        tester.printCoverage();
    }
}
