package br.com.willianantunes.labdatatables.component;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class SessionControl
{
	private Object temporarily;
    
	public Object getTemporarily()
	{
		return temporarily;
	}
	public void setTemporarily(Object temporarily)
	{
		this.temporarily = temporarily;
	}
}

