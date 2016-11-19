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
