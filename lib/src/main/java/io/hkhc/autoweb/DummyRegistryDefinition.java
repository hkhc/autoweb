/**
 * 
 */
package io.hkhc.autoweb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author panda
 *
 */
public class DummyRegistryDefinition implements RegistryDefinition {

	/* (non-Javadoc)
	 * @see RegistryDefinition#getDefinition()
	 */
	public List<PageMatcher> getDefinition() {
		List<PageMatcher> l = new ArrayList<PageMatcher>();
		l.add(new DummyPageMatcher());
		return l;
	}

}
