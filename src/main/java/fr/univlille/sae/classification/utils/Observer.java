package fr.univlille.sae.classification.utils;


/**
 * Interface pour implémenter le modèle Observateur.
 * Cette interface définit les méthodes que les classes doivent implémenter pour agir
 * comme des observateurs dans le cadre du modèle Observateur/Observé.
 * Les observateurs sont notifiés des changements d'état des objets observés
 * via les méthodes `update'.
 */
public interface Observer {

    /**
     * Méthode appelée pour notifier l'observateur qu'un changement s'est produit
     * dans l'objet observé.
     * @param observable L'objet observé qui a subi un changement
     */
    void update(Observable observable);

    /**
     * Méthode appelée pour notifier l'observateur qu'un changement s'est produit
     * dans l'objet observé, avec des données supplémentaires.
     * @param observable L'objet observé qui a subi un changement
     * @param data Informations supplémentaires concernant le changement
     */
    void update(Observable observable, Object data);


}
