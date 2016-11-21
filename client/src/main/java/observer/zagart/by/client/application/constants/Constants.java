package observer.zagart.by.client.application.constants;

/**
 * Class that contains constants for application's classes.
 *
 * @author zagart
 */
//TODO group constants
public class Constants {

    public static final String EMPTY_STRING = "";
    public static final String SPACE_STRING = " ";
    public static final String SELECT_FROM_STAND_WHERE_ID = "SELECT * FROM STAND WHERE id=?";
    public static final String SELECT_FROM_MODULE_WHERE_ID = "SELECT * FROM MODULE WHERE id=?";
    public static final String SELECT_ALL_MODULES = "SELECT * FROM MODULE;";
    public static final String SELECT_ALL_STANDS = "SELECT * FROM STAND;";
    public static final String CURRENT_ACCOUNT_NAME = "current_account_name";
    public static final byte EOF = -1;
    public static final String FAILED_TO_EXECUTE_CLOSING = "Failed to execute closing";
    public static final short READ_BUFFER_SIZE = 4096;
    public static final int TIMEOUT = 3000;
    public static final String PREF_FILE = "SharedFile";
    public static final String MODULE = "Module";
    public static final String STAND = "Stand";
    public static final String CONTENT = "content";
    public static final String AUTHORITY =
            "observer.zagart.by.client.mvp.models.repository.ObserverContentProvider";
    public static final String URI_SEPARATOR = "/";
    public static final String TRANSACTION_FAILED = "Transaction failed";
    public static final String INCORRECT_TABLE_NAME = "Incorrect table name";
    public static final String TOKEN = "token";
    public static final String STANDS_KEY = "stands";
    public static final String MODULES_KEY = "modules";
    public static final String REFLECTION = "reflection";
    public static final String TABLE_NAME_NULL_EXCEPTION = "Table name should't be null";
}
