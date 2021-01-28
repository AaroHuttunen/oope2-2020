
package harjoitustyo.kokoelma;

import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.dokumentit.Uutinen;
import harjoitustyo.dokumentit.Vitsi;
import harjoitustyo.apulaiset.Kokoava;
import harjoitustyo.omalista.OmaLista;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
* Kokoelma-luokka ohjelman logiikan toteuttamiseen.
* 
* Luokka muokkaa kokoelmaa käyttöliittymästä saatujen kutsujen mukaan.
*
* @author Aaro Huttunen. aaro.huttunen@tuni.fi
*
* 428490
*
*/

public class Kokoelma implements Kokoava<Dokumentti>{

    //Attribuutit.
    private OmaLista<Dokumentti> dokumentit;
    private LinkedList<String> sulkusanat;

    //Rakentaja.
    public Kokoelma() {
        dokumentit = new OmaLista<Dokumentti>();
        sulkusanat = new LinkedList<String>();
    }

    //Aksessorit.
    public OmaLista<Dokumentti> dokumentit() {
        return dokumentit;
    }
    public LinkedList<String> sulkusanat() {
        return sulkusanat;
    }

    /** Ladataan tiedostosta dokumentit ja sulkusanat. Metodi ottaa huomioon tiedostontyypin.
    * 
    */
    public int tiedostonLataus (String[] args) {

        //Tarkistetaan että komentoriviparametreja on oikea määrä.
        if(args.length > 1 && args.length < 3) {

            //Ladataan kokoelman sisältävä tiedosto ja nimetään dokumentti.
            String dokumentinNimi = args[0];
            File dokumenttiTiedosto = new File(dokumentinNimi);
    
            //Ladataan sulkusanat sisältävä tiedosto.
            File sulkusanatTiedosto = new File(args[1]);

            //Luodaan dokumentti ja sulkusanalista.
            dokumentit = new OmaLista<Dokumentti>();
            sulkusanat = new LinkedList<String>();

            //Tarkistetaan että tiedostot löytyvät, tai annetaan virheilmoitus.
            try {

                //Käytetään scanneria tiedoston lukemiseen.
                Scanner lukija = new Scanner(dokumenttiTiedosto);

                //Siirrytään silmukkaan dokumentin lataamiseksi.
                while (lukija.hasNextLine()) {
                    
                    //Jaetaan rivit osiin formatoimista varten.
                    String rivi = lukija.nextLine();
                    String[] osat = rivi.split("///");

                    //Jos dokumentin nimi alkaa "jokes", lisätään dokumenttiin tiedot Vitseinä.
                    if (dokumentinNimi.startsWith("jokes")) {
                        dokumentit.lisää(new Vitsi(Integer.parseInt(osat[0]), osat[1], osat[2]));

                    
                    //Jos dokumentin nimi alkaa "news", lisätään dokumenttiin tiedot Uutisina.
                    } else if (dokumentinNimi.startsWith("news")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
                        LocalDate päivä = LocalDate.parse(osat[1], formatter);
                        dokumentit.lisää(new Uutinen(Integer.parseInt(osat[0]), päivä, osat[2]));
                    }
                }

                lukija.close();

                //Käytetään scanneria tiedoston lukemiseen.
                lukija = new Scanner(sulkusanatTiedosto);

                //Siirrytään silmukkaan.
                while (lukija.hasNextLine()) {
                    
                    //Ladataan sulkusanat listaan.
                    String sana = lukija.nextLine();
                    sulkusanat.add(sana);

                }

                lukija.close();

                //Palautetaan oikeanlainen numero vastaamaan tapahtunutta.
                return 2;

            } catch (IOException e) {
                return 1;
            }
        } else{
            return 0;
        }
    }

    /**
    * Korvataan lisää metodi. En tätä metodia käytä, sillä dokumentin luominen
    * ja lisääminen tapahtuu toisessa metodissa.
    * Tämä johtuu siitä, että en saanut tätä metodia toimimaan halutulla tavalla.
    */
    @Override
    public void lisää(Dokumentti uusi) throws IllegalArgumentException {

        try {

            LinkedList<Integer> lista = new LinkedList<Integer>();
            
            for (Dokumentti vertailtava : dokumentit) {

                if (vertailtava == null) {
                }
                lista.add(vertailtava.tunniste());
            }
    
            if (lista.contains(uusi.tunniste())) {
                throw new IllegalArgumentException("Error! List already contains object with same tunniste.");

            } else {

                dokumentit.lisää(uusi);

            }
            
        } catch (Exception e) {

            throw new IllegalArgumentException("Error! List item cannot be null.");

        }
    }

    /** 
     * Korvataan hae metodi. Metodi hakee dokumentin tunnisteen perusteella.
     * 
     * @param Tunniste oikean dokumentin hakemiseksi.
     * @return Löydetty dokumentti, tai null jos dokumenttia ei löydetä.
     */
    @Override
    public Dokumentti hae(int tunniste) {

        //Suoritetaan vertailu.
        for (Dokumentti vertailtava : dokumentit) {
            
            //Palautetaan dokumentti jos tunnisteet ovat samat.
            if (vertailtava.tunniste() == tunniste) {
                return vertailtava;
            }
        }

        //Palautetaan null;
        return null;

    }

