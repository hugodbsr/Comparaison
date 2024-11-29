package fr.univlille.sae.classification.utils;

import fr.univlille.sae.classification.controller.DataStageController;
import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.LoadableData;
import javafx.geometry.Pos;
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

    private static Shape clickedForm;

    /**
     * Définit la couleur de la forme.
     * @param form       Forme à configurer
     * @param controller Contrôleur principal pour le menu contextuel
     * @return Forme configurée
     */
    public static Shape getForm(LoadableData dataLoaded, Shape form, Object controller) {
        try {
            System.out.println(dataLoaded.getClassification());
            System.out.println(LoadableData.getClassifications());
            Color color = LoadableData.getClassifications().get(dataLoaded.getClassification());

            form.setFill(color);
            System.out.println(color);
            form.setOnMouseClicked(e -> {
                if(clickedForm!=null) {
                    clickedForm.setStyle("-fx-stroke-width: 0;");
                }
                if (controller instanceof DataStageController) {
                    DataStageController dataController = (DataStageController) controller;
                    dataController.getPointInfo().getItems().clear();
                    dataController.getPointInfo().getItems().add(dataLoaded.toString());
                    form.setStyle("-fx-stroke-width: 2;");
                } else if (controller instanceof MainStageController) {
                    MainStageController mainController = (MainStageController) controller;
                    mainController.getPointInfo().getItems().clear();
                    mainController.getPointInfo().getItems().add(dataLoaded.toString());
                    form.setStyle("-fx-stroke: #60ffc6; -fx-stroke-width: 3;");
                    clickedForm = form;
                } else {
                    System.err.println("Contrôleur inconnu");
                }
            });
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la forme : " + e.getMessage());
        }
        return form;
    }

    /**
     * Configuration de la légende.
     * @return Le conteneur contenant la légende
     */
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
        for (int i = 0; i < colorsString.length; i += 7) {
            for (int j = 0; i + j < colorsString.length && j < i + 7; j++) {
                if (j % 7 == 0 && i != 0) {
                    legend.getChildren().add(line);
                    line = new HBox();
                    line.setSpacing(10);
                    line.setAlignment(Pos.CENTER);
                }

                tempHBox = new HBox();
                label = new Label(colorsString[i + j]);
                Circle circle = new Circle(5);
                circle.setFill(colors.get(colorsString[i + j]));
                tempHBox.getChildren().addAll(circle, label);
                line.getChildren().add(tempHBox);
            }
        }
                if (colorsString.length < 7) legend.getChildren().add(line);

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
