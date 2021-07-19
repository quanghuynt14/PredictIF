package dasi.projetdasi.metier.modele;

import dasi.projetdasi.util.EGenre;
import javax.persistence.Entity;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
@Entity
public class Astrologue extends Medium {
    
    private String formation;
    private int promotion;
    
    /**
     *
     * @param formation
     * @param promotion
     * @param denomination
     * @param genre
     * @param presentation
     */
    public Astrologue(String formation, int promotion, String denomination, EGenre genre, String presentation) {
        super(denomination, genre, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }

    /**
     *
     */
    public Astrologue() {
    }

    /**
     *
     * @return
     */
    public String getFormation() {
        return formation;
    }

    /**
     *
     * @param formation
     */
    public void setFormation(String formation) {
        this.formation = formation;
    }

    /**
     *
     * @return
     */
    public int getPromotion() {
        return promotion;
    }

    /**
     *
     * @param promotion
     */
    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "Astrologue{" + "formation=" + formation
                + ", promotion=" + promotion
                + "\n\t\\-" + super.toString();
    }
}
