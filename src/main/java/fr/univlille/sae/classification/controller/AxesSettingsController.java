package fr.univlille.sae.classification.controller;

import fr.univlille.sae.classification.view.DataVisualizationView;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

/**
 * Contrôleur pour le fichier FXML "axes-settings-stage", pour gérer les axes de la vue.
 */
public class AxesSettingsController{
    /**
     * Fenêtre associée à cette vue.
     */
    @FXML
    Stage stage;

    /**
     * Choix des données en axes (abscisse et ordonnée).
     */
    @FXML
    ChoiceBox selectOrd;

    @FXML
    ChoiceBox selectAbs;

    /**
     * Choix des limites inférieures et supérieures des axes.
     */
    @FXML
    Spinner OrdSizeLower;

    @FXML
    Spinner OrdSizeUpper;

    @FXML
    Spinner AbsSizeUpper;

    @FXML
    Spinner AbsSizeLower;


    /**
     * DataVisualizationView associé au contrôleur.
     */
    DataVisualizationView dataVisualizationView;

    /**
     * Ajout des éléments à sélectionner pour les ordonnées de la grille.
     * @param fields Éléments à ajouter
     */
    public void setSelectOrd(String[] fields){
        selectOrd.getItems().clear();
        selectOrd.getItems().addAll(fields);
        selectOrd.setValue(dataVisualizationView.getActualY());
    }

    /**
     * Ajout des éléments à sélectionner pour les abscisses de la grille.
     * @param fields Éléments à ajouter
     */
    public void setSelectAbs(String[] fields){
        selectAbs.getItems().clear();
        selectAbs.getItems().addAll(fields);
        selectAbs.setValue(dataVisualizationView.getActualX());
    }

    /**
     * Configure la limite supérieure de l'axe des ordonnées.
     * @param value Valeur initiale à définir pour la limite supérieure.
     */
    public void setOrdSizeUpper(double value){
        OrdSizeUpper.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 9999, value,1));
    }

    /**
     * Configure la limite inférieure de l'axe des ordonnées.
     * @param value Valeur initiale à définir pour la limite inférieure.
     */
    public void setOrdSizeLower(double value){
        OrdSizeLower.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 9999, value,1));
    }

    /**
     * Configure la limite supérieure de l'axe des abscisses.
     * @param value Valeur initiale à définir pour la limite supérieure.
     */
    public void setAbsSizeUpper(double value){
        AbsSizeUpper.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 9999, value,1));
    }

    /**
     * Configure la limite inférieure de l'axe des abscisses.
     * @param value Valeur initiale à définir pour la limite inférieure.
     */
    public void setAbsSizeLower(double value){
        AbsSizeLower.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 9999, value,1));
    }

    /**
     * Méthode permettante d'attribuer la dataVisualizationView associer à la classe
     * @param dataVisualizationView dataVisualizationView à attribuer
     */
    public void setdataVisualizationView(DataVisualizationView dataVisualizationView) {
        this.dataVisualizationView = dataVisualizationView;
    }

    /**
     * Validation des paramètres des axes.
     */
    public void validate(){
        if(dataVisualizationView.getActualX() != null || dataVisualizationView.getActualY() != null){
            if(!dataVisualizationView.getActualX().equals(selectAbs.getValue().toString()) ||
                    !dataVisualizationView.getActualY().equals(selectOrd.getValue().toString())){
                dataVisualizationView.setActualX(selectAbs.getValue().toString());
                dataVisualizationView.setActualY(selectOrd.getValue().toString());

                dataVisualizationView.getScatterChart().getXAxis().setLabel(dataVisualizationView.getActualX());
                dataVisualizationView.getScatterChart().getYAxis().setLabel(dataVisualizationView.getActualY());
                reset();
            }
        }else{
            dataVisualizationView.setActualX(selectAbs.getValue().toString());
            dataVisualizationView.setActualY(selectOrd.getValue().toString());

            dataVisualizationView.getScatterChart().getXAxis().setLabel(dataVisualizationView.getActualX());
            dataVisualizationView.getScatterChart().getYAxis().setLabel(dataVisualizationView.getActualY());
        }


        if((Double)AbsSizeUpper.getValue() != ((NumberAxis)dataVisualizationView.getScatterChart().getXAxis()).getUpperBound() ||
            (Double)OrdSizeUpper.getValue() != ((NumberAxis)dataVisualizationView.getScatterChart().getYAxis()).getUpperBound() ||
            (Double)AbsSizeLower.getValue() != ((NumberAxis)dataVisualizationView.getScatterChart().getXAxis()).getLowerBound() ||
            (Double)OrdSizeLower.getValue() != ((NumberAxis)dataVisualizationView.getScatterChart().getYAxis()).getLowerBound()
        ){
            ((NumberAxis) dataVisualizationView.getScatterChart().getXAxis()).setAutoRanging(false);
            ((NumberAxis) dataVisualizationView.getScatterChart().getYAxis()).setAutoRanging(false);

            ((NumberAxis) dataVisualizationView.getScatterChart().getXAxis()).setUpperBound((Double) AbsSizeUpper.getValue());
            ((NumberAxis) dataVisualizationView.getScatterChart().getYAxis()).setUpperBound((Double) OrdSizeUpper.getValue());

            ((NumberAxis) dataVisualizationView.getScatterChart().getXAxis()).setLowerBound((Double) AbsSizeLower.getValue());
            ((NumberAxis) dataVisualizationView.getScatterChart().getYAxis()).setLowerBound((Double) OrdSizeLower.getValue());
        }

        dataVisualizationView.reload();
        reset();

        stage.close();
    }

    /**
     * Réinitialise les paramètres des axes en activant l'ajustement
     * automatique et recharge la visualisation des données.
     */
    public void reset(){
        ((NumberAxis) dataVisualizationView.getScatterChart().getXAxis()).setAutoRanging(true);
        ((NumberAxis) dataVisualizationView.getScatterChart().getYAxis()).setAutoRanging(true);
        dataVisualizationView.reload();
        stage.close();
    }
}
