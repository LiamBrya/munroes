package com.com.munroes.data.load;

import com.com.munroes.model.Designation;
import com.com.munroes.model.GridReference;
import com.com.munroes.model.Munro;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CsvMunroDataLoaderTest {

    private static final String CSV_NAME = "munrotab_v6.2.csv";
    private static MunroDataLoader loader;

    private Set<Munro> munroes;

    @BeforeAll
    static void initialiseLoader() throws URISyntaxException {
        final URL csvUrl = CsvMunroDataLoaderTest.class.getClassLoader().getResource(CSV_NAME);

        loader = new CsvMunroDataLoader(new File(csvUrl.toURI()).toPath());
    }

    @BeforeEach
    void initialiseMunroes() {
        final Set<Munro> localMunroes = new HashSet<>();
        loader.loadInto(localMunroes);

        this.munroes = Collections.unmodifiableSet(localMunroes);
    }


    @Test
    void testParserFoundData() {
        Assertions.assertThat(this.munroes).isNotEmpty();
    }

    @Test
    void testParserFoundCorrectAmountOfData() {
        Assertions.assertThat(this.munroes.size()).isEqualTo(509);
    }

    @Test
    void testParsedMunroesContainsOneExpected() {
        final Munro expected = new Munro("Meall Ghaordaidh",
                GridReference.of("NN514397"),
                1039.8d,
                Designation.MUN);

        Assertions.assertThat(this.munroes).contains(expected);
    }
}
