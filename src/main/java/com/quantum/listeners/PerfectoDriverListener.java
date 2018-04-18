package com.quantum.listeners;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebDriverCommandAdapter;

public class PerfectoDriverListener extends QAFWebDriverCommandAdapter {

    @Override
    public void onInitialize(QAFExtendedWebDriver driver) {
        String appName = ConfigurationManager.getBundle().getString("appName");
        
        //Close Application if already its opened
        String command = "mobile:application:close";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", appName);
        try {
            ((RemoteWebDriver) driver.getUnderLayingDriver())
                    .executeScript(command, params);
        } catch (Exception e) {
            System.err.println("Unable to close app: " + appName);
        }

        //Open Application
        command = "mobile:application:open";
        ((RemoteWebDriver) driver.getUnderLayingDriver()).executeScript(
                command, params);
        
        
        //Switch Context
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(
                (RemoteWebDriver) driver.getUnderLayingDriver());
        params.clear();
        params.put("name","NATIVE");
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
    }

}