package hu.skzs.multiproperties.base.model;

public enum ImportMode
{
	/**
	 * <strong>Structural import:</strong> imports the {@link PropertyRecord}s together with {@link CommentRecord}s and
	 * {@link EmptyRecord}s, but for the {@link PropertyRecord}s no value is imported.
	 * In this case the <code>column</code> parameter is <code>null</code>.
	 */
	STRUCTURAL,

	/**
	 * <strong>Key-value import:</strong> imports only the {@link PropertyRecord}s.
	 * The previously non-existing {@link PropertyRecord}s are added to the end of table. The <code>column</code>
	 * parameters holds the given {@link Column} where the values should be associated to. If the returned list
	 * contains {@link CommentRecord}s or {@link EmptyRecord}s, they will be skipped by the importing framework.
	 */
	KEY_VALUE,

	/**
	 * <strong>Value import:</strong> imports only the {@link PropertyRecord}s.
	 * The previously non-existing {@link PropertyRecord}s are <strong>not</strong> added to the table.
	 * The <code>column</code> parameters holds the given {@link Column} where the values should be associated to.
	 * If the returned list contains {@link CommentRecord}s or {@link EmptyRecord}s, they will be skipped by
	 * the importing framework.
	 */
	VALUE;
}
