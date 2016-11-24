package observer.zagart.by.client.application.constants;

/**
 * Application constants related to database.
 *
 * @author zagart
 */

public class DatabaseConstants {

    //table creating
    public static final String TABLE_FIELDS_SEPARATOR = ", ";
    public static final String NOT_NULL = " NOT NULL";
    public static final String AUTOINCREMENT = " AUTOINCREMENT";

    //exception messages
    public static final String TRANSACTION_FAILED = "Transaction failed";
    public static final String INCORRECT_TABLE_NAME = "Incorrect table name";
    public static final String TABLE_NAME_NULL_EXCEPTION = "Table name should't be null";
    public static final String INSERT_FAILED = "Failed to insert row";
    public static final String DELETE_FAILED = "Deletion failed";
}
