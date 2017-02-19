package eu.vytenis.hamcrest.sqlmatchers;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Vector;

import org.gibello.zql.ParseException;
import org.gibello.zql.ZStatement;
import org.gibello.zql.ZqlParser;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class SqlMatcher extends TypeSafeMatcher<String> {
	private final Class<? extends ZStatement> expectedType;
	private final String statementPrefix;

	public SqlMatcher(Class<? extends ZStatement> expectedType) {
		this(expectedType, "");
	}

	public SqlMatcher(Class<? extends ZStatement> expectedType, String sqlPrefix) {
		this.expectedType = expectedType;
		this.statementPrefix = sqlPrefix;
	}

	public void describeTo(Description description) {
		description.appendText("a valid SELECT statement");
	}

	@Override
	protected boolean matchesSafely(String sqlFragment) {
		List<?> statements;
		try {
			statements = parseStatements(createFullSqlStatement(sqlFragment));
		} catch (ParseException e) {
			return false;
		}
		ZStatement statement;
		try {
			statement = getExactlyOneStatement(statements);
		} catch (NotOneStatement e) {
			return false;
		}
		try {
			ensureTypeMatchesExpected(statement);
			return true;
		} catch (ClassCastException e) {
			return false;
		}

	}

	private String createFullSqlStatement(String sqlFragment) {
		return statementPrefix + sqlFragment + ";";
	}

	private List<?> parseStatements(String statement) throws ParseException {
		ZqlParser parser = new ZqlParser();
		parser.initParser(new ByteArrayInputStream(statement.getBytes()));
		Vector<?> statements = parser.readStatements();
		return statements;
	}

	private ZStatement getExactlyOneStatement(List<?> statements) throws NotOneStatement {
		if (statements.size() != 1)
			throw new NotOneStatement();
		return (ZStatement) statements.iterator().next();
	}

	private void ensureTypeMatchesExpected(ZStatement statement) {
		expectedType.cast(statement);
	}

	private static class NotOneStatement extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
