package hu.skzs.multiproperties.ui.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * The <code>Perspective</code> is used to initialize the MultiProperties perspective layout.
 * It only shows the outline view on the right side.
 * @author sallai
 */
public class Perspective implements IPerspectiveFactory
{

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	public void createInitialLayout(final IPageLayout layout)
	{
		final String editorArea = layout.getEditorArea();
		final IFolderLayout left = layout.createFolder("right", IPageLayout.RIGHT, (float) 0.80, editorArea); //$NON-NLS-1$
		left.addView(IPageLayout.ID_OUTLINE);
	}
}
