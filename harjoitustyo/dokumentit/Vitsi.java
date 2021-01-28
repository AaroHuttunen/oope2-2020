package harjoitustyo.dokumentit;

/**
* Vitsi-luokka Vitsi-tyyppisiä olioita varten.
*
* @author Aaro Huttunen. aaro.huttunen@tuni.fi
*
* 428490
*
*/

public class Vitsi extends Dokumentti {

    /**
    * Otuksen laji.
    */
    private String laji;

    //Rakentajat.
    public Vitsi(int i, String l, String s) {
        super(i, s);
        laji(l);
    }

    //Aksessorit.
    public void laji(String l) throws IllegalArgumentException {
        if (l != null && l.length() > 0) {
            laji = l;
        } else {
            throw new IllegalArgumentException("Error! String can't be null or empty");
        }
    }

    public String laji() {
        return laji;
    }

    /**
     * Korvataan toString metodi.
     * 
     * @return String oikeassa muodossa.
     */
    @Override
    public String toString() {
        String[] osat = super.toString().split("///");
        String osa1 = osat[0];
        String osa2 = osat[1];
        return osa1 + "///" + laji + "///" + osa2;
    }

}