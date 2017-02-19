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
	private final static List<Object[]> testCases = new ArrayList<Object[]>();
	private final String sqlFragment;
	private final MatcherType matcherType;
	private final ExpectedResult expectedResult;

	public SqlMatcherTest(String sqlFragment, MatcherType matcherType, ExpectedResult expectedResult) {
		this.sqlFragment = sqlFragment;
		this.matcherType = matcherType;
		this.expectedResult = expectedResult;
	}

	@Parameters
	public static List<Object[]> testCases() {
		return testCases;
	}

	static {
		addCase("select * from dual", Select, Pass);
		addCase("select *, from dual", Select, Fail);
		addCase("insert into mytable values(1)", Insert, Pass);
		addCase("insert into table", Insert, ExpectedResult.Fail);
		addCase("update mytable set a = b", Update, Pass);
		addCase("update mytable", Update, Fail);
		addCase("delete from mytable", Delete, Pass);
		addCase("delete from mytable where", Delete, Fail);
	}

	private static void addCase(String sql, MatcherType matcherType, ExpectedResult expectedResult) {
		testCases.add(new Object[] { sql, matcherType, expectedResult });
	}

	@Test
	public void sqlFragmentValidityMatchesTypeAndExpectedResult() {
		assertThat(sqlFragment, expectedResult.matcher(matcherType.matcher()));
	}
}
