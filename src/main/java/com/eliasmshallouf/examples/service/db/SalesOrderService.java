package com.eliasmshallouf.examples.service.db;

import com.eliasmshallouf.examples.model.OrderDetailTable;
import com.eliasmshallouf.examples.model.SalesOrder;
import com.eliasmshallouf.examples.model.SalesOrderTable;
import com.eliasmshallouf.orm.ConnectionManager;
import com.eliasmshallouf.orm.functions.aggergation.Sum;
import com.eliasmshallouf.orm.query.SubQuery;
import com.eliasmshallouf.orm.table.EntityManager;
import java.util.List;
import static com.eliasmshallouf.orm.columns.ColumnInfo.valueOf;

public class SalesOrderService {
    public ConnectionManager connectionManager;
    private EntityManager<SalesOrder, Long> entityManager;

    public SalesOrderService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.entityManager = new SalesOrderTable().manager(connectionManager);
    }

    public List<SalesOrder> getAllWithTotalsBetween(double low, double high) {
        /*
            SELECT a.* from (
                select so.*, sum(od.unitPrice * od.quantity * (1 - od.discount)) total
                FROM salesorder so
                join orderdetail od on od.orderId = so.orderId
                GROUP by so.orderId
                ORDER by total desc
            ) a where a.total BETWEEN %f and %f
         */

        SalesOrderTable so = new SalesOrderTable().aliased("so");
        OrderDetailTable od = new OrderDetailTable().aliased("od");

        Sum<Double> total = Sum.of(
            od.unitPrice().multiple(
                od.quantity().castTo(Double.class).multiple(
                    valueOf(1).<Double>asNumber().subtract(od.discount())
                )
            )
        ).as("total");

        var subQuery =
            new SubQuery<SalesOrder>()
                .table(so)
                .select(
                    so.allColumns(),
                    total
                )
                .join(od, od.orderId().equal(so.orderId()))
                .groupBy(so.orderId())
                .orderBy(total.descendingOrder())
                .aliased("a");

        return
            entityManager
                .query()
                .table(subQuery)
                .select(subQuery.allColumns())
                .where(total.between(valueOf(low), valueOf(high)))
                .list();
    }
}
