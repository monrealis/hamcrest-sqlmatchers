package eu.vytenis.hamcrest.sqlmatchers;

import static eu.vytenis.hamcrest.sqlmatchers.ExpectedResult.Fail;
import static eu.vytenis.hamcrest.sqlmatchers.ExpectedResult.Pass;
import static eu.vytenis.hamcrest.sqlmatchers.MatcherType.Delete;
import static eu.vytenis.hamcrest.sqlmatchers.MatcherType.Insert;
import static eu.vytenis.hamcrest.sqlmatchers.MatcherType.Select;
import static eu.vytenis.hamcrest.sqlmatchers.MatcherType.Update;
import static eu.vytenis.hamcrest.sqlmatchers.MatcherType.Where;
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

	public SqlMatcherTest(MatcherType matcherType, ExpectedResult expectedResult, String sqlFragment) {
		this.sqlFragment = sqlFragment;
		this.matcherType = matcherType;
		this.expectedResult = expectedResult;
	}

	@Parameters
	public static List<Object[]> testCases() {
		return testCases;
	}

	static {
		addCase(Select, Pass, "select * from dual");
		addCase(Select, Fail, "select *, from dual");
		addCase(Insert, Pass, "insert into mytable values(1)");
		addCase(Insert, Fail, "insert into table");
		addCase(Update, Pass, "update mytable set a = b");
		addCase(Update, Fail, "update mytable");
		addCase(Delete, Pass, "delete from mytable");
		addCase(Delete, Fail, "delete from mytable where");
		addCase(Where, Pass, "1 > 0");
		addCase(Where, Pass, "exists (select 1 from mytable t where 1 > 0)");
		addCase(Where, Fail, "exists (select 1 from mytable t where 1 > 0");
		addCase(Select, Pass, "select * from dual;");
		addCase(Select, Pass, " select * from dual ; ");
		addCase(Select, Fail, "select * from dual;select * from dual;");
		addCase(Select, Fail, "insert into mytable values(1)");
	}

	private static void addCase(MatcherType matcherType, ExpectedResult expectedResult, String sqlFragment) {
		testCases.add(new Object[] { matcherType, expectedResult, sqlFragment });
	}

	@Test
	public void sqlFragmentValidityMatchesTypeAndExpectedResult() {
		assertThat(sqlFragment, expectedResult.matcher(matcherType.matcher()));
	}
}
