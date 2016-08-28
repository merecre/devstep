package lv.itsms.web.page.smspanel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.importdata.ImportPhoneNumbers;
import lv.itsms.web.request.parameter.SmsGroupBuilder;
import lv.itsms.web.request.parameter.smspanel.ImportSmsGroupContactsFilenameParameter;
import lv.itsms.web.request.parameter.smspanel.ImportSmsGroupContactsRequestParameter;
import lv.itsms.web.session.Session;
import transfer.domain.SmsGroup;

public class DoImportSmsGroupContacts implements PageRequestCommand {

	private static final String COMMAND_INFO = "Contacts.uploaded.successfully";
	
	CustomerPanelCommandFactory factory;
		
	public DoImportSmsGroupContacts(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() throws Exception {
		// Create a new file upload handler
		//ServletFileUpload upload = new ServletFileUpload();

		storeUserInputedSmsGroupData();
		
	   // String description = factory.request.getParameter("description"); // Retrieves <input type="text" name="description">
	    Part filePart = factory.request.getPart(ImportSmsGroupContactsFilenameParameter.URL_PARAMETER); // Retrieves <input type="file" name="file">
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
	    ImportPhoneNumbers importer = new ImportPhoneNumbers(fileName, fileContent);
	    List<String> phoneNumbers = importer.importPhoneNumbers();	
	 
	    System.out.println("phoneNumbers"+phoneNumbers);
	    factory.getSession().updateSessionAttribute(Session.SESSION_IMPORTPHONE_PARAMETER, phoneNumbers);
		factory.getSession().updateSessionAttribute(Session.SESSION_INFORMATION_PARAMETER, COMMAND_INFO);
	}
	
	private void storeUserInputedSmsGroupData() {
		SmsGroup smsGroup = getInputedSmsGroupRecord();			
		factory.getSession().updateSessionAttribute(Session.SESSION_SMSGROUPREC_PARAMETER, smsGroup);
	}
	
	private SmsGroup getInputedSmsGroupRecord() {
		SmsGroupBuilder smsGroupBuilder = new SmsGroupBuilder();
		return smsGroupBuilder.build(factory.getRequest());		
	}
}
