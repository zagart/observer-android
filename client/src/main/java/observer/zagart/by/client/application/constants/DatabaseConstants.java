package observer.zagart.by.client.application.constants;

/**
 * Application constants related to database.
 *
 * @author zagart
 */

public interface DatabaseConstants {

    String TABLE_FIELDS_SEPARATOR = ", ";
    String NOT_NULL = " NOT NULL";
    String AUTOINCREMENT = " AUTOINCREMENT";

    String TRANSACTION_FAILED = "Transaction failed";
    String INCORRECT_TABLE_NAME = "Incorrect table name";
    String TABLE_NAME_NULL_EXCEPTION = "Table name should't be null";
    String INSERT_FAILED = "Failed to insert row";
    String DELETE_FAILED = "Deletion failed";
}
