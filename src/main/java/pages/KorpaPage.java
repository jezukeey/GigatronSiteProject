package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class KorpaPage extends BasePage {
    public KorpaPage(ChromeDriver driver) { super(driver); }

    public KorpaPage clickAgreeWithConditions() {
        var checkbox = visible(By.id("aggr"));
        if (!checkbox.isSelected()) click(By.id("aggr"));
        return this;
    }
}