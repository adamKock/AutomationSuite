import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.microsoft.playwright.Page;
import com.swaglabs.BrowserUtil.browserUtil;
import com.swaglabs.uipages.InventoryPage;
import com.swaglabs.uipages.cartPage;
import com.swaglabs.uipages.checkOut;
import com.swaglabs.uipages.loginPage;

//next time need to not rely on passing in the page in the framework as it's very hard to fix
public class loginTest {
    static browserUtil bUtil; 
    Page testPage;
    static loginPage hM; 
    static InventoryPage iP;
    checkOut checkOut;
    cartPage cartPge;
    String url = "https://www.saucedemo.com/v1/";
    List<String> items = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");
    List<String> noItems = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");

    String userName = "standard_user";
    String passWord = "secret_sauce";
    


      @BeforeClass
        public static void setupClass() {
        bUtil = new browserUtil();
       
        
    }

    @Before
    public void setup() {
        testPage = bUtil.newPage(); // Fresh page per test
        hM = new loginPage(testPage);
        iP = new InventoryPage(testPage);
        checkOut = new checkOut(testPage);
        cartPge = new cartPage(testPage); // One browser per test run will have multiple tabs when running all the tests at the same time
    }

    @Test
    public void login() {
        testPage.navigate(url);
        hM.login(userName, passWord);
    }

    @Test
    public void sort() throws InterruptedException {
        testPage.navigate(url);
        hM.login(userName, passWord);
        iP.sortItems();

    }

    @Test
    public void removeItemsFromCheckout(){
        testPage.navigate(url);
        hM.login(userName, passWord);
        iP.removeFromBasket(items);
    }
    
    @Test 
    public void selectItemFromItemListAndPay(){
        testPage.navigate(url);
        hM.login(userName, passWord);
        iP.checkOut(items, "John", "Doe", "12345");
        

    }
   @Test
   public void buyAllItemsFromSite(){
        testPage.navigate(url);
        hM.login(userName, passWord);
        List <String> allItems = iP.getAllItems();
        iP.checkOut( allItems, "John", "Doe", "12345");

   }

   @Test 
   public void logingWithInvalidCredentials(){
    testPage.navigate(url);
    hM.login("invalidUser", "invalidPassword");
   }

   @Test
   public void checkOutWithNoItems(){
    testPage.navigate(url);
    hM.login(userName, passWord);
    iP.checkOut(noItems, "Adam", "Kock", "1234");
   }





    
}
