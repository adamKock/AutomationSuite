package com.swaglabs.uipages;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.swaglabs.BrowserUtil.screenshotUtil;

public class InventoryPage {
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
    private final Page page;
    private final CheckOut checkOut;
    private final CartPage cartPge; 

    public InventoryPage(Page page) {
    this.page = page; // Ensures 'page' is assigned
    this.checkOut = new CheckOut(page);
    this.cartPge = new CartPage(page);
    }
    
     
    public void checkOut(List<String> items, String firstName, String lastName, String postalCode) {
        selectItem(items);
        openShoppingCart();
        cartPge.goToCheckOut();
        checkOut.payForItems( firstName, lastName, postalCode);

    }

    public void sortItems() throws InterruptedException{
           page.getByRole(AriaRole.COMBOBOX).selectOption(new SelectOption().setLabel("Price (low to high)"));
           sUtil.screenshotUtil(page, "low_to_high_" + System.currentTimeMillis() + ".png");
           System.out.println("Low to high selected");
           Thread.sleep(1000); 
           page.getByRole(AriaRole.COMBOBOX).selectOption(new SelectOption().setLabel("Name (Z to A)"));
           sUtil.screenshotUtil(page, "z_to_a_" + System.currentTimeMillis() + ".png");
           System.out.println("Z-A Selected");
       }

       public void removeFromBasket(List<String> items) {
            selectItem(items);
            goToCart();
            int itemSize = items.size();
            cartPge.removeItemsFromCart(itemSize);
       }

       //custom method to get all items 
        public List<String> getAllItems() {
            Locator labels = page.locator(".inventory_item_name");
            System.out.println("Total labels found: " + labels.count());
            List<String> labelList = new ArrayList<>();
                for (int i = 0; i < labels.count(); i++) {
                    String textContent = labels.nth(i).textContent();
                    labelList.add(textContent);
    }
    return labelList;
}

    //custom method to take in a list of strings (Item names) then add them all to the cart
    public void selectItem(List<String> items) {
        List<Locator> labelList = getItemLabels();
        List<Locator> containerList = getItemContainers();
    
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

    public List<Locator> getItemContainers() {
        return page.locator(".inventory_item").all(); // Returns all parent containers for inventory items
    }
    public List<Locator> getItemLabels( ) {
        return page.locator(".inventory_item_name").all(); // Returns a list of all item name locators
    }

    

    public void goToCart(){
        openShoppingCart(); 
    }

    public void openShoppingCart(){
        Locator shoppingCartLink = page.locator(".shopping_cart_link");
        shoppingCartLink.click();
    }

   

    


}


