package eu.vytenis.hamcrest.sqlmatchers;

import static eu.vytenis.hamcrest.sqlmatchers.SqlMatchers.isDelete;
import static eu.vytenis.hamcrest.sqlmatchers.SqlMatchers.isInsert;
import static eu.vytenis.hamcrest.sqlmatchers.SqlMatchers.isSelect;
import static eu.vytenis.hamcrest.sqlmatchers.SqlMatchers.isUpdate;
import static eu.vytenis.hamcrest.sqlmatchers.SqlMatchers.isWhere;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SqlMatcherTest {
	@Test
	public void formatsDescriptionByType() {
		assertDescription("a valid SELECT statement", isSelect());
		assertDescription("a valid INSERT statement", isInsert());
		assertDescription("a valid UPDATE statement", isUpdate());
		assertDescription("a valid DELETE statement", isDelete());
		assertDescription("a valid WHERE clause", isWhere());
	}

	private void assertDescription(String expectedDescription, SqlMatcher matcher) {
		assertEquals(expectedDescription, matcher.createDescription());
	}
}