    /**
     * Metodi koko kokoelman tulostamiseksi.
     * 
     * @return Kokoelma tulostettavaksi käyvässä muodossa.
     */
    public String tulosta () {

        //Alustetaan String
        String tulosta = "";

        //Käydään dokumentit läpi, ja lisätään tulostusta varten Stringiin.
        for (Dokumentti vertailtava : dokumentit) {

            tulosta = tulosta + vertailtava + "\n";

        }

        return tulosta;

    }

    /**
     * Etsitään kokoelmasta parametrina saatuja sanoja.
     * 
     * @param Parametrina saadut hakusanat.
     * @return Löydetyt sanat.
     */
    public String etsi (String[] hakusanat) {

        //Alustetaan
        String löydetyt = "";

        //Luodaan lista hakusanoille.
        LinkedList<String> hakusanalista = new LinkedList<String>();

        for (int i = 1; i < hakusanat.length; i++) {
            hakusanalista.add(hakusanat[i]);
        }

        //Suoritetaan vertailu.
        for (Dokumentti vertailtava : dokumentit) {

            //Kutsutaan sanatTäsmäävät metodia.
            boolean löytyi = vertailtava.sanatTäsmäävät(hakusanalista);

            //Otetaan tulostusta varten String talteen.
            if (löytyi == true) {
                löydetyt = löydetyt + vertailtava.tunniste() + "\n";
            }
        }

        return löydetyt;
    }

    /**
     * Metodi käynnistää tai sulkee kauittamisen.
     * 
     * @param kaiuttamisen totuusarvo.
     * @return kaiuttamisen totuusarvo.
     */
    public boolean kauita(boolean kauita) {

        if (kauita == false) {
            kauita = true;
            return true;
        } else {
            kauita = true;
            return false;
        }
    }

    /**
     * Esikäsitellään dokumenttia. Dokumentista poistetaan sulkusanat, syötteen parametriarvot
     * sekä muutetaan isot kirjaimet pieniksi.
     * 
     * @param Syötteen parametriarvo.
     */
    public void siivoa (String välimerkit) {
        
        //Suoritetaan esikäsittely.
        for (Dokumentti käsiteltävä : dokumentit) {
            käsiteltävä.siivoa(sulkusanat, välimerkit);
        }
    }

    /**
     * Poistetaan dokumentti.
     * 
     * @param Tunniste oikean dokumentin poistamiseksi.
     * 
     */
    public void poista (int tunniste) {

        //Suoritetaan vertailu.
        for (Dokumentti poistettava : dokumentit) {
            
            //Palautetaan dokumentti jos tunnisteet ovat samat.
            if (poistettava.tunniste() == tunniste) {
                dokumentit.remove(poistettava);
                return;
            }
        }
    }

    /**
     * Käyttäjän syötettä tarkastava metodi.
     * 
     * @param syote tarkastusta varten.
     * @return totuusarvo siitä riippuen, onko syöte oikein.
     */
    public boolean tarkista (String syote) {
        
        //Alustetaan muuttujat.
        String[] syote2;
        String[] syote3 = syote.split(" ");

        if (syote3[0].equals("add")) {

            //Tarkistetaan että parametriosa on oikein. Palautetaan oikea arvo.
            syote2 = syote.split(" ", 2);
            if (syote2.length < 2) {
                return false;
            } else {
                return true;
            }

        } else if (syote3[0].equals("find")) {

            //Tarkistetaan että parametriosa on oikein. Palautetaan oikea arvo.
            syote2 = syote.split(" ");
            if (syote2.length < 2) {
                return false;
            } else {
                return true;
            }

        } else {

            //Tarkistetaan että parametriosa on oikein. Palautetaan oikea arvo.
            syote2 = syote.split(" ");
            if (syote2.length != 2) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Luo metodi uuden dokumentin luomiseksi ja lisäämiseksi.
     * 
     * @param Tunniste, tyyppi, teksti ja tiedostotyyppi.
     * @return palauttaa totuusarvon riippuen siitä, onnistuiko kokoelman luonti.
     */
    public boolean luo (int tunniste, String tyyppi, String teksti, String tiedostotyyppi) {

        //Alustetaan totuusarvo.
        boolean onVitsi = false;

        //Tarkistetaan että lisätään oikeanlaista dokumenttia.
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            LocalDate päivä = LocalDate.parse(tyyppi, formatter);
            
        } catch (Exception e) {
            //Tyyppi ei ole päivämäärä, joten dokumentin tyyppi on vitsi.
            onVitsi = true;
        }

        //Tarkistetaan tiedostotyypin perusteella lisäys.
        if (tiedostotyyppi.equals("jokes") && onVitsi == true) {
            dokumentit.lisää(new Vitsi(tunniste, tyyppi, teksti));

            return true;

        } else if (tiedostotyyppi.equals("news") && onVitsi == false) {
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            LocalDate päivä = LocalDate.parse(tyyppi, formatter);
            dokumentit.lisää(new Uutinen(tunniste, päivä, teksti));
            
            return true;

        } else {
            return false;
            
        }
    }
}