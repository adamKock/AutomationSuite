package com.swaglabs.uipages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.swaglabs.BrowserUtil.screenshotUtil;

public class inventoryPage {
    screenshotUtil sUtil = new screenshotUtil();
    /**
     * Check out with two items in the cart.
     *
     * @param page               Playwright page object
     * @param itemNumber         Item number to add to cart
     * @param itemNumber2        Second item number to add to cart
     * @param firstName          First name for checkout form
     * @param lastName           Last name for checkout form
     * @param postalCode         Postal code for checkout form
     */
    public void checkOut(Page page, int itemNumber, int itemNumber2, String firstName, String lastName, String postalCode){
        addToCart(page, itemNumber, itemNumber2); //-> Add items to cart
        payForItems(page,firstName, lastName, postalCode); //-> Pay for items
    }

    public void addToCart(Page page, int itemNumber, int itemNumber2){
        Locator addToCart = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ADD TO CART")).nth(itemNumber); 
        Locator addToCart1 = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ADD TO CART")).nth(itemNumber2); 
        Locator shoppingCartLink = page.locator(".shopping_cart_link");
        String checkOutScreen = "Checkout: Your Information";
    
        for (int i = 0; i <=itemNumber2; i++) {
            if(i==itemNumber){
                addToCart.click();
                System.out.println("Item 1 added to cart");
                Locator remove = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("REMOVE"));
                assertThat(remove).isVisible();
            }if(i==itemNumber2){
                addToCart1.click();
                System.out.println("Item 2 added to cart");
                Locator remove1 = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("REMOVE")).nth(1);
                assertThat(remove1).isVisible();
            } 
        }
        shoppingCartLink.click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CHECKOUT")).click();
        assertThat(page.getByText(checkOutScreen)).isVisible();
        
    }

    public void payForItems(Page page, String firstName, String lastName, String postalCode){
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name")).fill(firstName);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name")).fill(lastName);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Zip/Postal Code")).fill(postalCode);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CONTINUE")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("FINISH")).click();
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("THANK YOU FOR YOUR ORDER"))).isVisible();
        System.out.println("Order complete");
    }


    public void sortItems(Page page) throws InterruptedException{
           page.getByRole(AriaRole.COMBOBOX).selectOption(new SelectOption().setLabel("Price (low to high)"));
           sUtil.screenshotUtil(page, "low_to_high_" + System.currentTimeMillis() + ".png");
           System.out.println("Low to high selected");
           Thread.sleep(1000); 
           page.getByRole(AriaRole.COMBOBOX).selectOption(new SelectOption().setLabel("Name (Z to A)"));
           sUtil.screenshotUtil(page, "z_to_a_" + System.currentTimeMillis() + ".png");
           System.out.println("Z-A Selected");
       }
       
    }







