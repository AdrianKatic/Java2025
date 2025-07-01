package com.dronesim.viewer.gui.paging;

import java.util.List;

/**
 * Interface zur Darstellung einer Seite mit Drohnendaten.
 * Von Panels wie DynamicsPanel oder CatalogPanel implementierbar.
 */
public interface DronePaginationView<T> {
    /**
     * Wird vom Controller aufgerufen, um eine neue Seite von Daten anzuzeigen.
     *
     * @param entries     die anzuzeigenden Objekte der aktuellen Seite
     * @param currentPage die aktuelle Seitenzahl (0-basiert)
     * @param pageSize    die Anzahl der Objekte pro Seite
     */
    void updatePage(List<T> entries, int currentPage, int pageSize);
}
