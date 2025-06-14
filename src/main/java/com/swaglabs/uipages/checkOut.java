package com.swaglabs.uipages;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;

public class CheckOut {

    private Page page;

    public CheckOut(Page page) {
        this.page = page;
    }

     public void payForItems(String firstName, String lastName, String postalCode){
        FillIntextBoxByLabel("First Name", firstName);
        FillIntextBoxByLabel("Last Name", lastName);
        FillIntextBoxByLabel("Zip/Postal Code", postalCode);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CONTINUE")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("FINISH")).click();
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("THANK YOU FOR YOUR ORDER"))).isVisible();
        System.out.println("Order complete");
    }


    public void FillIntextBoxByLabel(String label, String value){
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(label)).fill(value);

    }
    
}
