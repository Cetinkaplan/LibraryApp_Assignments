package com.library.steps;

import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class us5StepDef {
    String popularGenreCount;
    String humorGenreCount;
    @When("I execute query to find most popular book genre")
    public void i_execute_query_to_find_most_popular_book_genre() {
        String query="select book_category_id, count(*),bc.name from books b join book_categories bc on b.book_category_id = bc.id\n" +
                "group by book_category_id\n" +
                "order by count(*) DESC;";
        DB_Util.runQuery(query);

       popularGenreCount= DB_Util.getCellValue(1,2);




    }
    @Then("verify {string} is the most popular book genre.")
    public void verify_is_the_most_popular_book_genre(String genre) {
        DB_Util.runQuery("select book_category_id, count(*),bc.name from books b join book_categories bc on b.book_category_id = bc.id\n" +
                " where bc.name='"+genre+"'\n" +
                "group by book_category_id;");

        humorGenreCount=DB_Util.getCellValue(1,2);

        Assert.assertEquals("If test is failed means humor is not popular genre",popularGenreCount,humorGenreCount);
    }
}
