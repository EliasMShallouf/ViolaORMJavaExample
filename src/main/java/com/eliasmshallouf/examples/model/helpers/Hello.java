package com.eliasmshallouf.examples.model.helpers;

import com.eliasmshallouf.orm.annotations.Column;
import com.eliasmshallouf.orm.annotations.Entity;
import com.eliasmshallouf.orm.annotations.Id;

import java.time.LocalDate;

@Entity
public class Hello {
    @Id private String a;
    @Id private int b;
    @Column long c;
    @Column LocalDate d;

    public void setA(String a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setC(long c) {
        this.c = c;
    }

    public String getA() {
        return a;
    }
}
