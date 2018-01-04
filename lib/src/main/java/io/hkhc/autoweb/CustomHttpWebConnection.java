package io.hkhc.autoweb;

import org.apache.http.impl.client.HttpClientBuilder;

import com.gargoylesoftware.htmlunit.HttpWebConnection;
import com.gargoylesoftware.htmlunit.WebClient;

public class CustomHttpWebConnection extends HttpWebConnection {

	private HttpClientBuilder hcb;
	
	public CustomHttpWebConnection(WebClient webClient, HttpClientBuilder hcb) {
		super(webClient);
		this.hcb = hcb;
	}

	@Override
	protected HttpClientBuilder createHttpClient() {
		return hcb;
	}

	
	
}
