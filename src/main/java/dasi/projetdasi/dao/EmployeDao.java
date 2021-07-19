package dasi.projetdasi.dao;

import dasi.projetdasi.metier.modele.Employe;
import dasi.projetdasi.util.EGenre;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
public class EmployeDao {

    /**
     *
     * @param employe
     */
    public void creer(Employe employe) {
        JpaUtil.obtenirContextePersistance().persist(employe);
    }

    /**
     *
     * @param employe
     */
    public void supprimer(Employe employe) {
        JpaUtil.obtenirContextePersistance().remove(employe);
    }

    /**
     *
     * @param employe
     * @return
     */
    public Employe modifier(Employe employe) {
        return JpaUtil.obtenirContextePersistance().merge(employe);
    }

    /**
     *
     * @param id
     * @return
     */
    public Employe chercher(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Employe.class, id);
    }

    /**
     *
     * @param mail
     * @return
     */
    public Employe chercher(String mail) {
        //Tester si renvoie null qd pas trouvé
        final String qstring = "select e from Employe e where e.mail = :mail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(qstring, Employe.class);
        query.setParameter("mail", mail);
        return (Employe) query.getSingleResult();
    }
       
    /**
     *
     * @return
     */
    public List<Employe> chercher() {
        String q = "select e from Employe e order by e.nom asc, e.prenom asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Employe.class);
        return query.getResultList();

    }
    
    /**
     *
     * @param genre
     * @return
     */
    public Employe chercherDisponible(EGenre genre)
    {
        String req = "select e from Employe e where e.disponible = :dispo and e.genre = :genre";
        TypedQuery<Employe> query = JpaUtil.obtenirContextePersistance().createQuery(req, Employe.class);
        query.setParameter("dispo", true);
        query.setParameter("genre", genre);
        List<Employe> employes = query.getResultList();
        Employe result = null;
        if(!employes.isEmpty())
        {
            result = employes.get(0);
            //System.out.println(result.getConsultations().size());
            for (Employe em : employes) {
                if (em.getConsultations().size() < result.getConsultations().size()) {
                    result = em;
                }
            }
        }
        return result;
    }
    
    /**
     *
     * @param e
     * @return
     */
    public Long nbConsultation(Employe e) {
        final String qstring = "select count(c) from Consultation c where c.employe = :e";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(qstring, Long.class);
        query.setParameter("e", e);
        return (Long) query.getSingleResult();
    }
}
