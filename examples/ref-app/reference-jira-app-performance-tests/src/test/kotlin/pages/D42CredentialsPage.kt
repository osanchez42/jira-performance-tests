package com.atlassian.performance.tools.referencejiraapp.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class D42CredentialsPage(
    private val driver: WebDriver,
    private val d42BaseUrl: String,
    private val d42Username: String,
    private val d42Password: String

)
{
    private val configLocator = By.id("baseUrl")

    private val baseUrl = driver.findElement(By.id("baseUrl"))
    private val username = driver.findElement(By.id("username"))
    private val password = driver.findElement(By.id("password"))
    private val saveButton = driver.findElement(By.cssSelector("#content > div.aui-com.device42.performance.tools.api.page-panel > div > section > form > div.buttons-container > div > input"))

    fun loginJira()
    {
        val enterUsername = "admin"
        val enterPassword = "admin"

        val username =  driver.findElement(By.id("login-form-username"))
        val password = driver.findElement(By.id("login-form-password"))
        val login_submit = driver.findElement(By.id("login-form-submit"))

        //clear possibly existing text
        username.clear()
        password.clear()

        //enter username and password
        username.sendKeys(enterUsername)
        password.sendKeys(enterPassword)

        //submit form
        login_submit.click()

        println("Logged into Jira, configuring D42 connector...")
    }

    fun configureD42Plugin(
    )
    {
        //check if the user is logged in, or they was redirected to a login screen
        val needLogin = driver.title.contains("Log in")

        if(needLogin)
        {
            println("Need to login to Jira....")
            loginJira()
        }

        //ensure the the com.device42.performance.tools.api.page is loaded before proceeding
        Thread.sleep(5000)

        //clear the fields just in case there is text inside them
        baseUrl.clear()
        username.clear()
        password.clear()

        //enter the device42 credentials
        baseUrl.sendKeys(d42BaseUrl)
        username.sendKeys(d42Username)
        password.sendKeys(d42Password)

        //send the form
        saveButton.click()

        //wait 10 seconds to allow the request to be made and com.device42.performance.tools.api.page to update
        Thread.sleep(2000)

        //make sure configuration was completed
        if(driver.pageSource.contains("Device42 server returned 403") || driver.pageSource.contains("Error to call REST API"))
        {
            println("Device42 connection failed, cancelling configuration")
        }
        else
        {
            println("Connection successful, running scan")
        }
    }
}