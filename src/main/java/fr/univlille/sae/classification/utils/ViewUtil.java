package fr.univlille.sae.classification.utils;

import fr.univlille.sae.classification.controller.DataStageController;
import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.LoadableData;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * Classe utilitaire pour la gestion des vues.
 */
public class ViewUtil {

    /**
     * Définit la couleur de la forme
     * @param form       forme à configurer.
     * @param controller contrôleur principale pour le menu contextuel.
     * @return forme configurée.
     */
    public static Shape getForm(LoadableData dataLoaded, Shape form, Object controller) {
        try {
            form.setFill(dataLoaded.getColor());

            form.setOnMouseClicked(e -> {
                if (controller instanceof DataStageController) {
                    DataStageController dataController = (DataStageController) controller;
                    dataController.getPointInfo().getItems().clear();
                    dataController.getPointInfo().getItems().add(dataLoaded.toString());
                } else if (controller instanceof MainStageController) {
                    MainStageController mainController = (MainStageController) controller;
                    mainController.getPointInfo().getItems().clear();
                    mainController.getPointInfo().getItems().add(dataLoaded.toString());
                } else {
                    System.err.println("Contrôleur inconnu");
                }
            });
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la forme : " + e.getMessage());
        }
        return form;
    }

}
