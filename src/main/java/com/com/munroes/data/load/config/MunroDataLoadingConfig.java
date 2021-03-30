package com.com.munroes.data.load.config;

import com.com.munroes.data.load.CsvMunroDataLoader;
import com.com.munroes.data.load.MunroDataLoader;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Configuration class which provides the bean for the {@link MunroDataLoader}.
 */
// Written as a Configuration class to allow conditional swapping to a non-CSV implementation,
// for example, if a .xlsx file was provided, or it was told to load from elsewhere.
@Configuration
public class MunroDataLoadingConfig {

    @Bean
    public MunroDataLoader loader() {
        final URL csvFile =
                MunroDataLoadingConfig.class.getClassLoader().getResource("munrotab_v6.2.csv");

        try {
            return new CsvMunroDataLoader(new File(csvFile.toURI()).toPath());
        } catch (final URISyntaxException | NullPointerException uriSEx) {
            throw new ApplicationContextException("Failed to find CSV file for munro data", uriSEx);
        }
    }
}
