package dasi.projetdasi.ihm.console;

import dasi.projetdasi.dao.JpaUtil;
import dasi.projetdasi.metier.modele.Astrologue;
import dasi.projetdasi.metier.modele.Cartomancien;
import dasi.projetdasi.metier.modele.Client;
import dasi.projetdasi.metier.modele.Consultation;
import dasi.projetdasi.metier.modele.Employe;
import dasi.projetdasi.metier.modele.Medium;
import dasi.projetdasi.metier.modele.Spirite;
import dasi.projetdasi.metier.service.Service;
import dasi.projetdasi.util.EGenre;
import dasi.projetdasi.util.Utils;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author DASI Team
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        // Contrôlez l'affichage du log de JpaUtil grâce à la méthode log de la classe JpaUtil
        JpaUtil.init();      
        
        Client currentClient = null;
        Employe currentEmployee = null;
        
        initialiserBD();
        
        boolean ok = true;
        boolean ok2 = true;
        boolean ok3 = true;
        
        while (ok) {

            System.out.println("\n***** PredictIF *****");
            System.out.println("# Home : ");
            System.out.println("1 - Client");
            System.out.println("2 - Employee");
            System.out.println("0 - Quit");

            Integer choice;
            choice = Saisie.lireInteger("Choice : ");

            switch (choice) {
                case 1:
                    ok2 = true;

                    while (ok2) {
                        System.out.println("\n# Service Client : ");
                        System.out.println("1 - Log In");
                        System.out.println("2 - Create New Account");
                        System.out.println("3 - Forgot Password ?");
                        System.out.println("0 - Back to Home");

                        Integer choice2;
                        choice2 = Saisie.lireInteger("Choice : ");

                        switch (choice2) {
                            case 1:
                                currentClient = authentificationClient();
                                if (currentClient != null) {
                                    ok3 = true;
                                    while (ok3) {
                                        System.out.println("\n# Service Connected Client : ");
                                        System.out.println("1 - Client profile");
                                        System.out.println("2 - Medium list");
                                        System.out.println("3 - Request a consultation");
                                        System.out.println("0 - Log Out");

                                        Integer choice3;
                                        choice3 = Saisie.lireInteger("Choice : ");

                                        switch (choice3) {
                                            case 1:
                                                afficherClient(currentClient);
                                                break;
                                            case 2:
                                                listerMediums();
                                                break;
                                            case 3:
                                                demanderConsultation(currentClient.getId());
                                                break;
                                            case 0:
                                                currentClient = null;
                                                System.out.println("Deconnected client !");
                                                ok3 = false;
                                                break;
                                            default:
                                                System.out.println("\nMauvais choix !");
                                                break;
                                        }
                                    }
                                }
                                break;
                            case 2:
                                inscriptionClient();
                                break;
                            case 3:
                                motDePasseOublie();
                                break;
                            case 0:
                                ok2 = false;
                                break;
                            default:
                                System.out.println("\nMauvais choix !");
                                break;
                        }
                    }

                    break;
                case 2:

                    ok2 = true;

                    while (ok2) {
                        System.out.println("\n# Service Employe : ");
                        System.out.println("1 - Log In");
                        System.out.println("0 - Back to Home");

                        Integer choice2;
                        choice2 = Saisie.lireInteger("Choice : ");

                        switch (choice2) {
                            case 1:
                                currentEmployee = authentificationEmploye();
                                if (currentEmployee != null) {
                                    ok3 = true;
                                    while (ok3) {
                                        System.out.println("\n# Service Connected Employe : ");
                                        System.out.println("1 - Client profile of current consultation");
                                        System.out.println("2 - Situation of current consultation");
                                        System.out.println("3 - Accept consultation");
                                        System.out.println("4 - (Help) Predictions of current consultation");
                                        System.out.println("5 - End consultation");
                                        System.out.println("6 - Statistics");
                                        System.out.println("0 - Log Out");

                                        Integer choice3;
                                        choice3 = Saisie.lireInteger("Choice : ");

                                        switch (choice3) {
                                            case 1:
                                                chercherClientDeEmploye(currentEmployee.getId());
                                                break;
                                            case 2:
                                                chercherActualConsultation(currentEmployee.getId());
                                                break;
                                            case 3:
                                                accepterConsultation(currentEmployee.getId());
                                                break;
                                            case 4:
                                                predictionsCurrentConsultation(currentEmployee.getId());
                                                break;
                                            case 5:
                                                arreterConsultation(currentEmployee.getId());
                                                break;
                                            case 6:
                                                statistics();
                                                break;
                                            case 0:
                                                currentEmployee = null;
                                                System.out.println("Deconnected employee !");
                                                ok3 = false;
                                                break;
                                            default:
                                                System.out.println("\nMauvais choix !");
                                                break;
                                        }
                                    }
                                }
                                break;
                            case 0:
                                ok2 = false;
                                break;
                            default:
                                System.out.println("\nMauvais choix !");
                                break;
                        }
                    }
                    break;

                case 0:
                    ok = false;
                    System.out.println("\nAU REVOIR !");
                    break;
                default:
                    System.out.println("\nMauvais choix !");
                    break;
            }
        }

        JpaUtil.destroy();
    }
    
    public static void afficherHistorique(Client client) {

        Service service = new Service();
        
        List<Consultation> consuls = service.historiqueUtilisateur(client);
        if (consuls != null) {
            System.out.println(consuls.size());
        } else {
            System.out.println("ERROR");
        }

    }
    
    /**
     *
     * @param client
     */
    public static void afficherClient(Client client) {
        System.out.println();
        System.out.println("Id : " + client.getId());
        System.out.println("Nom : " + client.getNom());
        System.out.println("Prenom : " + client.getPrenom());
        System.out.println("Numéro de téléphone : " + client.getNumTel());
        System.out.println("Email : " + client.getMail());
        System.out.println("Genre : " + client.getGenre());
        System.out.println("Date de naissance : " + client.getDateNaissance());
        System.out.println("Adresse : " + client.getAdresse());
        System.out.println("Profil Astral : ");
        System.out.println("\tSigne du zodiaque : " + client.getProfilAstral().getSigneZod());
        System.out.println("\tSigne astrologique chinois : " + client.getProfilAstral().getSigneChin());
        System.out.println("\tCouleur porte-bonheur : " + client.getProfilAstral().getCouleur());
        System.out.println("\tAnimal-totem : " + client.getProfilAstral().getAnimal());
        System.out.println("Historique des consultations : " + client.getConsultations().size() + " consultation(s)");
        for (Consultation con : client.getConsultations()) {
            System.out.println("\t " + client.getConsultations().indexOf(con) + "> Statut : " + con.getStatut());
            System.out.println("\t    Employe : " + con.getEmploye().getPrenom() + " " + con.getEmploye().getNom());
            System.out.println("\t    Medium : " + con.getMedium().getDenomination());
            System.out.println("\t    Date debut : " + con.getDebut());
            System.out.println("\t    Date fin : " + con.getFin());
            System.out.println("\t    Commentaire : " + con.getCommentaire());
        }
    }

    /**
     *
     * @param medium
     */
    public static void afficherMedium(Medium medium) {
        String mediumType = medium.getClass().getName().replaceAll("dasi.projetdasi.metier.modele.", "");
        switch (mediumType) {
            case "Spirite":
                Spirite spirite = (Spirite) medium;
                System.out.println();
                System.out.println("Id : " + spirite.getId());
                System.out.println("Type : Spirite");
                System.out.println("Dénomination : " + spirite.getDenomination());
                System.out.println("Genre : " + spirite.getGenre());
                System.out.println("Support : " + spirite.getSupport());
                System.out.println("Présentation : " + spirite.getPresentation());
                break;
            case "Cartomancien":
                Cartomancien cartomancien = (Cartomancien) medium;
                System.out.println();
                System.out.println("Id : " + cartomancien.getId());
                System.out.println("Type : Cartomancien");
                System.out.println("Dénomination : " + cartomancien.getDenomination());
                System.out.println("Genre : " + cartomancien.getGenre());
                System.out.println("Présentation : " + cartomancien.getPresentation());
                break;
            case "Astrologue":
                Astrologue astrologue = (Astrologue) medium;
                System.out.println();
                System.out.println("Id : " + astrologue.getId());
                System.out.println("Type : Astrologue");
                System.out.println("Dénomination : " + astrologue.getDenomination());
                System.out.println("Genre : " + astrologue.getGenre());
                System.out.println("Formation : " + astrologue.getFormation());
                System.out.println("Promotion : " + astrologue.getPromotion());
                System.out.println("Présentation : " + astrologue.getPresentation());
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param employe
     */
    public static void afficherEmploye(Employe employe) {
        System.out.println("=> " + employe);
    }

    /**
     *
     */
    public static void initialiserBD() {

        System.out.println();
        System.out.println("**** initialiserBD() ****");
        System.out.println();

        Service service = new Service();
        service.initaliserBD();

        System.out.println();
        System.out.println("** Succès initialisation ! ");
        System.out.println();
    }

    /**
     *
     */
    public static void testerInscriptionClient() {

        System.out.println();
        System.out.println("**** testerInscriptionClient() ****");
        System.out.println();

        Service service = new Service();
        Client cyril = new Client("CUCCHA", "Cyril", "ccuccha4844@yahoo.com", "HaCKeR", "0599941887", EGenre.H, Utils.dateFromString("1995-12-12"), "2 Rue des Barottieres, Villeurbanne");
        Long idCyril = service.inscrireClient(cyril);
        if (idCyril != null) {
            System.out.println("=> Succès inscription");
        } else {
            System.out.println("=> Échec inscription");
        }
        afficherClient(cyril);
        System.out.println();

        Client kevin = new Client("RASENDE MOTTIALI", "Kevin", "krasendemottiali@laposte.net", "HaCKeR", "0931216829", EGenre.H, Utils.dateFromString("	1992-11-12"), "1 Rue Saint Jean, Villeurbanne");
        Long idKevin = service.inscrireClient(kevin);
        if (idKevin != null) {
            System.out.println("=> Succès inscription");
        } else {
            System.out.println("=> Échec inscription");
        }
        afficherClient(kevin);
        System.out.println();

    }

    /**
     *
     */
    public static void testerRechercheClient() {

        System.out.println();
        System.out.println("**** testerRechercheClient() ****");
        System.out.println();

        Service service = new Service();
        Long id;
        Client client;

        id = Long.valueOf(101);
        System.out.println("** Recherche du Client #" + id);
        client = service.rechercherClientParId(id);
        if (client != null) {
            afficherClient(client);
        } else {
            System.out.println("=> Client non-trouvé");
        }
        System.out.println();

        id = Long.valueOf(11);
        System.out.println("** Recherche du Client #" + id);
        client = service.rechercherClientParId(id);
        if (client != null) {
            afficherClient(client);
        } else {
            System.out.println("=> Client non-trouvé");
        }
        System.out.println();

        id = Long.valueOf(151);
        System.out.println("** Recherche du Client #" + id);
        client = service.rechercherClientParId(id);
        if (client != null) {
            afficherClient(client);
        } else {
            System.out.println("=> Client #" + id + " non-trouvé");
        }
        System.out.println();
    }

    /**
     *
     * @return
     */
    public static Client authentificationClient() {

        Service service = new Service();
        Client client;
        String mail;
        String motDePasse;

        System.out.println();
        System.out.println("***********");
        System.out.println("** LOGIN **");
        System.out.println("***********");
        System.out.println();

        mail = Saisie.lireChaine("Email : ");
        motDePasse = Saisie.lireChaine("Passwork : ");
        client = service.authentifierClient(mail, motDePasse);
        if (client != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }
        return client;
    }

    /**
     *
     */
    public static void inscriptionClient() {
        Service service = new Service();

        System.out.println();
        System.out.println("**************************");
        System.out.println("** NOUVELLE INSCRIPTION **");
        System.out.println("**************************");
        System.out.println();

        String nom = Saisie.lireChaine("Nom (de famille) ? ");
        String prenom = Saisie.lireChaine("Prénom ? ");
        String mail = Saisie.lireChaine("Mail ? ");
        String motDePasse = Saisie.lireChaine("Mot de passe ? ");
        String numTel = Saisie.lireChaine("Numero telephone ? ");
        List<Integer> valeursPossiblesGenre = List.of(0, 1);
        Integer genre = Saisie.lireInteger("Genre {Homme: 0, Femme: 1}} ? ", valeursPossiblesGenre);
        System.out.println("Date de naissance (Day/Month/Year) : ");
        Integer day = Saisie.lireInteger("Day ? ");
        Integer month = Saisie.lireInteger("Month ? ");
        Integer year = Saisie.lireInteger("Year ? ");
        String date = year.toString() + "-" + month.toString() + "-" + day.toString();
        String adresse = Saisie.lireChaine("Adresse ? ");

        Client client = new Client(nom, prenom, mail, motDePasse, numTel, EGenre.values()[genre], Utils.dateFromString(date), adresse);
        Long idClient = service.inscrireClient(client);

        if (idClient != null) {
            System.out.println("=> Succès inscription");
        } else {
            System.out.println("=> Échec inscription");
        }
    }

    /**
     *
     * @return
     */
    public static Employe authentificationEmploye() {

        Service service = new Service();
        Employe employe;
        String mail;
        String motDePasse;

        mail = Saisie.lireChaine("Email : ");
        motDePasse = Saisie.lireChaine("Passwork : ");
        employe = service.authentifierEmploye(mail, motDePasse);
        if (employe != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }
        return employe;
    }

    /**
     *
     */
    public static void saisirRechercheClient() {
        Service service = new Service();

        System.out.println();
        System.out.println("*********************");
        System.out.println("** MENU INTERACTIF **");
        System.out.println("*********************");
        System.out.println();

        Saisie.pause();

        System.out.println();
        System.out.println("**************************");
        System.out.println("** RECHERCHE de CLIENTS **");
        System.out.println("**************************");
        System.out.println();
        System.out.println();
        System.out.println("** Recherche par Identifiant:");
        System.out.println();

        Integer idClient = Saisie.lireInteger("Identifiant ? [0 pour quitter] ");
        while (idClient != 0) {
            Client client = service.rechercherClientParId(idClient.longValue());
            if (client != null) {
                afficherClient(client);
            } else {
                System.out.println("=> Client #" + idClient + " non-trouvé");
            }
            System.out.println();
            idClient = Saisie.lireInteger("Identifiant ? [0 pour quitter] ");
        }

        System.out.println();
        System.out.println("********************************");
        System.out.println("** AUTHENTIFICATION de CLIENT **");
        System.out.println("********************************");
        System.out.println();
        System.out.println();
        System.out.println("** Authentifier Client:");
        System.out.println();

        String clientMail = Saisie.lireChaine("Mail ? [0 pour quitter] ");

        while (!clientMail.equals("0")) {
            String clientMotDePasse = Saisie.lireChaine("Mot de passe ? ");
            Client client = service.authentifierClient(clientMail, clientMotDePasse);
            if (client != null) {
                afficherClient(client);
            } else {
                System.out.println("=> Client non-authentifié");
            }
            clientMail = Saisie.lireChaine("Mail ? [0 pour quitter] ");
        }

        System.out.println();
        System.out.println("*****************");
        System.out.println("** AU REVOIR ! **");
        System.out.println("*****************");
        System.out.println();

    }

    /**
     *
     */
    public static void testerListeClients() {

        Service service = new Service();
        List<Client> listeClients = service.listerClients();
        System.out.println("*** Liste des Clients");
        if (listeClients != null) {
            listeClients.forEach(client -> {
                afficherClient(client);
            });
        } else {
            System.out.println("=> Empty list !");
        }
        System.out.println();
    }

    /**
     *
     */
    public static void listerMediums() {

        Service service = new Service();
        List<Medium> listeMediums = service.listerMediums();
        System.out.println("*** Liste des Mediums : ");
        if (listeMediums != null) {
            listeMediums.forEach(medium -> {
                afficherMedium(medium);
            });
        } else {
            System.out.println("=> Empty list !");
        }
        System.out.println();
    }

    /**
     *
     */
    public static void testerListeEmployes() {
        System.out.println();
        System.out.println("**** testerListerEmployes() ****");
        System.out.println();

        Service service = new Service();
        List<Employe> listeEmployes = service.listerEmployes();
        System.out.println("*** Liste des Clients");
        if (listeEmployes != null) {
            listeEmployes.forEach(employe -> {
                afficherEmploye(employe);
            });
        } else {
            System.out.println("=> ERREUR...");
        }
        System.out.println();
    }
    
    /**
     *
     * @param idEmploye
     */
    public static void chercherActualConsultation(Long idEmploye) {
        Service service = new Service();
        Consultation consultation = service.rechercherConsultationDeEmploye(idEmploye);
        
        if (consultation != null) {
            System.out.println("Information de actual consultation :");
            System.out.println("\tStatut : " + consultation.getStatut());
            System.out.println("\tClietn : id : " + consultation.getClient().getId() + ", nom : " + consultation.getClient().getNom());
            System.out.println("\tMedium : id : " + consultation.getMedium().getId() + ", denomination : " + consultation.getMedium().getDenomination());
            if (consultation.getDebut() != null) {
                System.out.println("\tDate debut : " + consultation.getDebut());
            }
        } else {
            System.out.println("Il n'y a pas consultation en ce moment!");
        }
        System.out.println();
    }

    /**
     *
     * @param idClient
     */
    public static void demanderConsultation(Long idClient) {

        Service service = new Service();

        Integer idMedium = Saisie.lireInteger("Id Medium : ");
        int deCon = service.demanderConsultation(idClient, Long.valueOf(idMedium));
        switch (deCon) {
            case 3 ->
                System.out.println("Ok Demander Consultation");
            case 2 ->
                System.out.println("=> Il n'y a pas de employe disponible !");
            case 1 ->
                System.out.println("=> Vous avez dèjá demandé une consultation avec ce médium !");
            default ->
                System.out.println("=> ERREUR...");
        }
        System.out.println();
    }

    /**
     *
     * @param idEmploye
     */
    public static void accepterConsultation(Long idEmploye) {

        Service service = new Service();

        int accepter = service.accepterConsultation(idEmploye);
        switch (accepter) {
            case 2 -> System.out.println("Ok Accepter Consultation");
            case 1 -> System.out.println("Consultation a déjà été accepté !");
            default -> System.out.println("=> ERREUR...");
        }
        System.out.println();
    }

    /**
     *
     * @param idEmploye
     */
    public static void arreterConsultation(Long idEmploye) {
        Service service = new Service();

        String commentaire = Saisie.lireChaine("Commentaire : ");
        boolean arreter = service.arreterConsultation(idEmploye, commentaire);
        if (arreter) {
            System.out.println("Ok Arreter Consultation");
        } else {
            System.out.println("=> ERREUR...");
        }
        System.out.println();
    }

    /**
     *
     * @param idEmploye
     */
    public static void chercherClientDeEmploye(Long idEmploye) {

        Service service = new Service();

        Client client = service.rechercherClientDeEmploye(idEmploye);
        if (client != null) {
            afficherClient(client);
        } else {
            System.out.println("=> ERREUR. Il n'y a pas encore client.");
        }
        System.out.println();
    }

    /**
     *
     * @param idEmploye
     */
    public static void predictionsCurrentConsultation(Long idEmploye) {

        Service service = new Service();

        List<Integer> valeursPossibles = List.of(1, 2, 3, 4);
        Integer amour = Saisie.lireInteger("Amour : ", valeursPossibles);
        Integer sante = Saisie.lireInteger("Santé : ", valeursPossibles);
        Integer travail = Saisie.lireInteger("Travail : ", valeursPossibles);

        List<String> predictions = service.helpPrediction(idEmploye, amour, sante, travail);
        Client client = service.rechercherClientDeEmploye(idEmploye);
        if (predictions != null) {
            System.out.println("Prédictions de client : " + client.getPrenom() + " " + client.getNom());
            System.out.println("\tAmour : " + predictions.get(0));
            System.out.println("\tSanté : " + predictions.get(1));
            System.out.println("\tTravail : " + predictions.get(2));
            System.out.println();
        } else {
            System.out.println("=> ERREUR...");
        }
    }
    
    /**
     *
     */
    public static void statistics() {
        Service service = new Service();
        
        System.out.println("-----Statistiques-----");
        System.out.println();
        System.out.println("Nombre de consultations par médium :");
        HashMap<Medium, Long> mediums = service.statMedium();
        mediums.keySet().forEach(m -> {
            System.out.println("\tMedium " + m.getDenomination() + " a " + mediums.get(m) + " consultation(s)");
        });
        
        System.out.println();
        System.out.println("Nombre de consultations par employe :");
        HashMap<Employe, Long> employes = service.statEmploye();
        employes.keySet().forEach(e -> {
            System.out.println("\tEmploye " + e.getPrenom()+ " " + e.getNom() + " a " + employes.get(e) + " consultation(s)");
        });
        
        System.out.println();
        System.out.println("Top 5 des mediums choisis par les clients :");
        List<Medium> top5s = service.statTop5();
        top5s.forEach(m -> {
            System.out.println("\tMedium : " + m.getDenomination());
        });
        
        System.out.println();
        System.out.println("Duree Moyenne de toutes consultations : " + service.statDureeMoyConsultation() + " heures");
    }
    
    /**
     *
     */
    public static void motDePasseOublie() {
        Service service = new Service();
        
        String mail = Saisie.lireChaine("Mail : ");
        
        Long idClient = service.motDePasseOublie(mail);
        
        if (idClient == 0) {
            System.out.println("Cet e-mail n'est pas enregistré dans le système !");
        } 
    }
}
