package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import pages.BookPage;
import pages.LoginPage;
import utility.BrowserUtil;
import utility.ConfigurationReader;
import utility.DB_Util;
import utility.LibraryAPI_Util;

import java.util.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class APIStepsDefs {
    RequestSpecification givenPart = given().log().uri();
    Response response;
    ValidatableResponse thenPart;
    String expectedpathValue;
    JsonPath jp;
    Map<String, Object> randomData;
    BookPage bookPage = new BookPage();
  //  int bookId ;
   // int userId ;
    String id;

    @Given("I logged in library API as {string}")
    public void i_logged_in_library_api_as(String role) {

        givenPart.header("x-library-token", LibraryAPI_Util.getToken(role));


    }

    @Given("Accept header should be {string}")
    public void accept_header_should_be(String contentType) {
        givenPart.accept(contentType);

    }
    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endPoint) {
         response = givenPart.when().get(ConfigurationReader.getProperty("library.baseUri") + endPoint);
        thenPart = response.then();
        jp = response.jsonPath();
        response.prettyPeek();


    }
    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endPoint) {
        response = givenPart.when().post(endPoint);
        thenPart = response.then();
        jp = response.jsonPath();
        response.prettyPeek();

        switch (endPoint){
            case "/add_book" :
                id = jp.getString("book_id");
                System.out.println("bookId = " + id);
                break;
            case "/add_user":
                id = jp.getString("user_id");
                System.out.println("user_id = " + id);
                break;

            //default:
            //    throw new RuntimeException("Invalid Endpoint "+ endPoint);
        }


    }
