package hu.skzs.multiproperties.base.io;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IFileEditorInput;

/**
 * A {@link IFileEditorInput} based implementation of
 * {@link EditorInputAdapter}.
 * 
 * @author skzs
 */
class WorkspaceEditorInputAdapter extends EditorInputAdapter<IFileEditorInput> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return editorInput.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMarker(final String message) throws IOException {
		try {
			final IFile file = editorInput.getFile();
			file.deleteMarkers(null, true, IResource.DEPTH_ZERO);
			if (message != null) {
				IMarker marker = file.findMarker(0);
				if (marker == null) {
					marker = file.createMarker(IMarker.PROBLEM);
					marker.setAttribute(IMarker.MESSAGE, message);
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
				}
			}
		} catch (final CoreException e) {
			throw new IOException("Unable to set the marker", e); //$NON-NLS-1$
		}
	}

}
