# Provera projekta — 13. oktobar 2026.

Osnova: fix/maven-baseline, commit 7f508182f064384a906418c69fcf0f04c1a7ea38.
Ispravke: lokalna grana codex/repair-tests.

## Potvrđeno

- Maven kompilacija svih page i test klasa prolazi.
- Završno pokretanje: LoginTests (13) i SortingTests (2).
- Rezultat: 15 testova, 0 failures, 0 errors, 0 skipped.
- Provera završena 13.10.2026. u 10:57:41, Europe/Belgrade.
- Okruženje: Windows 11, JDK 17, Chrome 154, Selenium 4.50.0.
- Headless prikaz: 1440 x 1000.
- Validna lozinka pročitana je iz lokalne IntelliJ konfiguracije u memoriju
  procesa testiranja. Nije dodata u izvorni kod ili ovaj dokument.
- ShoppingTests nije izvršen.

## Ispravke

- Jedinstveno pokretanje i zatvaranje browsera kroz BaseTest.
- Ograničeno čekanje učitavanja stranice i skripti.
- Login pomoćne metode sada zaista otvaraju formu sa početne stranice.
- Kontrola Moj profil i link Prijavi se koriste aria-label umesto imena
  vidljivog samo na određenim širinama ekrana.
- Ponovno pronalaženje polja pri zameni DOM elementa tokom unosa;
  najviše tri pokušaja, bez stalnog ponavljanja unosa u svakom poll-u.
- Čekanje učitavanja profila do 45 sekundi uz korisnu dijagnostiku.
- Odjava koristi normalan klik i čeka ako animacija još blokira element.
- SortingTests je aktivan i proverava oba redosleda cena na stranici rezultata.
- Desktop i mobilna kontrola sortiranja su podržane.
- Neispravne PageFactory anotacije i netačne URL assertions su uklonjene.
- JUnit se koristi samo u test kodu.
- Selenium 4.50.0 uključuje CDP 154; prethodna CDP upozorenja su uklonjena.
- ShoppingTests: popravljeno sakrivanje driver-a, zatvaranje browsera,
  stvarna explicit čekanja, nedostajući klik i assertions.
- ShoppingTests je isključen iz podrazumevanog Maven pokretanja;
  scenario kupovine dodatno zahteva allowLivePurchase=true.

## Granice provere

Shopping lokatori, stare adrese proizvoda i ceo checkout još zahtevaju
dogovorenu zajedničku proveru. Shopping deo nije potvrđen kao funkcionalan.
Sorting proverava trenutno prikazanu stranicu rezultata, ne sve stranice kataloga.
Sajt i stanje naloga mogu se promeniti; jedan uspešan prolaz nije garancija
da nikada neće biti mrežnog ili serverskog problema.
Tokom provere pojavio se povremen problem inicijalizacije profila;
posle produženja čekanja cela klasa je prošla. Uticaj druge korisničke
sesije nije zasebno dokazan.

## Ponoviti proveru

U IntelliJ Run Configuration zadržati GIGATRON_TEST_PASSWORD.
Za Maven terminal istu promenljivu obezbediti u okruženju procesa.

```shell
mvn -DskipTests test-compile
mvn -Dtest=LoginTests,SortingTests -Dheadless=true test
```

Ne pokretati ShoppingTests pre dogovorene zajedničke provere.