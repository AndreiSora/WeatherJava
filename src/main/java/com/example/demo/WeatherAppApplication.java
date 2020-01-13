package com.example.demo;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.net.URLConnection;

@SpringBootApplication
public class WeatherAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);

		HttpResponse<String> httpResponse = Unirest.get("https://api.weather.gov/gridpoints/TOP/31,80")
       .header("x-rapidapi-key", "c2e075a4f9msh7dcde5e254e52c0p1a1641jsn91f26824d44")
       .asString();
		System.out.println(httpResponse.getBody());
	}
	
}
