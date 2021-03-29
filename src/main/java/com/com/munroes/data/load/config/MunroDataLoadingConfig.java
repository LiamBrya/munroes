package com.com.munroes.data.load.config;

import com.com.munroes.data.load.CsvMunroDataLoader;
import com.com.munroes.data.load.MunroDataLoader;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

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
