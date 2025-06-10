package com.swaglabs.uipages;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.swaglabs.BrowserUtil.screenshotUtil;

public class inventoryPage {
    screenshotUtil sUtil = new screenshotUtil();

    static Page page;
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

     public inventoryPage(Page page) {
        this.page = page;
    }
     
    public void checkOut(Page page, List<String> items, String firstName, String lastName, String postalCode) {
        selectItem(page, items);
        goToCheckOut();
        payForItems(page,firstName, lastName, postalCode); //-> Pay for items
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

       public void removeFromBasket(Page page, List<String> items) {
            selectItem(page, items);
            goToCart();
            int itemSize = items.size();
            for(int i = 0; i < itemSize; i++){
                Locator removeButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove")).nth(0);
                removeButton.click();
            }
        
       }
       
       //custom method to get all items 
        public List<String> getAllItems(Page page) {
            Locator labels = page.locator(".inventory_item_name");
            System.out.println("Total labels found: " + labels.count());
            List<String> labelList = new ArrayList<>();
                for (int i = 0; i < labels.count(); i++) {
                    String textContent = labels.nth(i).textContent();
                    System.out.println(textContent);
                    labelList.add(textContent);
    }
    return labelList;
}

    //custom method to take in a list of strings (Item names) then add them all to the cart
    public void selectItem(Page page, List<String> items) {
        List<Locator> labelList = getItemLabels(page);
        List<Locator> containerList = getItemContainers(page);
    
        for (int i = 0; i < labelList.size(); i++) {
            String itemName = labelList.get(i).textContent().trim();
            if (items.contains(itemName)) {
                // Locate "ADD TO CART" button inside the respective container
                Locator addButton = containerList.get(i).locator("button:has-text('ADD TO CART')");
                addButton.click();
                System.out.println("Item " + itemName + " added to cart");
            }
        }
    }

    public List<Locator> getItemContainers(Page page) {
        return page.locator(".inventory_item").all(); // Returns all parent containers for inventory items
    }
    public List<Locator> getItemLabels(Page page) {
        return page.locator(".inventory_item_name").all(); // Returns a list of all item name locators
    }

    public void goToCheckOut(){
        Locator shoppingCartLink = page.locator(".shopping_cart_link");
        String checkOutScreen = "Checkout: Your Information";
        shoppingCartLink.click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CHECKOUT")).click();
        assertThat(page.getByText(checkOutScreen)).isVisible();
    }

    public void goToCart(){
        Locator shoppingCartLink = page.locator(".shopping_cart_link");
        shoppingCartLink.click();
    }
    


}


