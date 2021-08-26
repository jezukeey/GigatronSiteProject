import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.KorisnikPage;
import pages.Strings;

import java.util.List;

public class SortingTests extends BaseTest{

    //Test for sorting in ascending order
//    @Test
//    public void testSortingByPriceAscending() {
//        ChromeDriver driver =  openChromeDriver();
//        try {
//            KorisnikPage inventoryPage = loginToKorisnikPage(driver);
//            List<String> itemPricesUnsorted = inventoryPage.getAllItemPrices();
//            inventoryPage.sortBy(Strings.SORT_BY_PRICE_ASC);
//            List<String> itemPricesSorted = inventoryPage.getAllItemPrices();
//            for(int i = 0; i<itemPricesSorted.size()-1; i++) {
//                float first = Float.parseFloat(itemPricesSorted.get(i));
//                float second = Float.parseFloat(itemPricesSorted.get(i+1));
//                if (second < first)
//                    System.out.println("ERROR. Array is not sorted");
//            }
//        }finally {
//            driver.quit();
//        }
//
//    }
//
//    private KorisnikPage loginToKorisnikPage (ChromeDriver driver) {
//        return null;
//    }

}