package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Response info:
    https://www.weatherbit.io/api/weather-current
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherData {

    private Weather weather;
    private int lat;
    private int lon;
    private int pres;
    private int clouds;
    private int slp;
    private int vis;
    private int h_angle;
    private int uv;
    private int ghi;
    private int wind_dir;
    private int snow;
    private int precip;
    private int aqi;
    private int dhi;
    private int temp;
    private int app_temp;
    private int elev_angle;

    private String timezone;
    private String ob_time;
    private String country_code;
    private String state_code;
    private String city_name;
    private String wind_cdir_full;
    private String wind_cdir;
    private String sunset;
    private String sunrise;
    private String datetime;
    private String station;


}