//    @When("I send {string} request to {string} endpoint")
//    public void i_send_request_to_endpoint(String sendOrder, String endPoint) {
//        switch (sendOrder){
//            case "get":
//                response = givenPart.when().get(ConfigurationReader.getProperty("library.baseUri") + endPoint);
//                break;
//            case "post":
//                response = givenPart.when().post(ConfigurationReader.getProperty("library.baseUri") + endPoint);
//                break;
//        }
//        thenPart = response.then();
//        jp = response.jsonPath();
//        response.prettyPeek();
//
//
//        switch (endPoint){
//            case "/add_book" :
//                bookId = jp.getInt("book_id");
//                System.out.println("bookId = " + bookId);
//                break;
//            case "/add_user":
//                userId = jp.getInt("user_id");
//                System.out.println("user_id = " + userId);
//                break;
//        }
//
//
//
//
//
//    }


    @When("Response content type should be {string}")
    public void response_content_type_should_be(String responseContentType) {
        thenPart.contentType(responseContentType);

    }

    @Then("status code should be {int}")
    public void status_code_should_be(Integer expectedStatusCode) {
        thenPart.statusCode(expectedStatusCode);
    }

    @When("Each {string} field should not be null")
    public void each_field_should_not_be_null(String path) {
        Assert.assertNotNull(path);

    }

    @Given("path param should be {string} as a {string}")
    public void path_param_should_be_as_a(String path, String pathValue) {
        givenPart.pathParam(path, pathValue);
        expectedpathValue = pathValue;


    }


    @Then("{string} field should be same as the path pram")
    public void field_should_be_same_as_the_path_pram(String path) {
        String actualPathValue = jp.getString(path);
        Assert.assertEquals(expectedpathValue, actualPathValue);


    }

    @Then("the following fields should not be null")
    public void the_following_fields_should_not_be_null(List<String > listPath) {
        for (String eachPath : listPath) {
          //  System.out.println("jp.getString(eachPath) = " + jp.getString(eachPath));
            Assert.assertNotNull(jp.getString(eachPath));
        }


    }
    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String requestContentType) {
        givenPart.contentType(requestContentType);

    }
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String dataType) {
        switch (dataType){
            case "book":
                 randomData = LibraryAPI_Util.getRandomBookMap();
                 break;
            case "user":
                randomData = LibraryAPI_Util.getRandomUserMap();
                break;
            default:
                throw new RuntimeException("Invalid Data Type "+ dataType);
        }
        givenPart.formParams(randomData);
        System.out.println("randomData = " + randomData);

    }


    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String message, String expectedMessageValue) {
        Assert.assertEquals(expectedMessageValue, jp.getString(message));
    }
    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        Assert.assertNotNull(path);

    }
    @Then("UI,Database and API created book information must match")
    public void ui_database_and_api_created_book_information_must_match() {
        // Create query to get information of new book from data base :

        //String query = "select B.name as 'book_name', isbn, BC.name as category, year, author from books B join book_categories BC on B.book_category_id = BC.id where B.id ="+id;
        String query = "select B.name as book_name, isbn, BC.name as category_name, year, author from books B join book_categories BC on B.book_category_id = BC.id where B.id ="+id+";";
        DB_Util.runQuery(query);
        // Create List of new book info from data Base Here we can not create Map because we do have to name in query for book name and category name
        // so when the Database return the info just give us back one of the name that why we have to use List Instead of Map:
        List<String> dbBookInfoList = DB_Util.getRowDataAsList(1);
        System.out.println("dbBookInfoList = " + dbBookInfoList);
        Collections.sort(dbBookInfoList);
        System.out.println("dbBookInfoList = " + dbBookInfoList);


        //Creat API List of new book info to compare with data base list :
         jp = givenPart.when().get("/get_book_by_id/" + id).then().statusCode(200).extract().jsonPath();
        Map<String, String > apiBookInfoMap = jp.getMap("");

        apiBookInfoMap.remove("description");
        apiBookInfoMap.remove("added_date");
        apiBookInfoMap.remove("id");
        System.out.println("ApiBookInfoMap = " + apiBookInfoMap);
        String category_Id = jp.getString("book_category_id");
        System.out.println("category_Id = " + category_Id);

        // Get the name of the Book Category Id name by using the it ;
       jp = givenPart.when().get("get_book_categories").then().statusCode(200).extract().jsonPath();
        String category_Book_Name = jp.getString("find {it.id == '" + category_Id + "'}.name");
        System.out.println("category_Book_Name = " + category_Book_Name);
        apiBookInfoMap.replace("book_category_id", category_Book_Name);

        // Converting the API map TO API List to compare with database List :
        List<String>apiBookInfoList = new ArrayList<>(apiBookInfoMap.values());
        Collections.sort(apiBookInfoList);
        System.out.println("apiBookInfoList = " + apiBookInfoList);

        //Verify Database and API created book information are match :
        Assert.assertEquals(dbBookInfoList, apiBookInfoList);

        // Search the new book in search box in UI to access the new book info :
        bookPage.search.sendKeys((CharSequence) randomData.get("name"));
        BrowserUtil.waitFor(3);
        List<String >uiBookInfoList = new ArrayList<>();
        for (WebElement element : bookPage.bookInfo) {
            uiBookInfoList.add(element.getText());

        }
        Collections.sort(uiBookInfoList);
        System.out.println("uiBookInfoList = " + uiBookInfoList);

        Assert.assertEquals(dbBookInfoList, uiBookInfoList);
        Assert.assertEquals(apiBookInfoList, uiBookInfoList);

    }

    @Then("created user information should be match with database")
    public void created_user_information_should_be_match_with_database() {

        String query = "select full_name,email,status,start_date,end_date,address from users\n" +
                "where id ="+id;
        DB_Util.runQuery(query);
        Map<String, Object> actualUserInfoMap = DB_Util.getRowMap(1);
        System.out.println("userInfoMap = " + actualUserInfoMap);
        String password = (String) randomData.remove("password");
        String  userGroupId = (String) randomData.remove("user_group_id");

        Assert.assertEquals(randomData, actualUserInfoMap);

        randomData.put("password", password);
        randomData.put("user_group_id", userGroupId);

    }
    @Then("created user should be able to login in Library UI")
    public void created_user_should_be_able_to_login_in_library_ui() {
        LoginPage loginPage = new LoginPage();

        loginPage.login((String) randomData.get("email"), (String) randomData.get("password"));
        System.out.println("accountHolderName.getText() = " + bookPage.accountHolderName.getText());

        BrowserUtil.waitForVisibility(bookPage.accountHolderName,10);

    }
    @Then("created user name should be appear in Dashboard Page")
    public void created_user_name_should_be_appear_in_dashboard_page() {
        Assert.assertEquals(randomData.get("full_name"), bookPage.accountHolderName.getText());

    }
    String token1;
    @Given("I logged in library with credentials {string} and {string}")
    public void i_logged_in_library_with_credentials_and(String email, String password) {
        Map<String ,String >loginUserPass = new HashMap<>();
        loginUserPass.put("email", email);
        loginUserPass.put("password", password);
        LibraryAPI_Util.getToken(email, password);
        token1 = LibraryAPI_Util.getToken(email, password);
        //givenPart.formParams(loginUserPass);

//        givenPart = given()
//                .formParam("email", email)
//                .formParam("password", password);
//        response = givenPart.when().post(ConfigurationReader.getProperty("library.baseUri") + "/login");
//        response.prettyPeek();
//        thenPart = response.then();
//        thenPart.statusCode(200);
//        jp =  response.jsonPath();



    }
    @Given("I send {string} information as a request body")
    public void i_send_information_as_a_request_body(String token) {

               givenPart.formParam("token",token1);

    }
}

