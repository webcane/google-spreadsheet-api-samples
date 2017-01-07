package cane.brothers.spring.service;

import java.io.IOException;
import java.util.List;

public interface GoogleSheets {

	List<List<Object>> readTable(GoogleConnection gc)  throws IOException;
}
