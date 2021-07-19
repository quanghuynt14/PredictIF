package dasi.projetdasi.metier.modele;

import dasi.projetdasi.util.EGenre;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Utilisateur implements Serializable{

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    /**
     *
     */
    protected String nom;

    /**
     *
     */
    protected String prenom;

    /**
     *
     */
    protected String numTel;

    /**
     *
     */
    @Column(nullable = false, unique = true)
    protected String mail;

    /**
     *
     */
    protected String motDePasse;

    /**
     *
     */
    protected EGenre genre;
    
    /**
     *
     */
    public Utilisateur() {
    }

    /**
     *
     * @param nom
     * @param prenom
     * @param numTel
     * @param mail
     * @param motDePasse
     * @param genre
     */
    public Utilisateur(String nom, String prenom, String numTel, String mail, String motDePasse, EGenre genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.genre = genre;
    }
    
    /**
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @param prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     *
     * @param numTel
     */
    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    /**
     *
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     *
     * @param motDePasse
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
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
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @return
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     *
     * @return
     */
    public String getNumTel() {
        return numTel;
    }

    /**
     *
     * @return
     */
    public String getMail() {
        return mail;
    }

    /**
     *
     * @return
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     *
     * @return
     */
    public EGenre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "User{" + "nom=" + nom + ", prenom=" + prenom 
                + ", numTel=" + numTel + ", mail=" + mail 
                + ", motDePasse=" + motDePasse 
                + ", genre=" + genre + '}';
    }
}
