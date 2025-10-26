package com.eliasmshallouf.examples.service.db;

import com.eliasmshallouf.examples.model.Customer;
import com.eliasmshallouf.examples.model.CustomerTable;
import com.eliasmshallouf.examples.model.OrderDetailTable;
import com.eliasmshallouf.examples.model.SalesOrderTable;
import com.eliasmshallouf.examples.model.helpers.CustomerLivePlace;
import com.eliasmshallouf.examples.model.helpers.CustomerWithTotal;
import com.eliasmshallouf.orm.ConnectionManager;
import com.eliasmshallouf.orm.functions.aggergation.Count;
import com.eliasmshallouf.orm.functions.aggergation.Sum;
import com.eliasmshallouf.orm.table.EntityManager;
import java.util.List;
import static com.eliasmshallouf.orm.columns.ColumnInfo.valueOf;

public class CustomerService {
    public ConnectionManager connectionManager;
    private final CustomerTable table = new CustomerTable();
    private final EntityManager<Customer, Long> entityManager;

    public CustomerService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.entityManager = table.manager(connectionManager);
    }

    public void deleteById(Long id) {
        entityManager.deleteById(id);
    }

    public List<CustomerWithTotal> getCustomersWithTotals() {
        /*
            SELECT c.contactName as name, sum(od.unitPrice * od.quantity * (1 - od.discount)) as total
            FROM customer c
            join salesorder so on cast(so.custId as integer) = c.custId
            join orderdetail od on od.orderId = so.orderId
            GROUP by c.custId
            ORDER by total DESC;
        */

        CustomerTable c = new CustomerTable().aliased("c");
        SalesOrderTable so = new SalesOrderTable().aliased("so");
        OrderDetailTable od = new OrderDetailTable().aliased("od");

        var total = Sum.of(
            od.unitPrice().multiple(
                od.quantity().castTo(Double.class).multiple(
                    valueOf(1).<Double>asNumber().subtract(od.discount())
                )
            )
        ).as("total");

        return entityManager.<Customer>query()
            .table(c)
            .join(so, c.custId().equal(so.custId()))
            .join(od, od.orderId().equal(so.orderId()))
            .select(
                c.contactName().as("name"),
                total
            )
            .groupBy(c.custId())
            .orderBy(total.descendingOrder())
            .list(CustomerWithTotal.class);
    }

    public List<CustomerLivePlace> getCustomersLivePlaces() {
        /*
            SELECT c.country, c.city, count(c.custId) as count
            FROM customer c
            GROUP by c.country, c.city
            ORDER by count desc;
         */

        CustomerTable c = new CustomerTable().aliased("c");

        var count = Count.of(c.custId()).as("count");

        return entityManager.<Customer>query()
            .table(c)
            .select(
                c.country(),
                c.city(),
                count
            )
            .groupBy(
                c.country(),
                c.city()
            )
            .orderBy(count.descendingOrder())
            .list(CustomerLivePlace.class);
    }
}
