package by.grodno.zagart.observer.observerandroid.db;
import junit.framework.Assert;

import org.testng.annotations.Test;

import by.grodno.zagart.observer.observerandroid.cache.model.contract.User;

/**
 * @author zagart
 */
public class DbTest {
    private String mSql = DbHelper.getTableCreateQuery(User.class);

    @Test
    public void test() {
        Assert.assertNotNull(mSql);
        Assert.assertEquals("CREATE TABLE (id INTEGER, title TEXT, date BIGINT);", mSql);
    }
}
