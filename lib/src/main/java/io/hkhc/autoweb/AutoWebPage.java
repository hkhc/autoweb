/**
 * file comment
 */

package io.hkhc.autoweb;

import java.net.URL;

import com.gargoylesoftware.htmlunit.Page;


/**
 * @author panda
 *
 */

public interface AutoWebPage {
	
	void init() throws PageRuntimeException;
	URL getResponseUrl();
	void setPage(Page p);
	void setRegistry(IRegistry registry);

}
