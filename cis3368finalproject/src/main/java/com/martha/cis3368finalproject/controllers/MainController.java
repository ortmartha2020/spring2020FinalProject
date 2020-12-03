package com.martha.cis3368finalproject.controllers;

import com.martha.cis3368finalproject.models.chartData;
import com.martha.cis3368finalproject.models.chartDataRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    chartDataRepo ChartDataRepo;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@RequestParam("id") String id, @RequestParam("country_name")String country_name,
                     @RequestParam("date") String date, @RequestParam("total_cases") String total_cases,
                     @RequestParam("total_deaths") String total_deaths, @RequestParam("new_cases") String new_cases,
                     @RequestParam("user_id") String user_id){
        ModelAndView mv = new ModelAndView("redirect:/");
        chartData covid = new chartData();
        covid.setId(id);
        covid.setCountry_name(country_name);
        covid.setDate(date);
        covid.setTotal_cases(total_cases.toString());
        covid.setTotal_deaths(total_deaths.toString());
        covid.setNew_cases(new_cases.toString());
        covid.setUser_id(user_id);
        ChartDataRepo.save(covid);
        return mv;
    }



    //the logger will help me log the error messages. It helped me resolve the parsing error!!
    Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/get", method = RequestMethod.GET)

    public ModelAndView get(@RequestParam("Slug") String slugRequest) {
        ModelAndView mv = new ModelAndView("redirect:/");
        String jsonString = getCountryByName();
        try {
            JSONObject json = new JSONObject(jsonString);
            String countriesJsonString = json.getString("Countries");
            JSONArray countries = new JSONArray(countriesJsonString);
            JSONObject country = null;
            for (int i = 0; i < countries.length(); i++) {
                country = countries.getJSONObject(i);
                String slug = country.getString("Slug");
                if (slug != null && slug.equals(slugRequest)) {
                    break;
                }
            }
            mv.addObject("Country", country.getString("Country"));
            mv.addObject("Date", country.getString("Date"));
            mv.addObject("Total-Cases", country.getString("TotalConfirmed"));
            mv.addObject("Total-Deaths", country.getString("TotalDeaths"));
            mv.addObject("New-Cases", country.getString("NewConfirmed"));

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return mv;
    }
    //countries are in an array [] so had to do a loop to do a for loop to pull specific country data
    //and pass it to my model and view

    @RequestMapping(value = "/get/countries", method = RequestMethod.GET)
    public ModelAndView getSlug() {
        ModelAndView mv = new ModelAndView("redirect:/");
        String jsonString = getCountryByName();
        try {
            JSONObject json = new JSONObject(jsonString);
            String countriesJsonString = json.getString("Countries");
            JSONArray jsonCountries = new JSONArray(countriesJsonString);
            Map<String, String> countries = Collections.emptyMap();
            for (int i = 0; i < jsonCountries.length(); i++) {
                JSONObject country = jsonCountries.getJSONObject(i);
                String countryCode = country.getString("Slug");
                String countrName = country.getString("Country");
                countries.put(countryCode, countrName);
            }
            mv.addObject("Slug", countries);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return mv;
    }
    // API connection details
    private String getCountryByName() {
        try {
            //String apiKey = "5cf9dfd5-3449-485e-b5ae-70a60e997864";
            URL urlForGetReq = new URL("https://api.covid19api.com/summary");

            HttpURLConnection conn = (HttpURLConnection) urlForGetReq.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //if connection is successful, we need the buffer reader that is able to read to us from the connection
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();

                //we read line by line as the information is coming through
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                //String response
                System.out.println("Response: " + response.toString());
                return response.toString();
            } else {
                return "Unexpected HTTP response";
            }
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }

    }
}

