package observer.zagart.by.client.repository.entities.contracts;

import observer.zagart.by.client.repository.entities.annotations.Table;
import observer.zagart.by.client.repository.entities.annotations.dbInteger;
import observer.zagart.by.client.repository.entities.annotations.dbNotNull;
import observer.zagart.by.client.repository.entities.annotations.dbString;

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
