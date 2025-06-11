package com.swaglabs.BrowserUtil;

import java.nio.file.Paths;

import com.microsoft.playwright.Page;

public class screenshotUtil {

    public void screenshotUtil(Page page , String fileName) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)).setFullPage(true));

    }

    
}