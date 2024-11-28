package fr.univlille.sae.classification.controller;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LoadDataController {


    @FXML
    Stage stage;


    @FXML
    TextField filePath;

    @FXML
    ChoiceBox<DataType> fileType = new ChoiceBox<>();

    /**
     * Fichier sélectionné
     */
    File file;

    @FXML
    public void initialize() {
        fileType.getItems().addAll(DataType.values());
        fileType.setValue(DataType.values()[0]);
    }

    /**
     * Ouvre un explorateur de fichiers pour sélectionner le fichier à étudier
     */
    public void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez le fichier à importer");
         this.file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            filePath.setText(file.getPath());
        }

    }



    /**
     * Valide le fichier sélectionné au préalable
     */
    public void validate(){

        DataType typeChoisi = fileType.getValue();


        if (file == null || file.isDirectory() || !file.exists() || fileType.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement du fichier");
            alert.setHeaderText(null);
            alert.initOwner(stage);
            alert.setContentText("Le chargement du fichier à echoué, veuillez reessayer !");
            alert.showAndWait();
            openFileChooser();
            return;
        }

        ClassificationModel.getClassificationModel().setType(typeChoisi);
        try {
            ClassificationModel.getClassificationModel().loadData(file);
        }catch (RuntimeException | CsvRequiredFieldEmptyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setHeaderText(e.toString());
            alert.setContentText("Le chargement du fichier à echoué, veuillez reessayer !");
            alert.showAndWait();
        }
        stage.close();
    }
}
