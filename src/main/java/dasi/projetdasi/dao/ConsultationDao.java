package dasi.projetdasi.dao;

import dasi.projetdasi.metier.modele.Consultation;
import dasi.projetdasi.util.EStatut;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
public class ConsultationDao {

    /**
     *
     * @param consultation
     */
    public void creer(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }

    /**
     *
     * @param consultation
     */
    public void supprimer(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().remove(consultation);
    }

    /**
     *
     * @param consultation
     * @return
     */
    public Consultation modifier(Consultation consultation) {
        return JpaUtil.obtenirContextePersistance().merge(consultation);
    }

    /**
     *
     * @param id
     * @return
     */
    public Consultation chercher(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, id);
    }
       
    /**
     *
     * @return
     */
    public List<Consultation> chercher() {
        String q = "select c from Consultation c order by c.id asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Consultation.class);
        return query.getResultList();
    }
    
    /**
     *
     * @param idEmploye
     * @return
     */
    public Consultation chercherParIdEmployeEncours(Long idEmploye) {
        String q = "select c from Consultation c where c.employe.id = :idEmploye and c.statut = :statut";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Consultation.class);
        query.setParameter("idEmploye", idEmploye);
        query.setParameter("statut", EStatut.enCours);
        
        List<Consultation> consultations = query.getResultList();
        Consultation result = null;
        if(!consultations.isEmpty())
        {
            result = consultations.get(0);
        }
        return result;
    }
    
    /**
     *
     * @param idEmploye
     * @return
     */
    public Consultation chercherParIdEmployeATraiter(Long idEmploye) {
        String q = "select c from Consultation c where c.employe.id = :idEmploye and c.statut = :statut";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Consultation.class);
        query.setParameter("idEmploye", idEmploye);
        query.setParameter("statut", EStatut.aTraiter);
        
        List<Consultation> consultations = query.getResultList();
        Consultation result = null;
        if(!consultations.isEmpty())
        {
            result = consultations.get(0);
        }
        return result;
    }
    
    /**
     *
     * @param idEmploye
     * @return
     */
    public Consultation chercherParIdEmploye(Long idEmploye) {
        String q = "select c from Consultation c where c.employe.id = :idEmploye and (c.statut = :Encours or c.statut = :ATraiter)";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Consultation.class);
        query.setParameter("idEmploye", idEmploye);
        query.setParameter("Encours", EStatut.enCours);
        query.setParameter("ATraiter", EStatut.aTraiter);

        List<Consultation> consultations = query.getResultList();
        Consultation result = null;
        if(!consultations.isEmpty())
        {
            result = consultations.get(0);
        }
        return result;
    }
    
    /**
     *
     * @return
     */
    public float dureeMoy() {
        String q = "select c.debut from Consultation c";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Consultation.class);
        List<Date> debuts = query.getResultList();
        q = "select c.fin from Consultation c";
        query = JpaUtil.obtenirContextePersistance().createQuery(q, Consultation.class);
        List<Date> fins = query.getResultList();
        float avg = 0;
        int n = 0;
        for (int i = 0; i < debuts.size(); i++) {
            if (fins.get(i) != null && debuts.get(i) != null) {
               avg += (fins.get(i).getTime() - debuts.get(i).getTime()); 
               n++;
            }
        }
        avg /= n;
        avg /= 86400;
        return avg;
    }
}
