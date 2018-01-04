/**
 * 
 */
package io.hkhc.autoweb;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;


/**
 * @author panda
 *
 */
public class SourceLocator implements BeanFactoryAware {
	
	private BeanFactory beanFactory;
	private String siteConfigPattern;

	public Site getSite(String key) {
		
		String beanName = siteConfigPattern.replace("%SOURCE%", key);
		
		return (Site)beanFactory.getBean(beanName);
		
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	/**
	 * @return the beanFactory
	 */
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	/**
	 * @return the siteConfigPattern
	 */
	public String getSiteConfigPattern() {
		return siteConfigPattern;
	}

	/**
	 * @param siteConfigPattern the siteConfigPattern to set
	 */
	public void setSiteConfigPattern(String siteConfigPattern) {
		this.siteConfigPattern = siteConfigPattern;
	}

}
