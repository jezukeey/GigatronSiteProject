import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Strings;

import java.time.Duration;

public class ShoppingTests extends BaseTest {


//       //Test case : Tests for adding items and removing
//    /*
//    Test: Adding and removing item from Inventory Page
//    Steps:
//    1. Login to Gigatron site
//    2. Add 1 item to shopping cart by clicking on 'add to cart' button
//    3. Remove item from shopping cart by clicking 'remove' button on Inventory page
//    4. Go back to Home page

    @Test
    public void AddAndRemoveItem () {
        ChromeDriver driver=new ChromeDriver();
        driver.get(Strings.LENOVO_LAPTOP);
        driver.manage().window().maximize();

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(3));
        //Add product to cart
        driver.findElement(By.xpath("//div[@class='product-det-cart-btns']")).click();
        sleep();

        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        //Go to cart
        sleep();
        driver.findElement(By.xpath("//button[normalize-space()='Idi u korpu']")).click();
        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        sleep();
        //Close pop up
        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.xpath("//button[normalize-space()='Nastavi kao neregistrovani kupac']")).click();
        //Remove item from cart
        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.className("remove-item")).click();
        //Go back to Base Page
        sleep();
        driver.findElement(By.xpath("//*[contains(text(), 'Vratite')]")).click();
        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        String URL=driver.getCurrentUrl();
        if (URL.contains("korpa")) {
            System.out.println("You are still on Korisnik page " +
                    "" + URL);

        } else {
            System.out.println("You are back on Base page");
        }

        driver.quit();
    }
    //       //Test case : Tests for adding items and removing
//    /*
//    Test: Adding and removing item from Inventory Page
//    Steps:
//    1. Navigate to https://gigatron.rs/laptop-racunari/lenovo-ideapad-3-14ada05-81w0005tya-385007
//    2. Add 1 item to shopping cart by clicking on 'add to cart' button
//    3. Close pop up
//    4. Choose way of delivery
//    5. Enter customer information
//    6. Choose payment methods
//    7. Agree to the terms of purchase
//    8. Finish shopping


    @Test
    public void CompleteBuyOfItem () {
        try {
        ChromeDriver driver=new ChromeDriver();
        driver.get(Strings.LENOVO_LAPTOP_BUY);
        driver.manage().window().maximize();

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(9));

//        //Click accept cookie
//        driver.findElement(By.xpath("//*[text()='Prihvatam']")).click();

        //Add product to cart
        driver.findElement(By.xpath("//div[@class='product-det-cart-btns']")).click();
        sleep();

        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        //Go to cart
        sleep();
        driver.findElement(By.xpath("//button[normalize-space()='Idi u korpu']")).click();
        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        sleep();
        //Close pop up
        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.xpath("//button[normalize-space()='Nastavi kao neregistrovani kupac']")).click();
        //Continue to pay
        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.xpath("//*[@id=\"cart-page-regular\"]/div[1]/div/div[2]/div[2]/div/button")).click();
        //Method of shipping and click forward
        sleep();
        driver.findElement(By.xpath("//button[normalize-space()='Dalje']")).click();
        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        //Customer e-mail
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(Strings.VALID_USER);

        //Customer address
        driver.findElement(By.xpath("//*[@id=\"address\"]")).sendKeys("Gigatronska");

        //Name and surname
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("Boza Pub");

        //Phone number
        driver.findElement(By.xpath("//*[@id=\"phone\"]")).sendKeys("+381612777777");
        //Postal code
        driver.findElement(By.xpath("//*[@id=\"postal_code\"]")).sendKeys("11000");
        //City
        driver.findElement(By.xpath("//*[@id=\"city\"]")).sendKeys("Belgrade");

        wait=new WebDriverWait(driver, Duration.ofSeconds(3));

        //Choose a buyer in a legal sense
        driver.findElement(By.xpath("//*[@id=\"form-address-shipping\"]/div[1]/div[8]/div[4]/div[1]/label"));

        wait=new WebDriverWait(driver, Duration.ofSeconds(3));

        //Forward to paying
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[1]/div/div[2]/div[2]/div/button[2]")).click();

        //Method of payment
        driver.findElement(By.xpath("//body/div[@id='root']/div[@id='app']/div[@id='content']/div[@id='app-container']/div[@id='cart-page-regular']/div[@class='theme-wrap show']/div[@class='row']/div[@id='containerSelectorFocus']/div[@class='step-content']/div[@id='payment']/div[@class='cart-box']/div[@class='payments']/div[1]")).click();

        wait=new WebDriverWait(driver, Duration.ofSeconds(5));


        //Complete the purchase
        driver.findElement(By.xpath("//*[@id=\"cart-page-regular\"]/div[1]/div/div[2]/div[2]/div/button[2]")).click();
        sleep();
        //Click check box I agree with conditions
        driver.findElement(By.xpath("//input[@id='aggr']")).click();
        sleep();
        driver.findElement(By.xpath("//*[@id=\"cart-page-regular\"]/div[1]/div/div[2]/div[2]/div/button[2]")).click();

        wait=new WebDriverWait(driver, Duration.ofSeconds(5));
//            String currentURL = driver.getCurrentUrl();
//            assert currentURL.equals(SUCCESSFUL_PURCHASE_MESSAGE) : "Expected text: " + Strings.SUCCESSFUL_PURCHASE_MESSAGE + ". Actual: "
//                    + equals(Strings.SUCCESSFUL_PURCHASE_ERROR_MESSAGE);

        String URL=driver.getCurrentUrl();
        if (URL.contains("uspesn")) {
            System.out.println("Vaša porudžbenica je uspešno kreirana!" +
                    "" + URL);

        } else {
            System.out.println("DOŠLO JE DO GREŠKE PRILIKOM PROCESUIRANJA VAŠE PORUDŽBINE");
        }
        System.out.println("DOŠLO JE DO GREŠKE PRILIKOM PROCESUIRANJA VAŠE PORUDŽBINE");

    } finally {
        }
        driver.quit();
    }


}
