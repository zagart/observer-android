package observer.zagart.by.client.repository.model.contracts;
import observer.zagart.by.client.repository.model.annotations.Table;
import observer.zagart.by.client.repository.model.annotations.dbId;
import observer.zagart.by.client.repository.model.annotations.dbInteger;
import observer.zagart.by.client.repository.model.annotations.dbNotNull;
import observer.zagart.by.client.repository.model.annotations.dbString;

/**
 * Database contract for Module model.
 *
 * @author zagart
 */
@Table(name = "MODULE")
public class ModuleContract {
    @dbId
    @dbInteger
    @dbNotNull
    public static final String ID = "id";
    @dbString
    @dbNotNull
    public static final String NAME = "name";
    @dbNotNull
    @dbInteger
    public static final String STAND_ID = "stand_id";
    @dbNotNull
    @dbString
    public static final String STATUS = "status";
    @dbNotNull
    @dbInteger
    public static final String STATUS_CHANGE_DATE = "status_change_date";
    @dbNotNull
    @dbString
    public static final String VALUE = "value";
}
