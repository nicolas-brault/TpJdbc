package fr.univ_amu.iut;// Ne pas faire un copier/coller du pdf...

// Importer les classes jdbc

import fr.univ_amu.iut.beans.Module;
import fr.univ_amu.iut.beans.Prof;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestAsso1 {
    // Chaine de connexion
    // La requete de test
    static PreparedStatement preparedStatementModule;
    static PreparedStatement preparedStatementProf;

    static final String reqProfs = "SELECT * " +
            "FROM PROF";

    static final String reqProf = "SELECT * " +
            "FROM PROF " +
            "WHERE NUM_PROF=?";

    static final String reqModule = "SELECT * " +
            "FROM MODULE " +
            "WHERE CODE=?";

    public static void main(String[] args) throws SQLException {
        // Connexion a la base
        System.out.println("Connexion a ");
        try (Connection conn = ConnexionUnique.getInstance().getConnection()){
            System.out.println("Connecte\n");
            // Creation d'une instruction SQL
            preparedStatementModule = conn.prepareStatement(reqModule);
            preparedStatementProf = conn.prepareStatement(reqProf);
            Statement stmt = conn.createStatement();
            // Execution de la requete
            System.out.println("Execution de la requete : " + reqProfs );
            ResultSet rset = stmt.executeQuery(reqProfs);
            List<Prof> profs = new ArrayList<>();
            // Affichage du resultat
            while (rset.next()){
                Prof prof = creerProf(rset);
                System.out.println(prof);
                profs.add(prof);
            }
            stmt.close();
            System.out.println("\nOk.\n");
            System.out.println(profs);
        } catch (SQLException e) {
            e.printStackTrace();// Arggg!!!
            System.out.println(e.getMessage() + "\n");
        }
    }

    private static Module creerModule(String code) throws SQLException {
        preparedStatementModule.setString(1,code);
        ResultSet resultSet = preparedStatementModule.executeQuery();
        if(resultSet.next())
            return creerModule(resultSet);
        else
            return null;
    }

    private static Module creerModule(ResultSet resultSet) throws SQLException {
        Module module = new Module();
        module.setCode(resultSet.getString("CODE"));
        module.setLibelle(resultSet.getString("LIBELLE"));
        module.sethCoursPrev(resultSet.getInt("H_COURS_PREV"));
        module.sethCoursRea(resultSet.getInt("H_COURS_REA"));
        module.sethTpPrev(resultSet.getInt("H_TP_PREV"));
        module.sethTpRea(resultSet.getInt("H_TP_REA"));
        module.setDiscipline(resultSet.getString("DISCIPLINE"));
        module.setCoefTest(resultSet.getInt("COEFF_TEST"));
        module.setCoefCc(resultSet.getInt("COEFF_CC"));
        //module.setResponsable(creerProf(resultSet.getInt("RESP")));
        module.setPere(creerModule(resultSet.getString("CODEPERE")));
        return module;
    }

    private static Prof creerProf(int numProf) throws SQLException {
        preparedStatementProf.setInt(1,numProf);
        ResultSet resultSet = preparedStatementProf.executeQuery();
        if(resultSet.next())
            return creerProf(resultSet);
        else
            return null;
    }

    private static Prof creerProf(ResultSet rset) throws SQLException {
        Prof prof = new Prof();
        prof.setNumProf(rset.getInt("NUM_PROF"));
        prof.setNomProf(rset.getString("NOM_PROF"));
        prof.setPrenomProf(rset.getString("PRENOM_PROF"));
        prof.setCpProf(rset.getString("CP_PROF"));
        prof.setVilleProf(rset.getString("VILLE_PROF"));
        prof.setMatSpec(creerModule(rset.getString("MAT_SPEC")));
        return prof;
    }
}