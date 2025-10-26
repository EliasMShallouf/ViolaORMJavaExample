package com.eliasmshallouf.examples.model.helpers;

import com.eliasmshallouf.orm.annotations.Column;
public class ProductWithTotal {
    @Column String name;
    @Column Double total;
}
