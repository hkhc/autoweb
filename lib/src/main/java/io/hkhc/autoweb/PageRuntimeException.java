/**
 * file comment
 */

package io.hkhc.autoweb;

/**
 * @author panda
 *
 */

public class PageRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2817443503168980479L;

	/**
	 * 
	 */
	public PageRuntimeException() {
		super();
	}

	/**
	 * @param message
	 */
	public PageRuntimeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PageRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PageRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
