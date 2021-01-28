package harjoitustyo.dokumentit;

import harjoitustyo.apulaiset.Tietoinen;
import java.util.LinkedList;

/**
* Dokumentti-luokka, joka toteuttaa Comparable ja Tietoinen rajapinnat.
* 
* Luokka käsittelee dokumentteja.
*
* @author Aaro Huttunen. aaro.huttunen@tuni.fi
*
* 428490
*
*/

public abstract class Dokumentti implements Comparable<Dokumentti>, Tietoinen<Dokumentti> {

    /**
     * Otuksen tunniste ja teksti.
     */
    private int tunniste;
    private String teksti;

    //Rakentajat.
    public Dokumentti(int i, String s) {
        tunniste(i);
        teksti(s);
    }

    //Aksessorit.
    public void tunniste(int i) throws IllegalArgumentException {
        if (i > 0) {
            tunniste = i;
        } else {
            throw new IllegalArgumentException("Error! Number too small.");
        }
    }

    public void teksti(String s) throws IllegalArgumentException {
        if (s != null && s.length() > 0) {
            teksti = s;
        } else {
            throw new IllegalArgumentException("Error! String can't be null or empty");
        }
    }

    public int tunniste() {
        return tunniste;
    }

    public String teksti() {
        return teksti;
    }

    /**
     * Korvataan toString metodi.
     * 
     * @return tunniste ja teksti oikeassa muodossa välimerkkien kanssa.
     */
    @Override
    public String toString() {
        return tunniste + "///" + teksti;
    }

    /**
     * Korvataan equals metodi.
     * 
     * @param viite olioon.
     * @return totuusarvo siitä, täsmääkö olio.
     */
    @Override
    public boolean equals(Object obj) {
        try {
            //Asetetaan olioon tunnisteen viite aksessoreita varten.
            Dokumentti toinen = (Dokumentti)obj;
        
            //Oliot ovat samat jos tunnisteet ovat samat.
            return (tunniste == toinen.tunniste);

        } catch (Exception e) {
            return false;
        }
        
    }

    /**
     * Korvataan compareTo metodi.
     * 
     * @param Verrattava dokumentti.
     * @return int arvo, joka kertoo dokumentin tunnisteen suhteessa verrattavaan.
     */
    @Override
    public int compareTo (Dokumentti verrattava) {

        //Suoritetaan vertailu.
        if (this.tunniste > verrattava.tunniste) {
            return 1;
        } else if (this.tunniste < verrattava.tunniste) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * SanatTäsmäävät metodi.
     * 
     * @param Hakusanat listana.
     * @return totuusarvo siitä, täsmääkö sanat.
     * @throws IllegalArgumentException jos lista on tyhjä tai null.
     */
    public boolean sanatTäsmäävät(LinkedList<String> hakusanat) throws IllegalArgumentException {

        //Alustetaan taulukko duplikaattien tarkastamiseksi.
        LinkedList<String> duplikaatti = new LinkedList<String>();

        //Tarkistetaan että hakusanat ei ole tyhjä.
        boolean tyhjä = false;
        if (hakusanat == null) {
            tyhjä = true;
        }

        if (tyhjä == true || hakusanat.isEmpty() == true) {
            //Heitetään poikkeus.
            throw new IllegalArgumentException("Error! LinkedList can't be null or empty");
        }

        //Alustetaan laskuri sanojen määrälle ja löytyneille sanoille.
        int sanojenMaara = hakusanat.size();
        int oikeatSanat = 0;

        //Siirrytään silmukkaan.
        for (int i = 0; i < hakusanat.size(); i++) {

            //Otetaan objekti listalta, ja muutetaan se Stringiin vertailua varten.
            Object element = hakusanat.get(i);
            String verrattava = element.toString();

            //Etsitään hakusana tekstistä.
            if (teksti.contains(verrattava)) {

                //Tarkistetaan että sana löytyy kokonaan.
                String[] sanat = teksti.split(" ");
                for (int a = 0; a < sanat.length; a++) {
                    if (sanat[a].equals(verrattava) == true) {
                        if (duplikaatti.contains(sanat[a])) {
                        } else {
                            oikeatSanat++;
                            duplikaatti.add(sanat[a]);
                        }
                    }
                }
            }
        }

        //Tarkistetaan että kaikki sanat löytyivät.
        if (oikeatSanat >= sanojenMaara) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Korvataan siivoa metodi.
     * 
     * @param Sulkusanat listana, sekä välimerkit.
     * @throws IllegalArgumentException jos lista on tyhjä tai null.
     */
    @Override
    public void siivoa(LinkedList<String> sulkusanat, String välimerkit) throws IllegalArgumentException {

        //Tarkistetaan että sulkusanat ja välimerkit eivät ole tyhjiä.
        boolean tyhjä = false;
        boolean merkitTyhjä = false;

        //Estetään NullPointerException tarkistamalla null-arvot etukäteen.
        if (sulkusanat == null) {
            tyhjä = true;
        }

        if (välimerkit == null) {
            merkitTyhjä = true;
        }

        if (tyhjä == true || sulkusanat.isEmpty() == true || merkitTyhjä == true || välimerkit.equals("")) {
            //Heitetään poikkeus.
            throw new IllegalArgumentException("Error! LinkedList can't be null or empty");
        }

        //Poistetaan välimerkit.
        String[] merkit = null;
        merkit = välimerkit.split("");
        for (int m = 0; m < merkit.length; m++) {
            this.teksti = this.teksti.replace(merkit[m], "");
        }

        //Muunnetaan kirjaimet pieniksi.
        this.teksti = this.teksti.toLowerCase();

        //Otetaan sanat erikseen.
        String[] sanat = teksti.split(" ");
        LinkedList<String> hyvätSanat = new LinkedList<String>();



        //Käydään tekstiä läpi sana kerrallaan, ja lisätään listaan jos se ei ole sulkusana.
        for (int s = 0; s < sanat.length; s++) {
            if (!sulkusanat.contains(sanat[s])) {
                hyvätSanat.add(sanat[s]);
            }
        }

        //Otetaan sanat tekstiin LinkedList hyvätSanat:sta
        //Alustetaan uusiTeksti.
        String uusiTeksti = "";

        for (int a = 0; a < hyvätSanat.size(); a++) {
            Object var = hyvätSanat.get(a);
            uusiTeksti = uusiTeksti + " " + var; 
        }

        //Uusiteksti käyttöön.
        teksti = uusiTeksti;
        //Suoritetaan vielä ylimääräisten välimerkkien poisto.
        teksti = teksti.trim().replaceAll("\\s+", " ");
        return;

    }
}