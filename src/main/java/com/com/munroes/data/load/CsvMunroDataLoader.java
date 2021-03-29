package com.com.munroes.data.load;

import com.com.munroes.model.Designation;
import com.com.munroes.model.GridReference;
import com.com.munroes.model.Munro;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class CsvMunroDataLoader implements MunroDataLoader {

    private final Path csvFilePath;

    public CsvMunroDataLoader(final Path csvFilePath) {
        Assert.isTrue(Files.exists(csvFilePath) && Files.isRegularFile(csvFilePath),
            "File: '" + csvFilePath + "' not recognised");

        this.csvFilePath = csvFilePath;
    }


    @Override
    public void loadInto(final Set<Munro> munroes) {
        try (final FileReader reader = new FileReader(this.csvFilePath.toFile());
             final CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader)) {

            final List<CSVRecord> records = parser.getRecords();

            this.doLoad(records, munroes);

        } catch (FileNotFoundException fnfEx) {
            throw new IllegalStateException(
                    "Failed to find CSV file: '" + this.csvFilePath + "'", fnfEx);
        } catch (IOException ioEx) {
            throw new IllegalStateException(
                    "Unable to read CSV file: '" + this.csvFilePath + "'", ioEx);
        }
    }


    private void doLoad(final List<CSVRecord> records, Set<Munro> munroes) {
        for (final CSVRecord record : records) {
            if (!StringUtils.hasLength(record.get(RelevantColumns.NAME))) {
                // End of data section reached.
                return;
            }

            final /* Nullable */ Munro result = parse(record);
            if (result != null) {
                munroes.add(result);
            }
        }
    }

    private static @Nullable Munro parse(final CSVRecord record) {
        final String designationString = record.get(RelevantColumns.DESIGNATION);
        if (!StringUtils.hasText(designationString)) {
            return null;
        }

        final Designation designation = Designation.valueOf(designationString);
        final String name = record.get(RelevantColumns.NAME);
        final GridReference gridReference =
                GridReference.of(record.get(RelevantColumns.GRID_REFERENCE));
        final double height = Double.parseDouble(record.get(RelevantColumns.HEIGHT));

        return new Munro(name, gridReference, height, designation);
    }


    private static final class RelevantColumns {
        private static final String NAME = "Name";
        private static final String GRID_REFERENCE = "Grid Ref";
        private static final String HEIGHT = "Height (m)";
        private static final String DESIGNATION = "Post 1997";
    }
}
