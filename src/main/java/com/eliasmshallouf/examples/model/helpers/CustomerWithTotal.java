package com.eliasmshallouf.examples.model.helpers;

import com.eliasmshallouf.orm.annotations.Column;
public class CustomerWithTotal {
    @Column String name;
    @Column Double total;

    public String getName() {
        return name;
    }

    public Double getTotal() {
        return total;
    }
}
