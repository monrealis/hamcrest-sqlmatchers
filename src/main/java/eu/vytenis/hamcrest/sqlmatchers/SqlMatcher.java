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
	private final String name;

	public SqlMatcher(Class<? extends ZStatement> expectedType, String name) {
		this(expectedType, name, "");
	}

	public SqlMatcher(Class<? extends ZStatement> expectedType, String name, String sqlPrefix) {
		this.expectedType = expectedType;
		this.name = name;
		this.statementPrefix = sqlPrefix;
	}

	public void describeTo(Description description) {
		description.appendText(createDescription());
	}

	protected String createDescription() {
		return String.format("a valid %s", name);
	}

	@Override
	protected boolean matchesSafely(String sqlFragment) {
		try {
			tryMatch(sqlFragment);
			return true;
		} catch (NotMatched e) {
			return false;
		}
	}

	private void tryMatch(String sqlFragment) throws NotMatched {
		List<?> statements = parseStatements(createFullSqlStatement(sqlFragment));
		ZStatement statement = getExactlyOneStatement(statements);
		ensureTypeMatchesExpected(statement);
	}

	private String createFullSqlStatement(String sqlFragment) {
		return statementPrefix + sqlFragment + ";";
	}

	private List<?> parseStatements(String statement) throws NotMatched {
		try {
			return tryParseStatements(statement);
		} catch (ParseException e) {
			throw new NotMatched(e);
		}
	}

	private List<?> tryParseStatements(String statement) throws ParseException {
		ZqlParser parser = new ZqlParser();
		parser.initParser(new ByteArrayInputStream(statement.getBytes()));
		Vector<?> statements = parser.readStatements();
		return statements;
	}

	private ZStatement getExactlyOneStatement(List<?> statements) throws NotMatched {
		if (statements.size() != 1)
			throw new NotMatched(new NotOneStatement());
		return (ZStatement) statements.iterator().next();
	}

	private void ensureTypeMatchesExpected(ZStatement statement) throws NotMatched {
		try {
			expectedType.cast(statement);
		} catch (ClassCastException e) {
			throw new NotMatched(e);
		}
	}

	public static class NotMatched extends Exception {
		private static final long serialVersionUID = 1L;

		public NotMatched(Exception cause) {
			super(cause);
		}
	}

	private static class NotOneStatement extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
