package com.swaglabs.BrowserUtil;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class browserUtil {

    private Playwright playwright; 
    private Browser browser;
    private BrowserContext context;

    public browserUtil() {
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        this.context = browser.newContext();
    }

    public Page newPage(){
        return context.newPage();
    }

    public void tearDown(){
        if(browser != null) browser.close();
        if(playwright != null) playwright.close();
    }

    
}
