/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.suggester.files.suggestingfiles.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.suggester.files.suggestingfiles.controller.DataBindController;
import java.io.IOException;
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
    
    @GetMapping
    public ResponseEntity<JsonNode> lookFor(@RequestParam String q){
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode matchCities = mapper.createArrayNode();
        DataBindController databinder = new DataBindController();
        try {
            databinder.searchCities(matchCities);
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }
        JsonNode suggestedCities = mapper.createObjectNode().set("suggestions", matchCities);
        return new ResponseEntity<>(suggestedCities, HttpStatus.OK);
    }
}
