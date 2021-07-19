package dasi.projetdasi.metier.modele;

import dasi.projetdasi.util.EGenre;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
@Entity
public class Employe extends Utilisateur {

    private boolean disponible;

    @OneToMany(mappedBy = "employe")
    private List<Consultation> consultations;
    
    /**
     *
     * @param disponible
     * @param nom
     * @param prenom
     * @param numTel
     * @param mail
     * @param motDePasse
     * @param genre
     */
    public Employe(boolean disponible, String nom, String prenom, String numTel, String mail, String motDePasse, EGenre genre) {
        super(nom, prenom, numTel, mail, motDePasse, genre);
        this.consultations = new ArrayList<Consultation>();
        this.disponible = disponible;
    }

    /**
     *
     */
    public Employe() {
    }

    /**
     *
     * @return
     */
    public List<Consultation> getConsultations() {
        return consultations;
    }

    /**
     *
     * @return
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     *
     * @param disponible
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Employe{" + "disponible=" + disponible + '}'
                + "\n\t\\-" + super.toString();
    }

}
