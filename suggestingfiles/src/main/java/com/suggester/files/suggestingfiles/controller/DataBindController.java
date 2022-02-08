/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.suggester.files.suggestingfiles.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Jorge Ascencio
 */
@Controller
public class DataBindController {
    
    public DataBindController(){}
    
    /**
     *
     * @param matchCities
     * @param parameters
     * @throws IOException
     */
    public void searchCities(Map<String, List> matchCities, Map<String, String> parameters) throws IOException{
        
        Resource resource = new ClassPathResource("cities_canada-usa.tsv");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(resource.getFile()));
            String linea = br.readLine();
            List<String> listLine = Arrays.asList(linea.split("\t"));
            int nameIndex = listLine.indexOf("name");
            int countryIndex = listLine.indexOf("country");
            int cc2Index = listLine.indexOf("cc2");
            int latIndex = listLine.indexOf("lat");
            int lonIndex = listLine.indexOf("lon");
            List<Map> matchGroup = new ArrayList();
            
            while(linea != null){
                listLine = Arrays.asList(linea.split("\t"));
                System.out.println(listLine.get(nameIndex));
                String regex = "^.*"+ parameters.get("q").toLowerCase() +".*$";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(listLine.get(nameIndex).toLowerCase());
                if(matcher.find()){
                    matchGroup.add(Map.ofEntries(
                            entry("name", cc2Index >= 0 ? 
                                    setupName(listLine.get(nameIndex),listLine.get(cc2Index),listLine.get(countryIndex))
                                    : setupName(listLine.get(nameIndex),listLine.get(countryIndex),listLine.get(countryIndex))),
                            entry("latitude", latIndex >= 0 ? listLine.get(latIndex) : ""),
                            entry("longitude", lonIndex >= 0 ? listLine.get(lonIndex) : "")
                    ));
                }
                try {
                    linea = br.readLine();
                } catch(NullPointerException npe){
                    Logger.getLogger(DataBindController.class.getName()).log(Level.SEVERE, null, npe);
                    br.close();
                }
            }
            
            matchCities.put("suggestions", matchGroup);
            
        } catch (IOException ex) {
            Logger.getLogger(DataBindController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            br.close();
        }
    }
    
    public String setupName(String city, String cCountry, String country){
        return city +", "+ cCountry +", "+ country;
    }
}
