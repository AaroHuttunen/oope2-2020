package harjoitustyo.kayttoliittyma;

import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.kokoelma.Kokoelma;
import java.util.Scanner;

/**
* Käyttöliittymä-luokka jossa toteutetaan ohjelman vuorovaikutus käyttäjän kanssa.
* 
* Luokka kutsuu metodeja muista luokista, ja suorittaa käyttäjän syötteen mukaan funktioita.
*
* @author Aaro Huttunen. aaro.huttunen@tuni.fi
*
* 428490
*
*/

public class Kayttoliittyma {

    //Alustetaan tulosteet ja muuttujat.
    public static final String kehote = "Please, enter a command:";
    public static final String virhe ="Error!";
    public static final String lopetus = "quit";
    public static final String tulostus = "print";
    public static final String lisaa = "add";
    public static final String hae = "find";
    public static final String poista = "remove";
    public static final String kasittely = "polish";
    public static final String peruuta = "reset";
    public static final String kauita = "echo";
    String tiedostotyyppi = "";
    String syote = "";
    String[] syote2;
    String[] syote3;
    String[] syote4;
    String[] hakusanat;
    boolean jatketaan = true;
    boolean kaikuu = false; 
    

    //Luodaan scanner-olio.
    Scanner myObj = new Scanner(System.in);

