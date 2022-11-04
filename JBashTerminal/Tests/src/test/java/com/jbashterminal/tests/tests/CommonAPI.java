package com.jbashterminal.tests.tests;

import com.jbashterminal.windows.common.Helper;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

public class CommonAPI {
    private static String osName;
    private static String osVersion;
    private static String finalVersion;

    @Given("the Helper class is called")
    public void the_helper_class_is_called() {
        boolean result = Helper.classCalled;
        assertFalse(result);
    }

    @When("the getOSName method is used")
    public void the_get_os_name_method_is_used() {
        osName = Helper.getOSName();
    }

    @And("the getOSVersion method is used")
    public void the_get_os_version_method_is_used() {
        osVersion = Helper.getOSVersion();
    }

    @And("specifying the comma between their results")
    public void specifying_the_comma_between_their_results() {
        finalVersion = osName + ", " + osVersion;
    }

    @Then("the result should return the following message:")
    public void the_result_should_return_the_following_message(io.cucumber.datatable.DataTable dataTable) {
        String msg = dataTable.cell(1, 0);
        assertEquals(finalVersion, msg);
        assertTrue(Helper.classCalled);
    }
}
