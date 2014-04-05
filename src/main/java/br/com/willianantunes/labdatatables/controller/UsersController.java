package br.com.willianantunes.labdatatables.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.view.Results;
import br.com.willianantunes.labdatatables.component.SessionControl;
import br.com.willianantunes.labdatatables.controller.data.UserDataTables;
import br.com.willianantunes.labdatatables.dao.UserDao;
import br.com.willianantunes.labdatatables.model.User;
import br.com.willianantunes.utils.datatables.CommonController;

@Resource
public class UsersController extends CommonController
{
	private final Result result;
	private final UserDao userDao;
    private final Localization localization;
    private final SessionControl sessionControl;

	public UsersController(Result result, UserDao userDao, 
			Localization localization, HttpServletRequest request,
			SessionControl sessionControl) 
	{
		super(request);
		this.result = result;
		this.userDao = userDao;
		this.localization = localization;
		this.sessionControl = sessionControl;
	}

	@Get("/")
	public List<User> list() 
	{
		return userDao.listAll();
	}
	
	@Post
	@Path("/users")
	public void add(User user)
	{
		userDao.save(user);
		result.include("mensagem", this.localization.getMessage("lbl.user.save"));
		result.redirectTo(UsersController.class).list();
	}
	
	@Get("/users/{id}")
	public User view(Long id)
	{
		User urFound = userDao.load(id);
		this.sessionControl.setTemporarily(urFound);
		
		return userDao.load(id);
	}
	
	@Get("/users/delete/{id}")
	public void delete(Long id)
	{
		userDao.remove(id);
		result.include("mensagem", this.localization.getMessage("lbl.user.delete"));
		result.redirectTo(UsersController.class).list();
	}
	
	@Put
	@Path("/users")
	public void edit(User user)
	{
		User usCached = ((User)this.sessionControl.getTemporarily());
		user.setId(usCached.getId());
		
		userDao.update(user);
		result.include("mensagem", this.localization.getMessage("lbl.user.update"));
		result.redirectTo(UsersController.class).list();
	}
	
	@Get("/language/{language}")
	public void changePassword(String language)
	{
		Locale locale;
		if(language.equals("en_US"))
		{
			locale = new Locale("en", "US");
			
			Config.set(super.request.getSession(), Config.FMT_LOCALE, locale);
			Config.set(super.request.getSession(), Config.FMT_FALLBACK_LOCALE, locale);
		}
		else
		{
			locale = new Locale("pt", "BR");
			
			Config.set(super.request.getSession(), Config.FMT_LOCALE, locale);
			Config.set(super.request.getSession(), Config.FMT_FALLBACK_LOCALE, locale);
		}
		
		result.use(Results.referer()).redirect();
	}

	@SuppressWarnings("unchecked")
	@Post("/users/json/datatables/paginate")
	@Override
	public void paginate() 
	{
		UserDataTables usrDataTable = new UserDataTables();
		
		// String sSearch, String sEcho, int iDisplayStart, int iDisplayLength
		Map<String, Object> parametersDataTable = super.getParametersDataTable();  
		
		String sSearch = (String) parametersDataTable.get("allSearch");
		String sEcho = (String) parametersDataTable.get("sEcho");
		int iDisplayStart = (Integer) parametersDataTable.get("iDisplayStart");
		int iDisplayLength = (Integer) parametersDataTable.get("iDisplayLength");
		String fdSortCol = usrDataTable.getColumnNameById(Integer.parseInt((String) ((ArrayList<Object>) parametersDataTable.get("fdSortCol")).get(0)));
		String fdSortDir = (String) ((ArrayList<Object>) parametersDataTable.get("fdSortDir")).get(0);
		
		List<User> users = sSearch != null ? (List<User>) this.userDao.
				findIntervalByAttribute("name", sSearch, iDisplayStart, iDisplayLength, fdSortDir, fdSortCol) : 
					(List<User>) this.userDao.findTheRange(iDisplayStart, iDisplayLength);			
		
		usrDataTable = new UserDataTables(
				users, // AaData
				users.size(), // iTotalRecords 
				this.userDao.totalRecords("id"), // iTotalDisplayRecords
				sSearch, // sSearch
				sEcho // sEcho
				);
		
		result.use(Results.json()).withoutRoot().from(usrDataTable).include("aaData").serialize();
	}
}
