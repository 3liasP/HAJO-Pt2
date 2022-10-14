# Tehtävänanto DEMO2

## Oppimistavoitteet
Säieturvallisuus, kriittinen alue, synkronointi (mutexit ja monitorit), kilpailutilanteet ja lukkiumatilanteet.

## Yleiset ohjeet
Demokerran tehtävät tulisi tehdä niille varattuihin kansioihin (hakemistopuussa src/main alla olevat kansiot): eli siis tämän viikon ensimmäinen harjoitus tulisi tehdä kansioon "assignment1" ja toinen harjoitus kansioon "assignment2" jne. **Tämän viikon tehtävät ovat pääasiassa suoritettavissa toisistaan riippumattomasti**. Ainoastaan tehtävä 6 vaatii tehtävän 5 ratkaisemista, muutoin tehtävien tekojärjestyksellä ei ole merkitystä. Tehtävä 3 käyttää apunansa `common`-pakkauksen luokkia, mutta kyseisen pakkauksen luokkiin ei tule tehdä muutoksia tämän kerran demotehtävissä.

## Tehtävät

### Tehtävä 1 - Laskin
Ensimmäisessä tehtävässä on luotu valmiiksi kolme luokkaa: `App1`, `Count`, ja `Counter`. Kuten luokkien nimestä voi päätellä, tässä tehtävässä lasketaan lukumääriä. `main`-metodissa luodaan muuttujan `threadCount` verran `Counter`-säikeitä, jotka suoritetaan samanaikaisesti, sekä yksi `Count`-olio, joka pitää sisällänsä lukuarvon (alussa 0). `Counter`-säieolioiden elämäntehtävä on yksinkertainen: lue `Count`-olion nykyinen arvo ja lisää siihen yksi, kuole onnellisena. Mikäli ala-asteen matematiikka on hallussa, voi päätellä, että kun lukuarvoon 0 lisätään luku 1, 20000 kertaa, pitäisi `Count`-olion lukuarvon olla lopuksi 20000. Näin ei kuitenkaan näytä olevan. Jollain tietokoneilla ja joinakin kertoina lopullinen lukuarvo saattaa olla 20000, mutta tämä ei ole mitenkään varmaa. Muokkaa `Counter`-luokkaa siten, että eri säikeistä ajettaessakin loppusumma tulee olemaan 20000.

Huom! Boksin ulkopuolelta ajatteleville tiedoksi: Ratkaisu, jossa jokainen säie asettaa arvoksi 20000 ei ole hyväksyttävä ratkaisu.

### Tehtävä 2 - Lista
Tehtävässä 2 on melko lailla sama rakenne kuin tehtävässä 1. Erona on, että säikeistä jokainen haluaa lisäillä samaan listaan luvun 123 ja täten lopuksi listassa tulisi olla säikeiden verran näitä lukuja. Näin ei kuitenkaan ole. Ratkaise ongelma jälleen siten, että samanaikaisista säikeistä riippumatta listaan tulee oikea määrä lukuja.

Pohdi myös, miksi lista ei toimi halutun kaltaisesti - eihän säie muuta tee kuin lisää listaan uuden luvun, vai tekeekö...

### Tehtävä 3 - Roboassarit
On vuosi 2100 ja assarit on korvattu automaattitarkastajilla, joita kuvaa ohjelmassamme `AutomaticGrader`-luokan oliot. Näille automaattitarkastajille voidaan antaa kasa tarkistettavia tehtäviä ja ne tarkistavat annetut tehtävät automaattisesti käyttäen samaa algoritmia kuin viime viikon tarkistustehtävissä käytettiin (noppa). Kun tarkistus on suoritettu, heittää tarkastaja arvioidun tehtävän palautettujen tehtävien "koriin". Toki automaattitarkastajia on useampia ja näistä jokainen heittelee tarkistettuja tehtäviä samaan koriin.

Integraatio opintorekisterijärjestelmään on toteutettu `StudyRegistrar`-luokan olion avulla. Tämän luokan olion tehtävänä on seurata valmistuneiden palautusten koria uusien palautusten varalta ja napata palautus heti kun koriin tällainen ilmestyy. Napattuaan palautuksen, `StudyRegistrar`-olio lisää `StudyRecord`-olion näille osoitettuun `studyRegistry`-listaan. Kyseessä on siis eräänlainen kyseessä on siis eräänlainen tuottaja -- kuluttaja -ongelma, jossa n kappaletta arvioijia tuottaa arvioituja töitä ja 1 kappale StudyRegistrar-olioita kuluttaa arvioituja töitä.

Tällä hetkellä järjestelmää kehittänyt henkilö on yrittänyt ratkaista ongelman siten, että kaikki automaattitarkistajat sekä yksi arvosanojenkirjaaja yhdessä käyttävät `ArrayList`-oliota nimeltä `graderSubmissions` "puskurina". 

```java
List<Submission> gradedSubmissions = new ArrayList<Submission>(30);
```

Tämä ei tietenkään toimi, sillä ensinnäkään ArrayList ei ole säieturvallinen ja toiseksi listan kokoa ei olla rajoitettu, jolloin suurella määrällä automaattitarkastajia, lista täyttyy koko ajan nopeammin, mitä kirjuri ehtii kirjaamaan, kunnes tietokoneesta loppuu muisti. Tarkastajat eivät saisi siis täyttää puskuria äärettömästi.

