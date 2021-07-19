package dasi.projetdasi.metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author salom
 */

@Entity
public class Commentaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float stars;
    private String titre;
    private String contenu;

    @ManyToOne
    private Client auteur;

    /**
     *
     */
    public Commentaire() {
    }

    /**
     *
     * @param stars
     * @param titre
     * @param contenu
     * @param auteur
     */
    public Commentaire(float stars, String titre, String contenu, Client auteur) {
        this.stars = stars;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
    }

    /**
     *
     * @return
     */
    public Client getAuteur() {
        return auteur;
    }

    /**
     *
     * @param auteur
     */
    public void setAuteur(Client auteur) {
        this.auteur = auteur;
    }

    /**
     *
     * @return
     */
    public float getStars() {
        return stars;
    }

    /**
     *
     * @param stars
     */
    public void setStars(float stars) {
        this.stars = stars;
    }

    /**
     *
     * @return
     */
    public String getTitre() {
        return titre;
    }

    /**
     *
     * @param titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     *
     * @return
     */
    public String getContenu() {
        return contenu;
    }

    /**
     *
     * @param contenu
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
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
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", stars=" + stars + ", titre=" + titre + ", contenu=" + contenu + ", auteur=" + auteur + '}';
    }
    
}
