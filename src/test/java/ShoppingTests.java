import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Assert;
import org.junit.Assume;
import pages.Strings;

import java.time.Duration;

public class ShoppingTests extends BaseTest {

    @Test
    public void AddAndRemoveItem () {

        driver.get(Strings.LENOVO_LAPTOP);
        driver.manage().window().maximize();

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='product-det-cart-btns']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Idi u korpu']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Nastavi kao neregistrovani kupac']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.className("remove-item"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Vratite')]"))).click();

        wait.until(ExpectedConditions.urlToBe(Strings.MAIN_URL));
        Assert.assertEquals("Povratak na početnu stranicu nije uspeo.", Strings.MAIN_URL, driver.getCurrentUrl());

    }

    @Test
    public void CompleteBuyOfItem () {
        Assume.assumeTrue("Kupovina se pokreće samo uz -DallowLivePurchase=true.", Boolean.getBoolean("allowLivePurchase"));

        driver.get(Strings.LENOVO_LAPTOP_BUY);
        driver.manage().window().maximize();

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='product-det-cart-btns']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Idi u korpu']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Nastavi kao neregistrovani kupac']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart-page-regular\"]/div[1]/div/div[2]/div[2]/div/button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Dalje']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"email\"]"))).sendKeys(Strings.VALID_EMAIL);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"address\"]"))).sendKeys("Gigatronska");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"name\"]"))).sendKeys("Boza Pub");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"phone\"]"))).sendKeys("+381612777777");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"postal_code\"]"))).sendKeys("11000");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"city\"]"))).sendKeys("Belgrade");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form-address-shipping\"]/div[1]/div[8]/div[4]/div[1]/label"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[2]/div/button[2]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body/div[@id='root']/div[@id='app']/div[@id='content']/div[@id='app-container']/div[@id='cart-page-regular']/div[@class='theme-wrap show']/div[@class='row']/div[@id='containerSelectorFocus']/div[@class='step-content']/div[@id='payment']/div[@class='cart-box']/div[@class='payments']/div[1]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart-page-regular\"]/div[1]/div/div[2]/div[2]/div/button[2]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='aggr']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart-page-regular\"]/div[1]/div/div[2]/div[2]/div/button[2]"))).click();

        wait.until(ExpectedConditions.urlContains("uspesna-kupovina"));
        Assert.assertTrue("Porudžbina nije potvrđena.", driver.getCurrentUrl().contains("uspesna-kupovina"));

    }

}
