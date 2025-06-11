import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.microsoft.playwright.Page;
import com.swaglabs.BrowserUtil.browserUtil;
import com.swaglabs.uipages.InventoryPage;
import com.swaglabs.uipages.loginPage;

//next time need to not rely on passing in the page in the framework as it's very hard to fix
public class loginTest {
    static browserUtil bUtil; 
    Page testPage;
    static loginPage hM; 
    static InventoryPage iP;
    String url = "https://www.saucedemo.com/v1/";
    List<String> items = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");
    List<String> noItems = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");

    String userName = "standard_user";
    String passWord = "secret_sauce";


      @BeforeClass
        public static void setupClass() {
        bUtil = new browserUtil();
        hM = new loginPage();
        
    }

    @Before
    public void setup() {
        testPage = bUtil.newPage(); // Fresh page per test
        iP = new InventoryPage(testPage); // One browser per test run will have multiple tabs when running all the tests at the same time
    }

    @Test
    public void login() {
        testPage.navigate(url);
        hM.login(testPage, userName, passWord);
    }

    @Test
    public void sort() throws InterruptedException {
        testPage.navigate(url);
        hM.login(testPage, userName, passWord);
        iP.sortItems(testPage);

    }

    @Test
    public void removeItemsFromCheckout(){
        testPage.navigate(url);
        hM.login(testPage, userName, passWord);
        iP.removeFromBasket(testPage, items);
    }
    
    @Test 
    public void selectItemFromItemListAndPay(){
        testPage.navigate(url);
        hM.login(testPage, userName, passWord);
        iP.checkOut(testPage, items, "John", "Doe", "12345");
        

    }
   @Test
   public void buyAllItemsFromSite(){
        testPage.navigate(url);
        hM.login(testPage, userName, passWord);
        List <String> allItems = iP.getAllItems(testPage);
        iP.checkOut(testPage, allItems, "John", "Doe", "12345");

   }

   @Test 
   public void logingWithInvalidCredentials(){
    testPage.navigate(url);
    hM.login(testPage, "invalidUser", "invalidPassword");
   }

   @Test
   public void checkOutWithNoItems(){
    testPage.navigate(url);
    hM.login(testPage, userName, passWord);
    iP.checkOut(testPage, noItems, "Adam", "Kock", "1234");
   }





    
}
