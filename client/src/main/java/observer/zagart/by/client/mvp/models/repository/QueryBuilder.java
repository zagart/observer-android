package observer.zagart.by.client.mvp.models.repository;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.application.utils.ReflectionUtil;

/**
 * @author zagart
 */

public class QueryBuilder {

    public <Contract> Selection select(final Class<Contract> pClass) {
        final String tableName = ReflectionUtil.getTableName(pClass);
        return new Selection(tableName);
    }

    private class Selection {

        private static final String ROW = " *";
        private static final String FROM = " FROM ";
        private static final String END = ";";
        private static final String SELECT = "SELECT";

        private StringBuilder mQuery = new StringBuilder().append(SELECT);
        private String mTableName;
        private List<String> mFields = new ArrayList<>();

        private Selection(String pTableName) {
            mTableName = pTableName;
        }

        public Selection allFields() {
            mQuery.append(ROW).append(FROM).append(mTableName);
            return this;
        }

        public String allRecords() {
            return mQuery.append(END).toString();
        }
    }

    private class Deletion {

    }

    private class Insertion {

    }
}
