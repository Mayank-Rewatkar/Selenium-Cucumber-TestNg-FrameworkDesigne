package definitions;

import com.diligenta.deathClaim.deathClaimBase;
import com.diligenta.pages.LoginPage;
import com.diligenta.pages.PageFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class StepDefinitionDeathLife extends deathClaimBase
{
        LoginPage obj_loginPage;


        @Given("I Landed on Ecommerce Page")
        public void I_Landed_on_Ecommerce_Page() throws IOException {
                init();
                loadBrowser();
                Add_Log.info("Browser launched");
        }

        //^ -> $ indicates Regular expression
        @Given("^Login with username (.+) and password (.+)$")
        public void Login_with_username_and_password(String username, String Password) {
                obj_loginPage = new LoginPage(getDriver());
                //-------------** Methods Calling and Actual Execution Starts Here **-----------------
                obj_loginPage.login();
                PageFunctions.takeScreenShotWithText(getDriver(), "Cucumber Test", "Login Page");
                obj_loginPage.loginApplication(username, Password);
                PageFunctions.takeScreenShotWithText(getDriver(), "111", "Login Details Added");
        }

        @When("click product to cart")
        public void click_product_to_cart()
        {
                Add_Log.info("When Executed");
        }

        @Then("checkout and submit the order")
        public void checkout_and_submit_the_order()
        {
                Add_Log.info("Then Executed");
        }

}
