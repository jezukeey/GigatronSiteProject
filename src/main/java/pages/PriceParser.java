package pages;

import java.math.BigDecimal;

/** Pretvara prikazanu RSD cenu u broj, bez gubitka decimalne preciznosti. */
public final class PriceParser {
    private PriceParser() { }

    public static BigDecimal parse(String displayedPrice) {
        // Prazan podatak nije cena: prekidamo umesto da ga pogrešno protumačimo kao nulu.
        if (displayedPrice == null || displayedPrice.isBlank()) {
            throw new IllegalArgumentException("Cena nije prikazana.");
        }
        // Uklanjamo valutu i razmake, uključujući razmake koji se koriste u HTML-u.
        String value = displayedPrice.replaceAll("(?i)RSD", "")
                .replaceAll("[\\s\\u00a0\\u202f]", "");
        // Prihvatamo srpski format: 199.999,00, 199999,00 ili ceo broj.
        // Pogrešno grupisane hiljade ne smeju da postanu neka druga, naizgled ispravna cena.
        if (!value.matches("(?:[0-9]+|[0-9]{1,3}(?:\\.[0-9]{3})+)(?:,[0-9]{2})?")) {
            throw new IllegalArgumentException("Neprepoznat format RSD cene.");
        }
        // BigDecimal čuva tačnu vrednost; tačke su hiljade, a zarez decimalni separator.
        return new BigDecimal(value.replace(".", "").replace(',', '.'));
    }
}
