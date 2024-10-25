package fr.univlille.sae.classification.view;

import javafx.scene.chart.ScatterChart;

/**
 * Classe abstraite représentant une vue de visualisation des données.
 * Elle gère les axes actuels et le graphique de dispersion.
 */
public abstract class DataVisualizationView {

    protected String actualX;
    protected String actualY;
    protected ScatterChart scatterChart;

    /**
     * Constructeur par défaut.
     */
    protected DataVisualizationView() {}

    /**
     * Renvoie le nom de l'axe X actuel.
     * @return nom de l'axe X.
     */
    public String getActualX() {
        return actualX;
    }

    /**
     * Définit le nom de l'axe X actuel.
     * @param actualX nom de l'axe X à définir.
     */
    public void setActualX(String actualX) {
        this.actualX = actualX;
    }

    /**
     * Renvoie le nom de l'axe Y actuel.
     * @return nom de l'axe Y.
     */
    public String getActualY() {
        return actualY;
    }

    /**
     * Définit le nom de l'axe Y actuel.
     * @param actualY nom de l'axe Y à définir.
     */
    public void setActualY(String actualY) {
        this.actualY = actualY;
    }

    /**
     * Renvoie le graphique de dispersion associé à cette vue.
     * @return graphique de dispersion.
     */
    public ScatterChart getScatterChart() {
        return this.scatterChart;
    }

    /**
     * Méthode abstraite à implémenter pour recharger les données de la vue.
     */
    public abstract void reload();
}
