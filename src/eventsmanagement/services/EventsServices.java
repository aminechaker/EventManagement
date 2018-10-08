/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventsmanagement.services;

import eventsmanagement.entities.Events;
import eventsmanagement.technique.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Amine CHAKER
 */
public class EventsServices {

    Connection conn;

    public EventsServices() {
        conn = MyConnection.getInstance().getConnection();
    }

    public void ajouterEvenement(Events E) {
            try {
                String req = "INSERT INTO `event`(`subject`, `nom`, `date`, `lieu`, `nbrplace`) VALUES (?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(req);
                ps.setString(1, E.getSubject());
                ps.setString(2, E.getNom());
                ps.setDate(3, E.getDate());
                ps.setString(4, E.getLieux());
                ps.setInt(5, E.getNbrPlace());
                ps.executeUpdate();
                GenererAlerte("Ajout effectué avec succés");
            } catch (Exception e) {
            }
    }

    public void modifierEvenement(Events E) {
            try {
                String req = "UPDATE `event` SET `subject`=?,`nom`=?,`date`=?,`lieu`=?,`nbrplace`=? WHERE id=?";
                PreparedStatement ps = conn.prepareStatement(req);
                ps.setString(1, E.getSubject());
                ps.setString(2, E.getNom());
                ps.setDate(3, E.getDate());
                ps.setString(4, E.getLieux());
                ps.setInt(5, E.getNbrPlace());
                ps.setInt(6, E.getId());
                ps.executeUpdate();
                GenererAlerte("Modification effectué avec succés");
            } catch (Exception e) {
            }

    }

    public void supprimerEvenement(Events E) {
        try {
            String requete = "Delete FROM event WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(requete);
            pstmt.setInt(1, E.getId());
            pstmt.executeUpdate();
            GenererAlerte("Suppression effectuée avec succés.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<Events> afficherEvenement() {
        ObservableList<Events> maListe = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM event ORDER BY date";
            PreparedStatement st = conn.prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Events E = new Events();
                E.setId(rs.getInt(1));
                E.setSubject(rs.getString(2));
                E.setNom(rs.getString(3));
                E.setDate(rs.getDate(4));
                E.setLieux(rs.getString(5));
                E.setNbrPlace(rs.getInt(6));
                maListe.add(E);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return maListe;
    }

    private static void GenererAlerte(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération effectuée");
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
