package eu.vytenis.hamcrest.sqlmatchers;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

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
}
