package com.eliasmshallouf.examples.service.db;

import com.eliasmshallouf.examples.model.OrderDetail;
import com.eliasmshallouf.examples.model.OrderDetailTable;
import com.eliasmshallouf.orm.ConnectionManager;
import com.eliasmshallouf.orm.columns.ColumnInfo;
import com.eliasmshallouf.orm.paging.Paging;
import com.eliasmshallouf.orm.table.EntityManager;
import java.util.List;

public class OrderDetailService {
    public ConnectionManager connectionManager;
    private OrderDetailTable table = new OrderDetailTable();
    private final EntityManager<OrderDetail, Long> manager;

    public OrderDetailService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.manager = table.manager(connectionManager);
    }

    public List<OrderDetail> getAll() {
        return manager.getAll();
    }

    public Paging<OrderDetail> paging() {
        return manager.pages(1000, true);
    }

    public long size() {
        return manager.count();
    }

    public int update(
        long product,
        double discount
    ) {
        return this.manager.update(
            this.table.productId().equal(ColumnInfo.valueOf(product)),
            this.table.discount().setTo(
                ColumnInfo
                    .valueOf(discount)
                    .<Double>asNumber()
                    .divide(this.table.unitPrice())
            )
        );
    }
}
