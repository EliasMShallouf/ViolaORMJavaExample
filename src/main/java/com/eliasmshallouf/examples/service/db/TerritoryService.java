package com.eliasmshallouf.examples.service.db;

import com.eliasmshallouf.examples.model.Territory;
import com.eliasmshallouf.examples.model.TerritoryTable;
import com.eliasmshallouf.orm.ConnectionManager;
import com.eliasmshallouf.orm.table.EntityManager;
import java.util.List;

public class TerritoryService {
    public ConnectionManager connectionManager;
    private final TerritoryTable table = new TerritoryTable();
    private final EntityManager<Territory, String> entityManager;

    public TerritoryService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.entityManager = table.manager(connectionManager);
    }

    public List<Territory> getAllTerritories() {
        return entityManager.getAll();
    }

    public void save(Territory ...territories) {
        entityManager.save(territories);
    }

    public void deleteAll() {
        entityManager.deleteAll();
    }
}
