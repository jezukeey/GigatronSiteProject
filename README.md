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

## ShoppingTests — zajednička provera kasnije

ShoppingTests je izostavljen iz podrazumevanog Maven pokretanja.
Kod je kompajliran, ali shopping testovi nisu izvršeni tokom ovih ispravki.
Popravljeni su životni ciklus browsera, čekanja, izbor tipa kupca i assertions.
Stari lokatori, proizvodi i checkout koraci još zahtevaju proveru sa korisnikom
na trenutnoj platformi. Ova klasa još nije potvrđena kao funkcionalna.

CompleteBuyOfItem dodatno zahteva allowLivePurchase=true. Taj scenario može
poslati stvarnu porudžbinu; ne uključivati ga pre dogovorene zajedničke provere.

## Struktura

- BaseTest otvara browser pre testa i zatvara ga i kada test padne.
- LoginPage konstruktor ne otvara stranicu. Pozvati openFromHomePage().
- Unos ima najviše tri pokušaja pri zameni DOM elementa, bez stalnog prepisivanja.
- Page klase nemaju JUnit assertions; rezultat proveravaju testovi.
- Završne provere i preostala ograničenja navedeni su u VERIFICATION.md.