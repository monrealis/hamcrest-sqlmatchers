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

	@Parameters
	public static List<Object[]> parameters() {
		List<Object[]> r = new ArrayList<Object[]>();
		r.add(new Object[] { "select * from dual", MatcherType.Select, ExpectedResult.Pass });
		r.add(new Object[] { "select *, from dual", MatcherType.Select, ExpectedResult.Fail });
		r.add(new Object[] { "insert into mytable values(1)", MatcherType.Insert, ExpectedResult.Pass });
		r.add(new Object[] { "insert into table", MatcherType.Insert, ExpectedResult.Fail });
		r.add(new Object[] { "update mytable set a = b", MatcherType.Update, ExpectedResult.Pass });
		r.add(new Object[] { "update mytable", MatcherType.Update, ExpectedResult.Fail });
		r.add(new Object[] { "delete from mytable", MatcherType.Delete, ExpectedResult.Pass });
		r.add(new Object[] { "delete from mytable where", MatcherType.Delete, ExpectedResult.Fail });
		return r;
	}

	@Test
	public void sqlFragmentValidityMatchesTypeAndExpectedResult() {
		assertThat(sqlFragment, expectedResult.matcher(matcherType.matcher()));
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
}
