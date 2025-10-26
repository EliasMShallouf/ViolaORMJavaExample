package com.eliasmshallouf.examples.tests;

import com.eliasmshallouf.orm.ConnectionManager;
import com.eliasmshallouf.orm.annotations.Column;
import com.eliasmshallouf.orm.annotations.Id;
import com.eliasmshallouf.orm.columns.ColumnInfo;
import com.eliasmshallouf.orm.columns.NumericColumn;
import com.eliasmshallouf.orm.query.Query;
import com.eliasmshallouf.orm.table.EntityManager;
import com.eliasmshallouf.orm.table.EntityModel;
import com.eliasmshallouf.orm.transactions.TransactionResult;
import java.util.Map;

public class TestDeclareEntityWithoutAnnotationProcessor {
    public static class IpAddress {
        @Id
        public String ip;
        @Column
        public String country;
        @Column public String city;
        @Column public double lon;
        @Column public double lat;
    }

    public static void test(ConnectionManager manager) {
        EntityModel<IpAddress, String> model = EntityModel.defineEntity("address", IpAddress.class);

        ColumnInfo<String> ip = ColumnInfo.defineColumn(model, "ip");
        ColumnInfo<String> country = ColumnInfo.defineColumn(model, "country");
        ColumnInfo<String> city = ColumnInfo.defineColumn(model, "city");
        NumericColumn<Double> lon = ColumnInfo.defineColumn(model, "lon").asNumber();
        NumericColumn<Double> lat = ColumnInfo.defineColumn(model, "lat").asNumber();

        model.setIdField(ip.id());

        EntityManager<IpAddress, String> modelManager = model.manager(manager);

        modelManager.update(
            Query.noCondition(),
            country
                .setTo(
                    ip
                        .$switch()
                        .when(
                            ip.equal(ColumnInfo.valueOf("192.168.1.2")),
                            ColumnInfo.valueOf("no-ip")
                        )
                        .otherwise(ip)
                )
        );

        var sq = modelManager
                .query()
                .select(model);

        IpAddress res = sq.find();

        if(res != null) {
            System.out.println("address found : " + res.ip);

            res.country = "Lebanon";
            res.ip = "192.168.1.100";

            modelManager.updateById(res, "192.168.1.2");

            System.out.println("new ip = " + sq.find().ip);
        }

        // test find result as object[]
		Object[] resObj = sq.find(Object[].class);

		for(Object o : resObj)
			System.out.println(o + " : " + o.getClass().getSimpleName());

        // test transactions
        try {
            final String ipToDelete = "192.168.1.2";

            manager.transaction((con, tr) -> {
                System.out.println("Deleted " +
                        modelManager
                                .withConnection(con)
                                .delete(ip.equal(ColumnInfo.valueOf(ipToDelete)))
                        + " rows"
                );

                return TransactionResult.ROLLBACK;
            });

            // test find result as map/dict
            Map<String, ?> map = sq.where(ip.equal(ColumnInfo.valueOf(ipToDelete))).find(Map.class);

            if(map == null) {
                System.out.println("row is deleted");
                return;
            }

            System.out.println("row not deleted");
            map.entrySet().forEach(e -> {
                System.out.println(e.getKey() + " : " + e.getValue() + "/" + e.getValue().getClass().getSimpleName());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
