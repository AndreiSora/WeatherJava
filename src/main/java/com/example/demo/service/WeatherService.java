package com.example.demo.service;

import com.example.demo.model.WeatherData;
import com.example.demo.model.dto.WeatherDto;
import com.example.demo.model.entity.Person;
import com.example.demo.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class WeatherService {

    @Value("${weather.api.access.token}")
    private String accessToken;

    @Value("${weather.api.base.url}")
    private String url;

    @Autowired
    @Qualifier("threadPool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EmailService emailService;

    public WeatherDto getWeather(String city) {
        String queryParam = String.format("?city=%s&key=%s", city, accessToken);
        return restTemplate.getForEntity(url + queryParam, WeatherDto.class).getBody();
    }

    public void getWeather(String city, String name, String email) {

        log.info("Getting weather for city:{}", city);

        String queryParam = String.format("?city=%s&key=%s", city, accessToken);
        WeatherDto weather = restTemplate.getForEntity(url + queryParam, WeatherDto.class).getBody();

        boolean userExists = personRepository.findByEmail(email) != null;

        if (userExists)
            log.warn("User already exists in the database. Skipping saving");
        else {
            Person person = Person.builder()
                    .name(name)
                    .email(email)
                    .build();
            personRepository.save(person);
            log.info("User with name:{} and email:{} saved into the database", name, email);
        }

        sendMail(email, weather.getData());
    }

    private CompletableFuture<Void> sendMail(String email, List<WeatherData> data) {
        log.info("Sending email with weather to email:{}", email);
        return CompletableFuture.runAsync(() -> {
                    try {
                        emailService.sendHtmlEmail(email, "Vremea de astazi:" + LocalDateTime.now(), data.toString());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                },
                threadPoolTaskExecutor);
    }

}
