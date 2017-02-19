package eu.vytenis.hamcrest.sqlmatchers;

enum MatcherType {
	Select {
		@Override
		public SqlMatcher matcher() {
			return SqlMatchers.isSelect();
		}
	},
	Insert {
		@Override
		public SqlMatcher matcher() {
			return SqlMatchers.isInsert();
		}
	},
	Update {
		@Override
		public SqlMatcher matcher() {
			return SqlMatchers.isUpdate();
		}
	},
	Delete {
		@Override
		public SqlMatcher matcher() {
			return SqlMatchers.isDelete();
		}
	},
	Where {
		@Override
		public SqlMatcher matcher() {
			return SqlMatchers.isWhere();
		}
	}
	;

	public abstract SqlMatcher matcher();
}