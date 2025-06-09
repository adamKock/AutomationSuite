package com.swaglabs.uipages;
import java.util.regex.Pattern;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;


public class loginPage {
    private  String username = "standard_user";
    private  String password = "secret_sauce";
    static String url = "https://www.saucedemo.*"; 

    //write login method here
    //test commit123

    public void login(Page page){
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("username")).fill(username);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("password")).fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("LOGIN")).click();
        assertThat(page).hasURL(Pattern.compile(url));
        String title = page.url();
        System.out.println(title);

    }
    
}
