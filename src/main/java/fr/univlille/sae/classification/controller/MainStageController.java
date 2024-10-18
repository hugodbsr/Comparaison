package fr.univlille.sae.classification.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainStageController {


    @FXML
    Stage stage;

    @FXML
    CategoryAxis absAxe;

    @FXML
    NumberAxis ordAxe;

    @FXML
    Button settings;

    @FXML
    Button loadData;

    @FXML
    Button addData;

    @FXML
    Button classifyData;



    Stage loadStage;

    /**
     * Ouvre l'interface de chargement des données.
     * @throws IOException
     */
    public void openLoadData() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = new File(System.getProperty("user.dir") + File.separator + "res" + File.separator + "stages" + File.separator + "load-data-stage.fxml").toURI().toURL();

        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        loadStage = loader.load();

        loadStage.setResizable(false);
        loadStage.initOwner(stage);
        loadStage.initModality(Modality.APPLICATION_MODAL);
        loadStage.setTitle("Chargement des donées");

        loadStage.showAndWait();


    }

}
