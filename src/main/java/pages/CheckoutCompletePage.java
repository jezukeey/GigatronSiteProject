package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class CheckoutCompletePage extends BasePage {
    public CheckoutCompletePage(ChromeDriver driver) { super(driver); }

    public String checkOutPageErrorMessage() {
        return visible(By.cssSelector("#app-container h4")).getText().trim();
    }
}