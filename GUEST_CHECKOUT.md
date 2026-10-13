# Guest checkout do Cloudflare provere

## Automatski scenario

Pokrenuti ShoppingTests.GuestCheckoutToVerification u IntelliJ-u.
Test proverava ASUS TUF F16 FX608JMI-TU271, dodavanje proizvoda, otvaranje guest checkouta,
preuzimanje u G-62 Merkur (Novi Sad) i prikaz Cloudflare provere.
Vidljiv okvir prepoznat po Cloudflare adresi, cf-chl-widget identifikatoru ili naslovu security challenge (uključujući shadow DOM)
predstavlja očekivani završetak ovog ograničenog scenarija. Test ne rešava proveru i ne tvrdi
 da su kontakt forma, checkout ili plaćanje prošli. To jasno ispisuje u konzoli.
Ako provera nije pronađena ili ranija provera proizvoda/prodavnice ne uspe, test pada.
Na kraju uklanja proizvod i zatvara browser; neuspešno čišćenje takođe obara test.

Kontakt promenljive nisu potrebne za ovaj test. ShoppingTests ostaje isključen iz podrazumevanog Maven pokretanja.
Prethodni GuestCheckoutToPayment zamenjen je ovim užim scenarijem; u IntelliJ-u pokrenuti novu metodu preko zelene strelice.

## Ručni nastavak u običnom browseru

Automatski test čisti korpu, zato ručni scenario počinje ponovnim dodavanjem proizvoda.

1. Otvoriti ASUS TUF F16 proizvod, dodati jedan komad i otvoriti korpu.
2. Nastaviti na način isporuke kao gost. Izabrati preuzimanje u Gigatron prodavnici i G-62 Merkur, Novi Sad. Ako proizvod tamo nije dostupan, zabeležiti prepreku; ne menjati prodavnicu bez dogovora.
3. Ručno završiti Cloudflare proveru i kliknuti Nastavite. Ako piše Verification failed, ovaj ručni korak nije prošao.
4. Uneti lokalne test podatke u Adresa i podaci. Telefon: 8 ili 9 cifara bez +381 i početne nule. Email ne sme biti vezan za postojeći Gigatron nalog. Ne dodavati kontakt podatke u Git.
5. Kliknuti Nastavite i izabrati tačno Plaćanje platnim karticama, zatim Nastavite.
6. Na Pregled korpe proveriti proizvod, količinu, prodavnicu, podatke i izabrani način plaćanja. Pročitati uslove i označiti saglasnost samo ako ih prihvataš.
7. Kliknuti Završite kupovinu za prelazak na WSPay. Ovo može kreirati stvarnu neplaćenu porudžbinu, što je korisnik odobrio.
8. Potvrditi WSPay stranicu, prikazan iznos i prazna polja za karticu. Ne unositi karticu i ne kliknuti PLATI.
9. Kliknuti Odustani i proveriti povratak na Gigatron. Ukloniti proizvod iz korpe ako je ostao.

Povratak sa WSPay-a i prazna korpa ne potvrđuju otkazivanje porudžbine.
Ručni rezultat beležiti odvojeno od automatskog prolaza; ne označavati neizvršene korake kao prošle.

## Provera koda

Kompilacija bez pokretanja: mvn -DskipTests test-compile.
Automatizovani scenario se pokreće sa korisnikom; kompilacija sama ne potvrđuje prolaz testa.