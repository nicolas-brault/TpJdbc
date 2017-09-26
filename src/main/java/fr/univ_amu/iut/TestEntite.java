package fr.univ_amu.iut;// Ne pas faire un copier/coller du pdf...

// Importer les classes jdbc
import fr.univ_amu.iut.beans.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestEntite {

    // La requete de test
    static final String req = "SELECT *" +
            "FROM ETUDIANT " +
            "WHERE VILLE_ET = 'AIX-EN-PROVENCE'";

    public static void main(String[] args) throws SQLException {
        // Connexion a la base
        System.out.println("Connexion a ");
        try (Connection conn = ConnexionUnique.getInstance().getConnexion()){
            System.out.println("Connecte\n");
            // Creation d'une instruction SQL
            Statement stmt = conn.createStatement();
            // Execution de la requete
            System.out.println("Execution de la requete : " + req );
            ResultSet rset = stmt.executeQuery(req);
            // Affichage du resultat
            List etudiants = new ArrayList();
            while (rset.next()){
                Etudiant etudiant = new Etudiant();
//                System.out.print(rset.getInt("NUM_ET") + " ");
//                System.out.print(rset.getString("NOM_ET") + " ");
//                System.out.println(rset.getString("PRENOM_ET"));
                etudiant.setNumEt(rset.getInt("NUM_ET"));
                etudiant.setNomEt(rset.getString("NOM_ET") + " ");
                etudiant.setPrenomEt(rset.getString("PRENOM_ET") + " ");
                etudiant.setVilleEt(rset.getString("VILLE_ET"));
                etudiant.setAnnee(rset.getInt("ANNEE"));
                etudiant.setCpEt(rset.getString("CP_ET"));
                etudiant.setGroupe(rset.getInt("GROUPE"));
                etudiants.add(etudiants);
            }
            // Fermeture de l'instruction (liberation des ressources)
            stmt.close();
            System.out.println("\nOk.\n");
        } catch (SQLException e) {
            e.printStackTrace();// Arggg!!!
            System.out.println(e.getMessage() + "\n");
        }
    }
}
