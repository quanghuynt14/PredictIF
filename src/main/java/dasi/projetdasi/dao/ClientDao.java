package dasi.projetdasi.dao;

import dasi.projetdasi.metier.modele.Client;
import dasi.projetdasi.metier.modele.Consultation;
import dasi.projetdasi.util.EStatut;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
public class ClientDao {

    /**
     *
     * @param client
     */
    public void creer(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }

    /**
     *
     * @param client
     */
    public void supprimer(Client client) {
        JpaUtil.obtenirContextePersistance().remove(client);
    }

    /**
     *
     * @param client
     * @return
     */
    public Client modifier(Client client) {
        return JpaUtil.obtenirContextePersistance().merge(client);
    }

    /**
     *
     * @param id
     * @return
     */
    public Client chercher(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }

    /**
     *
     * @param mail
     * @return
     */
    public Client chercher(String mail) {
        //Tester si renvoie null qd pas trouvé
        final String qstring = "select c from Client c where c.mail = :mail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(qstring, Client.class);
        query.setParameter("mail", mail);
        return (Client) query.getSingleResult();
    }
       
    /**
     *
     * @return
     */
    public List<Client> chercher() {
        String q = "select c from Client c order by c.nom asc, c.prenom asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Client.class);
        return query.getResultList();

    }
    
    /**
     *
     * @param idClient
     * @param idMedium
     * @return
     */
    public Client chercherPourConsultation(Long idClient, Long idMedium) {
        final String qstring = "select c from Consultation c where c.client.id = :idClient and c.medium.id = :idMedium and (c.statut = :Encours or c.statut = :ATraiter)";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(qstring, Consultation.class);
        query.setParameter("idClient", idClient);
        query.setParameter("idMedium", idMedium);
        query.setParameter("Encours", EStatut.enCours);
        query.setParameter("ATraiter", EStatut.aTraiter);
        List<Consultation> busyConsuls = query.getResultList();
        
        if (!busyConsuls.isEmpty()) {
            return null;
        } else {
            return this.chercher(idClient);
        }
    }
    
    /**
     *
     * @param idEmploye
     * @return
     */
    public Client chercherClientDeEmploye(Long idEmploye) {
        final String qstring = "select c from Consultation c where c.employe.id = :idEmploye and (c.statut = :Encours or c.statut = :ATraiter)";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(qstring, Consultation.class);
        query.setParameter("idEmploye", idEmploye);
        query.setParameter("Encours", EStatut.enCours);
        query.setParameter("ATraiter", EStatut.aTraiter);
        List<Consultation> ConsulATraiter = query.getResultList();       
        
        return ConsulATraiter.get(0).getClient();
        
    }
}
