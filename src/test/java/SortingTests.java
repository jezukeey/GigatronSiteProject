import org.openqa.selenium.chrome.ChromeDriver;
import pages.BasePage;

public class SortingTests extends BasePage {
    public SortingTests (ChromeDriver driver) {
        super(driver);
    }

//    //Test for sorting price in ascending order
//    @Test
//    public void testSortingByPriceAscending() {
//        ChromeDriver driver = openChromeDriver();
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
//    @Contract(pure=true)
//    private @Nullable KorisnikPage loginToKorisnikPage (ChromeDriver driver) {
//        return null;
//    }

}