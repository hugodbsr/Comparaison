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
import java.text.ParseException;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Contrôleur pour le fichier FXML "add-data-stage", permettant à l'utilisateur
 * d'ajouter une nouvelle donnée dans le modèle de classification.
 */
public class AddDataController {

    /**
     * Fenêtre associée à cette vue.
     */
    @FXML
    private Stage stage;

    /**
     * Conteneur contenant les champs d'entrée de données.
     */
    @FXML
    private VBox entries;

    /**
     * MainStageView associée au contrôleur.
     */
    MainStageView mainStageView;

    /**
     * Liste des composants d'entrée gérés dynamiquement (Spinner, TextField,
     * ChoiceBox) selon le type des données.
     */
    private List<Object> components;

    /**
     * Méthode d'initialisation du contrôleur.
     */
    @FXML
    public void initialize() {
        this.components = new ArrayList<>();
        ClassificationModel model = ClassificationModel.getClassificationModel();
        if (!model.getDatas().isEmpty()) {
            Map<String, Object> attrMap = model.getDatas().get(0).getAttributesNames();
            int classificationType = model.getDatas().get(0).getClassificationType();
            for (Map.Entry<String, Object> entry : attrMap.entrySet()) {
                String attrName = entry.getKey();
                Object attrValue = entry.getValue();

                if(!attrMap.keySet().toArray()[classificationType].equals(attrName))  {
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

                        TextField editor = doubleSpinner.getEditor();

                        // On bloque la siasi de texte autre que des chiffres dans le spinner
                        Pattern validDoublePattern = Pattern.compile("-?\\d*(\\.\\d*)?");
                        UnaryOperator<TextFormatter.Change> filter = change -> {
                            String newText = change.getControlNewText();
                            if (validDoublePattern.matcher(newText).matches()) {
                                return change;
                            }
                            return null;
                        };

                        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
                        editor.setTextFormatter(textFormatter);

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

                        TextField editor = integerSpinner.getEditor();

                        Pattern validIntegerPattern = Pattern.compile("-?\\d*");
                        UnaryOperator<TextFormatter.Change> filter = change -> {
                            String newText = change.getControlNewText();
                            if (validIntegerPattern.matcher(newText).matches()) {
                                return change;
                            }
                            return null;
                        };

                        // Appliquer le TextFormatter au TextField du Spinner
                        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
                        editor.setTextFormatter(textFormatter);

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
    }

    /**
     * Méthode permettant d'attribuer la mainStageView associée à la classe.
     * @param mainStageView mainStageView à attribuer
     */
    public void setMainStageView(MainStageView mainStageView) {
        this.mainStageView = mainStageView;
    }
    /**
     * Validation des données à ajouter.
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
        }catch (NumberFormatException e) {
            openErrorStage(e, "Erreur, les données ne respecte pas le format specifié");
        }catch (IllegalArgumentException e) {
            openErrorStage(e);
        }
        stage.close();
    }
    private void openErrorStage(Exception e, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur - " + e.getClass());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openErrorStage(Exception e) {
        openErrorStage(e, e.getMessage());
    }
}
