package main;

import identificadores.AnalizadorIdentificadores;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import lexical.Lexanalizer;
import syntactical.thisparser;
import tipado.AnalizadorTipos;
import traductor.TraductorACodigo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class SintacticoMain {
    public static void main(String[] args) {
        try{
            Lexanalizer l=new Lexanalizer(new InputStreamReader(new java.io.FileInputStream(args[0])), new ComplexSymbolFactory());
            thisparser p;
            p = new thisparser(l);//(SymbolFactory) new ComplexSymbolFactory()
            Symbol s=p.parse();
            System.out.println(s.value);
            AnalizadorIdentificadores ai=new AnalizadorIdentificadores();
            ai.parsear(s);
            AnalizadorTipos at=new AnalizadorTipos();
            at.parsear(s);
            PrintWriter output = new PrintWriter("output.p", "UTF-8");
            TraductorACodigo tr=new TraductorACodigo(output);
            tr.traducir(s);
            output.close();
        }catch(Exception e){
            System.out.println(e);}
    }
}
