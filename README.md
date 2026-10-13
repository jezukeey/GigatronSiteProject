# GigatronSiteProject

Java 17, Maven 3.9+, Selenium 4.50.0, JUnit 4.13.2.
ChromeDriver se bira preko Selenium Manager-a; potreban je instaliran Chrome.

## IntelliJ

Otvoriti ovaj folder kao Maven projekat i izabrati JDK 17.
U Run Configuration za LoginTests dodati GIGATRON_TEST_PASSWORD.
Nalog ostaje Petar. Lozinka se ne upisuje u Java kod i ne ispisuje u log.
Promenljiva podešena samo u IntelliJ-u nije dostupna običnom terminalu.

## Kompilacija bez izvršavanja testova

```shell
mvn -DskipTests clean test-compile
```

Ovo kompajlira i ShoppingTests, ali ne pokreće nijedan test.

## Login i sortiranje

```shell
mvn -Dtest=LoginTests test
mvn -Dtest=SortingTests test
```

Za rad bez vidljivog Chrome prozora dodati -Dheadless=true.
LoginTests ima 13 scenarija. Testovi sa validnom lozinkom zahtevaju
GIGATRON_TEST_PASSWORD u okruženju procesa koji ih pokreće.
SortingTests proverava rastuću i opadajuću cenu na trenutno prikazanoj
stranici laptopova. Preporučeni proizvodi nisu deo te provere.

## ShoppingTests

ShoppingTests je izostavljen iz podrazumevanog Maven pokretanja.
AddAndRemoveItem proverava dodavanje i uklanjanje ASUS TUF F16 laptopa; korisnik je potvrdio prolaz.
GuestCheckoutToVerification proverava proizvod, guest checkout, izbor G-62 i prikaz Cloudflare provere.
Prolaz znači samo da je test stigao do te provere. CAPTCHA, kontakt podaci i plaćanje nisu testirani.
Test čisti korpu i zatvara browser. Ne šalje porudžbinu.
Prethodni GuestCheckoutToPayment zamenjen je ovom metodom.
Ručni nastavak do WSPay-a opisan je u [GUEST_CHECKOUT.md](GUEST_CHECKOUT.md).
## Struktura

- BaseTest otvara browser pre testa i zatvara ga i kada test padne.
- LoginPage konstruktor ne otvara stranicu. Pozvati openFromHomePage().
- Unos ima najviše tri pokušaja pri zameni DOM elementa, bez stalnog prepisivanja.
- Page klase nemaju JUnit assertions; rezultat proveravaju testovi.
- Završne provere i preostala ograničenja navedeni su u VERIFICATION.md.