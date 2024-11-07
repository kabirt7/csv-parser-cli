# CSVParser-CLI

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

# Log

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
* I'm going to build the project with Maven so it can be easily built and deployed (I want to use some JUnit 5 testing libraries)
* Need to add some build notes to the README when finished
* I think I'm going to include a CLI argument for the file path so that different lists of people can be analaysed
