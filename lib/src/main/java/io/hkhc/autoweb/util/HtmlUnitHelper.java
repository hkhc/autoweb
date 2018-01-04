/**
 * file comment
 */

package io.hkhc.autoweb.util;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import io.hkhc.autoweb.EmptyNodeSetException;
import io.hkhc.autoweb.PageRuntimeException;
import io.hkhc.autoweb.WebException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

/**
 * @author panda
 *
 */

public class HtmlUnitHelper {
	
	
//	private static XPathPool xPathPool ;//= new XPathPool(); 
	private Log log = LogFactory.getLog(HtmlUnitHelper.class);
	
	public Page getPage(WebClient client, URL url) throws WebException {

		try {
			WebRequest wrs = new WebRequest(url);
			wrs.setCharset("UTF-8");
		    HtmlPage p = (HtmlPage)client.getPage(wrs);
		    return p;
		} 
		catch (FailingHttpStatusCodeException e) {
			throw new WebException("eLAPS HTTP error : "+e.getMessage(), e);
		} catch (IOException e) {
			throw new WebException(e.getClass().getName() + " : " + e.getMessage(), e);
		}
		
	}
	
	public Page getPage(WebClient client, URL url , String cookiePolicy) throws WebException {

		try {
			WebRequest wrs = new WebRequest(url);
			wrs.setCharset("UTF-8");
//			wrs.setCookiePolicy(cookiePolicy);
		    HtmlPage p = (HtmlPage)client.getPage(wrs);
		    return p;
		} 
		catch (FailingHttpStatusCodeException e) {
			throw new WebException("eLAPS HTTP error : "+e.getMessage(), e);
		} catch (IOException e) {
			throw new WebException(e.getClass().getName() + " : " + e.getMessage(), e);
		}
		
	}
	
	public HtmlForm getForm(HtmlPage page, String name) throws PageRuntimeException {
		
		try {
			return page.getFormByName(name);
		}
		catch (ElementNotFoundException e) {
			throw new PageRuntimeException("Form not found : "+name, e);
		}
		
	}
	
	public HtmlInput getInputElement(HtmlForm form, String name) throws PageRuntimeException {

		try {
			return form.getInputByName(name);
		}
		catch (ElementNotFoundException e) {
			throw new PageRuntimeException("Input not found : "+name, e);
		}

	}
	
	public HtmlAnchor getLink(HtmlPage page, String url) throws PageRuntimeException {

		try {
			return page.getAnchorByHref(url);
		}
		catch (ElementNotFoundException e) {
			throw new PageRuntimeException("Anchor not found : "+url, e);
		}
		
	}

	public HtmlPage clickLink(HtmlPage page, String url) throws PageRuntimeException, WebException {

		try {
			long startTime = System.currentTimeMillis();
			HtmlAnchor link = getLink(page, url);
		    HtmlPage p = (HtmlPage)link.click();
		    long endTime = System.currentTimeMillis();
		    if (log.isDebugEnabled()) 
		    	log.debug("clickLink 2 : "+(endTime-startTime));
		    return p;
		} catch (IOException e) {
			throw new WebException(e.getClass().getName() + " : " + e.getMessage(), e);
		}
		
	}
	
	public HtmlPage clickLink(HtmlAnchor link) throws WebException {

		try {
			long startTime = System.currentTimeMillis();
		    HtmlPage p = (HtmlPage)link.click();
		    long endTime = System.currentTimeMillis();
		    if (log.isDebugEnabled())
		    	log.debug("clickLink 1 : "+(endTime-startTime));
		    return p;
		} catch (IOException e) {
			throw new WebException(e.getClass().getName() + " : " + e.getMessage(), e);
		}
		
	}
	
	public List<? extends HtmlElement> getNodesWithKey(String xPath, DomNode node) throws EmptyNodeSetException {

		@SuppressWarnings("unchecked")
		List<? extends HtmlElement> l = (List<? extends HtmlElement>)node.getByXPath(xPath);
		if (l==null)
			throw new EmptyNodeSetException("Empty Node-Set: "+xPath);		
		return l;
		
	}

	public List<? extends HtmlElement> getNodes(String xPath, Page page) throws EmptyNodeSetException {

		@SuppressWarnings("unchecked")
		List<? extends HtmlElement> l = (List<? extends HtmlElement>)((HtmlPage)page).getDocumentElement().getByXPath(xPath);
		
		if (l==null)
			throw new EmptyNodeSetException("Empty Node-Set: "+xPath);		
		return l;
		
	}
	
	public List<? extends HtmlElement> getNodes(String xPath, DomNode node) throws EmptyNodeSetException {

		@SuppressWarnings("unchecked")
		List<? extends HtmlElement> l = (List<? extends HtmlElement>)node.getByXPath(xPath);
		
		if (l==null)
			throw new EmptyNodeSetException("Empty Node-Set: "+xPath);		
		return l;
		
	}

	public Object getSingleNode(String xPath, Page page) throws EmptyNodeSetException {
		
		Object o = ((HtmlPage)page).getDocumentElement().getFirstByXPath(xPath);
		
		if (o==null)
			throw new EmptyNodeSetException("Empty Node-Set: "+xPath);		
		
		return o;
		
	}

	public Object getSingleNode(String xPath, DomNode node) throws EmptyNodeSetException {
		
		Object o = node.getFirstByXPath(xPath);
		
		if (o==null)
			throw new EmptyNodeSetException("Empty Node-Set: "+xPath);		
		
		return o;
		
	}

	public Object getSingleNodeWithKey(String xPath, DomNode node) throws EmptyNodeSetException {

		Object o = node.getFirstByXPath(xPath);
		if (o==null) {
			throw new EmptyNodeSetException("empty node-set : "+xPath);
		}
		return o;
		
	}
	
	public String getText(Object n) {
		
		if (n==null) return null;
		
		if (n instanceof HtmlElement)
			return ((HtmlElement)n).asText().trim();
		else if (n instanceof DomAttr)
			return ((DomAttr)n).getNodeValue();
		else if (n instanceof DomNode) 
			return ((DomNode)n).asText().trim();
		else if (n instanceof String)
			return ((String)n).trim();
		else if (n instanceof Collection) {
			StringBuffer buffer = new StringBuffer();
			@SuppressWarnings("unchecked")
			Collection<? extends Object> c = (Collection<? extends Object>)n;
			for(Iterator<? extends Object> i=c.iterator();i.hasNext();) {
				Object o = i.next();
				buffer.append(getText(o));
				buffer.append(" ");
			}
			return buffer.toString();
		}
		else 
			return n.toString();
	}
	
	public String getTextByKey(String key, DomNode node) throws PageRuntimeException {
		try {
			if (node==null) return null;
			HtmlElement e = (HtmlElement)getSingleNodeWithKey(key, node);
			return getText(e);
		}
		catch (EmptyNodeSetException e) {
			return null;
		}
	}
	
	public void setHtmlTextArea(HtmlTextArea a, String s) {
		
		String as = a.getText();
		if (!as.equals(s))
			a.setText(s);
		
	}
	
	public void setHtmlInput(HtmlInput i, String s) {

		String ai = i.getValueAttribute();
		if (!ai.equals(s))
			i.setValueAttribute(s);
		
	}

}
