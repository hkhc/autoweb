package io.hkhc.autoweb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.gargoylesoftware.css.parser.CSSErrorHandler;
import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSParseException;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.parser.HTMLParserListener;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;
import org.apache.http.impl.client.HttpClientBuilder;

import io.hkhc.autoweb.util.HtmlUnitHelper;

public class GenericSite implements Site {
	
	protected HtmlUnitHelper htmlUnitHelper;
	protected IRegistry registry;
	protected URL startUrl;
	private HttpClientBuilder httpClientBuilder = null;
	protected WebClient webClient = null;
	protected AutoWebPage currentPage = null;
	protected boolean javascriptEnabled = true;
	protected boolean cookieEnabled = true;
	protected boolean redirectionEnabled = true;
	protected boolean cssEnabled = true;
	protected boolean exceptionOnJSError = false;
	protected boolean exceptionOnStatusCode = false;
	private CSSErrorHandler cssErrorHandler = new SilentCssErrorHandler();
	private JavaScriptErrorListener javascriptErrorHandler = new SilentJavaScriptErrorListener();
	private HTMLParserListener htmlParserListener = new HTMLParserListener() {
		@Override
		public void error(String message, URL url, String html, int line, int column, String key) {
			// do nothing
		}

		@Override
		public void warning(String message, URL url, String html, int line, int column, String key) {
			// do nothing
		}
	};

	public AutoWebPage getCurrentPage() throws IOException {
		return getCurrentPage(null);
	}

	public AutoWebPage getCurrentPage(SortedMap<String, String> parameters) throws IOException {
		if (currentPage==null)
			currentPage = getStartPage(parameters);
		return currentPage;
	}

	private URL getURLWithParam(URL url, Map<String,String> parameters) {

//		String urlStr = url.toString();
		String query = url.getQuery();
		StringBuilder builder = new StringBuilder();
		
		builder.append(url.getProtocol());
		builder.append("://");
		builder.append(url.getHost());
		if (("http".equals(url.getProtocol()) && url.getPort()!=80) ||
				("https".equals(url.getProtocol()) && url.getPort()!=443)) {
			builder.append(':');
			builder.append(url.getPort());
		}
		builder.append(url.getPath());
		
		boolean first = true;
		if (query==null && parameters!=null && parameters.size()>0) {
			builder.append('?');
			first = true;
		}
		else if (query!=null) {
			builder.append("?");
			builder.append(url.getQuery());
			first = false;
		}
		
		try {
			if (parameters!=null)
				for(Entry<String,String > entry : parameters.entrySet()) {
					if (!first) builder.append('&');
					builder.append(entry.getKey());
					builder.append('=');
					builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
					first = false;
				}
		}
		catch (UnsupportedEncodingException e) {
			// not possible to throw this exception because of "UTF-8"
		}
		
		try {
			return new URL(builder.toString());
		}
		catch (MalformedURLException e) {
			return null;
		}
		
	}

	public void setCssErrorHandler(CSSErrorHandler handler) {
		this.cssErrorHandler = handler;
	}

	public void setJavascriptErrorHandler(JavaScriptErrorListener handler) {
		this.javascriptErrorHandler = handler;
	}
	
	private WebClient initWebClient() {
		WebClient webClient = new WebClient(getBrowserVersion());
		
		WebClientOptions options = webClient.getOptions();
		options.setJavaScriptEnabled(javascriptEnabled);
		webClient.getCookieManager().setCookiesEnabled(cookieEnabled);
		options.setRedirectEnabled(redirectionEnabled);
        options.setThrowExceptionOnScriptError(exceptionOnJSError);
        options.setThrowExceptionOnFailingStatusCode(exceptionOnStatusCode);
        options.setCssEnabled(cssEnabled);

        if (cssErrorHandler!=null) {
			webClient.setCssErrorHandler(cssErrorHandler);
		}

        if (javascriptErrorHandler!=null) {
        	webClient.setJavaScriptErrorListener(javascriptErrorHandler);
		}

        if (htmlParserListener!=null) {
        	webClient.setHTMLParserListener(htmlParserListener);
		}

        if (httpClientBuilder!=null) {
        	webClient.setWebConnection(new CustomHttpWebConnection(webClient, httpClientBuilder));
        }
        
        return webClient;
	}
	
	public BrowserVersion getBrowserVersion() {
		return BrowserVersion.getDefault();
	}
	
	private AutoWebPage getStartPage(Map<String, String> parameters) throws IOException {
		
		URL url = getURLWithParam(startUrl, parameters);
		if (webClient==null) webClient = initWebClient();
		Page p = webClient.getPage(url);
		return registry.lookup(p);
		
	}
	
	public void init() {

	}

	public HtmlUnitHelper getHtmlUnitHelper() {
		return htmlUnitHelper;
	}

	public void setHtmlUnitHelper(HtmlUnitHelper htmlUnitHelper) {
		this.htmlUnitHelper = htmlUnitHelper;
	}

	public IRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(IRegistry registry) {
		this.registry = registry;
	}

	public URL getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(URL startUrl) {
		this.startUrl = startUrl;
	}

	public boolean isJavascriptEnabled() {
		return javascriptEnabled;
	}

	public void setJavascriptEnabled(boolean javascriptEnabled) {
		this.javascriptEnabled = javascriptEnabled;
	}

	public boolean isCookieEnabled() {
		return cookieEnabled;
	}

	public void setCookieEnabled(boolean cookieEnabled) {
		this.cookieEnabled = cookieEnabled;
	}

	public boolean getRedirectionEnabled() {
		return redirectionEnabled;
	}

	public void setRedirectionEnabled(boolean redirectionEnabled) {
		this.redirectionEnabled = redirectionEnabled;
	}

	public boolean isCssEnabled() {
		return cssEnabled;
	}

	public void setCssEnabled(boolean cssEnabled) {
		this.cssEnabled = cssEnabled;
	}

	public HttpClientBuilder getHttpClientBuilder() {
		return httpClientBuilder;
	}

	public void setHttpClient(HttpClientBuilder httpClientBuilder) {
		this.httpClientBuilder = httpClientBuilder;
	}

	public boolean isExceptionOnJSError() {
		return exceptionOnJSError;
	}

	public void setExceptionOnJSError(boolean exceptionOnJSError) {
		this.exceptionOnJSError = exceptionOnJSError;
	}

	public boolean isExceptionOnStatusCode() {
		return exceptionOnStatusCode;
	}

	public void setExceptionOnStatusCode(boolean exceptionOnStatusCode) {
		this.exceptionOnStatusCode = exceptionOnStatusCode;
	}

}
