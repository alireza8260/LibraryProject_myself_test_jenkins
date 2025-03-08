package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import pages.LoginPage;

public class UIStepsDefs extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(UIStepsDefs.class);
    LoginPage loginPage = new LoginPage();

    @Given("I logged in Library UI as {string}")
    public void i_logged_in_library_ui_as(String userType) {
        loginPage.login(userType);


    }
    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String moduleName) {
        navigateModule(moduleName);



    }

}
