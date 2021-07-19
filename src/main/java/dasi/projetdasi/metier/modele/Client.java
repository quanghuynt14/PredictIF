package dasi.projetdasi.metier.modele;

import dasi.projetdasi.util.EGenre;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
@Entity
public class Client extends Utilisateur {

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateNaissance;
    private String adresse;

    @OneToOne(cascade = CascadeType.ALL)
    private ProfilAstral profilAstral;

    @OneToMany(mappedBy = "client")
    private List<Consultation> consultations;

    /**
     *
     */
    public Client() {

    }

    /**
     *
     * @param nom
     * @param prenom
     * @param mail
     * @param motDePasse
     * @param numTel
     * @param genre
     * @param dateNaissance
     * @param adresse
     */
    public Client(String nom, String prenom, String mail, String motDePasse, 
            String numTel, EGenre genre, Date dateNaissance, String adresse) {
        super(nom, prenom, numTel, mail, motDePasse, genre);
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.consultations = new ArrayList<Consultation>();
    }
    
    /**
     *
     * @param consultation
     */
    public void ajouterConsultation(Consultation consultation) {
        this.consultations.add(consultation);
    }

    /**
     *
     * @return
     */
    public Date getDateNaissance() {
        return dateNaissance;
    }

    /**
     *
     * @return
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     *
     * @return
     */
    public ProfilAstral getProfilAstral() {
        return profilAstral;
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
     * @param dateNaissance
     */
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     *
     * @param adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     *
     * @param profilAstral
     */
    public void setProfilAstral(ProfilAstral profilAstral) {
        this.profilAstral = profilAstral;
    }

    /**
     *
     * @param consultations
     */
    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    @Override
    public String toString() {
        String res = "Client{ id = " + id + " " + super.toString()
                + "\n\t dateNaissance=" + dateNaissance 
                + ", adresse=" + adresse 
                + "\n\t " + profilAstral + "}";
        
        res += "\n\t Historique des consultations: \n";
        for (Consultation con : consultations) {
            res += "\n\t\t - " + con.toString() + "\n";
        }
        
        return res;
    }
}
