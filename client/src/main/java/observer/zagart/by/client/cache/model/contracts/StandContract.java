package observer.zagart.by.client.cache.model.contracts;
import observer.zagart.by.client.cache.model.annotations.dbId;
import observer.zagart.by.client.cache.model.annotations.dbNotNull;
import observer.zagart.by.client.cache.model.annotations.Table;
import observer.zagart.by.client.cache.model.annotations.dbInteger;
import observer.zagart.by.client.cache.model.annotations.dbString;

/**
 * Database contract for Stand model.
 *
 * @author zagart
 */
@Table(name = "STAND")
public class StandContract {
    @dbInteger
    @dbId
    @dbNotNull
    public static final String ID = "id";
    @dbString
    @dbNotNull
    public static final String NUMBER = "number";
    @dbNotNull
    @dbString
    public static final String DESCRIPTION = "description";
}
