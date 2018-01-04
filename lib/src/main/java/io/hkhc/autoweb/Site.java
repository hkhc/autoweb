/**
 * 
 */
package io.hkhc.autoweb;

import java.io.IOException;
import java.net.URL;

/**
 * @author panda
 *
 */
public interface Site {
	
	public URL getStartUrl();
	public void init();
	public AutoWebPage getCurrentPage() throws IOException;

}
