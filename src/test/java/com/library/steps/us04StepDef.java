package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class us04StepDef {
    BookPage bookPage=new BookPage();
    List<String>actualBookInformation=new ArrayList<>();
    String booksName;
    @When("the user searches for {string} book")
    public void the_user_searches_for_book(String bookName) {
        bookPage.navigateModule("Books");
        bookPage.search.sendKeys(bookName);
        booksName=bookName;
    }
    @When("the user clicks edit book button")
    public void the_user_clicks_edit_book_button() {

       // bookPage.editBook("Sapiens").click();

        Driver.getDriver().findElement(By.xpath("//a[@onclick='Books.edit_book(24821)']")).click();

       // bookPage.editBook(booksName).click();
        BrowserUtil.waitFor(5);


        actualBookInformation.add(bookPage.bookName.getAttribute("value"));
        actualBookInformation.add(bookPage.isbn.getAttribute("value"));
        actualBookInformation.add(bookPage.year.getAttribute("value"));
        actualBookInformation.add(bookPage.author.getAttribute("value"));
        Select select=new Select(bookPage.categoryDropdown);

       // actualBookInformation.add(select.getFirstSelectedOption().getAttribute("value"));


    }
    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {
        String query="select b.name as name,b.isbn,b.year,b.author from books b join book_categories bc on b.book_category_id = bc.id\n" +
                "where b.name='Sapiens';";
        DB_Util.runQuery(query);

        List<String> expectedBookInformation  = DB_Util.getRowDataAsList(1);

        Assert.assertEquals(actualBookInformation,expectedBookInformation);
    }
}
