package hu.skzs.multiproperties.base.io;

import hu.skzs.multiproperties.base.api.HandlerException;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ide.FileStoreEditorInput;

/**
 * Factory for constructing {@link EditorInputAdapter} instances.
 * @author skzs
 */
public class EditorInputAdapterFactory
{

	private static EditorInputAdapterFactory instance;

	/**
	 * Private constructor
	 */
	private EditorInputAdapterFactory()
	{
	}

	/**
	 * Returns the singleton instance of this factory.
	 * @return the singleton instance of this factory
	 */
	public static EditorInputAdapterFactory getInstance()
	{
		if (instance != null)
			return instance;
		synchronized (EditorInputAdapterFactory.class)
		{
			if (instance == null)
				instance = new EditorInputAdapterFactory();
			return instance;
		}
	}

	/**
	 * Returns a new {@link EditorInputAdapter} instance based on the given {@link IEditorInput} instance.
	 * @param editorInput the given editor input
	 * @return a new {@link EditorInputAdapter} instance
	 * @throws HandlerException 
	 */
	public EditorInputAdapter<?> getEditorInputAdapter(final IEditorInput editorInput) throws HandlerException
	{
		if (editorInput instanceof IFileEditorInput)
		{
			final IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
			final WorkspaceEditorInputAdapter fileEditorInputAdapter = new WorkspaceEditorInputAdapter();
			final IFile file = fileEditorInput.getFile();
			final WorkspaceFileContentAccessor fileContentAccessor = new WorkspaceFileContentAccessor(file);

			fileEditorInputAdapter.setEditorInput(fileEditorInput);
			fileEditorInputAdapter.setFileContenAccessor(fileContentAccessor);

			return fileEditorInputAdapter;
		}
		else if (editorInput instanceof FileStoreEditorInput)
		{
			final FileStoreEditorInput fileStoreEditorInput = (FileStoreEditorInput) editorInput;
			final FileSystemEditorInputAdapter fileStoreEditorInputAdapter = new FileSystemEditorInputAdapter();
			final File file = new File(fileStoreEditorInput.getURI());
			final FileSystemFileContentAccessor fileContentAccessor = new FileSystemFileContentAccessor(file);

			fileStoreEditorInputAdapter.setEditorInput(fileStoreEditorInput);
			fileStoreEditorInputAdapter.setFileContenAccessor(fileContentAccessor);

			return fileStoreEditorInputAdapter;
		}
		else
		{
			throw new IllegalArgumentException("Unsupported editor input: " + editorInput); //$NON-NLS-1$
		}
	}
}
