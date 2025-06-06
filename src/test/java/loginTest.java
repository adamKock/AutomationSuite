import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.microsoft.playwright.Page;
import com.swaglabs.BrowserUtil.browserUtil;
import com.swaglabs.uipages.inventoryPage;
import com.swaglabs.uipages.loginPage;


public class loginTest {
    static browserUtil bUtil; 
    Page testPage;
    static loginPage hM; 
    static inventoryPage iP; 
    String url = "https://www.saucedemo.com/v1/";

      @BeforeClass
        public static void setupClass() {
        bUtil = new browserUtil();
        hM = new loginPage();
        
    }

    @Before
    public void setup() {
        testPage = bUtil.newPage(); // Fresh page per test
        iP = new inventoryPage(testPage); // One browser per test run will have multiple tabs when running all the tests at the same time
    }

    @Test
    public void login() {
        testPage.navigate(url);
        hM.login(testPage);
        iP.checkOut(1, 4, "Adam", "Kock", "123");
    }

    @Test
    public void sort() throws InterruptedException {
        testPage.navigate(url);
        hM.login(testPage);
        iP.sortItems(testPage);
        
    }
    
   




    
}
