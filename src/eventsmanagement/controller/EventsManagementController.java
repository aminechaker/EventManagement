/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventsmanagement.controller;

import java.awt.Desktop;
import java.net.URI;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import eventsmanagement.entities.Events;
import eventsmanagement.services.EventsServices;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Amine CHAKER
 */
public class EventsManagementController implements Initializable {

    private static URL url1;
    private static ResourceBundle rb1;
    public static Events event;
    public static long x;
    FeedReader fd = new FeedReader();
    EventsServices es = new EventsServices();
    @FXML
    private Pane EventPane;
    @FXML
    private DatePicker date;
    @FXML
    private Hyperlink link;
    @FXML
    private Label title;

    @FXML
    private TableColumn<?, ?> sujetT;

    @FXML
    private TableColumn<?, ?> lieuT;

    @FXML
    private TableView<Events> TableEvents;

    @FXML
    private TableColumn<?, ?> nbrT;

    @FXML
    private JFXTextField nomText;

    @FXML
    private TableColumn<?, ?> dateT;

    @FXML
    private JFXTextField sujetText;

    @FXML
    private TableColumn<?, ?> nomT;

    @FXML
    private AnchorPane planningAnchor;

    @FXML
    private JFXTextField nbrText;

    @FXML
    private JFXComboBox<String> lieuCB;

    @FXML
    void ajouterEvenement(ActionEvent event) {
        if (verification()) {
            Events E = new Events(sujetText.getText(), nomText.getText(), java.sql.Date.valueOf(date.getValue()), lieuCB.getValue().toString(), Integer.parseInt(nbrText.getText()));
            es.ajouterEvenement(E);
            this.initialize(url1, rb1);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Opération echoué");
            alert.setContentText("Erreur lors de l'ajout de l'evenement");
            alert.showAndWait();
        }
    }

    @FXML
    void modifierEvenement(ActionEvent event) throws IOException {
        Events E = TableEvents.getSelectionModel().getSelectedItem();
        this.event = E;
        if (E != null) {
            Scene x = new Scene(FXMLLoader.load(getClass().getResource("/eventsmanagement/gui/EventsUpdate.fxml")));
            Stage stage = new Stage();
            stage.setScene(x);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification d'un Evenement");
            alert.setContentText("Veuillez choisir un evenement de la liste");
            alert.showAndWait();
        }

    }

