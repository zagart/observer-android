package observer.zagart.by.client.mvp.models.repository;

import observer.zagart.by.client.application.utils.ReflectionUtil;

/**
 * Class for fast extracting SQL-queries.
 *
 * @author zagart
 */

public class QueryBuilder {

    private static final String TABLE_CREATE_TEMPLATE = "CREATE TABLE IF NOT EXISTS %s (%s);";
    private static final String QUESTION_MARK = "?";
    private static final String WHERE = " WHERE ";
    private static final String EQUALS = "=";
    private static final String DEFAULT_ID_CONSTANT = "id";
    private static final int EMPTY_BUILDER = 0;
    private static final String ALL_FIELDS = " *";
    private static final String FROM = " FROM ";
    private static final String END = ";";
    private static final String SELECT = "SELECT";
    final private String mTableName;
    final private StringBuilder mQuery;

    public QueryBuilder(final Class pClass) {
        mQuery = new StringBuilder();
        mTableName = ReflectionUtil.getTableName(pClass);
    }

    public String createTable() {
        return TABLE_CREATE_TEMPLATE;
    }

    public String selectAll() {
        mQuery.setLength(EMPTY_BUILDER);
        mQuery.append(SELECT).append(ALL_FIELDS).append(FROM).append(mTableName).append(END);
        return mQuery.toString();
    }

    public String selectById() {
        return selectByField(DEFAULT_ID_CONSTANT);
    }

    private String selectByField(final String pField) {
        mQuery.setLength(EMPTY_BUILDER);
        mQuery
                .append(SELECT).append(ALL_FIELDS).append(FROM).append(mTableName)
                .append(WHERE).append(pField).append(EQUALS).append(QUESTION_MARK);
        return mQuery.toString();
    }
}
