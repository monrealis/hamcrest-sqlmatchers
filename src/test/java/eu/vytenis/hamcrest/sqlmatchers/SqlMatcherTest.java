package eu.vytenis.hamcrest.sqlmatchers;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.gibello.zql.ZDelete;
import org.gibello.zql.ZInsert;
import org.gibello.zql.ZQuery;
import org.gibello.zql.ZUpdate;
import org.junit.Test;

public class SqlMatcherTest {
	@Test
	public void matchesValidSelect() {
		assertThat("select * from dual", isSelect());
	}

	@Test
	public void doesNotMatchInvalidSelect() {
		assertThat("select *, from dual", not(isSelect()));
	}

	@Test
	public void matchesValidInsert() {
		assertThat("insert into mytable values(1)", isInsert());
	}

	@Test
	public void doesNotMatchInvalidInsert() {
		assertThat("insert into table", not(isInsert()));
	}

	@Test
	public void matchesValidUpdate() {
		assertThat("update mytable set a = b", isUpdate());
	}

	@Test
	public void doesNotMatchInvalidUpdate() {
		assertThat("update mytable", not(isUpdate()));
	}
	
	@Test
	public void matchesValidDelete() {
		assertThat("delete from mytable", isDelete());
	}

	@Test
	public void doesNotMatchInvalidDelete() {
		assertThat("delete from mytable where", not(isDelete()));
	}

	private SqlMatcher isSelect() {
		return new SqlMatcher(ZQuery.class);
	}

	private SqlMatcher isInsert() {
		return new SqlMatcher(ZInsert.class);
	}

	private SqlMatcher isUpdate() {
		return new SqlMatcher(ZUpdate.class);
	}

	private SqlMatcher isDelete() {
		return new SqlMatcher(ZDelete.class);
	}
}
