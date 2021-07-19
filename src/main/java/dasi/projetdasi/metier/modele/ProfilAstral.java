package dasi.projetdasi.metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
@Entity
public class ProfilAstral implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String signeZod;
    private String signeChin;
    private String couleur;
    private String animal;

    /**
     *
     */
    public ProfilAstral() {

    }

    /**
     *
     * @param signeZod
     * @param signeChin
     * @param couleur
     * @param animal
     */
    public ProfilAstral(String signeZod, String signeChin, String couleur, String animal) {
        this.signeZod = signeZod;
        this.signeChin = signeChin;
        this.couleur = couleur;
        this.animal = animal;
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
    public String getSigneZod() {
        return signeZod;
    }

    /**
     *
     * @return
     */
    public String getSigneChin() {
        return signeChin;
    }

    /**
     *
     * @return
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     *
     * @return
     */
    public String getAnimal() {
        return animal;
    }

    /**
     *
     * @param signeZod
     */
    public void setSigneZod(String signeZod) {
        this.signeZod = signeZod;
    }

    /**
     *
     * @param signeChin
     */
    public void setSigneChin(String signeChin) {
        this.signeChin = signeChin;
    }

    /**
     *
     * @param couleur
     */
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    /**
     *
     * @param animal
     */
    public void setAnimal(String animal) {
        this.animal = animal;
    }
    
    @Override
    public String toString() {
        return "ProfilAstral{" + "signeZod=" + signeZod + ", signeChin=" + signeChin + ", couleur=" + couleur + ", animal=" + animal + '}';
    }
}
