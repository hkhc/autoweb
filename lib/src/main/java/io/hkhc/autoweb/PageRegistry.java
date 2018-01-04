/**
 * file comment
 */

package io.hkhc.autoweb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gargoylesoftware.htmlunit.Page;

/**
 * @author panda
 *
 */

public class PageRegistry implements IRegistry, ApplicationContextAware {
	
	private List<PageMatcher> matchers;
	private static Log log = LogFactory.getLog(PageRegistry.class);
	private ApplicationContext applicationContext;
	private Site site;

	/**
	 * 
	 */
	public PageRegistry() {
		matchers = new ArrayList<PageMatcher>();
	}
	
	public void setBaseUrl(String url) {
	}
	
	public void setRegistryDefinition(RegistryDefinition rd) {
		matchers = rd.getDefinition();
	}
	
	public void setSite(Site site) {
		this.site = site;
	}
	
	/* (non-Javadoc)
	 * @see com.pithk.elaps.Registry#register(java.lang.Class)
	 */
	public void register(String name, Class<?> pageClass) {
//		registry.add(pageClass);
//		nameMap.put(pageClass.getName(), name);
	}
	
	/* (non-Javadoc)
	 * @see com.pithk.elaps.Registry#lookup(com.gargoylesoftware.htmlunit.html.HtmlPage, java.lang.String)
	 */
	public AutoWebPage lookup(Page page) throws PageRuntimeException {
//		AutoWebPage tempPage = null;
//		int count =0;
		if (log.isDebugEnabled())
			if (page==null)
				log.debug("page object is null.");
		
		for(Iterator<PageMatcher> i=matchers.iterator();i.hasNext();) {
			PageMatcher pm = i.next();
			try {
				if (pm.match(page)) {
					AutoWebPage awp = (GenericPage)applicationContext.getBean(pm.getPageBeanName());
					awp.setRegistry(this);
					awp.setPage(page);
					return awp;
				}
			} catch (IllegalArgumentException e) {
				log.debug("lookup : IllegalArgumentException : "+e.getMessage());
			}
		}			

		if (log.isDebugEnabled()) {
				log.debug("Lookup Page : page not found : " + page);
		}

		throw new PageRuntimeException("Fail to lookup page class for : " +page );
		
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	

}
