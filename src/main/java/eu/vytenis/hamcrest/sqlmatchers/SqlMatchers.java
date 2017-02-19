package eu.vytenis.hamcrest.sqlmatchers;

import org.gibello.zql.ZDelete;
import org.gibello.zql.ZInsert;
import org.gibello.zql.ZQuery;
import org.gibello.zql.ZUpdate;

public class SqlMatchers {
	public static SqlMatcher isSelect() {
		return new SqlMatcher(ZQuery.class, "SELECT statement");
	}

	public static SqlMatcher isInsert() {
		return new SqlMatcher(ZInsert.class, "INSERT statement");
	}

	public static SqlMatcher isUpdate() {
		return new SqlMatcher(ZUpdate.class, "UPDATE statement");
	}

	public static SqlMatcher isDelete() {
		return new SqlMatcher(ZDelete.class, "DELETE statement");
	}

	public static SqlMatcher isWhere() {
		return new SqlMatcher(ZQuery.class, "WHERE clause", "select * from mytable where ");
	}
}
