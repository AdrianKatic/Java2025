package com.dronesim.viewer.gui.paging;

import java.util.List;

/**
 * Page navigation for drone table (Next/Prev buttons).
 */
public interface DronePaginationView<T> {

    void updatePage(List<T> entries, int currentPage, int pageSize);
}
