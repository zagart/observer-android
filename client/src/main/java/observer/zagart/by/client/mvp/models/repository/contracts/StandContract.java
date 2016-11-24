package observer.zagart.by.client.mvp.models.repository.contracts;

import observer.zagart.by.client.mvp.models.repository.annotations.Table;
import observer.zagart.by.client.mvp.models.repository.annotations.dbInteger;
import observer.zagart.by.client.mvp.models.repository.annotations.dbNotNull;
import observer.zagart.by.client.mvp.models.repository.annotations.dbString;

/**
 * Database contract for Stand model.
 *
 * @author zagart
 */
@Table(name = "STAND")
public class StandContract {

    public static final String SELECT_FROM_STAND_WHERE_ID = "SELECT * FROM STAND WHERE id=?";
    public static final String SELECT_ALL_STANDS = "SELECT * FROM STAND;";

    @dbInteger
    @dbNotNull
    public static final String ID = "id";
    @dbString
    @dbNotNull
    public static final String NUMBER = "number";
    @dbNotNull
    @dbString
    public static final String DESCRIPTION = "description";
}
