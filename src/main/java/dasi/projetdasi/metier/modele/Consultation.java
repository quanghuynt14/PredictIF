package dasi.projetdasi.metier.modele;

import dasi.projetdasi.util.EStatut;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
@Entity
public class Consultation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private EStatut statut;
    private String commentaire;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date debut;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fin;

    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Medium medium;

    /**
     *
     * @param client
     * @param medium
     * @param employe
     */
    public Consultation(Client client, Medium medium, Employe employe) {
        this.statut = EStatut.aTraiter;
        this.employe = employe;
        this.client = client;
        this.medium = medium;
    }

    /**
     *
     */
    public Consultation() {
    }

    /**
     *
     * @param employe
     */
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    /**
     *
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     *
     * @param medium
     */
    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    /**
     *
     * @return
     */
    public Employe getEmploye() {
        return employe;
    }

    /**
     *
     * @return
     */
    public Client getClient() {
        return client;
    }

    /**
     *
     * @return
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     *
     * @return
     */
    public EStatut getStatut() {
        return statut;
    }

    /**
     *
     * @param statut
     */
    public void setStatut(EStatut statut) {
        this.statut = statut;
    }

    /**
     *
     * @return
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     *
     * @param commentaire
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     *
     * @return
     */
    public Date getDebut() {
        return debut;
    }

    /**
     *
     * @param debut
     */
    public void setDebut(Date debut) {
        this.debut = debut;
    }

    /**
     *
     * @return
     */
    public Date getFin() {
        return fin;
    }

    /**
     *
     * @param fin
     */
    public void setFin(Date fin) {
        this.fin = fin;
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
        return "Consultation{" + "id=" + id + ", statut=" + statut + ", commentaire=" + commentaire + ", debut=" + debut + ", fin=" + fin + "}";
//                + "\n\t" + " client=" + client + ", medium=" + medium + "}";
    }
    
}
