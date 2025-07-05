package com.dronesim.model;

import java.util.List;

/**
 * Helps with parsing paginated JSON responses (count, next, results).
 */
public interface PagedDataProvider<T> {

    List<T> getPage(int pageIndex, int pageSize) throws Exception;
}
