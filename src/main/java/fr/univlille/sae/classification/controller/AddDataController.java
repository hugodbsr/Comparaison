package fr.univlille.sae.classification.controller;

import com.sun.scenario.effect.impl.state.HVSeparableKernel;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.view.MainStageView;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Controlleur pour le FXML add-data-stage, pour ajouter une nouvelle donnée
 */
public class AddDataController {

    @FXML
    private Stage stage;

    @FXML
    private VBox entries;

    /**
     * MainStageView associé au controlleur
     */
    MainStageView mainStageView;

    private List<Object> components;

    /**
     * Méthode d'intitialisation du controlleur
     */
    @FXML
    public void initialize() {
        this.components = new ArrayList<>();
        ClassificationModel model = ClassificationModel.getClassificationModel();
        if (!model.getDatas().isEmpty()) {
            Map<String, Object> attrMap = model.getDatas().get(0).getAttributesNames();
            for (Map.Entry<String, Object> entry : attrMap.entrySet()) {
                String attrName = entry.getKey();
                Object attrValue = entry.getValue();

                Label label = new Label(attrName);
                HBox hbox = new HBox(10, label);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(10);

                if (attrValue instanceof Double) {
                    Spinner<Double> doubleSpinner = new Spinner<>();
                    doubleSpinner.setEditable(true);
                    SpinnerValueFactory<Double> valueFactory =
                            new SpinnerValueFactory.DoubleSpinnerValueFactory(
                                    0.0,
                                    9999,
                                    0.0,
                                    0.5
                            );
                    doubleSpinner.setValueFactory(valueFactory);
                    hbox.getChildren().add(doubleSpinner);
                    components.add(doubleSpinner);
                }
                else if (attrValue instanceof Integer) {
                    Spinner<Integer> integerSpinner = new Spinner<>();
                    integerSpinner.setEditable(true);
                    SpinnerValueFactory<Integer> valueFactory =
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(
                                    0,
                                    Integer.MAX_VALUE,
                                    0,
                                    1
                            );
                    integerSpinner.setValueFactory(valueFactory);
                    hbox.getChildren().add(integerSpinner);
                    components.add(integerSpinner);
                }
                else if (attrValue instanceof String) {
                    TextField textField = new TextField();
                    hbox.getChildren().add(textField);
                    components.add(textField);
                }
                else if (attrValue instanceof Boolean) {
                    ChoiceBox<String> choiceBox = new ChoiceBox<>();
                    choiceBox.getItems().addAll("VRAI", "FAUX");
                    choiceBox.setValue("VRAI");
                    hbox.getChildren().add(choiceBox);
                    components.add(choiceBox);
                }
                entries.getChildren().add(hbox);
            }
        }
    }

    /**
     * Méthode permettante d'attribuer la mainStageView associer à la classe
     * @param mainStageView mainStageView à attribuer
     */
    public void setMainStageView(MainStageView mainStageView) {
        this.mainStageView = mainStageView;
    }
    /**
     * Validation des données à ajouter
     */
    public void validate() {
        System.out.println("validé");
        mainStageView.getController().getClassifyData().setDisable(false);

        try {
            Object[] values = components.stream().map(component -> {
                if (component instanceof Spinner) {
                    return ((Spinner<?>) component).getValue();
                } else if (component instanceof TextField) {
                    return ((TextField) component).getText();
                } else if (component instanceof ChoiceBox) {
                    return ((ChoiceBox<String>) component).getValue().equals("VRAI");
                }
                return null;
            }).toArray();

            System.out.println(Arrays.toString(values));
            ClassificationModel.getClassificationModel().ajouterDonnee(values);
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        stage.close();
    }

}
