package dev.kabirthethy.csvparser_cli;

import java.lang.reflect.Constructor;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class Record {
	
	private String firstName;
	private String lastName;
	private LocalDate date;
	private Integer division;
	private Integer points;
	private String summary;
	
	public Record(String firstName, String lastName, LocalDate date, Integer division, Integer points, String summary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = date;
		this.division = division;
		this.points = points;
		this.summary = summary;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getDate() {
		return date;
	}

	public Integer getDivision() {
		return division;
	}

	public Integer getPoints() {
		return points;
	}

	public String getSummary() {
		return summary;
	}
	
	public String toYAMLFormat() {
	    return String.format(
	      "- name: %s %s\n\n" + "details: In division %d from %s performing %s\n", firstName, lastName, division, date, summary
	    );
	}
	
	// necessary for parseFile method
	public static Constructor<Record> getConstructor() {
        try {
            return Record.class.getConstructor(
                String.class, String.class, LocalDate.class, Integer.class, Integer.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Required constructor not found", e);
        }
    }
	
	// necessary for Sorting the top 3 Records
    public static class RecordCompare implements Comparator<Record> {
        
    @Override
    public int compare(Record r1, Record r2) {
      int divisionCompare = Integer.compare(r1.getDivision(), r2.getDivision());
            
      if (divisionCompare == 0) {
        return Integer.compare(r2.getPoints(), r1.getPoints());  
      }
            
      return divisionCompare;
    }
    }
    
	public static List<Record> getTopThreePeople(List<Record> people) {
        return people.stream()
                     .sorted(new RecordCompare())  
                     .limit(3)                        
                     .collect(Collectors.toList());
    }
}
