package dasi.projetdasi.metier.modele;

import dasi.projetdasi.util.EGenre;
import javax.persistence.Entity;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
@Entity
public class Cartomancien extends Medium {

    /**
     *
     * @param denomination
     * @param genre
     * @param presentation
     */
    public Cartomancien(String denomination, EGenre genre, String presentation) {
        super(denomination, genre, presentation);
    }

    /**
     *
     */
    public Cartomancien() {
    }
   
    @Override
    public String toString() {
        return "Cartomancien{}-" + super.toString();
    }
}
