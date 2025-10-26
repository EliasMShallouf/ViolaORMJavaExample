package com.eliasmshallouf.examples.service.db;

import com.eliasmshallouf.examples.model.*;
import com.eliasmshallouf.orm.ConnectionManager;
import com.eliasmshallouf.orm.functions.Concat;
import com.eliasmshallouf.orm.table.EntityManager;

import java.util.HashSet;
import java.util.List;
import static com.eliasmshallouf.orm.columns.ColumnInfo.valueOf;

public class EmployeeService {
    public ConnectionManager connectionManager;
    private final EmployeeTable table = new EmployeeTable();
    public EntityManager<Employee, Long> entityManager;

    public EmployeeService(ConnectionManager manager) {
        this.connectionManager = manager;
        entityManager = table.manager(connectionManager);
    }

    public List<Employee> getAllEmployees() {
        return
            entityManager
                .getAll();
    }

    public Employee findEmployeeById(Long id) {
        return entityManager.findById(id);
    }

    public Employee findByFirstAndLastName(String fullName) {
        /*
            select * from employee where concat(firstname, ' ', lastname) = '$fullName'
        */

        return entityManager
            .<Employee>query()
            .where(
                Concat.of(
                    table.firstName(),
                    valueOf(" "),
                    table.lastName()
                ).equal(valueOf(fullName))
            ).find();
    }

    public int updateEmployeeTitle(String title, String newTitle) {
        return entityManager.update(
            table.title().equal(valueOf(title)),
            table.title().setTo(valueOf(newTitle))
        );
    }
}
