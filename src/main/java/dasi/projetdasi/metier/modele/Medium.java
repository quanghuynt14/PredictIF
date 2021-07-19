package dasi.projetdasi.metier.modele;

import dasi.projetdasi.util.EGenre;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Medium implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    /**
     *
     */
    protected String denomination;

    /**
     *
     */
    protected EGenre genre;

    /**
     *
     */
    protected String presentation;

    /**
     *
     */
    @OneToMany(mappedBy = "medium")
    protected List<Consultation> consultations;

    /**
     *
     * @param denomination
     * @param genre
     * @param presentation
     */
    public Medium(String denomination, EGenre genre, String presentation) {
        this.denomination = denomination;
        this.genre = genre;
        this.presentation = presentation;
    }

    /**
     *
     */
    public Medium() {
    }

    /**
     *
     * @return
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     *
     * @param denomination
     */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    /**
     *
     * @return
     */
    public EGenre getGenre() {
        return genre;
    }

    /**
     *
     * @param genre
     */
    public void setGenre(EGenre genre) {
        this.genre = genre;
    }

    /**
     *
     * @return
     */
    public String getPresentation() {
        return presentation;
    }

    /**
     *
     * @param presentation
     */
    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Medium{" + "denomination=" + denomination + ", genre=" 
                + genre + ", presentation=" + presentation + '}';
    }
    
}
