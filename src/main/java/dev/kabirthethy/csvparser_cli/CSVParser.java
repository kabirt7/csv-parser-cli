package dev.kabirthethy.csvparser_cli;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVParser {
	
	private static final Logger logger = Logger.getLogger(CSVParser.class.getName());
	
	public <T> List<T> parseFile(String pathToFile, Constructor<T> constructor){
		List<T> records = new ArrayList<>();
		
		try (BufferedReader bfr = new BufferedReader(new FileReader(pathToFile))) {
			String line;
			bfr.readLine();
			int lineNumber = 0;
			
			while ((line = bfr.readLine()) != null) {
			 lineNumber++;
			 String[] fields = line.split(",");
			 
			 try {
				 Object[] params = convertFields(fields, constructor.getParameterTypes());
	             T record = constructor.newInstance(params);
	             records.add(record);
                 
             } catch (Exception e) {
                 logger.log(Level.WARNING, "Line " + lineNumber + ": Failed to create record", e);
             }
             }
		     
		} catch (IOException e) {
			logger.log(Level.SEVERE, "File read error: " + pathToFile, e);
			
		};	
		return records;
	 }
	
	private Object[] convertFields(String[] fields, Class<?>[] parameterTypes) {
        Object[] params = new Object[fields.length];

        for (int i = 0; i < fields.length; i++) {
            if (parameterTypes[i] == String.class) {
                params[i] = fields[i];
            } else if (parameterTypes[i] == Integer.class) {
                params[i] = Integer.parseInt(fields[i]);
            } else if (parameterTypes[i] == LocalDate.class) {
                params[i] = LocalDate.parse(fields[i]);
            }
        }
        return params;
    }
}
