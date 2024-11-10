package dev.kabirthethy.csvparser_cli;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
  public static void main(String[] args) {
	  final Logger logger = Logger.getLogger(CSVParser.class.getName());
	  final CSVParser csvParser = new CSVParser();
	  
	  if (args.length == 0) {
          System.err.println("Enter the file path as a command-line argument");
          return;
      }
	  String filePath = args[0];
	  
	  // Parse the CSV file into Record objects
      Constructor<Record> recordConstructor = Record.getConstructor();
	  List<Record> records = csvParser.parseFile(filePath, recordConstructor);
	  
	  if (records.size() < 3) {
          logger.log(Level.WARNING, "Too few people to sort");
          
      } else {
          List<Record> topThreePeople = Record.getTopThreePeople(records);
          
          System.out.println("\nrecords:\n");
          for (Record person : topThreePeople) {
              System.out.println(person.toYAMLFormat());
          }
      }
      }
}
