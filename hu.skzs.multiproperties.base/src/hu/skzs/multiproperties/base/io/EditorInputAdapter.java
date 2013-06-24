package hu.skzs.multiproperties.base.io;

import java.io.IOException;

import org.eclipse.ui.IEditorInput;

/**
 * The {@link EditorInputAdapter} and its implementations are providing a generic interface for the
 * different type of editor inputs.
 * @author skzs
 * @param <T> a sub-type of {@link IEditorInput}
 */
public abstract class EditorInputAdapter<T extends IEditorInput>
{

	protected T editorInput;
	protected FileContentAccessor<?> fileContentAccessor;

	/**
	 * Sets the underlying editor input
	 * @param editorInput the given editor input
	 */
	final void setEditorInput(final T editorInput)
	{
		this.editorInput = editorInput;
	}

	/**
	 * Sets the associated {@link FileContentAccessor} instance
	 * @param fileContentAccessor
	 */
	final void setFileContenAccessor(final FileContentAccessor<?> fileContentAccessor)
	{
		this.fileContentAccessor = fileContentAccessor;
	}

	/**
	 * Returns the name of the editor input.
	 * <p>This is basically the filename.</p>
	 * @return the name of the editor input
	 */
	public abstract String getName();

	/**
	 * Returns the {@link FileContentAccessor} instance associated to this {@link EditorInputAdapter}
	 * @return the {@link FileContentAccessor} instance
	 */
	public FileContentAccessor<?> getFileContentAccessor()
	{
		return fileContentAccessor;
	}

	/**
	 * Sets a new marker or updates the already existing marker with the given message.
	 * <p>If the given message is <code>null</code>, then the previously set marker will be removed
	 * if there is any.</p>
	 * @param message the given message
	 * @throws IOException
	 */
	public abstract void setMarker(String message) throws IOException;
}
