package dasi.projetdasi.dao;

import dasi.projetdasi.metier.modele.Commentaire;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author salom
 */
public class CommentaireDao {

    /**
     *
     * @param comment
     */
    public void creer(Commentaire comment) {
        JpaUtil.obtenirContextePersistance().persist(comment);
    }

    /**
     *
     * @param comment
     */
    public void supprimer(Commentaire comment) {
        JpaUtil.obtenirContextePersistance().remove(comment);
    }

    /**
     *
     * @param comment
     * @return
     */
    public Commentaire modifier(Commentaire comment) {
        return JpaUtil.obtenirContextePersistance().merge(comment);
    }
    
    /**
     *
     * @return
     */
    public List<Commentaire> chercher() {
        String q = "select c from Commentaire c where (c.stars > 4) order by c.stars asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(q, Commentaire.class);
        return query.getResultList();
    }
    
}
