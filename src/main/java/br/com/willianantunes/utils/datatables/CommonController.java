package br.com.willianantunes.utils.datatables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This controller class (MVC pattern) get all the information compatible with jQuery DataTables v1.9.4.
 * @author Willian Antunes
 * @version 1.0.0
 * @see <a href="http://datatables.net/usage/server-side">DataTables server-side</a>
 */
public abstract class CommonController 
{
	protected HttpServletRequest request;
	
    private final Logger logger = LogManager.getLogger(CommonController.class);
    
	/**
	 * It's responsible to provide data to be filled in the datatable jquery plugin
	 * @param sSearch			Global search field
	 * @param sEcho				Information for DataTables to use for rendering.
	 * @param iDisplayStart		Display start point in the current data set.
	 * @param iDisplayLength	Number of records that the table can display in 
	 * 							the current draw. It is expected that the number of 
	 * 							records returned will be equal to this number, unless 
	 * 							the server has fewer records to return.
	 */    
    protected abstract void paginate();
	
	public CommonController(HttpServletRequest request) 
	{
		this.request = request;
	}

	/**
	 * This method read all the parameters sent by jQuery DataTables. Example of usage on vRaptor v3.5.3:
	 * 
	 * <pre>
	 * {@code
	 * Map<String, Object> parametersDataTable = super.getParametersDataTable();
	 *   
	 * String sSearch = (String) parametersDataTable.get("allSearch");
	 * String sEcho = (String) parametersDataTable.get("sEcho");
	 * int iDisplayStart = (Integer) parametersDataTable.get("iDisplayStart");
	 * int iDisplayLength = (Integer) parametersDataTable.get("iDisplayLength");
	 * String fdSortCol = UserDataTables.getColumnNameById(Integer.parseInt((String) ((ArrayList<Object>) parametersDataTable.get("fdSortCol")).get(0)));
	 * String fdSortDir = (String) ((ArrayList<Object>) parametersDataTable.get("fdSortDir")).get(0);
	 * 
	 * List<User> users = sSearch != null ? (List<User>) this.userRepository.
	 * 		findIntervalByAttribute("name", sSearch, iDisplayStart, iDisplayLength, fdSortDir, fdSortCol) : 
	 * 			(List<User>) this.userRepository.findTheRange(iDisplayStart, iDisplayLength);
	 * 
	 * UserDataTables usrDataTable = new UserDataTables(
	 * 		users, // AaData
	 * 		users.size(), // iTotalRecords
	 * 		this.userRepository.totalRecords("id"), // iTotalDisplayRecords
	 * 		sSearch, // sSearch
	 * 		sEcho // sEcho
	 * 		);
	 * 
	 * result.use(Results.json()).withoutRoot().from(usrDataTable).include("aaData").serialize();
	 * 
	 * }</pre>
	 * @return {@code Map<String, Object>} 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map<String, Object> getParametersDataTable()
	{  
		Map<String, Object> ret = new HashMap<String, Object>();  

		Integer iDisplayLength = new Integer(request.getParameter("iDisplayLength") == null ? "0" : request.getParameter("iDisplayLength"));  
		Integer iDisplayStart = new Integer(request.getParameter("iDisplayStart") == null ? "0" : request.getParameter("iDisplayStart"));  
		Integer iColumns = new Integer(request.getParameter("iColumns") == null ? "0" : request.getParameter("iColumns"));  
		String allSearch = (request.getParameter("sSearch") == null ? "" : request.getParameter("sSearch"));  
		Boolean allRegex = new Boolean((request.getParameter("bRegex") == null ? false : true));  
		Boolean allSearchable = new Boolean((request.getParameter("bSearchable") == null ? false : true));  
		Boolean allSortable = new Boolean((request.getParameter("bSortable") == null ? false : true));
		String sEcho = new String((request.getParameter("sEcho")));

		List fdRegex = new ArrayList();
		List fdSearchable = new ArrayList();
		List fdSortable = new ArrayList();
		List fdDataProp = new ArrayList();
		List fdSearch = new ArrayList();
		List fdSortCol = new ArrayList();
		List fdSortDir = new ArrayList();

		for (int i = 0; i < iColumns; i++) 
		{  
			fdRegex.add(new Boolean((request.getParameter("bRegex_" + i) == null ? false : true)));  
			fdSearchable.add(new Boolean((request.getParameter("bSearchable_" + i) == null ? false : true)));  
			fdSortable.add(new Boolean((request.getParameter("bSortable_" + i) == null ? false : true)));  
			fdDataProp.add((request.getParameter("mDataProp_" + i) == null ? "" : request.getParameter("mDataProp_" + i)));  
			fdSearch.add((request.getParameter("sSearch_" + i) == null ? "" : request.getParameter("sSearch_" + i)));  
			fdSortCol.add((request.getParameter("iSortCol_" + i) == null ? "" : request.getParameter("iSortCol_" + i)));  
			fdSortDir.add((request.getParameter("sSortDir_" + i) == null ? "" : request.getParameter("sSortDir_" + i)));  
		}  
		
		this.logger.trace("[DATA TABLE PARAMETERS]");
		this.logger.trace(String.format("iDisplayLength..: (%s)", iDisplayLength.toString()));  
		this.logger.trace(String.format("iDisplayStart...: (%s)", iDisplayStart.toString()));  
		this.logger.trace(String.format("iColumns........: (%s)", iColumns.toString()));  
		this.logger.trace(String.format("allSearch.......: (%s)", allSearch.toString()));  
		this.logger.trace(String.format("allRegex........: (%s)", allRegex.toString()));  
		this.logger.trace(String.format("allSearchable...: (%s)", allSearchable.toString()));  
		this.logger.trace(String.format("allSortable.....: (%s)", allSortable.toString()));  
		this.logger.trace(String.format("fdRegex.........: (%s)", fdRegex.toString()));  
		this.logger.trace(String.format("fdSearchable....: (%s)", fdSearchable.toString()));  
		this.logger.trace(String.format("fdSortable......: (%s)", fdSortable.toString()));  
		this.logger.trace(String.format("fdDataProp......: (%s)", fdDataProp.toString()));  
		this.logger.trace(String.format("fdSearch........: (%s)", fdSearch.toString()));  
		this.logger.trace(String.format("fdSortCol.......: (%s)", fdSortCol.toString()));  
		this.logger.trace(String.format("fdSortDir.......: (%s)", fdSortDir.toString()));
		this.logger.trace(String.format("sEcho.......: (%s)", sEcho.toString()));
		this.logger.trace(String.format("[/DATA TABLE PARAMETERS]"));

		ret.put("iDisplayLength", iDisplayLength);  
		ret.put("iDisplayStart", iDisplayStart);  
		ret.put("iColumns", iColumns);  
		ret.put("allSearch", allSearch);  
		ret.put("allRegex", allRegex);  
		ret.put("allSearchable", allSearchable);  
		ret.put("allSortable", allSortable);  
		ret.put("fdRegex", fdRegex);  
		ret.put("fdSearchable", fdSearchable);  
		ret.put("fdSortable", fdSortable);  
		ret.put("fdDataProp", fdDataProp);  
		ret.put("fdSearch", fdSearch);  
		ret.put("fdSortCol", fdSortCol);  
		ret.put("fdSortDir", fdSortDir);
		ret.put("sEcho", sEcho);

		return ret;  
	}
}
