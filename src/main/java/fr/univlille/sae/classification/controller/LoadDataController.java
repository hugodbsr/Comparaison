package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
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
    ComboBox<DataType> typeModel;

    /**
     * Fichier sélectionné
     */
    File file;

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


    DataType typeChoisi = typeModel.getSelectionModel().getSelectedItem();

    /**
     * Valide le fichier sélectionné au préalable
     */
    public void validate(){

        if (file == null || file.isDirectory() || !file.exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement du fichier");
            alert.setHeaderText(null);
            alert.initOwner(stage);
            alert.setContentText("Le chargement du fichier à echoué, veuillez reessayer !");
            alert.showAndWait();
            openFileChooser();
            ClassificationModel.getClassificationModel().setType(typeChoisi);
            return;
        }
        

        ClassificationModel.getClassificationModel().loadData(file);
        stage.close();
    }
}
