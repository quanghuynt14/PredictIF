package dasi.projetdasi.metier.service;

import dasi.projetdasi.dao.ClientDao;
import dasi.projetdasi.dao.CommentaireDao;
import dasi.projetdasi.dao.ConsultationDao;
import dasi.projetdasi.dao.EmployeDao;
import dasi.projetdasi.metier.modele.Client;
import dasi.projetdasi.util.Message;
import dasi.projetdasi.util.Utils;
import dasi.projetdasi.dao.JpaUtil;
import dasi.projetdasi.dao.MediumDao;
import dasi.projetdasi.metier.modele.Astrologue;
import dasi.projetdasi.metier.modele.Cartomancien;
import dasi.projetdasi.metier.modele.Commentaire;
import dasi.projetdasi.metier.modele.Consultation;
import dasi.projetdasi.metier.modele.Employe;
import dasi.projetdasi.metier.modele.Medium;
import dasi.projetdasi.metier.modele.ProfilAstral;
import dasi.projetdasi.metier.modele.Spirite;
import dasi.projetdasi.util.AstroTest;
import dasi.projetdasi.util.EGenre;
import dasi.projetdasi.util.EStatut;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Thibaud, Florie, Salomé, Francine, Huy
 */
public class Service {
    
    /**
     * Initialise les tables Employes et Médiums de la base de données avec 
     * les données codées dans la fonction. 
     *
     * @return boolean true si l’insertion s’est bien passée, sinon false
     */

    public boolean initaliserBD() {
        boolean resultat = false;

        EmployeDao employeDao = new EmployeDao();
        MediumDao mediumDao = new MediumDao();

        ArrayList<Employe> le = new ArrayList<Employe>() {
            {
                add(new Employe(true, "BORROTI MATIAS DANTAS", "Raphaël",
                        "0328178508", "rborrotimatiasdantas4171@free.fr", "Raph318", EGenre.H));
                add(new Employe(true, "BIUVAER", "Nacer", "0308160740",
                        "nacer.biuvaer@yahoo.com", "password", EGenre.H));
                add(new Employe(true, "LOU", "Silvia", "0378851388",
                        "silvia.lou@laposte.net", "012285", EGenre.F));
                add(new Employe(true, "FRINCHAT", "Erika", "0195111691",
                        "efrinchat@yahoo.com", "rex", EGenre.F));
                add(new Employe(true, "YENG", "Karen", "0599335457",
                        "kyeng217@free.fr", "Tak3M3ToTh3Man@g3r", EGenre.F));
                add(new Employe(true, "DU", "Jean-Alexandre", "0333908329",
                        "jean-alexandre.du@outlook.com", "jadu69", EGenre.H));
                add(new Employe(true, "MUGANO", "Céline", "0431436169",
                        "cmugano4860@free.fr", "celinemugano", EGenre.F));
                add(new Employe(true, "MORY DE MINTIGNY", "Vincent", "0798987830",
                        "incent.mory-de-mintigny@free.fr", "123456789", EGenre.H));
                add(new Employe(true, "SMIJKIL", "Nicolas", "0948843291",
                        "nsmijkil1775@free.fr", "akifchngleac@ade647", EGenre.H));
                add(new Employe(true, "LOBAT", "Caroline", "0854222956",
                        "caroline.lobat@gmail.com", "upupdowndownleftrightleftrightBA", EGenre.F));
                add(new Employe(true, "CLOCHAR", "Éléonore", "0673073109",
                        "eclochar@laposte.net", "sivoupléunepiece", EGenre.F));
            }
        };

        ArrayList<Medium> lm = new ArrayList<Medium>() {
            {
                add(new Spirite("Boule de cristal", "Gwenaëlle", EGenre.F,
                        "Spécialiste des grandes conversations au-delà de TOUTES les frontières."));
                add(new Spirite("Marc de café, boule de cristal, oreilles de lapin", "Professeur Tran", EGenre.H,
                        "Votre avenir est devant vous : regardons-le ensemble !"));
                add(new Cartomancien("Mme Irma", EGenre.F,
                        "Comprenez votre entourage grâce à mes cartes ! Résultats rapides."));
                add(new Cartomancien("Endora", EGenre.F,
                        "Mes cartes répondront à toutes vos questions personnelles."));
                add(new Astrologue("École Normale Supérieure d’Astrologie (ENS-Astro)", 2006, "Serena", EGenre.F,
                        "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé."));
                add(new Astrologue("Institut des Nouveaux Savoirs Astrologiques", 2010, "Mr M", EGenre.H,
                        "Avenir, avenir, que nous réserves-tu ? N'attendez plus, demandez à me consulter!"));
            }
        };
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            le.forEach(e -> {
                employeDao.creer(e);
            });
            lm.forEach(m -> {
                mediumDao.creer(m);
            });
            JpaUtil.validerTransaction();
            System.out.println("> Base de donnée initialisée.");
            resultat = true;
        } catch (Exception e) {
            System.out.println("> Échec initialisation base de donnée");
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }
    
