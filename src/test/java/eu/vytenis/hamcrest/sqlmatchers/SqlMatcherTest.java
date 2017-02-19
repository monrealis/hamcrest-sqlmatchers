package eu.vytenis.hamcrest.sqlmatchers;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.gibello.zql.ZDelete;
import org.gibello.zql.ZInsert;
import org.gibello.zql.ZQuery;
import org.gibello.zql.ZUpdate;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SqlMatcherTest {
	private final String sqlFragment;
	private final MatcherType matcherType;
	private final ExpectedResult expectedResult;

	public SqlMatcherTest(String sqlFragment, MatcherType matcherType, ExpectedResult expectedResult) {
		this.sqlFragment = sqlFragment;
		this.matcherType = matcherType;
		this.expectedResult = expectedResult;
	}

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

	public enum MatcherType {
		Select {
			@Override
			public SqlMatcher matcher() {
				return isSelect();
			}
		},
		Insert {
			@Override
			public SqlMatcher matcher() {
				return isInsert();
			}
		},
		Update {
			@Override
			public SqlMatcher matcher() {
				return isUpdate();
			}
		},
		Delete {
			@Override
			public SqlMatcher matcher() {
				return isDelete();
			}
		};

		public abstract SqlMatcher matcher();
	}

	public enum ExpectedResult {
		Pass {
			@Override
			public <T> Matcher<T> matcher(Matcher<T> matcher) {
				return matcher;
			}
		},
		Fail {
			@Override
			public <T> Matcher<T> matcher(Matcher<T> matcher) {
				return not(matcher);
			}
		};
		public abstract <T> Matcher<T> matcher(Matcher<T> matcher);

	}

	private static SqlMatcher isSelect() {
		return new SqlMatcher(ZQuery.class);
	}

	private static SqlMatcher isInsert() {
		return new SqlMatcher(ZInsert.class);
	}

	private static SqlMatcher isUpdate() {
		return new SqlMatcher(ZUpdate.class);
	}

	private static SqlMatcher isDelete() {
		return new SqlMatcher(ZDelete.class);
	}

	@Parameters
	public static List<Object[]> parameters() {
		List<Object[]> r = new ArrayList<Object[]>();
		r.add(new Object[] { "select * from dual", MatcherType.Select, ExpectedResult.Pass });
		return r;
	}

}
