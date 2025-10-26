package com.eliasmshallouf.examples.model;

import com.eliasmshallouf.orm.annotations.Column;
import com.eliasmshallouf.orm.annotations.Entity;

@Entity(name = "employeeterritory")
public class EmployeeTerritory {
    @Column
    private Long employeeId;

    @Column
    private String territoryId;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(String territoryId) {
        this.territoryId = territoryId;
    }
}
