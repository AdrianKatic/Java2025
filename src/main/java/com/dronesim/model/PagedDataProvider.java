package com.dronesim.model;

import java.util.List;

public interface PagedDataProvider<T> {
    List<T> getPage(int pageIndex, int pageSize) throws Exception;
}
