package hu.skzs.multiproperties.ui.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory
{

	public void createInitialLayout(final IPageLayout layout)
	{
		final String editorArea = layout.getEditorArea();
		final IFolderLayout left = layout.createFolder("right", IPageLayout.RIGHT, (float) 0.80, editorArea);
		left.addView(IPageLayout.ID_OUTLINE);
	}
}
