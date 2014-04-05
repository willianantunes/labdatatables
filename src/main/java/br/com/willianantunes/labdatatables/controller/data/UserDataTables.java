package br.com.willianantunes.labdatatables.controller.data;

import java.util.ArrayList;
import java.util.List;

import br.com.willianantunes.labdatatables.model.User;
import br.com.willianantunes.utils.datatables.DataTables;

public class UserDataTables extends DataTables<User> 
{
	public UserDataTables() 
	{
		
	}

	public UserDataTables(List<User> t, long iTotalRecords,
			long iTotalDisplayRecords, String sSearch, String sEcho) 
	{
		super(t, iTotalRecords, iTotalDisplayRecords, sSearch, sEcho);
	}
	
	@Override
	protected List<String> getAttributes(User user)
	{
		List<String> list = new ArrayList<String>();
		
		list.add(this.getString(user.getId()));
		list.add(this.getString(user.getName()));
		
		return list;
	}
	
	@Override
	public String getColumnNameById(int id)
	{
		switch (id) 
		{
		case 1:
			return "name";
		default:
			return "id";
		}
	}
}
