package fr.univlille.sae.classification.view;

import javafx.scene.chart.ScatterChart;

public abstract class DataVisualizationView {
    protected DataVisualizationView() {}
    protected String actualX;
    protected String actualY;
    protected ScatterChart scatterChart;

    public String getActualX() {
        return actualX;
    }

    public void setActualX(String actualX) {
        this.actualX = actualX;
    }

    public String getActualY() {
        return actualY;
    }

    public void setActualY(String actualY) {
        this.actualY = actualY;
    }

    public ScatterChart getScatterChart() {
        return this.scatterChart;
    }

    public abstract void reload();
}
