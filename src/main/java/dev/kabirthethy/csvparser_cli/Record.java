package dev.kabirthethy.csvparser_cli;

import java.lang.reflect.Constructor;
import java.time.LocalDate;

public class Record {
	
	private String firstName;
	private String lastName;
	private LocalDate date;
	private Integer division;
	private Integer points;
	private String summary;
	
	public Record(String firstName, String lastName, LocalDate date, Integer division, Integer points, String summary) {
		super();
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
	
	public static Constructor<Record> getConstructor() {
        try {
            return Record.class.getConstructor(
                String.class, String.class, LocalDate.class, Integer.class, Integer.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Required constructor not found", e);
        }
    }
	
	public String toYAMLFormat() {
	    return String.format(
	      "- name: %s %s\n\n" + "details: In division %d from %s performing %s\n", firstName, lastName, division, date, summary
	    );
	}
}
