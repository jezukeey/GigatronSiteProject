package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.text.BreakIterator;

public class KorisnikPage extends BasePage {
    @FindBy(className= "profile-card profileCardHeader")
    WebElement profileCardHeader;

    @FindBy(className = "barcodesWrapperDeskd")
    WebElement barcodesWrapper;


    public KorisnikPage(ChromeDriver driver) {
        super(driver);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("Korišćenje podataka korisnika", Strings.KORISNIK_URL, currentUrl);
    }

}
