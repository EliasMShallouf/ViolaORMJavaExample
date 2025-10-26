package com.eliasmshallouf.examples.model;

import com.eliasmshallouf.orm.annotations.Column;
import com.eliasmshallouf.orm.annotations.Entity;
import com.eliasmshallouf.orm.annotations.Id;
import com.eliasmshallouf.orm.annotations.Lob;

import java.io.Serializable;

@Entity
public class Category implements Serializable {
    @Id long categoryId;
    @Column String categoryName;
    @Column String description;
    @Lob byte[] picture;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}