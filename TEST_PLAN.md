# Plan testiranja

## Cilj i obim

Proveriti glavne korisničke tokove i prikaz proizvoda u internet prodavnici koristeći Selenium i JUnit.
Odvojiti proveru lokalne logike, browser regresiju i manuelni nastavak checkouta.
Okruženje: Java 17, Chrome, Selenium 4.50.0; sajt https://gigatron.rs.
Svaki browser test dobija novu incognito sesiju. Testovi nisu zavisni od redosleda izvršavanja.

## Test slučajevi

| ID | Scenario | Preduslov | Očekivani rezultat | Način |
| --- | --- | --- | --- | --- |
| LOGIN-01 | Validna prijava | Test nalog i lokalna lozinka | Odjavi se u meniju naloga | LoginTests |
| LOGIN-02 | Prijava bez cookie izbora | Vidljiv banner, test nalog | Potvrđena prijava | LoginTests |
| LOGIN-03 | Prijava nakon prihvatanja/odbijanja kolačića | Test nalog | Potvrđena prijava za obe opcije | LoginTests, 2 scenarija |
| LOGIN-04 | Validan email, pogrešna/prazna lozinka | Test email | Greška prijave | LoginTests, 2 scenarija |
| LOGIN-05 | Nepostojeći email, validna/prazna lozinka | Sintaktički ispravan test email | Greška prijave | LoginTests, 2 scenarija |
| LOGIN-06 | Prazan email i prazna/validna/pogrešna lozinka | Forma otvorena | Greška emaila | LoginTests, 3 scenarija |
| LOGIN-07 | Prijava i odjava | Test nalog | Prijava ponovo vidljiva | LoginTests |
| COOKIE-01 | Prihvatanje cookie bannera | Nova sesija | Banner nestaje | LoginTests |
| FORM-01 | Zamena emaila | Otvorena forma | Samo drugi email u polju | LoginFormTests |
| FORM-02 | Brisanje emaila | Unet email | Prazno polje | LoginFormTests |
| FORM-03 | Maskiranje i zamena lozinke | Izmišljene vrednosti, bez submit-a | Maskirano polje sa drugim unosom | LoginFormTests |
| COOKIE-02 | Prihvatanje i refresh | Nova sesija | Izbor zapamćen; banner se ne vrati tokom 3 s posmatranja | CookieConsentTests |
| COOKIE-03 | Odbijanje i refresh | Nova sesija | Izbor zapamćen; banner se ne vrati tokom 3 s posmatranja | CookieConsentTests |
| CAT-01 | Prikaz kataloga | Dostupna lista laptopova | Najmanje 2 prikazane, pozitivne cene | CatalogTests |
| CAT-02 | Prikaz proizvoda | ASUS TUF F16 dostupan | Tačna putanja/naziv i omogućeno Dodaj u korpu | CatalogTests |
| SORT-01 | Cena rastuće | Lista laptopova | Svaka sledeća cena >= prethodna | SortingTests |
| SORT-02 | Cena opadajuće | Lista laptopova | Svaka sledeća cena <= prethodna | SortingTests |
| PRICE-01 | Ispravni RSD formati | Nije potrebna mreža | Tačan BigDecimal | PriceParserTests, 6 scenarija |
| PRICE-02 | Prazni/neispravni podaci | Nije potrebna mreža | IllegalArgumentException | PriceParserTests, 2 scenarija |
| CART-01 | Dodavanje, brisanje, refresh | Poznati proizvod | Korpa ostaje prazna i povratak na početnu | ShoppingTests |
| GUEST-01 | Guest preuzimanje G-62 | Dostupan proizvod/prodavnica | Cloudflare okvir vidljiv; kasniji koraci neizvršeni | ShoppingTests |
| MANUAL-01 | Guest do WSPay-a | Ručna CAPTCHA, lokalni podaci | Online kartica, WSPay i prazna kartična polja | Manuelno |
| MANUAL-02 | Odustajanje od plaćanja | Otvoren WSPay | Povratak na Gigatron bez kartice | Manuelno |

## Kriterijumi i dijagnostika

Automatski test prolazi samo kada svi njegovi assertions prođu. Neuspeh sajta, čekanja ili čišćenja korpe ostaje neuspeh.
GUEST-01 ima namerno ograničen obim: prikaz provere je očekivani kraj, a ne uspešan CAPTCHA rezultat.
Ručni i automatski rezultati beleže se odvojeno. Broj testova nije procenat pokrivenosti aplikacije.
JUnit XML i HTML izveštaj su dokazi za konkretno pokretanje; screenshot olakšava analizu pada.
Ne ponavljati prijavu ili slanje porudžbine automatski da bi rezultat postao zelen.

## Van obima i sledeće mogućnosti

Nisu potvrđeni: stvarno plaćanje, slanje kartičnih podataka, svi proizvodi, sve stranice kataloga,
pretraga, filteri, mobilni uređaji, Firefox/Edge, performance i API testovi.
Dostupnost F16 i G-62 može se promeniti. Cloudflare može odbiti automatizovanu sesiju.
Za šire checkout testiranje potreban je testni/staging sistem sa dogovorenim test podacima.