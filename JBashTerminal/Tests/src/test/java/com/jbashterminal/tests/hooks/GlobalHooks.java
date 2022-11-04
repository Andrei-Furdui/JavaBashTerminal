package com.jbashterminal.tests.hooks;

import com.jbashterminal.tests.tests.TouchAndLS;
import com.jbashterminal.windows.common.LS;
import com.jbashterminal.windows.common.RM;
import com.jbashterminal.windows.common.TOUCH;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.List;
import java.util.function.Consumer;

public class GlobalHooks {

    private LS lsObject;
    private TOUCH touchObject;

    @Before(value = "@TOUCH_LS")
    public void initializeObjects() {
        lsObject = new LS();
        touchObject = new TOUCH();
    }

    public LS getLSObject() {
        return this.lsObject;
    }

    public TOUCH getTouchObject() {
        return this.touchObject;
    }

    @After(value = "@TOUCH_LS")
    public void removeItems() {
        List<String> items = TouchAndLS.listLSItems;

        Consumer<String> removeItem = (item) -> {
            RM rm = new RM();
            rm.setFileName(item);
            rm.executeCommand("");
        };
        items.forEach(removeItem);
    }
}
