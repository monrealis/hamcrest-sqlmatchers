package eu.vytenis.hamcrest.sqlmatchers;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import org.gibello.zql.ParseException;
import org.gibello.zql.ZQuery;
import org.gibello.zql.ZqlParser;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class IsSelectMatcher extends TypeSafeMatcher<String> {
	public void describeTo(Description description) {
		description.appendText("a valid SELECT statement");
	}

	@Override
	protected boolean matchesSafely(String statement) {
		try {
			parseQuery(statement + ";");
			return true;
		} catch (ParseException e) {
			return false;
		}

	}

	private ZQuery parseQuery(String statement) throws ParseException {
		ZqlParser parser = new ZqlParser();
		parser.initParser(new ByteArrayInputStream(statement.getBytes()));
		Vector<?> statements = parser.readStatements();
		return (ZQuery) statements.get(0);
	}
}
