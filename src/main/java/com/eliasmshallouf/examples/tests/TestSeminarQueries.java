package com.eliasmshallouf.examples.tests;

import com.eliasmshallouf.examples.TaskRunner;
import com.eliasmshallouf.examples.model.OrderDetail;
import com.eliasmshallouf.examples.model.Territory;
import com.eliasmshallouf.examples.service.db.*;
import com.eliasmshallouf.orm.ConnectionManager;
import com.eliasmshallouf.orm.logger.Logger;
import com.eliasmshallouf.orm.paging.Page;
import com.eliasmshallouf.orm.paging.Paging;

import java.util.List;

public class TestSeminarQueries {
    public static void test(ConnectionManager connectionManager) {
        TaskRunner runner = TaskRunner.defaultRunner();

        ConnectionManager.setLogger(Logger.noLogger);

        EmployeeService employeeService = new EmployeeService(connectionManager);
        TerritoryService territoryService = new TerritoryService(connectionManager);
        OrderDetailService orderDetailService = new OrderDetailService(connectionManager);
        CustomerService customerService = new CustomerService(connectionManager);
        ProductService productService = new ProductService(connectionManager);
        SalesOrderService salesOrderService = new SalesOrderService(connectionManager);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q1", employeeService::getAllEmployees), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q2", orderDetailService::getAll), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q3", () -> employeeService.findEmployeeById(7L)), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q4", () -> employeeService.findByFirstAndLastName("Sven Buck")), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q5", () -> customerService.deleteById(280L)), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q6", () -> {
            List<Territory> territoryList = territoryService.getAllTerritories();
            territoryService.deleteAll();
            territoryService.save(territoryList.toArray(new Territory[0]));
        }), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q7", customerService::getCustomersWithTotals), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q8", customerService::getCustomersLivePlaces), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q9", productService::getProductsWithTotals), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q10", () -> salesOrderService.getAllWithTotalsBetween(5000, 12000)), 100);

        runner.executeOnceThenRepeatedly(new TaskRunner.Task("Q11", () -> orderDetailService.update(11, 10)), 20);

        System.out.println("Finished");
    }

    public static void testPaging(ConnectionManager connectionManager) {
        OrderDetailService orderDetailService = new OrderDetailService(connectionManager);

        // Test Paging - check its super method and parameters
        Paging<OrderDetail> pages = orderDetailService.paging();

        while (pages.hasNext()) {
            long t = System.currentTimeMillis();

            Page<OrderDetail> page = pages.next();

            System.out.println("Loaded page " + page.getIndex() + " in " + (System.currentTimeMillis() - t) + "ms with data size = " + page.getSize());
        }
    }
}
