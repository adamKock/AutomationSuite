package com.swaglabs.uipages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;

public class cartPage {
    static Page page;
    static InventoryPage iP = new InventoryPage(page);

    public cartPage(Page page) {
        this.page = page;
    }


    public void goToCheckOut(){
        
        String checkOutScreen = "Checkout: Your Information";
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CHECKOUT")).click();
        assertThat(page.getByText(checkOutScreen)).isVisible();
    }

    public void removeItemsFromCart(int itemSize){
        for(int i = 0; i < itemSize; i++){
                Locator removeButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove")).nth(0);
                removeButton.click();
            }
    }
    
}
