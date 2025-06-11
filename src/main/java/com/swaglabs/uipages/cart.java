package com.swaglabs.uipages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;

public class cart {
    static Page page;
    static InventoryPage iP = new InventoryPage(page);


    public void goToCheckOut(Page page ){
        iP.openShoppingCart();
        String checkOutScreen = "Checkout: Your Information";
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CHECKOUT")).click();
        assertThat(page.getByText(checkOutScreen)).isVisible();
    }

    public void removeItemsFromCart(int itemSize, Page page){
        for(int i = 0; i < itemSize; i++){
                Locator removeButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove")).nth(0);
                removeButton.click();
            }
    }
    
}
