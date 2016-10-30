package by.grodno.zagart.observer.observerandroid.db;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import by.grodno.zagart.observer.observerandroid.db.model.User;

/**
 * Test class for {@link DbHelper}
 *
 * @author zagart
 */
@RunWith(JUnit4.class)
public class DbHelperTest {
    private static final String CREATE_TABLE_TEST_TEMPLATE =
            "CREATE TABLE IF NOT EXISTS USER (id INTEGER, title TEXT, date BIGINT);";

    @Test
    public void getTableCreateQueryTest() {
        String query = DbHelper.getTableCreateQuery(User.class);
        Assert.assertEquals(CREATE_TABLE_TEST_TEMPLATE, query);
    }
}
