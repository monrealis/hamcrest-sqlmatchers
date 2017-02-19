package eu.vytenis.hamcrest.sqlmatchers;

import static org.hamcrest.CoreMatchers.not;

import org.hamcrest.Matcher;

enum ExpectedResult {
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