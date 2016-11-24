package observer.zagart.by.client.application.constants;

/**
 * Application constants related to database.
 *
 * @author zagart
 */

public class DatabaseConstants {

    //queries
    public static final String SELECT_FROM_STAND_WHERE_ID = "SELECT * FROM STAND WHERE id=?";
    public static final String SELECT_FROM_MODULE_WHERE_ID = "SELECT * FROM MODULE WHERE id=?";
    public static final String SELECT_ALL_MODULES = "SELECT * FROM MODULE;";
    public static final String SELECT_ALL_STANDS = "SELECT * FROM STAND;";
    public static final String TABLE_CREATE_TEMPLATE = "CREATE TABLE IF NOT EXISTS %s (%s);";

    //secondary
    public static final String TABLE_FIELDS_SEPARATOR = ", ";
    public static final String NOT_NULL = " NOT NULL";
    public static final String AUTOINCREMENT = " AUTOINCREMENT";

    //messages
    public static final String TRANSACTION_FAILED = "Transaction failed";
    public static final String INCORRECT_TABLE_NAME = "Incorrect table name";
    public static final String TABLE_NAME_NULL_EXCEPTION = "Table name should't be null";
    public static final String INSERT_FAILED = "Failed to insert row";
    public static final String DELETE_FAILED = "Deletion failed";
}
