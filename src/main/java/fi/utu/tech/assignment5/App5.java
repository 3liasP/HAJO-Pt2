package fi.utu.tech.assignment5;

/*
 * Kun ohjelma ajetaan, huomataan että Hub-luokassa valot kokoava HashMap-olio heittää
 * ConcurrentModificationException-poikkeuksia. Tämä johtuu siitä, kun kaikkia valoja
 * koskevia metodeja iteroidaan, samanaikaisesti iteroinnin aikana lisätään valoja.
 * 
 * Tämä on korjattu muuttamalla addLight-, turnOffAllLights- ja turnOnAllLights-metodit
 * synkronoiduiksi.
 * 
 * Toinen poikkeus ilmenee, kun Remote-luokan run-metodin lightIds-ArrayList-olion luonti
 * heittää ArrayIndexOutOfBoundsException-poikkeuksia. Tämä johtuu siitä, kun valoja lisätään
 * luonnin aikana. Luonnin aikana vaaditaan avainarvotaulukon luontia, mikä sisältää taas sen
 * iterointia. Täten pelkkä Hub-luokan getLightIds-metodin muuttaminen synkroiduksi ei riitä.
 * 
 * Tämä voidaan korjata laittamalla ArrayList-olion luonti omaan synkronoituun lohkoon.
 * 
 * Tehtävässä olisi ollut kuitenkin mahdollista muuttaa valot keräävä HashMap-olio
 * ConcurrentHashMap-olioksi. Tällöin kuitenkin muiden rinnakkaisuuden kannalta
 * täysin ongelmattomien metodein tehokkuus olisi kuitenkin kärsinyt.
 */

public class App5 {

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