package pages;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class PrenosniRacunariPage {

    //Select prenosivi racunari
    @FindBy(id="main-nav-1")
    WebElement prenosniRacunari;

    //Select dropdown menu price accidental
    @FindBy(xpath="/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/select[1]")
    WebElement cenaOpadajuca;
}
