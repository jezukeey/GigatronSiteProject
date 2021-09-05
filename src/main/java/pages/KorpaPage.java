package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class KorpaPage extends BasePage{


    //Agree with  conditions of buying
    @FindBy(xpath="//input[@id='aggr']")
    WebElement agreeWithConditions;

    //Successful purchase message on Checkout complete page
    @FindBy(xpath="//*[@id=\"app-container\"]/div/div/div/div[1]/div[1]/h1")
    WebElement successfulPurchaseMessage;

    //Error message on Checkout complete page
    @FindBy(xpath="//*[@id=\"app-container\"]/div/div/div/div/div/h4")
    WebElement checkOutErrorMessage;

    public KorpaPage (ChromeDriver driver) {
        super(driver);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Vaša porudžbenica je uspešno kreirana", currentUrl.equals(Strings.KORPA_URL));
    }




    public KorpaPage clickAgreeWithConditions() {
        //Check first if web element is displayed on a web page
        assert agreeWithConditions.isDisplayed();
        agreeWithConditions.click();
        return this;
    }

}
