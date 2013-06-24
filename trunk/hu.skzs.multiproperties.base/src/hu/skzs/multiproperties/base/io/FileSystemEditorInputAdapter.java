package hu.skzs.multiproperties.base.io;

import java.io.IOException;

import org.eclipse.ui.ide.FileStoreEditorInput;

/**
 * A {@link FileStoreEditorInput} based implementation of {@link EditorInputAdapter}.
 * @author skzs
 */
class FileSystemEditorInputAdapter extends EditorInputAdapter<FileStoreEditorInput>
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return editorInput.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMarker(final String message) throws IOException
	{
		// Do nothing
	}
}
