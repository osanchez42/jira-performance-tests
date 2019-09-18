package com.atlassian.performance.tools.referencejiraapp.pages

import com.atlassian.performance.tools.jiraactions.api.page.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import java.lang.Exception
import java.time.Duration

class D42CustomFieldPage(
    private val driver: WebDriver,
    private val password: String //the jira password
)
{
    private fun loginAdministrator()
    {
        println("re-authenticating user to access custom field com.device42.performance.tools.api.page")

        //enter the password into the password field
        val loginFormPasswordField = driver.findElement(By.id("login-form-authenticatePassword"))
        if(loginFormPasswordField.isDisplayed)
        {
            loginFormPasswordField.sendKeys(password)
        }

        //confirm login
        val confirmLoginButton = driver.findElement(By.id("login-form-submit"))
        if(confirmLoginButton.isDisplayed)
        {
            confirmLoginButton.click()
        }
    }

    private val addCustomFieldIndicator = By.id("add_custom_fields")
    private val advancedSearchIndicator = By.xpath("//button[contains(text(), 'Advanced')]")

    fun setCustomField()
    {
        //if the user was taken to the admin access com.device42.performance.tools.api.page sign in
        if(driver.title.contains("Administrator Access"))
        {
            loginAdministrator()
        }

        //wait for the add custom field button element to load
        driver.wait(
            Duration.ofMinutes(4),
            ExpectedConditions.presenceOfElementLocated(addCustomFieldIndicator)
        )

        //once its loaded select it
        val addCustomFieldButton = driver.findElement(By.id("add_custom_fields"))

        if(addCustomFieldButton.isDisplayed)
        {
            addCustomFieldButton.click()
        }

        //wait for add custom field window to load
        driver.wait(
            Duration.ofMinutes(4),
            ExpectedConditions.presenceOfElementLocated(advancedSearchIndicator)
        )

        //check to see if that annoying reindex window is covering the custom field creation button
        try {
            val indexScreen = driver.findElement(By.className("icon-close"))
            if(indexScreen.isDisplayed)
            {
                println("Closing re-index screen covering needed element")
                indexScreen.click()
            }
        } catch (e: Exception)
        {
            println("Re-index screen not displayed, continuing")
        }

        //select the advanced field type button
        val advancedSearchButton = driver.findElement(By.xpath("//button[contains(text(), 'Advanced')]"))
        if(advancedSearchButton.isDisplayed)
        {
            var actions = Actions(driver)
            actions.moveToElement(advancedSearchButton).click().perform()
        }

        //type into the custom field search bar, device42 field should be selected by default
        val customFieldSearchBar = driver.findElement(By.className("jira-field-search"))
        if(customFieldSearchBar.isDisplayed)
        {
            customFieldSearchBar.sendKeys("Device42")
        }

        Thread.sleep(1000)

        //select the next button
        val nextButton = driver.findElement(By.id("customfields-select-type-next"))

        if (nextButton.isDisplayed)
        {
            nextButton.click()
        }

        //enter fields to create the custom field
        val nameField = driver.findElement(By.id("custom-field-name"))
        if(nameField.isDisplayed)
        {
            nameField.sendKeys("Device42 Custom Field")
        }

        //create the custom field
        val createCustomField = driver.findElement(By.id("customfields-configure-next"))

        if(createCustomField.isDisplayed)
        {
            createCustomField.click()
        }

        println("created custom field, adding field to all screens")

        Thread.sleep(1000)
        //add custom field to all pages
        val allScreens = driver.findElements(By.xpath("//input[@type='checkbox']"))

        for(checkbox in allScreens)
        {
            if(!checkbox.isSelected) {
                checkbox.click()
            }
        }

        //hit the button! the update button
        val theButton = driver.findElement(By.id("update_submit"))

        if(theButton.isDisplayed)
        {
            theButton.click()
        }

        println("Device42 Plugin Configuration Completed")

    }

}