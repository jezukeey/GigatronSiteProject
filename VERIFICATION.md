# Evidencija provera — 13. oktobar 2026.

## Aktuelna provera posle proširenja

Demo profil: PriceParserTests (8), LoginFormTests (3), CookieConsentTests (2), CatalogTests (2), SortingTests (2).
Pokretanje: mvn -Pdemo -Dheadless=true test.
Rezultat: 17 testova, 0 failures, 0 errors, 0 skipped.
Završetak: 13.10.2026. u 13:12:04, Europe/Belgrade.
Okruženje: Windows, JDK 17, Chrome 154, Selenium 4.50.0, headless 1440 × 1000.
Demo ne šalje login podatke, ne menja korpu i ne kreira porudžbinu.

Lokalni PriceParserTests: 8 testova, 0 failures, 0 errors, 0 skipped.
Kompajlirane su sve test klase, uključujući ShoppingTests.

## Ranije potvrđeno

- LoginTests: 13/13 i SortingTests: 2/2, prethodno automatizovano provereni 13.10.2026; korisnik je potvrdio i lokalni IntelliJ prolaz.
- AddAndRemoveItem: korisnik je potvrdio prolaz dodavanja/uklanjanja.
- GuestCheckoutToVerification: korisnik je potvrdio prolaz do prikaza Cloudflare okvira.
- Ručni guest nastavak do WSPay-a i Odustani: korisnik je potvrdio da prolazi.

Ovo nisu rezultati jednog zajedničkog pokretanja svih 32 testova.
Postojeći login testovi nisu ponovo izvršeni u ovoj poslednjoj proveri.
ShoppingTests nije pokretan tokom proširenja projekta.

## Šta je promenjeno

- Dodato 15 scenarija: 8 lokalnih + 7 browser testova, uz komentare razloga i očekivanog ishoda.
- PriceParser koristi BigDecimal, proverava RSD format i odbija neispravne podatke.
- Cene skrivenih elemenata ne ulaze u proveru glavne liste.
- BaseTest kroz JUnit TestWatcher čuva screenshot pre zatvaranja neuspešne sesije.
- Dodati demo i unit Maven profili, lokalni HTML izveštaj i CI za lokalne testove.
- Uklonjeni nepozvani stari page stubovi i local_history.patch.
- Uklonjene nekorišćene URL konstante i stari nazivi proizvoda; F16 podaci su na jednom mestu.
- Ažurirani README, plan testiranja i uputstvo za prezentaciju.

## Granice dokaza

Zeleni GUEST test znači da su proizvod, G-62 i prikaz provere potvrđeni.
Ne potvrđuje CAPTCHA uspeh, kontakt formu, porudžbinu ili plaćanje.
Ručni povratak sa WSPay-a ne dokazuje da je neplaćena porudžbina otkazana.
Sorting proverava prikazanu stranicu, ne sve stranice kataloga.
Cookie test posmatra ponovno pojavljivanje bannera tokom 3 sekunde, ne potvrđuje svaku analitičku skriptu.
Produkt test zavisi od toga da je konkretan F16 i dalje dostupan za dodavanje.
CI je pripremljen; izvršavanje na GitHub-u tek se potvrđuje nakon push-a.

## Ponovljiva provera

mvn -Punit clean test — bez mrežnog pristupa sajtu ili Chrome-a (Maven može preuzeti dependencies).
mvn -Pdemo -Dheadless=true clean test — lokalni testovi i 9 browser scenarija bez naloga.
mvn -Dtest=LoginTests test — postojeća login regresija uz lokalno podešenu lozinku.
Shopping testove pokrenuti pojedinačno iz IntelliJ-a, uz dogovorenu zajedničku proveru.