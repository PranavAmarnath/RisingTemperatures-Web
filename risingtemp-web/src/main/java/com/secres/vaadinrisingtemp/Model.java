package com.secres.vaadinrisingtemp;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>Model</code> class defines all I/O from the CSV files.
 *
 * @author Pranav Amarnath
 *
 */
public class Model {

    /** Table header */
    private Object[] header;
    /** OpenCSV parser */
    private CSVReader reader = null;
    /** Current line */
    private String[] line;
    /** List of Rows */
    private List<GlobalTempDataRow> rowList = new ArrayList<>();
    /** Number of Rows for Global Dataset */
    private final int NUM_ROWS_GLOBAL = 3192;

    /**
     * Model constructor
     */
    public Model() {
		// still trying to figure out how to do I/O on background thread where data is still shown
        try {
            reader = new CSVReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/PranavAmarnath/RisingTemperatures/master/src/main/resources/GlobalTemperatures.csv").openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            header = reader.readNext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int i = 0;
        while (i < NUM_ROWS_GLOBAL) {
            try {
                line = reader.readNext();
            } catch (Exception e) {
                e.printStackTrace();
            }
            GlobalTempDataRow row = new GlobalTempDataRow();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            row.setDt(LocalDate.parse(line[0], formatter));
            row.setLandAverageTemperature(line[1]);
            row.setLandAverageTemperatureUncertainty(line[2]);
            row.setLandMinTemperature(line[3]);
            row.setLandMinTemperatureUncertainty(line[4]);
            row.setLandMaxTemperature(line[5]);
            row.setLandMaxTemperatureUncertainty(line[6]);
            row.setLandAndOceanAverageTemperature(line[7]);
            row.setLandAndOceanAverageTemperatureUncertainty(line[8]);
            rowList.add(row);
            i++;
        }

        try {
            TableView.getGrid().setItems(rowList);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns table header
     * @return <code>Object[]</code> - header
     */
    public Object[] getHeader() {
        return header;
    }

    public List<GlobalTempDataRow> getRowList() {
        return rowList;
    }

}
