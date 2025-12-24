package com.example.gestionmatch.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class AccueilController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("✅ AccueilController initialisé");
    }

    @FXML
    private void allerConnexion(ActionEvent event) {
        changerScene(event, "/fxml/login.fxml", "Connexion - FIFA 2030");
    }

    @FXML
    private void allerInscription(ActionEvent event) {
        changerScene(event, "/fxml/inscription.fxml", "Inscription - FIFA 2030");
    }

    private void changerScene(ActionEvent event, String fxmlPath, String title) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                System.err.println("❌ FXML introuvable : " + fxmlPath);
                return;
            }

            Parent root = FXMLLoader.load(fxmlUrl);

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root, 1600, 1000));
            stage.setTitle(title);
            stage.show();

            System.out.println("✅ Navigation vers : " + fxmlPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
