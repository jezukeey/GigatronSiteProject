import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.KorisnikPage;
import pages.LoginPage;
import pages.Strings;

import java.util.concurrent.atomic.AtomicReference;

public class LoginTests extends BaseTest {


        /**
     * Test - Successful login with valid credentials
     * Steps:
     * 1. Navigate to gigatron site
     * 2. enter valid username
     * 3. enter valid password
     * 4.click login button
     * <p>
     * Expected result:
     * 4.Verify that user is logged in
     */
    @Test
    public void testSuccessfulLogIn() {
        ChromeDriver driver = openChromeDriver();
        LoginPage loginPage = new LoginPage(driver);
        WebElement userNameField=driver.findElement(By.id("email"));
        userNameField.click();
        userNameField.sendKeys(Strings.VALID_USER);
        WebElement passwordField=driver.findElement(By.id("password"));
        passwordField.click();
        passwordField.sendKeys(Strings.VALID_PASSWORD);
        WebElement loginButton=driver.findElement(By.id("loginSubmit"));
        loginButton.click();
       String currentPageURL = driver.getCurrentUrl();
        Assert.assertTrue("We are not logged in. Expected url : " + Strings.KORISNIK_URL +
                ". Actual: " + currentPageURL, currentPageURL.contains(Strings.KORISNIK_URL));
//        KorisnikPage.clickBurgerButton()
//                .clickLogout();

        driver.quit();
    }



//    /**
//     * Test - Successful login with valid credentials
//     * Steps:
//     * 1. Navigate to sauce lab demo site
//     * 2. enter valid username
//     * 3. enter valid password
//     * 4.click login button
//     *
//     * Expected result:
//     * 4.Verify that user is logged in
//     */
//    @Test
//    public void testlLogIn() {
//        ChromeDriver driver =  openChromeDriver();
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.enterEmailAddress(Strings.VALID_USER)
//                .enterPassword(Strings.VALID_PASSWORD);
//        KorisnikPage inventoryPage = loginPage.clickSubmitButtonSuccess();
//        String currentPageURL = driver.getCurrentUrl();
//        Assert.assertTrue("We are not logged in. Expected url : " + Strings.KORISNIK_URL +
//                ". Actual: " + currentPageURL, currentPageURL.contains(Strings.KORISNIK_URL));
//        inventoryPage.clickBurgerButton()
//                .clickLogout();
//
//        driver.quit();
//    }

//    /**
//     * Test - Successful login with valid credentials
//     * Steps:
//     * 1. Navigate to gigatron site
//     * 2. enter valid username
//     * 3. enter valid password
//     * 4.click login button
//     * <p>
//     * Expected result:
//     * 4.Verify that user is logged in
//     */
//    @Test
//    public void testSuccessfulLogIn() {
//        ChromeDriver driver = openChromeDriver();
//        LoginPage loginPage = new LoginPage(driver);
//        WebElement userNameField=driver.findElement(By.id("email"));
//        userNameField.click();
//        userNameField.sendKeys(Strings.VALID_USER);
//        WebElement passwordField=driver.findElement(By.id("password"));
//        passwordField.click();
//        passwordField.sendKeys(Strings.VALID_PASSWORD);
//        WebElement loginButton=driver.findElement(By.id("loginSubmit"));
//        loginButton.click();
//       String currentPageURL = driver.getCurrentUrl();
//
//        driver.quit();
//    }

//    private void enterUserName(String validUserName) {
//    }
//    private void enterPassword(String validPassword) {
//    }


////    /**
//     * Test - Unsuccessful login with invalid username and valid pass
//     * Steps:
//     * 1. Navigate to sauce lab demo site
//     * 2. enter invalid username
//     * 3. enter valid password
//     * 4. click login button
//     *
//     * Expected result:
//     * 4.Verify that user is not logged in, stays on login page
//     *  Verify that error message is shown
//     */
//
//    @Test
//    public void testInvalidUsernameAndValidPass() {
//        ChromeDriver driver=openChromeDriver();
//        try {
//            LoginPage loginPage=new LoginPage(driver);
//            WebElement userNameField=driver.findElement(By.id("email"));
//            userNameField.click();
//            userNameField.sendKeys(Strings.INVALID_USER);
//            WebElement passwordField=driver.findElement(By.id("password"));
//            passwordField.click();
//            passwordField.sendKeys(Strings.VALID_PASSWORD);
//            WebElement loginButton=driver.findElement(By.id("loginSubmit"));
//            loginButton.click();
//            WebElement loggedInUser=driver.findElement(By.id("username"));
//            String loginUrl=driver.getCurrentUrl();
//
//            driver.quit();
//
//        } finally {
//            driver.quit();
//        }
//    /**
//     * Test - Successful login with valid credentials
//     * Steps:
//     * 1. Navigate to sauce lab demo site
//     * 2. enter valid username
//     * 3. enter valid password
//     * 4.click login button
//     *
//     * Expected result:
//     * 4.Verify that user is logged in
//     */
//    @Test
//    public void testlLogIn() {
//        ChromeDriver driver =  openChromeDriver();
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.enterEmailAddress(Strings.VALID_USER)
//                .enterPassword(Strings.VALID_PASSWORD);
//        KorisnikPage inventoryPage = loginPage.clickSubmitButtonSuccess();
//        String currentPageURL = driver.getCurrentUrl();
//        Assert.assertTrue("We are not logged in. Expected url : " + Strings.KORISNIK_URL +
//                ". Actual: " + currentPageURL, currentPageURL.contains(Strings.KORISNIK_URL));
//        inventoryPage.clickBurgerButton()
//                .clickLogout();
//
//        driver.quit();
//    }
//
//
//        private void clickSubmitButtonFailure () {
//        }


}