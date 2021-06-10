package com.coronavirus.worldtracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.coronavirus.worldtracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CovidDataService {

    private static final LocationStats locationStat = new LocationStats();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public List<LocationStats> getVirusData() throws IOException, InterruptedException{
        List<LocationStats> newStats= new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(COVID_DATA_URL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvData = new StringReader(response.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvData);
        for (CSVRecord record : records) {
            LocationStats looped = new LocationStats();
            looped.setCountry(record.get("Country/Region"));
            looped.setState(record.get("Province/State"));
            int latestTotalCases = Integer.parseInt(record.get(record.size() - 1));
            looped.setLatestTotalCases(latestTotalCases);
            int casesReportedYesterday = Integer.parseInt(record.get(record.size() - 2));
            looped.setNewCasesReported(latestTotalCases - casesReportedYesterday);
            newStats.add(looped);
            locationStat.setAllStats(newStats);
        }
        return locationStat.getAllStats();
    }

    public int getTotalCases(){
        return locationStat.getAllStats().stream().mapToInt(LocationStats::getLatestTotalCases).sum();
    }

    public int getTotalNewCases(){
        return locationStat.getAllStats().stream().mapToInt(LocationStats::getNewCasesReported).sum();
    }
}