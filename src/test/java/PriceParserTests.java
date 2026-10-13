import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import pages.PriceParser;

/** Lokalni testovi obrade cena: ne otvaraju browser i ne pristupaju Gigatronu. */
public class PriceParserTests {
    /**
     * Test - Parse a price with thousands separators and currency
     * Steps:
     * 1. Pass 199.999,00 RSD to the price parser.
     * 2. Compare the result with BigDecimal 199999.00.
     * Expected result:
     * The parser returns exactly 199999.00 without losing the decimal part.
     */
    @Test
    public void testPriceWithThousandsAndCurrency() {
        // Format sa sajta mora sačuvati i hiljade i decimalni deo.
        Assert.assertEquals(new BigDecimal("199999.00"), PriceParser.parse("199.999,00 RSD"));
    }

    /**
     * Test - Parse a price containing HTML spaces
     * Steps:
     * 1. Prepare a price containing non-breaking and narrow non-breaking spaces.
     * 2. Parse the price and compare its numeric value.
     * Expected result:
     * HTML spaces and the RSD label are removed; the result is 199999.00.
     */
    @Test
    public void testPriceWithHtmlSpaces() {
        // HTML razmaci ne smeju pokvariti poređenje cena u testovima sortiranja.
        Assert.assertEquals(new BigDecimal("199999.00"), PriceParser.parse("199\u00a0999,00\u202fRSD"));
    }

    /**
     * Test - Parse a price without a thousands separator
     * Steps:
     * 1. Pass 999,99 RSD to the parser.
     * 2. Compare the result with BigDecimal 999.99.
     * Expected result:
     * The decimal comma is correctly interpreted and the result is 999.99.
     */
    @Test
    public void testPriceWithoutThousandsSeparator() {
        // Manja cena može biti prikazana bez tačke za hiljade.
        Assert.assertEquals(new BigDecimal("999.99"), PriceParser.parse("999,99 RSD"));
    }

    /**
     * Test - Parse a whole-number price
     * Steps:
     * 1. Pass 1.000 RSD without a decimal part to the parser.
     * 2. Compare the result with BigDecimal 1000.
     * Expected result:
     * The thousands separator is removed and the result is exactly 1000.
     */
    @Test
    public void testWholeNumberPrice() {
        // Prihvatamo i prikaz bez decimalnog dela.
        Assert.assertEquals(new BigDecimal("1000"), PriceParser.parse("1.000 RSD"));
    }

    /**
     * Test - Parse a zero price
     * Steps:
     * 1. Pass 0,00 RSD to the parser.
     * 2. Compare the result with BigDecimal 0.00.
     * Expected result:
     * The parser returns 0.00. Product UI tests separately require positive selling prices.
     */
    @Test
    public void testZeroPrice() {
        // Nulu parser prepoznaje; UI test zasebno zahteva pozitivnu prodajnu cenu.
        Assert.assertEquals(new BigDecimal("0.00"), PriceParser.parse("0,00 RSD"));
    }

    /**
     * Test - Preserve exact price precision
     * Steps:
     * 1. Pass 1.234.567,89 RSD to the parser.
     * 2. Compare the result with BigDecimal 1234567.89.
     * Expected result:
     * Both thousands groups and the decimal part are preserved without floating-point rounding.
     */
    @Test
    public void testDecimalPrecision() {
        // Decimalni iznos se ne zaokružuje kroz double ili float.
        Assert.assertEquals(new BigDecimal("1234567.89"), PriceParser.parse("1.234.567,89 RSD"));
    }

    /**
     * Test - Reject missing or blank prices
     * Steps:
     * 1. Call the parser with null.
     * 2. Call the parser with a blank string.
     * 3. Check the exception from each call.
     * Expected result:
     * Both inputs throw IllegalArgumentException instead of being treated as zero.
     */
    @Test
    public void testMissingPriceIsRejected() {
        // Nedostajući podatak ne sme tiho da postane cena 0.
        Assert.assertThrows(IllegalArgumentException.class, () -> PriceParser.parse(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> PriceParser.parse(" "));
    }

    /**
     * Test - Reject malformed price values
     * Steps:
     * 1. Prepare invalid thousands grouping, decimal precision, a negative price, another currency and text instead of a price.
     * 2. Pass each value to the parser.
     * 3. Check that every invalid value is rejected.
     * Expected result:
     * Every supplied invalid value throws IllegalArgumentException.
     */
    @Test
    public void testMalformedPriceIsRejected() {
        // Hvata pogrešno grupisanje, pogrešnu valutu i tekst umesto cene.
        for (String invalid : new String[]{"19.99,00 RSD", "999,9 RSD", "-1,00 RSD", "199.99 USD", "Cena na upit"}) {
            Assert.assertThrows("Neispravan format mora biti odbijen: " + invalid,
                    IllegalArgumentException.class, () -> PriceParser.parse(invalid));
        }
    }
}
