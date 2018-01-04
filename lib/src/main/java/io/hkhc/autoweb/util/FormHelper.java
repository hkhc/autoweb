package io.hkhc.autoweb.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import io.hkhc.autoweb.AutoWebPage;
import io.hkhc.autoweb.GenericPage;

public class FormHelper {
	
	private GenericPage page;
	private HtmlForm elementForm;
	private Map<String,HtmlElement> inputs;
	
	public FormHelper(GenericPage p, String formPath) {
		this.page = p;
		inputs = new HashMap<String,HtmlElement>();
		elementForm = (HtmlForm)page.getHtmlUnitHelper().getSingleNode(formPath, p.getPage());
	}

	private HtmlElement getInput(String name) {
		HtmlElement e = inputs.get(name);
		if (e!=null) return e;
		e = (HtmlElement)page.getHtmlUnitHelper().getSingleNode(".//input[@name='"+name+"']", elementForm);
		if (e!=null)
			inputs.put(name, e);
		return e;
	}

	private HtmlTextArea getTextArea(String name) {
		HtmlElement e = inputs.get(name);
		if (e!=null) return (HtmlTextArea)e;
		e = (HtmlElement)page.getHtmlUnitHelper().getSingleNode(".//textarea[@name='"+name+"']", elementForm);
		if (e!=null)
			inputs.put(name, e);
		return (HtmlTextArea)e;
	}
	
	public void setInputText(String fieldName, String value) {
		HtmlTextInput e = (HtmlTextInput)getInput(fieldName);
		if (fieldName!=null) 
			e.setValueAttribute(value);
	}

	public String getInputText(String fieldName) {
		HtmlTextInput e = (HtmlTextInput)getInput(fieldName);
		if (fieldName!=null) 
			return e.getValueAttribute();
		else
			return null;
	}

	public void setInputTextArea(String fieldName, String value) {
		HtmlTextArea e = (HtmlTextArea)getTextArea(fieldName);
		if (fieldName!=null) 
			e.setText(value);
	}

	public String getInputTextArea(String fieldName) {
		HtmlTextArea e = (HtmlTextArea)getInput(fieldName);
		if (fieldName!=null) 
			return e.getText();
		else
			return null;
	}
	
	public void setInputHidden(String fieldName, String value) {
		HtmlHiddenInput e = (HtmlHiddenInput)getInput(fieldName);
		if (fieldName!=null) 
			e.setValueAttribute(value);
	}

	public String getInputHidden(String fieldName) {
		HtmlHiddenInput e = (HtmlHiddenInput)getInput(fieldName);
		if (fieldName!=null) 
			return e.getValueAttribute();
		else
			return null;
	}
	
	public void removeInput(String fieldName) {
		HtmlElement e = (HtmlElement)getInput(fieldName);
		if (fieldName!=null) {
			e.getParentNode().removeChild(e);
		}
	}
	
	public AutoWebPage submit(String name) throws IOException {
		
		return submitImpl(".//input[@type='submit' and @name='"+name+"']");
		
	}
	
	public AutoWebPage submit() throws IOException {
		return submitImpl(".//input[@type='submit']");
	}

	private AutoWebPage submitImpl(String submitPath) throws IOException {
		
		HtmlSubmitInput submitField = (HtmlSubmitInput)page.getHtmlUnitHelper().getSingleNode(submitPath, elementForm);

		try {
			HtmlPage resultPage = (HtmlPage)submitField.click();
			if (resultPage==null)
				return null;
			else {
				return page.getRegistry().lookup(resultPage);
			}
		}
		catch (FailingHttpStatusCodeException e) {
			return null;
		}	
		
	}
	
}