Tehtäväsi on selvittää, minkälainen tietorakenne sopisi em. "korille" paremmin ja muuttaa ohjelmaa käyttämään kyseistä tietorakennetta ongelman ratkaisuun. Tietorakenteen pitäisi olla säieturvallinen ja kokorajoitettu (ts. automaattitarkistajat jäävät odottamaan, mikäli kori on "täynnä"). Sinun ei tarvitse itse toteuttaa kyseistä tietorakennetta, **eikä** käyttää matalan tason säiemekanismeja (esim. wait ja notify).


Vinkkejä:
 - Sivulta <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/package-summary.html> voisi löytyä sopivia tietorakenteita...
 - Myös tuottaja--kuluttaja -esimerkistä voisi ottaa oppia: <https://gitlab.utu.fi/tech/education/distributed-systems/ProducerConsumer/>

### Tehtävä 4 - Progressio toiseen potenssiin
Valtio on päättänyt tasoittaa tuloeroja määräämällä lain, jossa tilisiirtoja voidaan tehdä ainoastaan tilanteessa, jossa lähdetilille jää yli 0 euroa ja kohdetilillä on siirron jälkeen maksimissaan 1000 euroa. Koska siirtoja tehdään useita samanaikaisesti samoille tileillekin, täytyy molemmat tilit (lähde ja kohde) lukita tilisiirron ajaksi. Muutenhan "katetarkistuksen" ja oikean tilisiirron välissä voisi joku toinen tilisiirtotapahtuma tehdä oman siirtonsa ja tilisiirto voisi olla laiton.

`BankTransfer`-luokan oliot vastaavat yksittäisistä tilisiirtotapahtumista. On tämän luokan vastuulla lukita molemmat tilit (joita kuvaavat luokan `Account` oliot), varmistaa tilisiirron "laillisuus" ja lopulta tehdä tilisiirto. Nykyisessä implementaatiossa on tosin jokin bugi, sillä vaikka tilisiirtoja pitäisi tapahtua jatkuvalla syötöllä, muutaman samanaikaisen tilisiirron alettua, yksikään tilisiirto ei enää mene läpi.

a. Millä nimellä tätä tilannetta kutsutaan ja miksi tilisiirrot tässä ohjelmassa hyytyvät (selosta vaikka esimerkin avulla)?
b. Pystytkö korjaamaan `BankTransfer`-luokan siten, että tilisiirrot eivät jämähdä?

Yritä keksiä tehtävään (b) ratkaisu, mutta suoritukseksi riittää, että olet tehnyt kohdan (a) ja pohtinut kohtaa (b), vaikket olisikaan löytänyt ratkaisua.

### Tehtävä 5 - Lamppukeskitin

Tehtävässä 5 simuloidaan eräänlaista älyvalaisimien keskitinlaitetta. Tehtäväpohjassa on valmiiksi annettuna kaikki tähän tarvittavat luokat, jotka esitellään seuraavaksi:

#### Light
Kuvaa yksittäistä älyvalaisinta, jonka voi kytkeä päälle tai pois joko käskemällä suoraan tai käyttämällä toggle-metodia, joka tekee jomman kumman riippuen valaisimen nykytilasta. Valaisimelta voi myös kysyä, onko se päällä.

#### Hub
Itse valaisinkeskitin, johon kaikki valaisimet ovat liitettyinä. Kaikkia valaisimia käytetään tämän luokan kautta kutsumalla sopivaa metodia valaisimen id-numeron kanssa (tai kohdistamalla operaatio kaikkiin valaisimiin).

#### Remote
Säikeessä ajettavat Remote-oliot simuloivat kaukosäätimiä, joilla valaisimia voi ohjata. Kaukosäätimiä voi olla useita yhtä keskitintä kohden ja ne saattavat samanaikaisesti ohjata samaa keskitintä ja samoja lamppuja. Kaukosäätimet pääsevät ohjaamaan keskitintä siten, että kaukosäätimille annetaan viittaus keskitinolioon.

Tehtäväsi on tutkia annettua ohjelmaa ja tunnistaa ns. kriittiset alueet, joissa useat säikeet koskevat samanaikaisesti samoihin muistirakenteihin ja yrittää suojata näitä samanaikaisuuden aiheuttamilta ongelmilta käyttämällä tarvittaessa säieturvallisia rakenteita ja synkronointilohkoja.

### Tehtävä 6 - Advanced lamppukeskitin
Tehtävä 6 rakentuu tehtävän 5 päälle. **Älä** kuitenkaan kopioi tehtävän 5 ratkaisua suoraan tehtävän 6 päälle, sillä tehtävässä 6 on muokattu luokkia `Hub` ja `Remote` siten, että kaukosäätimien on mahdollista myös poistaa valaisimia keskittimestä. Tämä aiheuttanee uudenlaisia samanaikaisuuteen liittyviä ongelmia, jotka olisi tämän tehtävän aikana ratkottava. Voit kuitenkin lähteä siitä, että lisäät tehtävän 6 pohjaan edellisessä tehtävässä löytämäsi ratkaisumallit.

Tehtävä 6 saattaa olla hivenen hankalampi ratkaista kuin tehtävä 5. Käyttämällä synkronointilohkoja, on periaatteessa mahdollista ratkaista ongelma. Toki riippuen ratkaisutavasta, tämä saattaa vähentää ohjelman samanaikaisuutta. Synkronointilohkojen käyttäminen ja samanaikaisuuden uhraaminen on tässä tehtävässä toki hyväksyttävää ja vaikka et saisi ongelmaa täysin ongelmitta ratkottua, voinet silti merkata tehtävän ratkaisuksi, mikäli osaat perustella ratkaisusi ja kertoa, mitä ongelmia ratkaisussasi on.

Vinkki: Eräs tapa ratkoa ongelma, on käyttää <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/locks/ReadWriteLock.html>
