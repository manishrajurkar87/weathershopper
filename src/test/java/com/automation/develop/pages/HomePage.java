package com.automation.develop.pages;

import com.automation.develop.base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BaseClass {
    String season = "No season";
    String temperature_string;

    /**
     * -----------------------------------------------------------------------------------------------------------
     * Object Repository for HomePage UI elements contains WebElements and Actions methods.
     * control is passed to next page based on the action.
     * Maintainable solution for changing locators xpath
     * author Manish Rajurkar
     * Date 14/12/2021
     * ----------------------------------------------------------------------------------------------------------------
     */

// Page Web elements locators (Object Repository)

    @FindBy(xpath = "//span[@id ='temperature']")
    @CacheLookup
    WebElement current_temperature;

    @FindBy(xpath = "//button[text()='Buy moisturizers']")
    @CacheLookup
    private WebElement buy_moisturiserButton;

    @FindBy(xpath = "//button[text()='Buy sunscreens']")
    @CacheLookup
    private WebElement buy_sunscreensButton;

    // constructor will initialize the page using PageFactory
    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    public void navigateToSeasonalProductCatalog() throws InterruptedException {
        temperature_string = current_temperature.getText();
        String[] temperature_array = temperature_string.split(" ");
        int temperature_value = Integer.parseInt(temperature_array[0]);

        if (temperature_value < 19) {
            season = "WINTER";
            navigateToMoisturiserPage();
        } else if (temperature_value > 34) {
            season = "SUMMER";
            navigateToSunscreenPage();
        } else {
            logger.error("Something is wrong");
        }
    }

    public BaseClass navigateToMoisturiserPage() throws InterruptedException {

        logger.info("It's " + season + " Season here & Today's Temperature is " + temperature_string + " Opening page to Buy Moisturiser");
        buy_moisturiserButton.click();
        return new MoisturizersPage().addProducts();
    }

    public BaseClass navigateToSunscreenPage() throws InterruptedException {

        logger.info("It's " + season + " Season here & Today's Temperature is " + temperature_string + " Opening page  to Buy Sunscreens");
        buy_sunscreensButton.click();
        return new SunscreensPage().addProducts();
    }

    /*-------------------------------------------------------
    @Comment: Open Given http url
    @developer : Manish Rajurkar
    @Date   : 15.12.2021
    ------------------------------------------------------- */
    public void opeUrl(String url) {
        try {
            if (url != null) {
                logger.info("Opening application URL  " + url);
                driver.get(url);
            } else {
                logger.error("Entered URL is null");
            }
        } catch (Exception e) {
            logger.error("Please check the URL");
            e.printStackTrace();
        }
    }
}
