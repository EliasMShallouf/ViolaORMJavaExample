package com.eliasmshallouf.examples.model;

import com.eliasmshallouf.orm.annotations.Column;
import com.eliasmshallouf.orm.annotations.Entity;
import com.eliasmshallouf.orm.annotations.Id;

import java.io.Serializable;

@Entity
public class Territory implements Serializable {
    @Id String territoryId;
    @Column(name = "territorydescription") String territoryDescription;
    @Column Long regionId;

    public String getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(String territoryId) {
        this.territoryId = territoryId;
    }

    public String getTerritoryDescription() {
        return territoryDescription;
    }

    public void setTerritoryDescription(String territoryDescription) {
        this.territoryDescription = territoryDescription;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getRegionId() {
        return regionId;
    }
}