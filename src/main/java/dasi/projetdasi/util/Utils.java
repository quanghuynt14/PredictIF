package dasi.projetdasi.util;

import dasi.projetdasi.metier.modele.Client;
import dasi.projetdasi.metier.modele.ProfilAstral;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
public class Utils {
    final static String DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     *
     * @param in
     * @return
     */
    public static Date dateFromString(String in) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date d = null;
        try {
            sdf.setLenient(false);
            d = sdf.parse(in);
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    /**
     *
     * @param c
     * @return
     * @throws IOException
     */
    public static ProfilAstral getProfilAstral(Client c) throws IOException {
        AstroTest astro = new AstroTest();

        List<String> profil = astro.getProfil(c.getPrenom(), c.getDateNaissance());
        return new ProfilAstral(profil.get(0), profil.get(1), profil.get(2), profil.get(3));
    }
}
