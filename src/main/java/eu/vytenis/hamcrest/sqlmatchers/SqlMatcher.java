package eu.vytenis.hamcrest.sqlmatchers;

import java.io.ByteArrayInputStream;
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
		try {
			parseQuery(createFullSqlStatement(sqlFragment));
			return true;
		} catch (ParseException e) {
			return false;
		} catch (ClassCastException e) {
			return false;
		}
	}

	private String createFullSqlStatement(String sqlFragment) {
		return statementPrefix + sqlFragment + ";";
	}

	private void parseQuery(String statement) throws ParseException {
		ZqlParser parser = new ZqlParser();
		parser.initParser(new ByteArrayInputStream(statement.getBytes()));
		Vector<?> statements = parser.readStatements();
		expectedType.cast(statements.get(0));
	}
}
