package dasi.projetdasi.metier.modele;

import dasi.projetdasi.util.EGenre;
import javax.persistence.Entity;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
@Entity
public class Spirite extends Medium {

    private String support;

    /**
     *
     * @param support
     * @param denomination
     * @param genre
     * @param presentation
     */
    public Spirite(String support, String denomination, 
            EGenre genre, String presentation) {
        super(denomination, genre, presentation);
        this.support = support;
    }

    /**
     *
     */
    public Spirite() {
    }

    /**
     *
     * @return
     */
    public String getSupport() {
        return support;
    }

    /**
     *
     * @param support
     */
    public void setSupport(String support) {
        this.support = support;
    }

    @Override
    public String toString() {
        return "Spirite{" + "support=" + support + "}\n\t\\-" 
                + super.toString();
    }

}
