package fr.univlille.sae.classification.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class LoadDataController {


    @FXML
    Stage stage;

    @FXML
    Button browseFile;

    @FXML
    Button confirmDataSelection;

    @FXML
    TextField filePath;

    File file;


    public void loadData() {

        System.out.println("Loading data");
        stage.close();

    }


    public void openFileChooser() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez le fichier à importer");
         this.file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            filePath.setText(file.getName());
        }

    }

    public void validate() {

        if (file == null) {
            stage.close();
            //throw exception
        }

        loadData();
    }



}
