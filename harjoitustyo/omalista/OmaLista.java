
package harjoitustyo.omalista;

import harjoitustyo.apulaiset.Ooperoiva;
import java.util.LinkedList;

/**
* OmaLista luokka joka periytyy LinkedList-luokasta, ja toteuttaa Ooperoiva-rajapinnan.
* 
* Luokka lisää olioita listalle, sekä poistaa olioita listalta.
*
* @author Aaro Huttunen. aaro.huttunen@tuni.fi
*
* 428490
*
*/

public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E> {

    /**
     * Korvataan lisää metodi.
     * 
     * @param lisättävä dokumentti.
     * @throws IllegalArgumentException poikkeuksen jos lista on tyhjä tai ei käytä rajapintaa.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void lisää(E uusi) throws IllegalArgumentException{

        //Alustetaan muuttujia.
        boolean toteuttaa = false;
        boolean tyhjä = false;

        //Tarkistetaan että lista ei ole null.
        if (uusi != null) {
            tyhjä = true;
        }

        //Tarkistetaan että listan olio voi toteutaa rajapinnan.
        if (uusi instanceof Comparable) {
            toteuttaa = true;
        }

        //Edetään jos lista ei ole tyhjä ja sen voi toteuttaa.
        if (tyhjä == true && toteuttaa == true) {

            //Alustetaan muuttujat.
            Comparable lisättävä = (Comparable)uusi;
            boolean jatketaan = false;
            int i = 0;

            //Lisätään listaan vain yksi uusi, jos koko on yksi.
            if (size() == 0) {

                //Lisätään uusi listaan.
                add(uusi);

            } else {

                //Siirrytään silmukkaan.
                do {

                    //Otetaan arvot vertailua varten.
                    Comparable verrattava = (Comparable)get(i);

                    //Suoritetaan vertailu, ja lisätään arvo jos verrattava on suurempi.
                    if (lisättävä.compareTo(verrattava) < 0) {
                        add(i, uusi);
                        jatketaan = true;
                    }
                    i++;

                //Poistutaan silmukasta jos ollaan lisätty ja koko täsmää.
                } while (jatketaan == false && i < size());

                //Lisätään viimeinen.
                if (i == size()) {
                    addLast(uusi);
                }
            }

        } else {

            //Heitetään poikkeus.
            throw new IllegalArgumentException("Error! LinkedList can't be null and must implement Comparable.");
        }
    }
}