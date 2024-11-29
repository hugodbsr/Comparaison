package fr.univlille.sae.classification.utils;

import fr.univlille.sae.classification.controller.DataStageController;
import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.LoadableData;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.Map;

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
            Color color = LoadableData.getClassifications().get(dataLoaded.getClassification());

            form.setFill(color);

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


    public static VBox loadLegend() {
        //Color

        Map<String, Color> colors = new HashMap<>(Map.copyOf(LoadableData.getClassifications()));
        Rectangle rectangle = new Rectangle(10, 10);
        rectangle.setFill(colors.remove("undefined"));
        Label label = new Label("undefined");
        HBox hbox = new HBox();
        HBox tempHBox = new HBox();
        tempHBox.getChildren().addAll(rectangle, label);
        hbox.getChildren().add(tempHBox);


        for(String s : colors.keySet()) {
            Circle c = new Circle(5);
            c.setFill(colors.get(s));
            label = new Label(s);
            tempHBox = new HBox();
            tempHBox.getChildren().addAll(c, label);

            hbox.getChildren().add(tempHBox);
        }

        return hbox;
    }

}
