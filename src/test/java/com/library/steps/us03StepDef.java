package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class us03StepDef {

    LoginPage login = new LoginPage();
    BookPage bookPage = new BookPage();

    List<String> actualBookCategories;

    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String userType) {
        Driver.getDriver().get(ConfigurationReader.getProperty("library_url"));
        BrowserUtil.waitFor(2);
        login.emailBox.sendKeys(ConfigurationReader.getProperty("librarian_username"));
        login.passwordBox.sendKeys(ConfigurationReader.getProperty("librarian_password"));
        login.loginButton.click();
    }

    @When("the user clicks book categories")
    public void the_user_clicks_book_categories() {
        bookPage.navigateModule("Books");
        BrowserUtil.waitFor(2);
        Select select = new Select(bookPage.mainCategoryElement);

        List<WebElement> options = select.getOptions();
        actualBookCategories=BrowserUtil.getElementsText(options);

        actualBookCategories.removeIf(p->p.equals("ALL"));

       // actualBookCategories.remove("ALL");



    }

    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {
        DB_Util.runQuery("select name from book_categories");
        List<String> expectedBookCategories = DB_Util.getColumnDataAsList(1);

        Assert.assertEquals(expectedBookCategories, actualBookCategories);
        System.out.println("expectedBookCategories = " + expectedBookCategories);
        System.out.println("actualBookCategories = " + actualBookCategories);
    }
}
