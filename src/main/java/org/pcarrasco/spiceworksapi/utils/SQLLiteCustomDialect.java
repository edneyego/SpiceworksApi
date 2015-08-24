package org.pcarrasco.spiceworksapi.utils;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

/**
 * Class that defines custom SQL used by Hibernate for the SQLLite database
 */
public class SQLLiteCustomDialect extends H2Dialect {

    @Override
    public String getIdentitySelectString() {
        return "SELECT last_insert_rowid()";
    }
}
