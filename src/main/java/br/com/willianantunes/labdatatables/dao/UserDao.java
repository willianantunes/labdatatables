package br.com.willianantunes.labdatatables.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.willianantunes.labdatatables.model.User;

@Component
public class UserDao 
{
	private Session session;
	
	public UserDao(Session session)
	{ 
		this.session = session;
	}
	
	public void save(User u)
	{
		this.session.save(u);
	}
	
	public void remove(Long id)
	{
		this.session.delete(this.session.load(User.class, id));
		this.session.flush();
	}
	
	public User load(Long id)
	{
		return (User) this.session.load(User.class, id);
	}
	
	public void update(User u)
	{
		this.session.update(u);
		this.session.flush();
	}

	@SuppressWarnings("unchecked")
	public List<User> listAll()
	{
		return this.session.createCriteria(User.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<User> findTheRange(int start, int length) 
	{
		return this.session.createCriteria(User.class).setFirstResult(start).setMaxResults(length).list();
	}	
	
	@SuppressWarnings("unchecked")
	public Collection<User> findIntervalByAttribute(String columnName, String search, int start, int length, String OrderBy, String OrderByColumn)
	{			
		return this.session.createCriteria(User.class).
				add(Restrictions.ilike(columnName, search, MatchMode.ANYWHERE)).
				setFirstResult(start).
				setMaxResults(length).
				addOrder(
						OrderBy.toLowerCase().equals("asc") ? 
								Order.asc(OrderByColumn) : 
									Order.desc(OrderByColumn)
									).list();
	}	
	
	public Long totalRecords(String column) 
	{
		return (Long) session.createCriteria(User.class).setProjection(Projections.countDistinct(column)).uniqueResult();
	}
}