    /**
     * Enregistre le client passé en paramètre dans la base de données et 
     * envoie un mail au client confirmant la création du compte. 
     *
     * @param client
     * @return Long Id du client en cas de succès, sinon null
     */

    public Long inscrireClient(Client client) {
        Long resultat = null;

        ClientDao clientDao = new ClientDao();

        StringWriter corps = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(corps);

        JpaUtil.creerContextePersistance();
        try {
            client.setProfilAstral(Utils.getProfilAstral(client));
            JpaUtil.ouvrirTransaction();
            clientDao.creer(client);
            JpaUtil.validerTransaction();

            mailWriter.println("Bonjour " + client.getNom() + ",");
            mailWriter.println();
            mailWriter.println("Nous vous confirmons votre inscription au service PREDICT'IF.");
            mailWriter.print("Rendez-vous vite sur notre site pour consulter votre profil Astrologique et ");
            mailWriter.println("profiter des dons incroyables de nos mediums.");
            mailWriter.println();
            mailWriter.println("Cordialement,");
            mailWriter.println("L'équipe PREDICT'IF");

            Message.envoyerMail(
                    "contact@predict.if",
                    client.getMail(),
                    "Bienvenue chez PREDICT'IF",
                    corps.toString()
            );

            resultat = client.getId();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

            mailWriter.println("Bonjour " + client.getNom() + ",");
            mailWriter.println();
            mailWriter.println("Votre inscription au service PREDICT'IF a malencontreusement échoué...");
            mailWriter.println("Merci de recommencer ultérieurement.");
            mailWriter.println();
            mailWriter.println("Cordialement,");
            mailWriter.println("L'équipe PREDICT'IF");

            Message.envoyerMail(
                    "contact@predict.if",
                    client.getMail(),
                    "Echec de l'inscritpion chez PREDICT'IF",
                    corps.toString()
            );

            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }
    
    /*
     * Vérifie l’existence de la combinaison mail/motdepasse passée en paramètre
     * dans la base de données de Client
     *
     * @param mail
     * @param motDePasse
     * @return Client le client correspondant si trouvé, sinon null
     */

    public Client authentifierClient(String mail, String motDePasse) {
        ClientDao clientDao = new ClientDao();
        Client resultat = null;

        JpaUtil.creerContextePersistance();
        try {
            Client client = clientDao.chercher(mail);
            if (client != null) {
                if (client.getMotDePasse().equals(motDePasse)) {
                    resultat = client;
                }
            }
        } catch (Exception ex) {
            System.out.println("> Echec de l'authentification client");
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    /**
     * Vérifie l’existence de la combinaison mail/motdepasse passée en paramètre
     * dans la base de données de Employe
     * Retourne l'employe correspondant si trouvé, sinon null
     *
     * @param mail
     * @param motDePasse
     * @return Employe l'employe correspondant si trouvé, sinon null
     */

    public Employe authentifierEmploye(String mail, String motDePasse) {
        EmployeDao employeDao = new EmployeDao();
        Employe resultat = null;

        JpaUtil.creerContextePersistance();
        try {
            Employe employe = employeDao.chercher(mail);
            if (employe != null) {
                if (employe.getMotDePasse().equals(motDePasse)) {
                    resultat = employe;
                }
            }
        } catch (Exception ex) {
            System.out.println("> Echec de l'authentification employe");
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    /**
     * Recherche le client dans la base de données correspondant 
     * à l’ID passé en paramètre. Renvoie null sinon
     *
     * @param id
     * @return Client le client correspondant à l'Id, sinon null
     */

    public Client rechercherClientParId(Long id) {
        ClientDao clientDao = new ClientDao();
        Client client = null;

        JpaUtil.creerContextePersistance();
        try {
            client = clientDao.chercher(id);
        } catch (Exception ex) {
            System.out.println("> Erreur de trouverClientParId");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return client;
    }
    
    /**
     * Recherche le client correspondant à la consultation
     * à traiter ou en cours de l’employé
     *
     * @param idEmploye
     * @return Client le client correspondant à la consultation 
     * à traiter ou en cours de l’employé
     */

    public Client rechercherClientDeEmploye(Long idEmploye) {
        ClientDao clientDao = new ClientDao();
        Client client = null;

        JpaUtil.creerContextePersistance();

        try {
            client = clientDao.chercherClientDeEmploye(idEmploye);
        } catch (Exception ex) {
            System.out.println("> Erreur de rechercherClientDeEmploye");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return client;
    }
    
    /**
     * Recherche la consultation "en cours" correspondant de l’employé 
     * dont l’id est passé en paramètre
     *
     * @param idEmploye
     * @return Consultation la consultation en cours correspondant de l’employé 
     */

    public Consultation rechercherConsultationEncoursDeEmploye(Long idEmploye) {
        ConsultationDao consultationDao = new ConsultationDao();
        Consultation consultation = null;

        JpaUtil.creerContextePersistance();

        try {
            consultation = consultationDao.chercherParIdEmployeEncours(idEmploye);
        } catch (Exception ex) {
            System.out.println("> Erreur de rechercherConsultationEncoursDeEmploye");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return consultation;
    }
    
    /**
     * Recherche la consultation "à traiter" ou "en cours" correspondant de l’employé 
     * dont l’id est passé en paramètre 
     * 
     * @param idEmploye
     * @return Consultation la consultation "à traiter" ou "en cours" 
     * correspondant de l’employé 
     */

    public Consultation rechercherConsultationDeEmploye(Long idEmploye) {
        ConsultationDao consultationDao = new ConsultationDao();
        Consultation consultation = null;

        JpaUtil.creerContextePersistance();

        try {
            consultation = consultationDao.chercherParIdEmploye(idEmploye);
        } catch (Exception ex) {
            System.out.println("> Erreur de rechercherConsultationDeEmploye");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return consultation;
    }

    /**
     *
     * @return List Client dans la base de données
     */
    public List<Client> listerClients() {
        ClientDao clientDao = new ClientDao();
        List<Client> clients = null;

        JpaUtil.creerContextePersistance();
        try {
            clients = clientDao.chercher();
        } catch (Exception e) {
            System.out.println("> Erreur de listerMediums");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return clients;
    }

    /**
     *
     * @return List Employe dans la base de données
     */

    public List<Employe> listerEmployes() {
        EmployeDao employeDao = new EmployeDao();
        List<Employe> employes = null;

        JpaUtil.creerContextePersistance();
        try {
            employes = employeDao.chercher();
        } catch (Exception e) {
            System.out.println("> Erreur de listerEmployes");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return employes;
    }

    /**
     *
     * @return List Medium dans la base de données    
     */

    public List<Medium> listerMediums() {
        MediumDao mediumDao = new MediumDao();
        List<Medium> mediums = null;

        JpaUtil.creerContextePersistance();
        try {
            mediums = mediumDao.chercher();
        } catch (Exception e) {
            System.out.println("> Erreur de listerMediums");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return mediums;
    }

    /**
     * La création d’une demande de consultation pour le client avec le médium 
     * correspondant aux ids passés en paramètre
     * si 1 -> Cet client as dèjá demandé une consultation avec ce médium 
     * si 2 -> Il n'y a pas de employe disponible 
     * si 3 -> Ok demander consultation
     * si l'autre cas -> ERREUR
     * Envoie  également  une  notification à l’employé choisi pour 
     * l’informer qu’un client demande une consultation.
     *
     * @param idClient
     * @param idMedium
     * @return int en fonction de comment s’est passé la création
     */

    public int demanderConsultation(Long idClient, Long idMedium) {
        int res = 0;

        MediumDao mediumDao = new MediumDao();
        ClientDao clientDao = new ClientDao();
        EmployeDao employeDao = new EmployeDao();
        ConsultationDao consultationDao = new ConsultationDao();

        Medium medium;
        Employe employe;
        Client client;

        JpaUtil.creerContextePersistance();

        try {
            medium = mediumDao.chercher(idMedium);
            employe = employeDao.chercherDisponible(medium.getGenre());
            client = clientDao.chercherPourConsultation(idClient, idMedium);

            if (client == null) {
                res = 1;
            } else if (employe == null) {
                res = 2;
            } else {
                Consultation consultation = new Consultation(client, medium, employe);
                client.ajouterConsultation(consultation);
                employe.setDisponible(false);

                JpaUtil.ouvrirTransaction();

                consultationDao.creer(consultation);
                clientDao.modifier(client);
                employeDao.modifier(employe);

                JpaUtil.validerTransaction();

                String msg = "Bonjour " + employe.getPrenom() + ". Consultation requise pour " + ((client.getGenre() == EGenre.F) ? "Mme" : "Mr") + " " + client.getPrenom()
                        + " " + client.getNom() + ". Médium à incarner : " + medium.getDenomination();
                Message.envoyerNotification(employe.getNumTel(), msg);

                res = 3;
            }
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            System.out.println("> Erreur de demanderConsultation");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }
    
    /**
     * L’acceptation d’une demande pour l’employé dont l’id est passé en paramètre
     * si 1 -> Consultation a déjà été accepté par cet employe
     * si 2 -> Ok Accepter Consultation
     * si l'autre cas -> ERREUR
     * Il passe le statut de la consultation à «En cours»  et envoie une notification 
     * au client, l’informantque l’employé est prêt à assurer la consultation.
     *
     * @param idEmploye
     * @return int en fonction de comment s’est passé la l’acceptation 
     */

    public int accepterConsultation(Long idEmploye) {
        int res = 0;

        EmployeDao employeeDao = new EmployeDao();
        ConsultationDao consultationDao = new ConsultationDao();

        JpaUtil.creerContextePersistance();

        try {
            Consultation consultation = consultationDao.chercherParIdEmployeATraiter(idEmploye);
            if (consultation != null) {

                consultation.setDebut(new Date());
                consultation.setStatut(EStatut.enCours);

                JpaUtil.ouvrirTransaction();

                consultationDao.modifier(consultation);

                JpaUtil.validerTransaction();

                String msg = "Bonjour " + consultation.getClient().getPrenom() + ". J'ai bien reçu votre demande de consultation du " + consultation.getDebut()
                        + ".\nVous pouvez dès à présent me contacter au 06 55 44 77 88. A tout de suite ! Médiumiquement vôtre, " + " " + consultation.getMedium().getDenomination();
                Message.envoyerNotification(consultation.getClient().getNumTel(), msg);

                res = 2;

            } else {
                res = 1;
            }
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            System.out.println("> Erreur de accepterConsultation");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }
    
    /**
     * Arrêter une consultation
     * Il met à jour la date de fin, passe  le  statut  de  la consultation à «finie» 
     * et associe le commentaire déposé par l’employé au sujet de la consultation.
     * 
     * @param idEmploye
     * @param comment
     * @return boolean true si réussi d'arreter une consultation, sinon false
     */

    public boolean arreterConsultation(Long idEmploye, String comment) {
        boolean res = false;
        EmployeDao employeeDao = new EmployeDao();
        ConsultationDao consultationDao = new ConsultationDao();

        JpaUtil.creerContextePersistance();

        try {
            Consultation consultation = consultationDao.chercherParIdEmployeEncours(idEmploye);
            if (consultation != null && consultation.getStatut() == EStatut.enCours) {

                consultation.setFin(new Date());
                consultation.setStatut(EStatut.finie);
                consultation.setCommentaire(comment);
                consultation.getEmploye().setDisponible(true);

                JpaUtil.ouvrirTransaction();

                consultationDao.modifier(consultation);
                employeeDao.modifier(consultation.getEmploye());

                JpaUtil.validerTransaction();

                res = true;

            }
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            System.out.println("> Erreur de arreterConsultation");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }
    
    /**
     * La liste des consultations effectuées par un client. 
     * Cela pourra être consulté par le client mais également par l’employé 
     * lors d’une consultation avec ce client.
     *
     * @param c
     * @return List Consultation des consultations effectuées par un client. 
     */

    public List<Consultation> historiqueUtilisateur(Client c) {
        List<Consultation> consults = null;
        ClientDao clientDao = new ClientDao();
        Client client;

        JpaUtil.creerContextePersistance();

        try {
            client = clientDao.chercher(c.getMail());
            System.out.println(c.getMail());
            System.out.println(client);
            if (client != null) {
                consults = client.getConsultations();
                int size = consults.size();
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        System.out.println("Consultation " + (i + 1));
                        System.out.println(consults.get(i));

                    }
                } else {
                    System.out.println("Pas de consultation effectuée");
                }

            }
        } catch (Exception e) {
            System.out.println("> Client non valide");
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return consults;

    }
    
    /**
     * Le profil astral d’un client. 
     * Cela sera utile lors d’une consultation mais 
     * également si le client veut consulter son profil astral.
     *
     * @param c
     * @return ProfilAstral le profil astral d’un client. 
     */

    public ProfilAstral profilAstralClient(Client c) {
        ProfilAstral profil = null;
        ClientDao clientDao = new ClientDao();
        Client client;

        JpaUtil.creerContextePersistance();

        try {
            client = clientDao.chercher(c.getMail());
            System.out.println(c.getMail());
            System.out.println(client);
            if (client != null) {
                profil = client.getProfilAstral();
                System.out.println(profil);
            }
        } catch (Exception e) {
            System.out.println("> Client non valide");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return profil;
    }
    
    /*
     * Liste de prédictions pour aider l’employé dont l’ID est donné 
     * selon les valeurs d’amour, sante, travail passées en paramètre
     * Utiliser AstroTest()
    */

    /**
     *
     * @param idEmploye
     * @param amour
     * @param sante
     * @param travail
     * @return List String des prédictions pour aider l’employé 
     */

    public List<String> helpPrediction(Long idEmploye, Integer amour, Integer sante, Integer travail) {
        Consultation consultation = rechercherConsultationEncoursDeEmploye(idEmploye);

        AstroTest astroApi = new AstroTest();
        List<String> predictions = null;
        try {
            if (consultation != null && consultation.getClient() != null) {
                predictions = astroApi.getPredictions(consultation.getClient().getProfilAstral().getCouleur(), consultation.getClient().getProfilAstral().getAnimal(), amour, sante, travail);
            }
        } catch (Exception ex) {
            System.out.println("Error helpPrediction");
        }
        
        return predictions;
    }
    
    /**
     *
     * @return Hashmap contenant la liste des médiums et le nombre de 
     * consultations associées à chacun
     */

    public HashMap<Medium, Long> statMedium() {

        HashMap<Medium, Long> res = new HashMap<>();

        List<Medium> mediums = listerMediums();
        MediumDao mediumDao = new MediumDao();
        
        JpaUtil.creerContextePersistance();
        try {
            mediums.forEach(m -> {
                res.put(m, mediumDao.nbConsultation(m));       
            });

        } catch (Exception e) {
            System.out.println("> Error StatMedium");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }
    
    /** 
     *
     * @return Hashmap contenant la liste des employes et le nombre de 
     * consultations associées à chacun
     */

    public HashMap<Employe, Long> statEmploye() {
        HashMap<Employe, Long> res = new HashMap<>();

        List<Employe> employes = listerEmployes();
        EmployeDao employeDao = new EmployeDao();

        JpaUtil.creerContextePersistance();
        try {
            employes.forEach(e -> {
                res.put(e, employeDao.nbConsultation(e));       
            });

        } catch (Exception e) {
            System.out.println("> Error statEmploye");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }
    
    /** 
     *
     * @return List Medium la liste des 5 médiums les plus populaires 
     * qui ont fait le plus de consultations
     */

    public List<Medium> statTop5() {
        List<Medium> mediums = listerMediums();
        return mediums.subList(0, 5);
    }
    
    /**
     *
     * @return float la durée moyenne d’une consultation
     */

    public float statDureeMoyConsultation() {
        ConsultationDao consultationDao = new ConsultationDao();
        float res = 0;
        JpaUtil.creerContextePersistance();
        try {
            res = consultationDao.dureeMoy();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("> Error statDureeMoyConsultation");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }

    /**
     *
     * @param client
     * @return
     */
    public Long modifierClient(Client client) {
        Long resultat = null;

        ClientDao clientDao = new ClientDao();
        JpaUtil.creerContextePersistance();
        try {

            JpaUtil.ouvrirTransaction();
            clientDao.modifier(client);
            JpaUtil.validerTransaction();
            resultat = client.getId();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }

    /**
     *
     * @param employe
     * @return
     */
    public Long modifierEmploye(Employe employe) {
        Long resultat = null;

        EmployeDao employeDao = new EmployeDao();
        JpaUtil.creerContextePersistance();
        try {

            JpaUtil.ouvrirTransaction();
            employeDao.modifier(employe);
            JpaUtil.validerTransaction();
            resultat = employe.getId();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }
    
    /**
     * Génère une chaine de caractère aléatoire et l’associe au client 
     * qui a oublié son mot de passe. Il envoie également un courriel au client
     * pour l’informer de son nouveau mot de passe.
     * Renvoie 0 si mail n'existe pas dans BD de Client 
     * et idClient si succes
     *
     * @param mailClient
     * @return Long id de client si réussi de génèrer une nouvelle mot de passe,
     * sinon null
     */

    public Long motDePasseOublie(String mailClient) {
        ClientDao clientDao = new ClientDao();
        Client client = null;
        JpaUtil.creerContextePersistance();
        try {

            JpaUtil.ouvrirTransaction();
            client = clientDao.chercher(mailClient);
            JpaUtil.validerTransaction();
   
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        if (client == null) {
            return Long.valueOf(0);
        } 
        Long resultat = null;
        StringWriter corps = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(corps);

        Random rand = new Random();
        String nouveauMdP = "";
        for (int i = 0; i < 10; i++) {
            char c = (char) (rand.nextInt(26) + 97);
            nouveauMdP += c;
        }
        client.setMotDePasse(nouveauMdP);
        modifierClient(client);

        mailWriter.println("Bonjour " + client.getNom() + ",");
        mailWriter.println();
        mailWriter.println("Voici votre nouveau mot de passe pour le service PREDICT'IF : ");
        mailWriter.println(nouveauMdP);
        mailWriter.println("Vous pouvez modifier ce mot de passe en allant sur la page Mon Compte. ");
        mailWriter.println();
        mailWriter.print("Rendez-vous vite sur notre site pour consulter votre profil Astrologique et ");
        mailWriter.println("profiter des dons incroyables de nos mediums.");
        mailWriter.println();
        mailWriter.println("Cordialement,");
        mailWriter.println("L'équipe PREDICT'IF");

        Message.envoyerMail(
                "contact@predict.if",
                client.getMail(),
                "Nouveau mot de passe PREDICT'IF",
                corps.toString()
        );

        resultat = client.getId();
        return resultat;
    }

    /**
     *
     * @param client
     * @param prenom
     * @return
     */
    public Long modifierPrenom(Client client, String prenom) {
        client.setPrenom(prenom);
        modifierClient(client);
        return client.getId();
    }

    /**
     *
     * @param employe
     * @param prenom
     * @return
     */
    public Long modifierPrenom(Employe employe, String prenom) {
        employe.setPrenom(prenom);
        modifierEmploye(employe);
        return employe.getId();
    }

    /**
     *
     * @param client
     * @param nom
     * @return
     */
    public Long modifierNom(Client client, String nom) {
        client.setNom(nom);
        modifierClient(client);
        return client.getId();
    }

    /**
     *
     * @param employe
     * @param nom
     * @return
     */
    public Long modifierNom(Employe employe, String nom) {
        employe.setNom(nom);
        modifierEmploye(employe);
        return employe.getId();
    }

    /**
     *
     * @param client
     * @param num
     * @return
     */
    public Long modifierNumTel(Client client, String num) {
        client.setNumTel(num);
        modifierClient(client);
        return client.getId();
    }

    /**
     *
     * @param employe
     * @param num
     * @return
     */
    public Long modifierNumTel(Employe employe, String num) {
        employe.setNumTel(num);
        modifierEmploye(employe);
        return employe.getId();
    }

    /**
     *
     * @param client
     * @param mail
     * @return
     */
    public Long modifierMail(Client client, String mail) {
        client.setMail(mail);
        modifierClient(client);
        return client.getId();
    }

    /**
     *
     * @param employe
     * @param mail
     * @return
     */
    public Long modifierMail(Employe employe, String mail) {
        employe.setMail(mail);
        modifierEmploye(employe);
        return employe.getId();
    }

    /**
     *
     * @param client
     * @param MdP
     * @return
     */
    public Long modifierMdP(Client client, String MdP) {
        client.setMotDePasse(MdP);
        modifierClient(client);
        return client.getId();
    }

    /**
     *
     * @param employe
     * @param MdP
     * @return
     */
    public Long modifierMdP(Employe employe, String MdP) {
        employe.setMotDePasse(MdP);
        modifierEmploye(employe);
        return employe.getId();
    }

    /**
     *
     * @param client
     * @param genre
     * @return
     */
    public Long modifierGenre(Client client, EGenre genre) {
        client.setGenre(genre);
        modifierClient(client);
        return client.getId();
    }

    /**
     *
     * @param employe
     * @param genre
     * @return
     */
    public Long modifierGenre(Employe employe, EGenre genre) {
        employe.setGenre(genre);
        modifierEmploye(employe);
        return employe.getId();
    }

    /**
     *
     * @param client
     * @param adresse
     * @return
     */
    public Long modifierAdresse(Client client, String adresse) {
        client.setAdresse(adresse);
        modifierClient(client);
        return client.getId();
    }

    /**
     *
     * @param client
     * @param date
     * @return
     */
    public Long modifierDateNaissance(Client client, Date date) {
        client.setDateNaissance(date);
        modifierClient(client);
        return client.getId();
    }
    
    /**
     * Persister un commentaire client au sein de la base de données.
     *
     * @param comment
     * @return Long id du commentaire dans la base de donné
     */

    public Long inscrireCommentaire(Commentaire comment) {
        Long resultat = null;

        CommentaireDao commentDao = new CommentaireDao();

        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            commentDao.creer(comment);
            JpaUtil.validerTransaction();
            resultat = comment.getId();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }
    
    /**
     * Les commentaires client dont le nombre d’étoiles est au-dessusd
     * un certain seuil. Cela permettra d’afficher dans l’ihm seulement
     * les commentaires positifs par exemple.
     *
     * @return List Commentaire les commentaires client 
     */

    public List<Commentaire> listerCommentParNote() {
        CommentaireDao commentaireDao = new CommentaireDao();
        List<Commentaire> commentaires = null;

        JpaUtil.creerContextePersistance();
        try {
            commentaires = commentaireDao.chercher();
        } catch (Exception e) {
            System.out.println("> Erreur de listerCommentParNote");
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return commentaires;
    }
}
