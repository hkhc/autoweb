/**
 * 
 */
package io.hkhc.autoweb;

import com.gargoylesoftware.htmlunit.Page;



/**
 * @author panda
 *
 */
public interface PageMatcher {
	
	public boolean match(Page p);
	public String getPageBeanName();

}
