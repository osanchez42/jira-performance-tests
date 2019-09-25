package com.atlassian.performance.tools.referencejiraapp.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class D42ScanPage(
    private val driver: WebDriver
)
{
    private val runFullScanIndicator = By.id("fullRescan")

    fun runScan()
    {
        //ensure the the com.device42.performance.tools.api.page is loaded before proceeding
        Thread.sleep(5000)

        val fullRescan = driver.findElement(By.id("fullRescan"))
        val agree = driver.findElement(By.id("agreeUpdate"))
        val updateDataButton = driver.findElement(By.cssSelector("#content > div > div > section > form > div.buttons-container > div > input"))

        //check all options
        fullRescan.click()
        agree.click()
        updateDataButton.click()

        //wait for scan to complete for a maximum of 1 minute
        for(x in 0..10)
        {
            Thread.sleep(10000)
            driver.navigate().refresh()
            if(driver.pageSource.contains("Scan completed successfully"))
            {
                println("Scan completed successfully")
                break;
            }
        }

        if(!driver.pageSource.contains("Scan completed successfully"))
        {
            println("Scan did not complete")
        }
    }

}
