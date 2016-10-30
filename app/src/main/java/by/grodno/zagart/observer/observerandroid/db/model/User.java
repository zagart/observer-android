package by.grodno.zagart.observer.observerandroid.db.model;
import by.grodno.zagart.observer.observerandroid.db.annotations.Table;
import by.grodno.zagart.observer.observerandroid.db.annotations.dbInteger;
import by.grodno.zagart.observer.observerandroid.db.annotations.dbLong;
import by.grodno.zagart.observer.observerandroid.db.annotations.dbString;

/**
 * User model.
 *
 * @author zagart
 */
@Table(name = "USER")
public class User {
    @dbInteger
    public static final String ID = "id";
    @dbString
    public static final String TITLE = "title";
    @dbLong
    public static final String DATE = "date";
}
