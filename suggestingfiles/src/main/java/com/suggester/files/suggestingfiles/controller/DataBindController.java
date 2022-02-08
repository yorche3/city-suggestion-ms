/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.suggester.files.suggestingfiles.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public void readFile(){
        Resource resource = new ClassPathResource("cities_canada-usa.tsv");
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(resource.getFile()));
            String linea = br.readLine();
            while(linea != null){
                System.out.println(linea);
                System.out.println(Arrays.asList(linea.split("\t")).size());
                linea = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(DataBindController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchCities(ArrayNode matchCities) throws IOException{
        readFile();
        
//        closeFile();
    }
}
