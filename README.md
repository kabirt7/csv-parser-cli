# csv-parser-cli

## MVP

* takes in a CSV file
* sorts the records by `division` and `points`
* selects the top three records
* prints the records to stdout in a specific YAML format

## Considerations

* Code style and commenting
* Unit Tests
* Overall design and architecture
* Suitability of data structures and algorithms used
* Handling of edge cases
* Logging and error reporting
* Ease of build and deployment

## Log

## 7/11/24
Initialised git repository and planned out structure of the Java project:
- Person Class to contain information for each entry
- Comparator class that uses the Comparator Interface for sorting
- CSV Parser class that uses the Buffered Reader import to analyse the CSV file
- A resources folder where the CSV file in contained

- A Test source directory that contains a package with tests

## 8/11/24
* So I've been reflecting on my initial thoughts, and I want to use generics so that any object (not just the Person object) can be analysed from the CSV Parser i.e.
```java
CSVParser<T>
```
Planning:
* I'm going to build the project with Maven so it can be easily built and deployed (I want to use some JUnit 5 testing libraries)
* Need to add some build notes to the README when finished
* I think I'm going to include a CLI argument for the file path so that different lists of people can be analaysed

Achieved:
* I’ve fleshed out my CSVParser (and test) class as well and my Record Class
* To allow for my CSVParser class to be used generically, I needed to pass in a constructor from the Object that’s being returned (in this case the Record class)
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
