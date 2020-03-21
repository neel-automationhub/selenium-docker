package com.newtours.tests;

import com.newtours.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tests.BaseTest;

public class BookFlightTest extends BaseTest {

    private String noOfPassengers;
    private String expectedPrice;

    @BeforeTest
    @Parameters({"noOfPassengers", "expectedPrice"})
    public void setUpParameters(String noOfPassengers, String expectedPrice)
    {
        this.noOfPassengers = noOfPassengers;
        this.expectedPrice = expectedPrice;
    }

    @Test
    public void registrationPage(){
        RegistrationPage page = new RegistrationPage(this.driver);
        page.goTo();
        page.enterUserDetails("selenium", "docker");
        page.enterUserCredentials("selenium", "docker");
        page.submit();
    }

    @Test(alwaysRun = true, dependsOnMethods = "registrationPage")
    public void regConfirmationPage(){
        RegConfirmPage page = new RegConfirmPage(this.driver);
        page.goToFlightDetailsPage();
    }

    @Test(alwaysRun = true, dependsOnMethods = "regConfirmationPage")
    public void flightDetailsPage(){
        FlightDetailsPage detailsPage = new FlightDetailsPage(this.driver);
        detailsPage.selectPassengers(noOfPassengers);
        detailsPage.goTOFindFlightsPage();
    }

    @Test(alwaysRun = true, dependsOnMethods = "flightDetailsPage")
    public void findFlightPage(){
        FindFlightPage flightPage = new FindFlightPage(this.driver);
        flightPage.submitFindFlightPage();
        flightPage.goToFlightConfirmationPage();
    }

    @Test(alwaysRun = true, dependsOnMethods = "findFlightPage")
    public void flightConfirmationPage(){
        FlightConfirmationPage confirmationPage = new FlightConfirmationPage(this.driver);
        String actualPrice = confirmationPage.getPrice();
        Assert.assertEquals(actualPrice, expectedPrice);
    }

}
