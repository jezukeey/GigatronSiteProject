# Prezentacija projekta — 5 do 7 minuta

## 1. Problem i cilj — 1 minut

„Napravio sam Selenium projekat za proveru osnovnih tokova internet prodavnice:
prijave, izbora kolačića, prikaza proizvoda, sortiranja i korpe.
Testove sam razdvojio od page objekata, a svaka provera ima jasan očekivani rezultat.“
Otvoriti README i pokazati tabelu pokrivenosti, bez tvrdnje da je testiran ceo Gigatron.

## 2. Struktura i jedan scenario — 2 minuta

Pokazati BaseTest, LoginFormTests i LoginPage.
Objasniti: nova sesija po testu, eksplicitno čekanje umesto fiksnog sleep-a,
pronalaženje vidljivog polja, ograničen retry kada DOM zameni element i proveru konačnog unosa.
Komentari objašnjavaju razlog komande i ishod, a assertions pripadaju testovima.
Stvarna lozinka je lokalna promenljiva; demo ne zahteva nalog.

## 3. Demonstracija — 2 minuta

Za pouzdanu lokalnu demonstraciju prvo pokrenuti:

```shell
mvn -Punit clean test
```

Pokazati 8 lokalnih testova koji proveravaju format cene i decimalnu preciznost.
Ako internet i Chrome rade, pokrenuti kraći browser primer:

```shell
mvn -Dtest=LoginFormTests test
```

Za ceo demo skup pre prezentacije proveriti mvn -Pdemo clean test.
Nije potrebno pokretati svih 32 scenarija uživo. Ne pokazivati stvarnu lozinku ili kontakt podatke.
Generisati izveštaj nakon odabranog skupa:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\New-TestReport.ps1
```

Otvoriti target/test-report.html i objasniti razliku PASS, FAIL, ERROR i SKIPPED.
Ako je rezultat neuspešan, pokazati screenshot i objasniti uzrok umesto skrivanja problema.

## 4. Checkout ograničenje i zaključak — 1 do 2 minuta

„Automatski guest scenario potvrđuje dolazak do Cloudflare provere.
Ne predstavljam to kao uspešno plaćanje: nastavak do WSPay-a proverio sam ručno.
Kartica se ne unosi. Live checkout može kreirati neplaćenu porudžbinu, zato ga ne demonstriram automatski.“
Pokazati GUEST_CHECKOUT.md i VERIFICATION.md.
GitHub Actions kompajlira projekat i izvršava lokalne testove; njegov prolaz ne potvrđuje živi sajt.
Sledeće proširenje: testno okruženje, pretraga i filteri, dodatni browseri.

## Pitanja koja možeš očekivati

- Zašto Page Object? Lokatori i akcije su u page klasama, a scenario i očekivanja u testu.
- Zašto explicit wait? Čeka konkretno stanje elementa, umesto pretpostavke koliko traje učitavanje.
- Zašto BigDecimal? Cena se čuva tačno; nema greške binarnog zaokruživanja.
- Zašto zaseban unit profil? Može se pokrenuti bez Chrome-a, mreže i naloga.
- Šta znači zeleni guest test? Samo potvrđen dolazak do provere, ne rešena CAPTCHA ili završena kupovina.
- Šta radiš kada test padne? Pregledam assertion/exception i screenshot; razlikujem problem testa od promene sajta.