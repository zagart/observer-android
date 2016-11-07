package observer.zagart.by.client.cache.model.contracts;
import observer.zagart.by.client.cache.model.annotations.Id;
import observer.zagart.by.client.cache.model.annotations.NotNull;
import observer.zagart.by.client.cache.model.annotations.Table;
import observer.zagart.by.client.cache.model.annotations.dbInteger;
import observer.zagart.by.client.cache.model.annotations.dbString;

/**
 * Database contract for Module model.
 *
 * @author zagart
 */
@Table(name = "MODULE")
public class ModuleContract {
    @Id
    @dbInteger
    @NotNull
    public static final String ID = "id";
    @dbString
    @NotNull
    public static final String NAME = "name";
    @NotNull
    @dbInteger
    public static final String STAND_ID = "stand_id";
    @NotNull
    @dbString
    public static final String STATUS = "status";
    @NotNull
    @dbInteger
    public static final String STATUS_CHANGE_DATE = "status_change_date";
    @NotNull
    @dbString
    public static final String VALUE = "value";
}
