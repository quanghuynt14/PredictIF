package dasi.projetdasi.dao;

import dasi.projetdasi.metier.modele.Consultation;
import dasi.projetdasi.metier.modele.Medium;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
public class MediumDao {

    /**
     *
     * @param medium
     */
    public void creer(Medium medium) {
        JpaUtil.obtenirContextePersistance().persist(medium);
    }

    /**
     *
     * @param medium
     */
    public void supprimer(Medium medium) {
        JpaUtil.obtenirContextePersistance().remove(medium);
    }

    /**
     *
     * @param medium
     * @return
     */
    public Medium modifier(Medium medium) {
        return JpaUtil.obtenirContextePersistance().merge(medium);
    }

    /**
     *
     * @param id
     * @return
     */
    public Medium chercher(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }
       
    /**
     *
     * @return
     */
    public List<Medium> chercher() {
        String q = "select m from Medium m order by m.id asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Medium.class);
        return query.getResultList();

    }
    
    /**
     *
     * @param m
     * @return
     */
    public Long nbConsultation(Medium m) {
        String q = "select count(c) as total from Consultation c where c.medium = :m";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Long.class);
        query.setParameter("m", m);
        return (Long) query.getSingleResult();
    }
}
