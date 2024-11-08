package dev.kabirthethy.csvparser_cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVParserTest {

    private static final String TEST_FILE_PATH = "test.csv";
    private CSVParser csvParser;
    private File testCSVFile;

    @BeforeEach
    public void setUp() throws Exception {
        csvParser = new CSVParser();
        createTestCSVFile();
    }

    @AfterEach
    public void tearDown() {
        if (testCSVFile != null && testCSVFile.exists()) {
            boolean deleted = testCSVFile.delete();
            if (!deleted) {
                System.err.println("Failed to delete the file: " + testCSVFile.getName());
            }
        }
    }

    private void createTestCSVFile() {
        testCSVFile = new File(TEST_FILE_PATH);

        try (FileWriter writer = new FileWriter(testCSVFile)) {
            writer.write("firstName,lastName,date,division,points,summary\n");
            writer.write("Katniss,Everdeen,2022-03-01,1,100,Offensive Duties\n");
            writer.write("Peta,Mellark,2023-01-01,2,150,Defensive Duties\n");
            writer.write("Effie,Trinket,2022-06-10,3,200,Managerial Duties\n");
            writer.write("Haymitch,Abernathy,2017-02-12,3,100,Teaching Duties\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParseFile_ValidData() throws Exception {
        Constructor<Record> recordConstructor = Record.getConstructor();
        List<Record> records = csvParser.parseFile(TEST_FILE_PATH, recordConstructor);

        assertNotNull(records);
        assertEquals(4, records.size(), "should have 4 valid entries.");
        Record firstRecord = records.get(0);
        assertEquals("Katniss", firstRecord.getFirstName());
        assertEquals("Everdeen", firstRecord.getLastName());
        assertEquals(LocalDate.of(2022, 3, 1), firstRecord.getDate());
        assertEquals(1, firstRecord.getDivision());
        assertEquals(100, firstRecord.getPoints());
        assertEquals("Offensive Duties", firstRecord.getSummary());
    }
}
