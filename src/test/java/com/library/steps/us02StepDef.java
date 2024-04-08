package com.library.steps;

import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class us02StepDef {

    LoginPage login=new LoginPage();
    DashBoardPage dashboard=new DashBoardPage();
    String actualBorrowNumber;

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String userType) {
        Driver.getDriver().get(ConfigurationReader.getProperty("library_url"));
        login.login(userType);
    }
    @When("the librarian gets borrowed books number")
    public void the_librarian_gets_borrowed_books_number() {
        BrowserUtil.waitFor(2);
        actualBorrowNumber=dashboard.borrowedBooksNumber.getText();
    }
    @Then("borrowed books number information must match with DB")
    public void borrowed_books_number_information_must_match_with_db() {
        DB_Util.runQuery("select count(is_returned) from book_borrow\n" +
                "where is_returned=0;");
        String expectedBorrowNumber=DB_Util.getFirstRowFirstColumn();

        Assert.assertEquals(expectedBorrowNumber,actualBorrowNumber);
        System.out.println("actualBorrowNumber = " + actualBorrowNumber);
        System.out.println("expectedBorrowNumber = " + expectedBorrowNumber);
    }
}
