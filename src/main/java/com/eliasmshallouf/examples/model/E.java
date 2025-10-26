package com.eliasmshallouf.examples.model;

import com.eliasmshallouf.orm.annotations.Column;
import com.eliasmshallouf.orm.annotations.Entity;
import com.eliasmshallouf.orm.annotations.Id;

@Entity
public class E {
    @Id int id;
    @Column long x;
    @Column String s;
    @Column double d;
}
