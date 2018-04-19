package main;

import java.io.InputStreamReader;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import lexical.Lexanalizer;
import syntactical.thisparser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tina
 */
public class SintacticoMain {
    public static void main(String[] args) {
        try{
            Lexanalizer l=new Lexanalizer(new InputStreamReader(new java.io.FileInputStream("/home/tina/Documentos/Compilator/Compilator/main/ejemplo.txt")), new ComplexSymbolFactory());
            thisparser p;
            p = new thisparser(l, (SymbolFactory) new ComplexSymbolFactory());
            Symbol s=p.parse();
            System.out.println(s);
        }catch(Exception e){
            System.out.println(e);}
    }
}
