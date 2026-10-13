package pages;

/** Podaci zajednički testovima; stvarna lozinka se čita isključivo iz okruženja. */
public final class Strings {
    private Strings() { }

    // Petar ostaje podrazumevani nalog; drugi korisnik može lokalno podesiti svoj email.
    public static final String VALID_EMAIL = System.getenv().getOrDefault(
            "GIGATRON_TEST_EMAIL", "projectgigatron@gmail.com");
    // Rezervisani domen ne pripada stvarnom primaocu; koristi se u negativnim testovima.
    public static final String INVALID_EMAIL = "nonexistent@example.invalid";
    // Lozinka nikada nije deo izvornog koda ili repozitorijuma.
    public static final String VALID_PASSWORD = System.getenv("GIGATRON_TEST_PASSWORD");
    public static final String INVALID_PASSWORD = "invalidpassword";

    // Koristimo samo adrese na koje aktuelni testovi zaista odlaze.
    public static final String MAIN_URL = "https://gigatron.rs/";
    public static final String PRENOSNI_RACUNARI_URL =
            "https://gigatron.rs/laptop-racunari-i-it-oprema/laptop-racunari";
    public static final String TEST_PRODUCT_URL =
            "https://gigatron.rs/proizvod/asus-tuf-f16-fx608jmi-tu271-i7-4711636513531";
    public static final String TEST_PRODUCT_NAME = "ASUS TUF F16 FX608JMI-TU271 i7/16/512GB/5060";
}