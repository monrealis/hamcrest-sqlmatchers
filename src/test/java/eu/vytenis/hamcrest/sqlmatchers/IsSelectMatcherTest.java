package eu.vytenis.hamcrest.sqlmatchers;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class IsSelectMatcherTest {
	@Test
	public void matchesValid() {
		assertThat("select * from dual", isSelect());
	}

	@Test
	public void doesNotMatchInvalid() {
		assertThat("select *, from dual", not(isSelect()));
	}

	private IsSelectMatcher isSelect() {
		return new IsSelectMatcher();
	}
}
