package sandbox.mdsd.ui;

import ikor.collection.List;

import sandbox.mdsd.data.Dataset;
import sandbox.mdsd.data.DatasetModel;

public class DatasetEditor extends Editor<Dataset> implements DatasetComponent
{
	private List<Label> headers; 
	
	public DatasetEditor (String id, DatasetModel model)
	{
		super(id,model);
		headers = new ikor.collection.DynamicList<Label>();
	}

	
	public void clearHeaders ()
	{
		headers.clear();
	}
	
	public void addHeader (Label header)
	{
		headers.add(header);
	}

	public void addHeader (String text)
	{
		addHeader ( new Label(text) );
	}
	
	public Label getHeader (int column)
	{
		if ((column>=0) && (column<headers.size()))
			return headers.get(column);
		else 
			return null;
	}

	public void setHeader (int column, Label header)
	{
		if ((column>=0) && (column<headers.size()))
			headers.set(column, header);
	}

	public void setHeader (int column, String text)
	{
		setHeader (column, new Label(text));
	}

}