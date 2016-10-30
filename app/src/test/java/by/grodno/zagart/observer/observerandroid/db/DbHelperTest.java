package by.grodno.zagart.observer.observerandroid.db;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import by.grodno.zagart.observer.observerandroid.cache.helper.DbHelper;
import by.grodno.zagart.observer.observerandroid.cache.model.User;

/**
 * Test class for {@link DbHelper}
 *
 * @author zagart
 */
@RunWith(JUnit4.class)
public class DbHelperTest {
    private static final String CREATE_TABLE_TEST_TEMPLATE =
            "CREATE TABLE IF NOT EXISTS USER (id BIGINT PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "login TEXT NOT NULL, " +
                    "timestamp_of_registration BIGINT NOT NULL, " +
                    "token TEXT NOT NULL, " +
                    "timestamp_of_last_activity BIGINT NOT NULL);";

    @Test
    public void getTableCreateQueryTest() {
        Assert.assertEquals(CREATE_TABLE_TEST_TEMPLATE, DbHelper.getTableCreateQuery(
                User.UserContract.class
        ));
    }

}
