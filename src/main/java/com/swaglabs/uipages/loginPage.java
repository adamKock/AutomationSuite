package com.swaglabs.uipages;
import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;



public class loginPage {
    static String url = "https://www.saucedemo.*"; 

    public void login(Page page, String userName, String password){
        performLogin(page, userName, password);
        assertThat(page).hasURL(Pattern.compile(url));
        String title = page.url();
        System.out.println(title);
        }

        public void loginInvalidCredentials(Page page, String userName, String password){
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("username")).fill(userName);
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("password")).fill(password);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("LOGIN")).click();
            Locator errorMessage = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Epic sadface: Username and password do not match any user in this service"));
            PlaywrightAssertions.assertThat(errorMessage).isVisible();
    
        }

        public void performLogin(Page page, String userName, String password){
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("username")).fill(userName);
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("password")).fill(password);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("LOGIN")).click();
        }
        
        

    }

    

