package com.jbashterminal.tests.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/features", glue = {"com.jbashterminal.tests.tests", "com.jbashterminal.tests.hooks"},
        tags = "@OS_API or @TOUCH_LS")
public class TestRunner {
}
