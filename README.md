# ViolaORM Java Example: Advanced Integration 🚀

This repository provides a comprehensive, fully-functional **Java** example that showcases advanced use-cases and best practices for integrating and leveraging the **Viola ORM** Micro-ORM.

Download this project to see how to harness Viola ORM's performance and type-safety within a standard enterprise application context.

***

## ✨ Concepts and Best Practices Demonstrated

This example application is built around real-world data access scenarios, including a suite of complex queries adapted from an MSc thesis, and illustrates the following advanced features of Viola ORM:

| Feature | Description |
| :--- | :--- |
| **Advanced Query Suite** | A complete suite of **11 complex queries** demonstrating intricate joins, subqueries, arithmetic operations, and aggregations (e.g., `GROUP BY`, `HAVING`). |
| **Composite Primary Keys** | Proper handling and mapping of entities that define their identity using **multiple fields** annotated with `@Id`. |
| **Manual Entity Definition** | Examples of defining database tables **programmatically** using the core `ColumnInfo` classes, completely bypassing the annotation processor for maximum flexibility. |
| **Efficient Pagination** | Implementation of **pagination logic** for handling large result sets, ensuring efficient data retrieval and minimal latency. |
| **Fluent `UPDATE` Queries** | Construction of complex **fluent `UPDATE` queries** with custom business logic embedded, specifically utilizing `CASE...WHEN` statements within the SQL logic. |
| **Transaction Management** | Best practices for managing database **transactions** using the `Transaction` class and `TransactionWorker` to ensure data integrity and atomicity. |
| **Multi-Format Fetching** | Demonstrations of fetching query results into flexible, non-entity formats like generic `Map<String, Object>` and raw `Object[]` arrays for versatile data consumption. |

***

## 🛠️ Get Started

1.  **Clone** this repository.
2.  Import the database from `database.sql` into your preferred SQL client (in this example, we have used MySQL).
2.  Ensure you have a configured database connection (check `Main.java` class) with the proper driver also (don't forget to implement it in the `build.gradle` file).
3.  Run the `Main.java` class.
4.  Explore the service/repository classes to view the advanced query implementations in action.