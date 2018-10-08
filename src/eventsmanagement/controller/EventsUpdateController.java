/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventsmanagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import eventsmanagement.entities.Events;
import eventsmanagement.services.EventsServices;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amine CHAKER
 */
public class EventsUpdateController implements Initializable {

    EventsServices es = new EventsServices();
    EventsManagementController emc = new EventsManagementController();
    @FXML
    private DatePicker date;
    @FXML
    private JFXTextField nomText;
    @FXML
    private JFXTextField sujetText;
    @FXML
    private JFXTextField nbrText;
    @FXML
    private JFXComboBox<String> lieuCB;

    @FXML
    void modifierEvenement(ActionEvent event) throws IOException {
        Events E = emc.event;
        if (verification()) {
            E.setSubject(sujetText.getText());
            E.setNom(nomText.getText());
            E.setDate(java.sql.Date.valueOf(date.getValue()));
            E.setLieux(lieuCB.getValue());
            E.setNbrPlace(Integer.parseInt(nbrText.getText()));
            es.modifierEvenement(E);
            Scene x = new Scene(FXMLLoader.load(getClass().getResource("/eventsmanagement/gui/EventsManagement.fxml")));
            Stage stage = new Stage();
            stage.setScene(x);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opération échoué");
            alert.setContentText("Erreur lors du modification de l'evenement");
            alert.showAndWait();
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sujetText.setText(emc.event.getSubject());
        nomText.setText(emc.event.getNom());
        //date.setValue(emc.event.getDate());
        nbrText.setText(emc.event.getNbrPlace() + "");
        setComboBoxItems();
    }

    private void setComboBoxItems() {
        List<String> list = new ArrayList<>();
        list.add("Tunis");
        list.add("Sousse");
        list.add("Sfax");
        list.add("Gabes");
        ObservableList items = FXCollections.observableArrayList(list);
        lieuCB.setItems(items);
    }

    public boolean verification() {

        if (sujetText.getText().equalsIgnoreCase(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrez le sujet de l'evenement");
            alert.showAndWait();
            sujetText.requestFocus();
            return false;
        }
        if (nomText.getText().equalsIgnoreCase(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrez le Nom de l'evenement");
            alert.showAndWait();
            nomText.requestFocus();
            return false;
        }
        if (lieuCB.getValue().equalsIgnoreCase(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrez le lieu de l'evenement");
            alert.showAndWait();
            lieuCB.requestFocus();
            return false;
        }
        if (date.getValue().toString() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrez la Date de l'evenement");
            alert.showAndWait();
            date.requestFocus();
            return false;
        }
        Date d = new Date();
        if (java.sql.Date.valueOf(date.getValue()).compareTo(d) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez verifier la Date de l'evenement");
            alert.showAndWait();
            date.requestFocus();
            return false;
        }
        if (!IsNumber(nbrText.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez verifier le Nombre de participants de l'evenement");
            alert.showAndWait();
            nbrText.requestFocus();
            return false;
        }
        return true;
    }

    public boolean IsNumber(String x) {
        boolean verif = true;
        try {
            Integer.parseInt(x);
        } catch (NumberFormatException e) {
            verif = false;
        }
        return verif;
    }

}
