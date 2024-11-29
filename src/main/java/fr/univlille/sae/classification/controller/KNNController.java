package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.knn.MethodKNN;
import fr.univlille.sae.classification.knn.distance.Distance;
import fr.univlille.sae.classification.knn.distance.DistanceManhattanNormalisee;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.LoadableData;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KNNController {

    @FXML
    private Stage stage;

    @FXML
    ChoiceBox<String> algoSelector;

    @FXML
    Spinner<Integer> kEntry;

    @FXML
    Button autoK;

    @FXML
    Button confirmK;


    @FXML
    public void initialize() {
        int max = (int) Math.sqrt(ClassificationModel.getClassificationModel().getDatas().size());
        kEntry.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                        (max%2 == 0) ? max-1 : max,
                        1,
                        2));

        kEntry.getValueFactory().setValue(ClassificationModel.getClassificationModel().getK());

        algoSelector.getItems().addAll("Euclidienne", "Euclidienne Normalisée", "Manhattan", "Manhattan Normalisée");
        algoSelector.setValue(Distance.getDistanceName(ClassificationModel.getClassificationModel().getDistance()));
    }



    public void bestK() {
        ClassificationModel model = ClassificationModel.getClassificationModel();

        if(model.getkOptimal() > 0) {
            // Le K Optimal à déja été calculé, il n'est pas necessaire de le recaculer.
            kEntry.getValueFactory().setValue(model.getkOptimal());
        }else {
            // Calcul du K Optimal:


            Task<Scene> knnTask = new Task<>() {
                @Override
                protected Scene call() throws Exception {
                    updateProgress(0, 3);
                    updateMessage("Préparation des données ");

                    //List<LoadableData> datasShuffle = new ArrayList<>(List.copyOf(model.getDatas()));
                   // Collections.shuffle(datasShuffle);
                    MethodKNN.updateModel(model.getDatas());
                    Distance dist = Distance.getByName(algoSelector.getValue());

                    updateProgress(1, 3);
                    updateMessage("Recherche du meilleur K");

                    int bestK = MethodKNN.bestK(model.getDatas(), dist);


                    updateMessage("Test de robustesse");
                    updateProgress(2, 3);


                    double robustesse = MethodKNN.robustesse( model.getDatas(), bestK, dist, 0.2);
                    model.setKOptimal(bestK);

                    updateMessage("Affichage du resultat");
                    updateProgress(2.5, 3);

                    model.setDistance(dist);

                    HBox hBox = new HBox();
                    Label label = new Label("Best K: " + bestK + " robustesse : " + robustesse);
                    hBox.getChildren().add(label);
                    Scene scene = new Scene(hBox);
                    kEntry.getValueFactory().setValue(bestK);

                    updateMessage("Finished");
                    updateProgress(3, 3);

                    return scene;
                }
            };
            VBox vBox = new VBox();
            ProgressBar pBar = new ProgressBar();
            pBar.progressProperty().bind(knnTask.progressProperty());
            Label statusLabel = new Label();
            statusLabel.textProperty().bind(knnTask.messageProperty());

            vBox.alignmentProperty().setValue(Pos.CENTER);
            vBox.getChildren().addAll( pBar, statusLabel);
            Stage stageLoad = new Stage();
            Scene scene = new Scene(vBox);
            stageLoad.setTitle("Alogirhme K-NN");
            stageLoad.setMinWidth(300);

            stageLoad.setScene(scene);
            stageLoad.show();

            Stage stageFinished = new Stage();
            stageFinished.setTitle("Algorithme K-NN - results");
            knnTask.setOnSucceeded(e -> {
                stageLoad.close();
                stageFinished.setScene(knnTask.getValue());
                stageFinished.show();

            });

            new Thread(knnTask).start();




        }

    }


    public void validate() {

        ClassificationModel model = ClassificationModel.getClassificationModel();


        int k = kEntry.getValue();
        Distance dist = Distance.getByName(algoSelector.getValue());

        model.setDistance(dist);
        model.setK(k);
        model.classifierDonnees();

        stage.close();

    }

}
