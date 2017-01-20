package net.draconia.ui.listdetails.observers;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

import net.draconia.ui.listdetails.model.ListTableModel;

public class DialogModelTableModelObserver<ModelType extends Observable> implements Observer, Serializable
{
	private static final long serialVersionUID = 1602184610283661428L;
	
	private JTable mTblList;
	
	public DialogModelTableModelObserver(final JTable tblList)
	{
		setTable(tblList);
	}
	
	protected JTable getTable()
	{
		return(mTblList);
	}
	
	protected void setTable(final JTable tblList)
	{
		mTblList = tblList;
	}
	
	@SuppressWarnings("unchecked")
	public void update(final Observable objObservable, final Object objArgument)
	{
		((ListTableModel<ModelType>)(getTable().getModel())).fireTableChanged();
		
		getTable().repaint(250);
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				for(MouseListener objMouseListener : getTable().getMouseListeners())
					{
					Rectangle rectCell0 = getTable().getCellRect(0, 0, true);
					
					objMouseListener.mouseClicked(new MouseEvent(getTable(), MouseEvent.MOUSE_CLICKED, (new Date()).getTime(), 0, rectCell0.x, rectCell0.y, 1, false, MouseEvent.BUTTON1));
					}
			}
		});
	}
}