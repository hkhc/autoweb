/**
 * file comment
 */

package io.hkhc.autoweb;

import com.gargoylesoftware.htmlunit.Page;



/**
 * @author panda
 *
 */

public interface IRegistry {

	public void setBaseUrl(String baseUrl);
	public void setRegistryDefinition(RegistryDefinition rd);
	public AutoWebPage lookup(Page page) throws PageRuntimeException;
	public void setSite(Site site);

}