package lv.itsms.web.importdata;

import java.io.InputStream;
import java.util.List;

import transfer.importer.Import;
import transfer.importer.ImportCSV;

public class ImportPhoneNumbers {

	Import messageDataImporter;
	
	String fileName;
	
	InputStream fileContent;

	public ImportPhoneNumbers(String fileName, InputStream fileContent) {
		this.fileName = fileName;
		this.fileContent = fileContent;
	}
			
	public List<String> importPhoneNumbers() throws Exception {
		Import messageDataImporter = prepareSource();
		List<String> dataLines = messageDataImporter.parse(fileContent);		
		
		return dataLines;
	}
	
	private Import prepareSource() {
		return new ImportCSV();		
	}
}
