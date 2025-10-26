package com.eliasmshallouf.examples.tests;

import com.eliasmshallouf.examples.model.helpers.Hello;
import com.eliasmshallouf.examples.model.helpers.HelloTable;
import com.eliasmshallouf.orm.ConnectionManager;
import com.eliasmshallouf.orm.columns.ColumnInfo;
import com.eliasmshallouf.orm.query.Query;
import com.eliasmshallouf.orm.table.EntityManager;

public class TestComplexPrimaryKey {
    public static void test(ConnectionManager manager) {
        HelloTable helloTable = new HelloTable();
        EntityManager<Hello, HelloTable.HelloTableId> helloIdEntityManager = helloTable.manager(manager);

        Hello hello = new Hello();
        hello.setA("elias");
        hello.setB(27);

        helloIdEntityManager.save(hello);

        HelloTable.HelloTableId id2 = new HelloTable.HelloTableId(hello);

        Hello res = helloIdEntityManager.findById(id2);

        helloIdEntityManager.update(hello);

        helloIdEntityManager.update(Query.noCondition(), helloTable.c().setTo(ColumnInfo.valueOf(33L)));
    }
}