    @FXML
    void supprimerEvenement(ActionEvent event) {
        Events E = TableEvents.getSelectionModel().getSelectedItem();
        if (E != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression d'un Evenement");
            alert.setContentText("Voulez vous vraiment supprimer cet Evenement ?");
            alert.showAndWait();
            if (alert.getResult().getText().equalsIgnoreCase("OK")) {
                es.supprimerEvenement(E);

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression d'un Evenement");
            alert.setContentText("Veuillez choisir un evenement de la liste");
            alert.showAndWait();
        }
        this.initialize(url1, rb1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            sujetText.setText(null);
            nomText.setText(null);
            date.setValue(null);
            nbrText.setText(null);
            lieuCB.setValue(null);
            setComboBoxItems();
            this.DisplayEvents();
            this.plannificationEvents();
            this.rssNews();
        } catch (InterruptedException ex) {
            Logger.getLogger(EventsManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        link.setOnMouseClicked(e -> {
            if (Desktop.isDesktopSupported()) //ou alors tu cast un exception
            {
                try {
                    Desktop.getDesktop().browse(new URI(link.getText()));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(EventsManagementController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(EventsManagementController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void nextNews(MouseEvent event) throws InterruptedException {
        this.rssNews();
    }

    private void rssNews() throws InterruptedException {
        List<String> titles = fd.getTitles();
        List<String> links = fd.getLinks();
        Random r = new Random();
        int min = 0;
        int max = 199;
        int Result = r.nextInt(max - min) + min;
        title.setText(titles.get(Result));
        link.setText(links.get(Result));
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

    private void DisplayEvents() {

        for (int i = 0; i < TableEvents.getItems().size(); i++) {
            TableEvents.getItems().clear();
        }
        sujetT.setCellValueFactory(new PropertyValueFactory<>("subject"));
        nomT.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dateT.setCellValueFactory(new PropertyValueFactory<>("date"));
        lieuT.setCellValueFactory(new PropertyValueFactory<>("lieux"));
        nbrT.setCellValueFactory(new PropertyValueFactory<>("nbrPlace"));
        TableEvents.setItems(es.afficherEvenement());
    }

    private void plannificationEvents() {
        EventPane.getChildren().clear();
        Float a = 1F;
        List<Events> events = es.afficherEvenement();
        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        pane1.setPrefHeight(3000);
        pane1.setPrefWidth(330);
        pane2.setPrefHeight(3000);
        pane2.setPrefWidth(330);
        pane1.setLayoutX(130);
        pane1.setLayoutY(59);
        pane2.setLayoutX(pane1.getLayoutX() + 100);
        pane2.setLayoutY(59);
        Double x = 140D;
        Double y = 65D;
        Double x1 = pane1.getLayoutX() + 330 + 5;
        Double y1 = 65D;
        for (Events event : events) {
            if ((a % 2) != 0) {
                Pane pane = new Pane();
                pane.setPrefHeight(90);
                pane.setPrefWidth(240);
                LocalDate date = event.getDate().toLocalDate();
                ZonedDateTime zdt = date.atStartOfDay(ZoneId.of("Europe/Paris"));
                //Instant instant = zdt.toInstant();
                //LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int day = zdt.getDayOfMonth();
                int month = zdt.getMonthValue();
                int year = zdt.getYear();
                Button button = new Button();
                Button button1 = new Button();
                button.setText("   " + day + "\n" + " " + this.getMonth(month) + "\n" + year + "\n");
                button.setPrefHeight(90);
                button.setPrefWidth(90);
                button.setStyle("-fx-background-color: #E7D11C;-fx-text-fill: white;-fx-font-family: " + "Arial"
                        + ";-fx-font-size: 14px;-fx-padding: 10 20 10 20;");
                button1.setCursor(Cursor.HAND);
                button1.setText(event.getNom() + "\n" + event.getSubject() + "\n" + event.getLieux());
                button1.setPrefHeight(90);
                button1.setPrefWidth(180);
                button1.setLayoutX(button.getLayoutX() + 90);
                button1.setStyle("-fx-background-color: #ffffff;-fx-text-fill: black;-fx-font-family: " + "Arial"
                        + "-fx-font-size: 12px;-fx-padding: 10 20 10 20;");
                pane.setLayoutX(x);
                pane.setLayoutY(y);
                pane1.getChildren().add(pane);
                y += 100D;
                a++;
                button1.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        final ContextMenu contextMenu = new ContextMenu();
                        MenuItem details = new MenuItem("Details");
                        details.setOnAction(r -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setContentText("Nom : " + event.getNom()
                                    + "\nSujet : " + event.getSubject()
                                    + "\nLocalisation : " + event.getLieux()
                                    + "\nNombre Participants :" + event.getNbrPlace());
                            alert.showAndWait();
                        });
                        MenuItem update = new MenuItem("Remaining time");
                        update.setOnAction(t -> {
                            Date d1 = event.getDate();
                            Date d2 = new Date();
                            long dureest = (d1.getTime() - d2.getTime()) / 3600000;
                            System.out.println(dureest);
                            this.x = dureest;
                            if (d1.compareTo(d2) == 1) {
                                Timer timer = new Timer();
                                timer.init();
                                timer.initialize(url1, rb1);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Temps Restant");
                                alert.setContentText("Cet evenement est déjà passé");
                                alert.showAndWait();
                            }
                        });
                        contextMenu.getItems().addAll(details, update);
                        button1.setContextMenu(contextMenu);
                    }
                });

                /**
                 * **********************************
                 */
                pane.getChildren().add(button);
                pane.getChildren().add(button1);
                EventPane.getChildren().add(pane);
            } else {
                Pane pane = new Pane();
                pane.setPrefHeight(90);
                pane.setPrefWidth(270);
                LocalDate date = event.getDate().toLocalDate();
                ZonedDateTime zdt = date.atStartOfDay(ZoneId.of("Europe/Paris"));
                int day = zdt.getDayOfMonth();
                int month = zdt.getMonthValue();
                int year = zdt.getYear();
                Button button = new Button();
                Button button1 = new Button();
                button.setText("   " + day + "\n" + " " + this.getMonth(month) + "\n" + year + "\n");
                button.setPrefHeight(90);
                button.setPrefWidth(90);
                button.setStyle("-fx-background-color: #E7D11C;-fx-text-fill: white;-fx-font-family: " + "Arial"
                        + ";-fx-font-size: 14px;-fx-padding: 10 20 10 20;");
                button1.setCursor(Cursor.HAND);
                button1.setText(event.getNom() + "\n" + event.getSubject() + "\n" + event.getLieux());
                button1.setPrefHeight(90);
                button1.setPrefWidth(180);
                button1.setLayoutX(button.getLayoutX() + 90);
                button1.setStyle("-fx-background-color: #ffffff;-fx-text-fill: black;-fx-font-family: " + "Arial"
                        + "-fx-font-size: 12px;-fx-padding: 10 20 10 20;");

                pane.setLayoutX(x1);
                pane.setLayoutY(y1);
                pane2.getChildren().add(pane);
                y1 += 100D;
                a++;
                button1.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        final ContextMenu contextMenu = new ContextMenu();
                        MenuItem details = new MenuItem("Details");
                        details.setOnAction(r -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setContentText("Nom : " + event.getNom()
                                    + "\nSujet : " + event.getSubject()
                                    + "\nLocalisation : " + event.getLieux()
                                    + "\nNombre Participants :" + event.getNbrPlace());
                            alert.showAndWait();
                        });
                        MenuItem update = new MenuItem("Remaining time");
                        update.setOnAction(t -> {
                            Date d1 = event.getDate();
                            Date d2 = new Date();
                            long dureest = (d1.getTime() - d2.getTime()) / 3600000;
                            this.x = dureest;
                            System.out.println(dureest);
                            if (d1.compareTo(d2) == 1) {
                                Timer timer = new Timer();
                                timer.init();
                                timer.initialize(url1, rb1);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Remaining time");
                                alert.setContentText("The event has already passed");
                                alert.showAndWait();
                            }
                        });
                        contextMenu.getItems().addAll(details, update);
                        button1.setContextMenu(contextMenu);
                    }
                });
                pane.getChildren().add(button);
                pane.getChildren().add(button1);
                EventPane.getChildren().add(pane);
            }
        }

    }

    public boolean verification() {

        if (sujetText.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrez le sujet de l'evenement");
            alert.showAndWait();
            sujetText.requestFocus();
            return false;
        }
        if (nomText.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrez le Nom de l'evenement");
            alert.showAndWait();
            nomText.requestFocus();
            return false;
        }
        if (date.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrez la Date de l'evenement");
            alert.showAndWait();
            date.requestFocus();
            return false;
        }
        Date d = new Date();
        if (!(java.sql.Date.valueOf(date.getValue()).compareTo(d) == 0) && !(java.sql.Date.valueOf(date.getValue()).compareTo(d) == 1)) {
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

    public String getMonth(int x) {
        return new DateFormatSymbols().getMonths()[x - 1];
    }
}
