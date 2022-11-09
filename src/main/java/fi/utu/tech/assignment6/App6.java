package fi.utu.tech.assignment6;

/*
 * Kun ohjelma ajetaan, huomataan että Hub-luokan toString-metodi heittää
 * NullPointerException-poikkeuksen. Tämä johtuu siitä, että lamppuja on
 * poistettu tunnuslistan luonnin ja sen läpikäynnnin aloittamisen välillä.
 * 
 * Poistetut lamput voidaan sivuuttaa try-catch lohkolla ja poikkeusta ei enää synny.
 * 
 * Toinen ilmenevä poikkeus, ConcurrentModificationException, joka ilmenee,
 * kun käsitellään kaikkia lamppuja koskevia metodeja. Jos lamppu poistetaan
 * kesken läpikäynnin, ohjelma heittää kyseisen poikkeuksen.
 * 
 * Tämä voidaan yrittää korjata synkroimalla lamppujen poistometodi.
 * 
 * Remote-luokka heittää taas NullPointerException-poikkeuksia, kun yksittäistä
 * lamppua koskeva metodi ei löydä kyseistä lamppua. Tämä voi johtua esimerkiksi
 * silloin, jos lamppu on poistettu tunnuslistan luonnin ja käskyn antamisen välillä.
 * 
 * Tämä voidaan sivuuttaa myös try-catch lohkolla.
 */


public class App6 {

    public static void main(String[] args) {
        // Kuinka monta lamppua luodaan alkutilanteeseen
        int initialLights = 40;
        // Kuinka monta kaukosäädintä luodaan ja käynnistetään
        int remoteAmount = 5;
        System.out.println("Press Ctrl+C to terminate application");
        Hub hub = new Hub();
        for (int i=0; i<initialLights; i++) {
            hub.addLight();
        }
        for (int i=0; i<remoteAmount; i++) {
            Remote r = new Remote(hub);
            // Kaukosäätimet tapetaan kun yhtäkään ei-demonisäiettä ei ole ajossa
            r.setDaemon(true);
            r.start();
        }
        // Keskitintä voidaan ajaa yhtä hyvin pääsäikeessä,
        // koska sillä ole tässä kohtaa muutakaan tekemistä
        hub.run();
    }
    
}
