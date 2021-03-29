package com.com.munroes.data.load;

import com.com.munroes.model.Munro;
import org.springframework.util.Assert;

import java.nio.file.Files;
import java.nio.file.Path;
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

    }
}
