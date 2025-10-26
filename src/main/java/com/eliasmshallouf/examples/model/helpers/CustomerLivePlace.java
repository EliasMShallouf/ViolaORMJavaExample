package com.eliasmshallouf.examples.model.helpers;

import com.eliasmshallouf.orm.annotations.Column;

public class CustomerLivePlace {
    @Column String country;
    @Column String city;
    @Column Long count;
}