    /**
     * Käynnistetään käyttöliittymä.
     */
    public void kaynnista(String[] args) {

        //Tervehditään käyttäjää.
        System.out.println("Welcome to L.O.T.");

        /**
         * Ladataan kokoelma, ja otetaan tulos tarkasteluun.
         * 
         * @param args
         * @return tulos numerona 
         */ 
        Kokoelma kokoelma = new Kokoelma();
        int tulos = kokoelma.tiedostonLataus(args);

        if (tulos == 0) {
            //Tulostetaan virheviesti.
            System.out.println("Wrong number of command-line arguments!");
            System.out.println("Program terminated.");

        } else if (tulos == 1) {

            //Tulostetaan virheviesti.
            System.out.println("Missing file!");
            System.out.println("Program terminated.");

        } else {

            //Oteaan tieto tiedoston tyylistä.
            if (args[0].startsWith("jokes")) {
                tiedostotyyppi = "jokes";
            } else {
                tiedostotyyppi = "news";
            }
 
            //Siirrytään silmukkaan
            do {
                    
                //Pyydetään käyttäjältä syöte.
                System.out.println(kehote);
    
                //Luetaan käyttäjältä syöte.
                syote = myObj.nextLine();
                
                //Tarkistetaan kauitus.
                if (kaikuu == true) {
                    System.out.println(syote);
                }

                //Jos syote on quit, lopetetaan ohjelma.
                //Tällöin ei tarvitse tarkistaa syötteen parametreja.
                if (syote.equals(lopetus)) {
    
                    //Jos syöte on "quit", lopetetaan ohjelma.
                    jatketaan = false;

                    //Tulostetaan päätös.
                    System.out.println("Program terminated.");

                //Jos syote on print, tulostetaan kaikki dokumentit.
                //Tällöin ei tarvitse tarkistaa syötteen parametreja.
                } else if (syote.equals("print")) {

                    /**
                     * Kutsutaan tulosta metodia kokoelman tulostamiseksi.
                     * @return Kokoelma tulostettavaksi käyvässä muodossa.
                     */
                    String tulosta = kokoelma.tulosta();
                    System.out.print(tulosta);

                //Jos syöte on "echo", käynnistetään kaiuttaminen, tai lopetetaan se.
                //Tällöin ei tarvitse tarkistaa syötteen parametreja.
                } else if (syote.equals(kauita)) {
    
                    /**
                     * Kutsutaan kauita metodia. Metodi käynnistää tai sulkee kauittamisen.
                     * 
                     * @param kaiuttamisen totuusarvo.
                     * @return kaiuttamisen totuusarvo.
                     */

                    kaikuu = kokoelma.kauita(kaikuu);

                    //Tarkistetaan kauitus.
                    if (kaikuu == true) {
                        System.out.println(syote);
                    }

                //Jos syöte on "reset", perutaan muutokset.
                //Tällöin ei tarvitse tarkistaa syötteen parametreja.
                } else if (syote.equals(peruuta)) {

                //Ladataan tiedostot uudestaan.
                kokoelma = new Kokoelma();
                kokoelma.tiedostonLataus(args);

                } else {

                    /**
                     * Käyttäjän syötettä tarkastava metodi.
                     * 
                     * @param syote tarkastusta varten.
                     * @return totuusarvo siitä riippuen, onko syöte oikein.
                     * 
                     */
                    boolean syoteTarkistus = kokoelma.tarkista(syote);

                    //Jos syote on ollut tähän asti oikea, edetään.
                    if (syoteTarkistus == true) {

                        //Napataan syöte erikseen tarkistusta ja parametriarvoa varten.
                        syote2 = syote.split(" ");
        
                        //Jos syöte on "print", tulostetaan.
                        if (syote2[0].equals(tulostus)) {
      
                            //Asetetaan syote2 arvo tunnisteeksi, ja napataan poikkeus.
                            try {
            
                                int tunniste = Integer.parseInt(syote2[1]);
                                    
                                /**
                                 * Kutsutaan hae-metodia, ja tulostetaan saatu vastaus.
                                 * 
                                 * @param Tunniste oikean dokumentin hakemiseksi.
                                 * @return Löydetty dokumentti, tai null jos dokumenttia ei löydetä.
                                 * 
                                 */
                                Dokumentti tulostettava = kokoelma.hae(tunniste);
                                if (tulostettava == null) {
                                    System.out.println(virhe);
                                    } else {
                                        System.out.println(tulostettava);
                                    }
        
                            //Napataan poikkeus.
                            } catch (Exception e) {
                                System.out.println(virhe);
                            }
                     
                        //Jos syöte on "add", lisätään dokumentit kokoelmaan.
                        } else if (syote2[0].equals(lisaa)) {
                            
                            //Pilkotaan syötettä.
                            syote2 = syote.split(" ", 2);
        
                            //Otetaan syote erikseen paloiksi.
                            syote4 = syote2[1].split("///");
            
                            //Tarkistetaan että parametriosa on oikein. Tulostetaan virhe jos
                            //parametrejä on liikaa.
                            if (syote4[0].contains(" ")) {
        
                                System.out.println(virhe);
            
                            } else {
        
                                try {
        
                                    //Otetaan syote2 tunniste erikseen. Otetaan myös teksti erikseen syötteestä.
                                    syote3 = syote.split("///");
                                    int tunniste = Integer.parseInt(syote4[0]);
                                    String tyyppi = syote3[1];
                                    String teksti = syote3[2];
        
                                    /**
                                     * Tarkistetaan että kokoelmassa ei ole samalla tunnisteella jo tietoa.
                                     * 
                                     * @param Tunniste oikean dokumentin hakemiseksi.
                                     * @return Löydetty dokumentti, tai null jos dokumenttia ei löydetä.
                                     *
                                     * Jos dokumentti löytyi, tulostetaan virhe. 
                                     * Muussatapauksessa siirrytään eteenpäin.
                                     */
                                    Dokumentti lisättävä = kokoelma.hae(tunniste);
                                    if (lisättävä == null) {
        
                                        /**
                                         * Kutsutaan luo metodia dokumentin luomiseksi ja lisäämiseksi.
                                         * 
                                         * @param Tunniste, tyyppi, teksti ja tiedostotyyppi.
                                         * @return palauttaa totuusarvon riippuen siitä, onnistuiko kokoelman luonti.
                                         */
                                        boolean onnistui = kokoelma.luo(tunniste, tyyppi, teksti, tiedostotyyppi);
                                        if (onnistui == false) {
                                            
                                            //Tulostetaan virhe jos lisääminen ei onnistunut.
                                            System.out.println(virhe);
                                        }
                                        
                                    } else {
                                        //Tulostetaan virhe jos tunniste on jo käytössä.
                                        System.out.println(virhe);
            
                                    }
                                    
                                //Napataan poikkeus.
                                } catch (Exception e) {
                                    System.out.println(virhe);
                                }
                            }    
            
                            
                        //Jos syöte on "remove", poistetaan dokumentti.
                        } else if (syote2[0].equals(poista)) {
            
                            //Asetetaan syote2 arvo tunnisteeksi, ja napataan poikkeus.
                            try {
            
                                int tunniste = Integer.parseInt(syote2[1]);
        
                                /**
                                 * Tarkistetaan että kokoelmassa ei ole samalla tunnisteella jo tietoa.
                                 * 
                                 * @param Tunniste oikean dokumentin hakemiseksi.
                                 * @return Löydetty dokumentti, tai null jos dokumenttia ei löydetä.
                                 *
                                 * Jos dokumenttia ei löytynyt, tulostetaan virhe. 
                                 * Muussatapauksessa siirrytään eteenpäin.
                                 * 
                                 */                            
                                Dokumentti poistettava = kokoelma.hae(tunniste);
                                if (poistettava == null) {
                                    System.out.println(virhe);
                                } else {
                                    /**
                                     * Poistetaan kokoelma.
                                     * 
                                     * @param Tunniste oikean dokumentin poistamiseksi.
                                     * 
                                     */
                                    kokoelma.poista(tunniste);
                                }
                                    
                                
                            //Napataan poikkeus.
                            } catch (Exception e) {
                                System.out.println(virhe);
                            }
                            
                        //Jos syöte on "polish", esikäsitellään kokoelma
                        } else if (syote2[0].equals(kasittely)) {
            
                            //Asetetaan syote2 arvo välimerkeiksi, ja napataan poikkeus.
                            try {
        
                                /**
                                 * Esikäsitellään dokumenttia. 
                                 * Dokumentista poistetaan sulkusanat, syötteen parametriarvot
                                 * sekä muutetaan isot kirjaimet pieniksi.
                                 * 
                                 * @param Syötteen parametriarvo.
                                 */
                                kokoelma.siivoa(syote2[1]);                           
        
                            //Napataan poikkeus.
                            } catch (Exception e) {
                                System.out.println(virhe);
                            }
                        
            
                        //Jos syöte on "find", etsitään kokoelmasta tiettyjä avaimia sisältäviä dokumentteja.
                        } else if (syote2[0].equals("find")) {
            
                            //Asetetaan syote2 arvo tunnisteeksi, ja tarkistetaan samalla että parametriosa on käyvä.
                            try {
        
                                /**
                                 * Etsitään kokoelmasta parametrina annettuja sanoja.
                                 * 
                                 * @param Parametrina saadut hakusanat.
                                 * @return Löydetyt sanat.
                                 */
                               String löydetyt = kokoelma.etsi(syote2);
                               System.out.print(löydetyt);
                            
                            //Napataan poikkeus.
                            } catch (Exception e) {
                                System.out.println(virhe);
                            }
        
                        //Tulostetaan virheilmoitus jos syötettä ei tunneta.
                        } else {
                            
                            System.out.println(virhe);
            
                        }

                    //Jos syöte ei ollut oikein, tulostetaan virheilmoitus.
                    } else {
                        System.out.println(virhe);
                    }
                }
            } while (jatketaan == true);
        }
    }
}