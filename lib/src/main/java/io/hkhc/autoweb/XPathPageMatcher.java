/**
 * 
 */
package io.hkhc.autoweb;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.hkhc.autoweb.util.HtmlUnitHelper;

/**
 * @author panda
 *
 */
public class XPathPageMatcher implements PageMatcher {
	
	private String xpath;
	private String pageBeanName;
	private HtmlUnitHelper htmlUnitHelper;

	/* (non-Javadoc)
	 * @see PageMatcher#getPageClassName()
	 */
	public String getPageBeanName() {
		// TODO Auto-generated method stub
		return pageBeanName;
	}

	/* (non-Javadoc)
	 * @see PageMatcher#match(com.gargoylesoftware.htmlunit.Page)
	 */
	public boolean match(Page p) {
		if (!(p instanceof HtmlPage)) return false;
		
		HtmlPage hp = (HtmlPage)p;
		
		//HtmlElement n = hp.getDocumentHtmlElement();
		HtmlElement n = hp.getDocumentElement();
		
		
		try {
			Object o = htmlUnitHelper.getSingleNode(xpath, n);
			
			//System.out.println("11111111111111111111");
			//System.out.println(o.equals(null));
			//System.out.println(hp.asXml());
			//System.out.println(xpath.);
			//System.out.println("11111111111111111111");
			
			return o!=null;
		}
		catch (EmptyNodeSetException e) {
			return false;
		}
		
	}

	/**
	 * @return the xpath
	 */
	public String getXpath() {
		return xpath;
	}

	/**
	 * @param xpath the xpath to set
	 */
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	/**
	 * @param pageClassName the pageClassName to set
	 */
	public void setPageBeanName(String pageClassName) {
		this.pageBeanName = pageClassName;
	}

	/**
	 * @return the htmlUnitHelper
	 */
	public HtmlUnitHelper getHtmlUnitHelper() {
		return htmlUnitHelper;
	}

	/**
	 * @param htmlUnitHelper the htmlUnitHelper to set
	 */
	public void setHtmlUnitHelper(HtmlUnitHelper htmlUnitHelper) {
		this.htmlUnitHelper = htmlUnitHelper;
	}

}
