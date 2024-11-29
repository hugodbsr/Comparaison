package fr.univlille.sae.classification.utils;

import fr.univlille.sae.classification.controller.DataStageController;
import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;
import javafx.geometry.Pos;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

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
        VBox legend = new VBox();
        legend.setAlignment(Pos.CENTER);
        HBox line = new HBox();
        line.setSpacing(10);
        line.setAlignment(Pos.CENTER);

        HBox tempHBox = new HBox();
        tempHBox.getChildren().addAll(rectangle, label);
        line.getChildren().add(tempHBox);

        String[] colorsString = colors.keySet().toArray(new String[0]);
        for(int i = 0 ; i < colorsString.length ; i+= 7) {
            for(int j = 0 ; i+j < colorsString.length && j < i+7 ; j++) {
                if(j%7 == 0 && i != 0 ) {
                    legend.getChildren().add(line);
                    line = new HBox();
                    line.setSpacing(10);
                    line.setAlignment(Pos.CENTER);
                }

                tempHBox = new HBox();
                label = new Label(colorsString[i+j]);
                rectangle = new Rectangle(10, 10);
                rectangle.setFill(colors.get(colorsString[i+j]));
                tempHBox.getChildren().addAll(rectangle, label);
                line.getChildren().add(tempHBox);

            }
        }

        if(colorsString.length < 7) legend.getChildren().add(line);

/**
        for(String s : colors.keySet()) {
            Circle c = new Circle(5);
            c.setFill(colors.get(s));
            label = new Label(s);
            tempHBox = new HBox();
            tempHBox.getChildren().addAll(c, label);

            hbox.getChildren().add(tempHBox);
        }
 */

        return legend;
    }

}
