package com.jbashterminal.tests.tests;

import com.jbashterminal.tests.hooks.GlobalHooks;
import com.jbashterminal.windows.common.LS;
import com.jbashterminal.windows.common.TOUCH;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TouchAndLS {
    private LS lsObject;
    private TOUCH touchObject;
    private String lsItems;

    public static List<String> listLSItems;

    public TouchAndLS(GlobalHooks globalHooks) {
        this.lsObject = globalHooks.getLSObject();
        this.touchObject = globalHooks.getTouchObject();
        listLSItems = new ArrayList<>();
    }

    @Given("the touch command is executed to create the {string}")
    public void the_touch_command_is_executed_to_create_the(String string) {
        touchObject.setFileName(string);
        // strange way to verify but the method executeCommand
        // should return null in case of success
        assertNull(touchObject.executeCommand(""));
    }

    @When("the ls command for the current directory is executed")
    public void the_ls_command_for_the_current_directory_is_executed() {
        lsItems = lsObject.executeCommand("ls");
    }

    @Then("the new {string} should be listed among the other existing ones")
    public void the_new_should_be_listed_among_the_other_existing_ones(String string) {
        if (!lsItems.contains(string)) {
            fail("The file: " + string + " not created!");
        }
        listLSItems.add(string);
    }
}
