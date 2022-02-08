/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.suggester.files.suggestingfiles.rest;

import com.suggester.files.suggestingfiles.controller.DataBindController;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jorge Ascencio
 */
@RestController
@RequestMapping("/suggestions")
public class CitySuggestRest {
    
    private Map<String, List> matchCities = new HashMap();
    private DataBindController databinder = new DataBindController();
        
    /**
     *
     * @param q
     * @param longitude
     * @param latitude
     * @return
     */
    @GetMapping
    public ResponseEntity<Map> lookFor(@RequestParam("q") String q,
            @RequestParam("longitude") Optional<String> longitude, 
            @RequestParam("latitude") Optional<String> latitude){
        
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("q", q);
            if(longitude.isPresent()) parameters.put("longitude", longitude.toString());
            if(longitude.isPresent()) parameters.put("latitude", latitude.toString());
            
            databinder.searchCities(matchCities, parameters);
        } catch (IOException ioe) {
            Logger.getLogger(ioe.getMessage()).log(Level.SEVERE, null, ioe.getCause());
        }
        
        return new ResponseEntity<Map>(matchCities, HttpStatus.OK);
    }
}
