package com.eliasmshallouf.examples.service.db;

import com.eliasmshallouf.examples.model.OrderDetailTable;
import com.eliasmshallouf.examples.model.Product;
import com.eliasmshallouf.examples.model.ProductTable;
import com.eliasmshallouf.examples.model.helpers.ProductWithTotal;
import com.eliasmshallouf.orm.ConnectionManager;
import com.eliasmshallouf.orm.functions.aggergation.Sum;
import com.eliasmshallouf.orm.table.EntityManager;

import java.util.List;
import static com.eliasmshallouf.orm.columns.ColumnInfo.valueOf;

public class ProductService {
    public ConnectionManager connectionManager;
    private final EntityManager<Product, Long> entityManager;

    public ProductService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.entityManager = new ProductTable().manager(connectionManager);
    }

    public List<ProductWithTotal> getProductsWithTotals() {
        /*
            SELECT p.productName as name, sum(od.unitPrice * od.quantity * (1 - od.discount)) as total
            FROM product p
            join orderdetail od on od.productId = p.productId
            GROUP by p.productId
            ORDER by total DESC;
        */

        ProductTable p = new ProductTable().aliased("p");
        OrderDetailTable od = new OrderDetailTable().aliased("od");

        var total = Sum.of(
            od.unitPrice().multiple(
                od.quantity().castTo(Double.class).multiple(
                    valueOf(1).<Double>asNumber().subtract(od.discount())
                )
            )
        ).as("total");

        return
            entityManager
                .<Product>query()
                .table(p)
                .select(
                    p.productName().as("name"),
                    total
                )
                .join(od, od.productId().equal(p.productId()))
                .groupBy(p.productId())
                .orderBy(total.descendingOrder())
                .list(ProductWithTotal.class);
    }
}
