package com.dronesim.model;

import com.dronesim.api.DataFetcher;
import java.util.List;

public class DroneDynamicsDataProvider implements PagedDataProvider<DroneDynamics> {
    private final DataFetcher fetcher = new DataFetcher();
    private final int droneId;

    public DroneDynamicsDataProvider(int droneId) {
        this.droneId = droneId;
    }

    @Override
    public List<DroneDynamics> getPage(int pageIndex, int pageSize) throws Exception {
        int offset = pageIndex * pageSize;
        return fetcher.fetchDroneDynamics(droneId, pageSize, offset);
    }
}
