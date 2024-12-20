# csv-parser-cli

## Build
* import the Maven project
* enter the file path in the CLI to run the program
```
src/main/java/resources/records.csv
```

## MVP
* takes in a CSV file
* sorts the records by `division` and `points`
* selects the top three records
* prints the records to stdout in a specific YAML format

## Features
* parses CSV file, sorts the records by `division` and `points`, selects top three records and prints the records in the specified YAML format
* can work with different Record Classes as long as the Record Class has the correct methods

## Considerations
* Code style and commenting
* Unit Tests
* Overall design and architecture
* Suitability of data structures and algorithms used
* Handling of edge cases
* Logging and error reporting
* Ease of build and deployment

## Improvements
* To improve with the App's usability with new Record classes, it would be better to make an Interface containing the sort and compare methods for the classes to extend (so this doesn't have to be added manually each time a Record Class is created)

## Log
## 7/11/24
Initialised git repository and planned out structure of the Java project:
- Record Class to contain information for each entry
- Comparator class that uses the Comparator Interface for sorting
- CSV Parser class that uses the Buffered Reader import to analyse the CSV file
- A resources folder where the CSV file in contained

- A Test source directory that contains a package with tests

## 8/11/24
* So I've been reflecting on my initial thoughts, and I want to use generics so that any object (not just the specified Record class) can be analysed from the CSV Parser i.e.
```java
CSVParser<T>
```
Planning:
* I'm going to build the project with Maven so it can be easily built and deployed (I want to use some JUnit 5 testing libraries)
* Need to add some build notes to the README when finished
* I think I'm going to include a CLI argument for the file path so that different lists of people can be analaysed

Achieved:
* I’ve fleshed out my CSVParser (and test) class as well and my Record Class
* To allow for my CSVParser class to be used generically, I needed to pass in a constructor from the Object (in the List) that’s being returned (in this case Person/Record)
```
public <T> List<T> parseFile(String pathToFile, Constructor<T> constructor){...}
```
* I also needed to add a method that types the incoming fields according to the specified types on the Object’s constructor.
```
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
```
* I’ve also gone ahead and added my first test for the CSVParser. The test class creates a sample CSV including an invalid entry which is then subsequently tested (more to come)…

## 9/11/24
Planning:
* I'm going to finish the App and Record Classes
* Record needs the sorter method as well as the Comparator class
* Make it so that the App requires a file directory as a command-line argument

Achieved:
* Record has new classes/methods:
```
public static class RecordCompare implements Comparator<Record> {
        
    public int compare(Record r1, Record r2) {
      int divisionCompare = Integer.compare(r1.getDivision(), r2.getDivision());
            
      if (divisionCompare == 0) {
        return Integer.compare(r2.getPoints(), r1.getPoints());  
      }
            
      return divisionCompare;
    }
}
```
```
public static List<Record> getTopThreePeople(List<Record> people) {
        return people.stream()
                     .sorted(new RecordCompare())  
                     .limit(3)                        
                     .collect(Collectors.toList());
 }
```
* Added in requirement for arg passed in to App
```
if (args.length == 0) {
          System.err.println("enter the file path as a command-line argument.");
          return;
      }
String filePath = args[0];
```
* MVP achieved

## 10/11/24
* added more tests: testParseFile_InvalidData, testRecordSort_ThreeReturned and testRecordSort_CorrectSorting
* fleshed out README to include build info
* Added an update to the typeFields method to remove the "" from the 'summary' field 
```
if (parameterTypes[i] == String.class) {
            	String value = fields[i];
            	
            	// remove any unnecessary ""
            	if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
            	
            	typedFields[i] = value;
}
```
