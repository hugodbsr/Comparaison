package fr.univlille.sae.classification.utils;

import java.util.Collection;
import java.util.HashSet;

/**
 * Classe abstraite représentant un sujet observable.
 */
public abstract class Observable {

    protected Collection<Observer> attached = new HashSet<>();
    protected Collection<Observer> toDetach = new HashSet<>();

    /**
     * Attache un observateur à l'objet observable.
     * @param obs observateur à attacher.
     */
    public void attach(Observer obs) {
        attached.add(obs);
    }

    /**
     * Détache un observateur de l'objet observable.
     * @param obs observateur à détacher.
     */
    public void detach(Observer obs) {
        this.toDetach.add(obs);
    }

    /**
     * Notifie tous les observateurs attachés de tout changement.
     */
    protected void notifyObservers() {
        this.updateList();
        for (Observer o : attached) {
            o.update(this);
        }
    }

    /**
     * Notifie tous les observateurs attachés avec des données supplémentaires.
     * @param data données à transmettre aux observateurs.
     */
    protected void notifyObservers(Object data) {
        this.updateList();
        for (Observer o : attached) {
            o.update(this, data);
        }
    }

    /**
     * Met à jour la liste des observateurs attachés en supprimant ceux à détacher.
     */
    private void updateList() {
        this.attached.removeAll(toDetach);
        this.toDetach.clear();
    }
}
