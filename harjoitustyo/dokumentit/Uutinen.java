package harjoitustyo.dokumentit;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
* Uutinen luokka Uutinen-tyyppisiä olioita varten.
*
* @author Aaro Huttunen. aaro.huttunen@tuni.fi
*
* 428490
*
*/

public class Uutinen extends Dokumentti {

    /**
     * Otuksen päivämäärä.
     */
    private LocalDate päivämäärä;

    //Rakentajat.
    public Uutinen(int i, LocalDate p, String s) {
    super(i, s);
    päivämäärä(p);
    }

    //Aksessorit.
    public void päivämäärä(LocalDate p) throws IllegalArgumentException {
        if (p != null){
            päivämäärä = p;
        } else {
            throw new IllegalArgumentException("Error! LocalDate can't be null");
        }
    }

    public LocalDate päivämäärä() {
        return päivämäärä;
    }
    
    /**
     * Korvataan toString metodi.
     * 
     * @return String oikeassa muodossa.
     */
    @Override
    public String toString() {
        //Formatoidaan päivämäärä oikeaan muotoon.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        String[] osat = super.toString().split("///");
        String osa1 = osat[0];
        String osa2 = osat[1];
        return osa1 + "///" + formatter.format(päivämäärä) + "///" + osa2;
    }

}