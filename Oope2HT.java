import harjoitustyo.kayttoliittyma.Kayttoliittyma;

/**
*
* Olio-ohjelmoinnin perusteet 2, TIEAB2.1b, harjoitustyö.
*
* Ohjelma lataa käynnistyessään komentoriviparametreina annetun dokumenttitiedoston, sekä 
* sulkusanat sisältävän tiedoston. Käyttäjä voi muokata tätä tiedostoa erilaisilla tavoilla,
* sekä resetoida muutokset tarvittaessa.
*
* @author Aaro Huttunen. aaro.huttunen@tuni.fi
*
* 428490
*
*/

public class Oope2HT{
    public static void main(String[] args){

        /**
         * Käynnistetään käyttöliittymä.
         */
        Kayttoliittyma UI = new Kayttoliittyma();
        UI.kaynnista(args);

    }
}