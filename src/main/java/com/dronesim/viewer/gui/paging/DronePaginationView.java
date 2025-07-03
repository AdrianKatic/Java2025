package com.dronesim.viewer.gui.paging;

import java.util.List;

/**
 * Interface for displaying a page of drone-related data.
 */
public interface DronePaginationView<T> {
    /**
     * Called by the controller to display a new page of data.
     */
    void updatePage(List<T> entries, int currentPage, int pageSize);
}
