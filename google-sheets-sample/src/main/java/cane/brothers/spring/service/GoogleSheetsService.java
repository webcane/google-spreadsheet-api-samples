package cane.brothers.spring.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import cane.brothers.spring.Global;

@Service
public class GoogleSheetsService implements GoogleSheets {

	@Value("${google.app.name}")
	String appName;
	
	@Value("${google.spreadsheet.id}")
	String spreadsheetId;
	
	@Value("${google.spreadsheet.sheet.name}")
	String sheetName;

	private Sheets sheetsService = null;

	@Override
	public List<List<Object>> readTable(GoogleConnection connection)  throws IOException {
		Sheets service = getSheetsService(connection);
		return readTable(service, spreadsheetId, sheetName);
	}
	
	private Sheets getSheetsService(GoogleConnection gc) throws IOException {
		if (this.sheetsService == null) {
			return new Sheets.Builder(Global.HTTP_TRANSPORT, Global.JSON_FACTORY, gc.getCredentials())
					.setApplicationName(appName).build();
		} else {
			return this.sheetsService;
		}
	}
	
	private List<List<Object>> readTable(Sheets service, String spreadsheetId, String sheetName)  throws IOException {
		ValueRange table = service.spreadsheets().values().get(spreadsheetId, sheetName)
				.execute();

		List<List<Object>> values = table.getValues();
		printTable(values);
		
		return values;
	}
	
	private void printTable(List<List<Object>> values) {
		if (values == null || values.size() == 0) {
			System.out.println("No data found.");
		}

		else {
			System.out.println("read data");
			for (List<Object> row : values) {
				for (int c = 0; c < row.size(); c++) {
					System.out.printf("%s ", row.get(c));
				}
				System.out.println();
			}
		}
	}

}
