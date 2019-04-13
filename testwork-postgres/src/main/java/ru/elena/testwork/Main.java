package ru.elena.testwork;

import com.opencsv.CSVIterator;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.elena.testwork.domain.User;
import ru.elena.testwork.service.UserService;

@SpringBootApplication
public class Main {

    /**
     * postgres=# CREATE DATADASE test;
     */
    public static final String URL = "https://raw.githubusercontent.com/revkov/JAVA.CSV.TEST/master/test_case.csv";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
    }

    @Autowired
    private UserService user;
    
    @EventListener
    public void init(ContextStartedEvent event)  MalformedURLException {
        final CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        URL url = new URL(URL);
        userService.deleteAll();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                CSVReader csvr = new CSVReaderBuilder(reader).withCSVParser(parser).withSkipLines(1).build();) {
            CSVIterator iterator = (CSVIterator) csvr.iterator();            
            while (iterator.hasNext()) {
                User user = User.createNewUser(iterator.next());
                if (user != null) {
                    userService.save(user);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
