/**
 * 
 */
package io.hkhc.autoweb;

import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.hkhc.autoweb.util.HtmlUnitHelper;


/**
 * @author panda
 *
 */
public class GenericPage   implements AutoWebPage {
	
	private Site site;
	private Page page;
	private IRegistry registry;

	private HtmlUnitHelper htmlUnitHelper;
	private String resultText;

	/* (non-Javadoc)
	 * @see AutoWebPage#init()
	 */
	public void init() throws PageRuntimeException {
		// TODO Auto-generated method stub
	
	}

	public void setSite(Site s) {
		site = s;
	}
	
	public Site getSite() {
		return site;
	}
	
	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}
	
	public URL getResponseUrl() {
		return getPage().getUrl();
	}
	
	public String getXml() {
		return ((HtmlPage)getPage()).asXml();
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
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

	/**
	 * @return the registry
	 */
	public IRegistry getRegistry() {
		return registry;
	}

	/**
	 * @param registry the registry to set
	 */
	public void setRegistry(IRegistry registry) {
		this.registry = registry;
	}

	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}
	
	public List<? extends HtmlElement> getNodes(String xpath, DomNode page) {
		return getHtmlUnitHelper().getNodes(xpath, page);
	}
	
	public List<? extends HtmlElement> getNodes(String xpath, Page page) {
		return getHtmlUnitHelper().getNodes(xpath, page);
	}
	

	
}
