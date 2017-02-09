package net.draconia.ui.subcomponent;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public abstract class SubComponentPanelObserver<ModelType extends Observable, SubComponentType extends Observable> implements Observer, Serializable
{
	private static final long serialVersionUID = 5922743807963118753L;
	
	private SubComponentPanel<ModelType, SubComponentType> mPnlSubComponent;
	
	public SubComponentPanelObserver(final SubComponentPanel<ModelType, SubComponentType> pnlSubComponent)
	{
		setPanel(pnlSubComponent);
	}
	
	protected SubComponentPanel<ModelType, SubComponentType> getPanel()
	{
		return(mPnlSubComponent);
	}
	
	protected void setPanel(final SubComponentPanel<ModelType, SubComponentType> pnlSubComponent)
	{
		mPnlSubComponent = pnlSubComponent;
	}
}