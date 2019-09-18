package com.atlassian.performance.tools.referencejiraapp.actions;

import com.atlassian.performance.tools.jiraactions.api.WebJira;
import com.atlassian.performance.tools.jiraactions.api.action.Action;
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter;
import com.atlassian.performance.tools.referencejiraapp.pages.D42CredentialsPage;
import com.atlassian.performance.tools.referencejiraapp.pages.D42CustomFieldPage;
import com.atlassian.performance.tools.referencejiraapp.pages.D42ScanPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurePluginAction implements Action  {
    private final static Logger logger = LogManager.getLogger(ConfigurePluginAction.class);

    private final WebJira jira;
    private final ActionMeter meter;

    //d42 configuration fields
    private final String d42Url;
    private final String d42Username;
    private final String d42Password;

    public ConfigurePluginAction(WebJira jira, ActionMeter meter, String d42Url, String d42Username, String d42Password)
    {
        this.jira = jira;
        this.meter = meter;
        this.d42Url = d42Url;
        this.d42Username = d42Username;
        this.d42Password = d42Password;
    }

    @Override
    public void run() {
        //jira.adminPassword is private so had to specify it manually here
        String adminPassword = "admin";

        //configure credentials
        jira.navigateTo("plugins/servlet/d42/admin?action=getEdit");
        new D42CredentialsPage(jira.getDriver(), d42Url, d42Username, d42Password).configureD42Plugin();

        //run the d42 scan
        jira.navigateTo("/plugins/servlet/d42/admin?action=getUpdate");
        new D42ScanPage(jira.getDriver()).runScan();

        //configure the custom field on all pages
        jira.navigateTo("/secure/admin/ViewCustomFields.jspa");
        new D42CustomFieldPage(jira.getDriver(), adminPassword).setCustomField();
    }
}