package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    //Complete buying ERROR MESSAGE
    @FindBy(xpath= "//*[@id=\"app-container\"]/div/div/div/div/div/h4")
    WebElement checkOutPageErrorMessage;

    //Back to cart button
    @FindBy(xpath= "//*[@id=\"app-container\"]/div/div/div/div/div/button")
    WebElement backCartButton;

    public CheckoutCompletePage(ChromeDriver driver) {
        super(driver);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("We are not on Checkout Complete page", currentUrl.equals(Strings.KORPA_URL));
    }

    public String checkOutPageErrorMessage() {
        String errorMessage=checkOutPageErrorMessage.getText();;
        return errorMessage;
    }



}
