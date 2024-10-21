package fr.univlille.sae.classification.view;

import fr.univlille.sae.classification.controller.LoadDataController;
import fr.univlille.sae.classification.controller.MainStageController;
import fr.univlille.sae.classification.model.ClassificationModel;
import fr.univlille.sae.classification.model.DataType;
import fr.univlille.sae.classification.model.Iris;
import fr.univlille.sae.classification.model.LoadableData;
import fr.univlille.sae.classification.utils.Observable;
import fr.univlille.sae.classification.utils.Observer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainStageView implements Observer {

    private ClassificationModel model;
    private ScatterChart scatterChart;

    private String actualX;
    private String actualY;

    public MainStageView(ClassificationModel model) {
        this.model = model;
        model.attach(this);
    }


    public void show() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        URL fxmlFileUrl = new File(System.getProperty("user.dir") + File.separator + "res" + File.separator + "stages" + File.separator + "main-stage.fxml").toURI().toURL();

        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Stage root = loader.load();
        root.setResizable(false);
        root.setTitle("SAE3.3 - Logiciel de classification");
        root.show();
        loader.getController();
        MainStageController controller = loader.getController();
        controller.setMainStageView(this);
        scatterChart = controller.getScatterChart();

    }


    @Override
    public void update(Observable observable) {
        if(scatterChart == null) throw new IllegalStateException();
        scatterChart.getData().clear();
        if(!(observable instanceof ClassificationModel)) throw new IllegalStateException();
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Dice Launch");
        scatterChart.getData().add(series1);
        for(LoadableData i : model.getDatas()) {
            if(model.getType() == DataType.IRIS) {
                series1.getData().add(new XYChart.Data<>(((Iris)i).getDataType(actualX),
                        ((Iris)i).getDataType(actualY)));
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {

    }

    public String getActualX() {
        return actualX;
    }

    public String getActualY() {
        return actualY;
    }

    public void setActualX(String actualX) {
        this.actualX = actualX;
    }

    public void setActualY(String actualY) {
        this.actualY = actualY;
    }

    public Observable getModel() {
        return this.model;
    }
}
