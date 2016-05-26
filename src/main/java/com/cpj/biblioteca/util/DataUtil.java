/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpj.biblioteca.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author praveen
 */
public class DataUtil {
    public Date stringToData(String data){
    if(data == null)
        return null;
    Date dataF = null;
    try{
        DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        long timestamp = dateFormat.parse(data).getTime();
        dataF = new Date(timestamp);
    
    }catch(ParseException pe){
        System.out.println("Erro ao converter data"+pe);
    }
    return dataF;
    }
}
