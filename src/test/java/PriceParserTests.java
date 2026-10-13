import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import pages.PriceParser;

/** Lokalni testovi obrade cena: ne otvaraju browser i ne pristupaju Gigatronu. */
public class PriceParserTests {
    @Test
    public void testPriceWithThousandsAndCurrency() {
        // Format sa sajta mora sačuvati i hiljade i decimalni deo.
        Assert.assertEquals(new BigDecimal("199999.00"), PriceParser.parse("199.999,00 RSD"));
    }

    @Test
    public void testPriceWithHtmlSpaces() {
        // HTML razmaci ne smeju pokvariti poređenje cena u testovima sortiranja.
        Assert.assertEquals(new BigDecimal("199999.00"), PriceParser.parse("199\u00a0999,00\u202fRSD"));
    }

    @Test
    public void testPriceWithoutThousandsSeparator() {
        // Manja cena može biti prikazana bez tačke za hiljade.
        Assert.assertEquals(new BigDecimal("999.99"), PriceParser.parse("999,99 RSD"));
    }

    @Test
    public void testWholeNumberPrice() {
        // Prihvatamo i prikaz bez decimalnog dela.
        Assert.assertEquals(new BigDecimal("1000"), PriceParser.parse("1.000 RSD"));
    }

    @Test
    public void testZeroPrice() {
        // Nulu parser prepoznaje; UI test zasebno zahteva pozitivnu prodajnu cenu.
        Assert.assertEquals(new BigDecimal("0.00"), PriceParser.parse("0,00 RSD"));
    }

    @Test
    public void testDecimalPrecision() {
        // Decimalni iznos se ne zaokružuje kroz double ili float.
        Assert.assertEquals(new BigDecimal("1234567.89"), PriceParser.parse("1.234.567,89 RSD"));
    }

    @Test
    public void testMissingPriceIsRejected() {
        // Nedostajući podatak ne sme tiho da postane cena 0.
        Assert.assertThrows(IllegalArgumentException.class, () -> PriceParser.parse(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> PriceParser.parse(" "));
    }

    @Test
    public void testMalformedPriceIsRejected() {
        // Hvata pogrešno grupisanje, pogrešnu valutu i tekst umesto cene.
        for (String invalid : new String[]{"19.99,00 RSD", "999,9 RSD", "-1,00 RSD", "199.99 USD", "Cena na upit"}) {
            Assert.assertThrows("Neispravan format mora biti odbijen: " + invalid,
                    IllegalArgumentException.class, () -> PriceParser.parse(invalid));
        }
    }
}
