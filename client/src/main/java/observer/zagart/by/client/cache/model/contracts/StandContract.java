package observer.zagart.by.client.cache.model.contracts;
import observer.zagart.by.client.cache.model.annotations.Id;
import observer.zagart.by.client.cache.model.annotations.NotNull;
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
    @Id
    @NotNull
    public static final String ID = "id";
    @dbString
    @NotNull
    public static final String NUMBER = "number";
    @NotNull
    @dbString
    public static final String DESCRIPTION = "description";
}
