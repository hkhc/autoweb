/**
 * 
 */
package io.hkhc.autoweb;

import com.gargoylesoftware.htmlunit.Page;



/**
 * @author panda
 *
 */
public class DummyPageMatcher implements PageMatcher {

	private String pageBeanName = "AutoWebPage";
	
	/* (non-Javadoc)
	 * @see PageMatcher#getPageClassName()
	 */
	public String getPageBeanName() {
		return pageBeanName;
	}

	/* (non-Javadoc)
	 * @see PageMatcher#match(com.gargoylesoftware.htmlunit.Page)
	 */
	public boolean match(Page p) {
		return true;
	}

	public void setPageBeanName(String pageBeanName) {
		this.pageBeanName = pageBeanName;
	}

}
