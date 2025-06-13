import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.microsoft.playwright.Page;
import com.swaglabs.BrowserUtil.ConfigReader;
import com.swaglabs.BrowserUtil.browserUtil;
import com.swaglabs.uipages.CartPage;
import com.swaglabs.uipages.CheckOut;
import com.swaglabs.uipages.InventoryPage;
import com.swaglabs.uipages.LoginPage;


//next time need to not rely on passing in the page in the framework as it's very hard to fix
public class LoginTest {
    static browserUtil bUtil; 
    Page testPage;
    static LoginPage hM; 
    static InventoryPage iP;
    CheckOut checkOut;
    CartPage cartPge;
    
    List<String> items = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");
    List<String> noItems = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");

    static String baseUrl;
    static String username;
    static String password;
    static String firstName;
    static String lastName;
    static String postalCode;
    


      @BeforeClass
        public static void setupClass() {
        bUtil = new browserUtil();
        ConfigReader.loadProperties("src/main/resources/testdata.properties");
        baseUrl = ConfigReader.get("baseUrl");
        username = ConfigReader.get("username");
        password = ConfigReader.get("password");
        firstName = ConfigReader.get("firstName");
        lastName = ConfigReader.get("lastName");
        postalCode = ConfigReader.get("postalCode");
        
       
        
    }

    @Before
    public void setup() {
        testPage = bUtil.newPage(); // Fresh page per test
        hM = new LoginPage(testPage);
        iP = new InventoryPage(testPage);
        checkOut = new CheckOut(testPage);
        cartPge = new CartPage(testPage); // One browser per test run will have multiple tabs when running all the tests at the same time
    }

    @Test
    public void login() {
        testPage.navigate(baseUrl);
        hM.login(username, password);
    }

    @Test
    public void sort() throws InterruptedException {
        
        testPage.navigate(baseUrl);
        hM.login(username, password);
        iP.sortItems();

    }

    @Test
    public void removeItemsFromCheckout(){
        testPage.navigate(baseUrl);
        hM.login(username, password);
        iP.removeFromBasket(items);
    }
    
    @Test 
    public void selectItemFromItemListAndPay(){
        testPage.navigate(baseUrl);
        hM.login(username, password);
        iP.checkOut(items, "John", "Doe", "12345");
        

    }
   @Test
   public void buyAllItemsFromSite(){
        testPage.navigate(baseUrl);
        hM.login(username, password);
        List <String> allItems = iP.getAllItems();
        iP.checkOut( allItems, "John", "Doe", "12345");

   }

   @Test 
   public void logingWithInvalidCredentials(){
    testPage.navigate(baseUrl);
    hM.login("invalidUser", "invalidPassword");
   }

   @Test
   public void checkOutWithNoItems(){
    testPage.navigate(baseUrl);
    hM.login(username, password);
    iP.checkOut(noItems, "Adam", "Kock", "1234");
   }





    
}
