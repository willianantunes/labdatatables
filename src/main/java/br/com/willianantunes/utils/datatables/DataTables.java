package br.com.willianantunes.utils.datatables;

import java.util.ArrayList;
import java.util.List;

/**
 * This class can be inherited by another class to expose information compatible with 
 * jQuery DataTables v1.9.4. The constructor of the class that will inherit this one, must set some attributes to
 * enable DataTables plugin work properly.
 * @author Willian Antunes
 * @version 1.0.0
 * @see <a href="https://datatables.net/usage/options">DataTables Options</a>
 * @see <a href="http://datatables.net/usage/server-side">DataTables server-side</a>
 */
public abstract class DataTables<T>
{  
	private List<List<String>> aaData;  
	private long iTotalRecords;  
	private long iTotalDisplayRecords;
	private String sSearch;
	private String sEcho;
	
	public DataTables() 
	{
		
	}

	public DataTables(List<T> t, long iTotalRecords, long iTotalDisplayRecords, String sSearch, String sEcho) 
	{
		setAaData(toListString(
				t == null ?
						new ArrayList<T>() :
							t
				));
		setiTotalRecords(iTotalRecords);
		setiTotalDisplayRecords(iTotalDisplayRecords);
		setSSearch(sSearch);
		setSEcho(sEcho);
	}
	
	@Override
	public String toString() {
		return "DataTables [aaData=" + aaData + ", iTotalRecords="
				+ iTotalRecords + ", iTotalDisplayRecords="
				+ iTotalDisplayRecords + ", sSearch=" + sSearch + ", sEcho="
				+ sEcho + "]";
	}

	@Override  
	public boolean equals(Object obj) 
	{  
		if (obj == null)  
			return false;  
  
		return this.hashCode() == obj.hashCode();  
	}  
  
	@Override  
	public int hashCode() {  
		return this.toString().hashCode();  
	}
	
	public String getString(Object object)
	{
		return object.toString();
	}
  
	protected void setAaData(List<List<String>> aaData) 
	{  
		if (aaData == null)  
			aaData = new ArrayList<List<String>>();  
  
		this.aaData = aaData;  
	}  
  
	protected void setiTotalRecords(long iTotalRecords) 
	{  
		if (iTotalRecords < 0)  
			iTotalRecords = 0;  
  
		this.iTotalRecords = iTotalRecords;  
	}  
  
	protected void setiTotalDisplayRecords(long iTotalDisplayRecords) 
	{  
		if (iTotalDisplayRecords < 0)  
			iTotalDisplayRecords = 0;  
  
		this.iTotalDisplayRecords = iTotalDisplayRecords;  
	}  
  
	protected void setSSearch(String sSearch) 
	{  
		if (sSearch == null)  
			sSearch = "";  
  
		this.sSearch = sSearch;  
	}
	
	protected void setSEcho(String sEcho)
	{
		this.sEcho = sEcho;
	}
	
	protected List<List<String>> toListString(List<T> listOfT)
	{
		List<List<String>> list = new ArrayList<List<String>>();

		for(T t : listOfT)
		{
			list.add(getAttributes(t));
		}
		
		return list;		
	}
	
	/**
	 * This method must feel a list that will be used by jQuery DataTables in "aaData" property. Example of implementation:
	 * <pre>
	 * private List<String> getAttributes(User user)
	 * {
	 * 	List<String> list = new ArrayList<String>();
	 * 
	 * 	list.add(this.getString(user.getId()));
	 * 	list.add(this.getString(user.getName()));
	 * 	list.add(this.getString(user.getEmail()));
	 * 	list.add(user.getRegisterDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")));
	 * 	list.add(this.getString(user.getUserProfile().getUserProfileType()));
	 * 
	 * 	return list;
	 * }
	 * </pre>
	 * @param t
	 * @return A {@code List<List<String>>} used by jQuery DataTables in "aaData" property.
	 */
	protected abstract List<String> getAttributes(T t);
	
	/**
	 * This method must implement a switch-case (example) that will return the column name in the database. Example of implementation:
	 * <pre>
	 * public static String getColumnNameById(int id)
	 * {
	 * 	switch (id) 
	 * 	{
	 * 	case 1:
	 * 		return "name";
	 * 	case 2:
	 * 		return "email";
	 * 	case 3:
	 * 		return "registerDate";
	 * 	case 4:
	 * 		// Criteria alias
	 * 		return "up.userProfileType";
	 * 	default:
	 * 		return "id";
	 * 	}
	 * }
	 * </pre>
	 * @param id
	 * @return A column name that match the column id in the database
	 */
	protected abstract String getColumnNameById(int id);
}
