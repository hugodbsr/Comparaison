package fr.univlille.sae.classification.utils;

import fr.univlille.sae.classification.model.LoadableData;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * Classe utilitaire pour la gestion des vues.
 */
public class ViewUtil {

    /**
     * Définit la couleur de la forme
     * @param iris objet de données à afficher.
     * @param form forme à configurer.
     * @param root scène principale pour le menu contextuel.
     * @return forme configurée.
     */
    public static Shape getForm(LoadableData iris, Shape form, Stage root) {
        try {
            form.setFill(iris.getColor());
            form.setOnMouseClicked(e -> {
                ContextMenu contextMenu = new ContextMenu();
                for (String attributes : iris.getAttributesName()) {
                    contextMenu.getItems().add(new MenuItem(attributes + " : " + iris.getDataType(attributes)));
                }
                contextMenu.show(root, e.getScreenX(), e.getScreenY());
            });
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la forme : " + e.getMessage());
        }
        return form;
    }
}
