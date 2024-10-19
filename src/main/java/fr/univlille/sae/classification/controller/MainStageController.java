package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.LoadDataView;
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
    NumberAxis absAxe;

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

    @FXML
    ScatterChart scatterChart;


    Stage loadStage;

    /**
     * Ouvre l'interface de chargement des données.
     * @throws IOException
     */
    public void openLoadData() throws IOException {

        LoadDataView loadDataView = new LoadDataView(ClassificationModel.getClassificationModel(), stage);
        loadDataView.show();


    }


    public ScatterChart getScatterChart() {
        return this.scatterChart;
    }
}
