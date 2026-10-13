# Gigatron UI Test Automation

Portfolio projekat za demonstraciju manuelnog i automatskog testiranja internet prodavnice.
Koristi Java 17, Selenium WebDriver 4.50.0, JUnit 4.13.2 i Maven 3.9+.
Testovi obuhvataju prijavu, kolačiće, katalog, sortiranje i guest checkout do Cloudflare provere.
Kod i komentari objašnjavaju akciju, razlog čekanja i očekivani ishod.

## Brzo pokretanje bez naloga

Potreban je JDK 17, Maven 3.9+ i instaliran Chrome za UI testove.
Selenium Manager pronalazi odgovarajući ChromeDriver; prva upotreba može zahtevati preuzimanje.
Iz korena projekta:

```shell
# Kompajlira projekat i izvršava 8 lokalnih testova, bez Chrome-a i pristupa sajtu.
mvn -Punit clean test

# Demo: 8 lokalnih + 9 browser testova; nema prijave, korpe ili porudžbine.
mvn -Pdemo clean test

# Isti demo bez vidljivog Chrome prozora.
mvn -Pdemo -Dheadless=true clean test
```

Demo profil obuhvata PriceParserTests, LoginFormTests, CookieConsentTests, CatalogTests i SortingTests.
Ne zahteva lozinku. Živi sajt, mreža i dostupnost proizvoda mogu uticati na UI rezultat.

## IntelliJ

1. Otvoriti koren projekta kao Maven projekat i izabrati Project SDK 17.
2. Reload All Maven Projects ako dependencies nisu učitane.
3. Za pojedinačni test kliknuti zelenu strelicu pored metode ili klase.
4. Za Maven demo otvoriti Maven → Profiles, izabrati demo, zatim Lifecycle → test.

LoginTests zahteva GIGATRON_TEST_PASSWORD u Run → Edit Configurations → Environment variables.
Petar ostaje podrazumevani nalog. Opcioni GIGATRON_TEST_EMAIL omogućava lokalni drugi test nalog.
Promenljive iz IntelliJ konfiguracije ne prenose se automatski u terminal.
Lozinka se ne upisuje u Java kod ili Git. Git ignoriše .env, ali projekat ne učitava .env automatski.

```shell
# Samo postojeća login regresija; lozinka mora biti u okruženju tog procesa.
mvn -Dtest=LoginTests test

# Oba smera sortiranja trenutno prikazane liste laptopova.
mvn -Dtest=SortingTests test

# Samo kompilacija, bez izvršavanja bilo kog testa.
mvn -DskipTests clean test-compile
```

Podrazumevani mvn test pokreće sve testove osim ShoppingTests; zato uključuje i LoginTests i zahteva lokalnu lozinku.

## Pokrivenost

| Klasa | Broj | Namena |
| --- | ---: | --- |
| LoginTests | 13 | Uspešna/neuspešna prijava, prazna polja, odjava i cookie izbor |
| LoginFormTests | 3 | Zamena i brisanje emaila, zamena i maskiranje lozinke bez slanja forme |
| CookieConsentTests | 2 | Pamćenje prihvatanja i odbijanja posle refresh-a |
| CatalogTests | 2 | Pozitivne cene i prikaz poznatog proizvoda |
| SortingTests | 2 | Cena rastuće i opadajuće na prikazanoj stranici |
| PriceParserTests | 8 | RSD format, HTML razmaci, decimalna preciznost i neispravni podaci |
| ShoppingTests | 2 | Dodavanje/uklanjanje i guest tok do Cloudflare provere; ručno pokretanje |

Ukupno je definisano 32 testa. To je broj scenarija u kodu, ne tvrdnja da su sva 32 izvršena zajedno.
Aktuelni rezultati i ograničenja su u [VERIFICATION.md](VERIFICATION.md).

## Struktura i tehničke odluke

- src/main/java/pages: page objekti i zajednički parser; bez JUnit assertions.
- src/test/java: testovi opisuju korisnički scenario i proveravaju rezultat.
- BaseTest: odvojena Chrome sesija po testu, ograničeni timeout-i, screenshot pre zatvaranja neuspešne sesije.
- LoginPage: ponovo pronalazi element tokom promene DOM-a; unos ima najviše tri pokušaja.
- PrenosniRacunariPage: sortira glavnu listu, bez preporučenih proizvoda.
- PriceParser: BigDecimal umesto float/double; neispravan format ne pretvara u pogrešnu cenu.
- Maven profili: demo za prezentaciju i unit za lokalnu/CI proveru.
- .github/workflows/verify.yml: kompajlira sve klase i izvršava samo lokalne testove.

## Izveštaji i dijagnostika

Maven čuva tekstualne i JUnit XML rezultate u target/surefire-reports.
Neuspešni UI testovi čuvaju screenshot u target/screenshots pre zatvaranja browsera.
Screenshot može sadržati podatke naloga; pregledati ga pre deljenja.
Za pregledan HTML izveštaj na Windows-u, nakon Maven pokretanja:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\New-TestReport.ps1
```

Otvoriti target/test-report.html. Skripta čita rezultate, ne pokreće testove.
Pre novog skupa koristiti clean da se prethodni XML fajlovi ne pomešaju sa novim rezultatima.
Izveštaji i screenshots nisu verzionisani u Git-u.

## Shopping i ručni checkout

ShoppingTests je izostavljen iz podrazumevanog i demo Maven skupa.
Pokreće se pojedinačno iz IntelliJ-a u dogovorenoj proveri.
GuestCheckoutToVerification prolazi kada potvrdi proizvod, izbor G-62 i prikaz Cloudflare okvira.
To nije dokaz da je CAPTCHA rešena ili da je checkout završen.
Automatski scenario ne popunjava kontakt podatke i ne šalje porudžbinu.
Ručni nastavak do WSPay-a dokumentovan je u [GUEST_CHECKOUT.md](GUEST_CHECKOUT.md).

## Prezentacija i plan testiranja

- [PRESENTATION.md](PRESENTATION.md): predlog demonstracije od 5–7 minuta i objašnjenje odluka.
- [TEST_PLAN.md](TEST_PLAN.md): obim, kriterijumi prolaza i lista test slučajeva.
- [VERIFICATION.md](VERIFICATION.md): provereni rezultati, izvor dokaza i preostala ograničenja.