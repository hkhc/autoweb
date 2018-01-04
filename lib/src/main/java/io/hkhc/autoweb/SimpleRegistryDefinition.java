/**
 * 
 */
package io.hkhc.autoweb;

import java.util.List;

/**
 * @author panda
 *
 */
public class SimpleRegistryDefinition implements RegistryDefinition {

	private List<PageMatcher> matchers;
	
	/* (non-Javadoc)
	 * @see RegistryDefinition#getDefinition()
	 */
	public List<PageMatcher> getDefinition() {
		return matchers;
	}

	/**
	 * @return the matchers
	 */
	public List<PageMatcher> getMatchers() {
		return matchers;
	}

	/**
	 * @param matchers the matchers to set
	 */
	public void setMatchers(List<PageMatcher> matchers) {
		this.matchers = matchers;
	}

}
