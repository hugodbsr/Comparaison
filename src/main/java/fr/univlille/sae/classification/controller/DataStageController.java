package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.AxesSettingsView;
import fr.univlille.sae.classification.view.DataStageView;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Contrôleur pour le fichier FXML "data-view-stage", pour gérer la vue supplémentaire.
 */
public class DataStageController extends DataVisualizationController{
    /**
     * Fenêtre associée à cette vue.
     */
    @FXML
    Stage stage;

    /**
     * Composant ListView pour l'affichage des informations des données d'un point.
     */
    @FXML
    ListView PointInfo;

    /**
     * Initialise le contrôleur en configurant le zoom et le déplacement de la vue.
     */
    public void initialize() {
        setupZoom();
        setupDrag();
    }

    /**
     * Associe la dataStageView associée à la classe.
     * @param dataStageView Instance de dataStageView à associer.
     */
    public void setDataStageView (DataStageView dataStageView) {
        this.view = dataStageView;
    }

    /**
     * Retourne l'instance de PointInfo.
     * @return Instance de PointInfo.
     */
    public ListView getPointInfo(){
        return this.PointInfo;
    };

}
