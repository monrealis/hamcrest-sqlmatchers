package eu.vytenis.hamcrest.sqlmatchers;

import org.gibello.zql.ZDelete;
import org.gibello.zql.ZInsert;
import org.gibello.zql.ZQuery;
import org.gibello.zql.ZUpdate;

public class SqlMatchers {
	public static SqlMatcher isSelect() {
		return new SqlMatcher(ZQuery.class);
	}

	public static SqlMatcher isInsert() {
		return new SqlMatcher(ZInsert.class);
	}

	public static SqlMatcher isUpdate() {
		return new SqlMatcher(ZUpdate.class);
	}

	public static SqlMatcher isDelete() {
		return new SqlMatcher(ZDelete.class);
	}
}
