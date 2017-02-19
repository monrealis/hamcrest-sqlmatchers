package eu.vytenis.hamcrest.sqlmatchers;

import static eu.vytenis.hamcrest.sqlmatchers.ExpectedResult.Fail;
import static eu.vytenis.hamcrest.sqlmatchers.ExpectedResult.Pass;
import static eu.vytenis.hamcrest.sqlmatchers.MatcherType.Delete;
import static eu.vytenis.hamcrest.sqlmatchers.MatcherType.Insert;
import static eu.vytenis.hamcrest.sqlmatchers.MatcherType.Select;
import static eu.vytenis.hamcrest.sqlmatchers.MatcherType.Update;
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
		r.add(new Object[] { "select * from dual", Select, Pass });
		r.add(new Object[] { "select *, from dual", Select, Fail });
		r.add(new Object[] { "insert into mytable values(1)", Insert, Pass });
		r.add(new Object[] { "insert into table", Insert, ExpectedResult.Fail });
		r.add(new Object[] { "update mytable set a = b", Update, Pass });
		r.add(new Object[] { "update mytable", Update, Fail });
		r.add(new Object[] { "delete from mytable", Delete, Pass });
		r.add(new Object[] { "delete from mytable where", Delete, Fail });
		return r;
	}

	@Test
	public void sqlFragmentValidityMatchesTypeAndExpectedResult() {
		assertThat(sqlFragment, expectedResult.matcher(matcherType.matcher()));
	}
}
