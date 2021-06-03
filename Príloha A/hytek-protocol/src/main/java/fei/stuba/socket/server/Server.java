package fei.stuba.socket.server;
import fei.stuba.socket.result.DeleteResult;
import fei.stuba.socket.result.Result;
import fei.stuba.socket.result.Results;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * only for iaaf rules tested !!
 */
public class Server {
    /**
     *  #serverSocket - Vytvorenie počúvajúceho port
     *
     *  #clientSocket - Prijatie spojenia
     *
     *  #in - Dáta ktoré prichádzajú v bytoch,
     *  prechádzame ich pomocou Buffra v ktorom sa posúvame príkazom read()
     *  a jeho preťaženiami
     *
     *  #results - Trieda na zapisovanie výsledkov - dočasné úložisko dát
     *
     *  #deleteResult - Trieda na zapisovanie vymazaných výsledkov(ktoré sa majú zmazať podla dát z Hyteku) - dočasné úložisko dát
     *
     */
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream in;
    private Results results = new Results();
    private DeleteResult deleteResult = new DeleteResult();
    private fei.stuba.socket.service.Results resultsService;

    public void setResultsService(fei.stuba.socket.service.Results resultsService) {
        this.resultsService = resultsService;
    }

    /**
     * Táto funkcia nám vytvorí port a následne spustí čítanie dát
     *
     */

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while(true) {
            try{
            clientSocket = serverSocket.accept();
            in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            readingBinaryDataFromSocket();}
            catch (EOFException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Krátky popis protokolu:
     * SOH : 01h
     * STX : 02h
     * EOT : 04h
     * DC3 : 13h
     * ¬ : 20h (space)
     * Na začiatku si pozrieme či predchádzajúci byte nebol náhodou nula, pretože nemôže nastať aby išli dve nuly zasebou.
     * 0113h značí začiatok dát
     * 4456h by mal byť druhý parameter
     * následuje switch, v ktorom sa už riadime podľa typu prichádzajúcich dát(typ je popísaný v nasledujúcich troch bytoch)
     * na konci je ukončenie 0400h ,ktoré nám značí koniec jedného cyklu
     */

    public void readingBinaryDataFromSocket() throws IOException{
        while(true){
            String SOHandDC3 = Integer.toHexString(in.readChar());
            if(SOHandDC3.equals("113")) { //113 -> SOH (01) -> start of communication + DC3 (13)
                String DV = Integer.toHexString(in.readChar());
                //  System.out.println("DV = " + DV);
                if (DV.equals("4456")) { //4456 -> DV(44 56)
                    String type = Integer.toHexString(in.readChar()) + Integer.toHexString(in.readByte());
                    //    System.out.println("type = " + type);
                    String STX = Integer.toHexString(in.readByte());
                    switch (type) {
                        case "534e52": //SNR
                            if (STX.equals("2")) {
                                //System.out.println("STX = " + STX);
                                //System.out.println("id race");
                                byte[] idRace = new byte[8];
                                in.read(idRace);
                                String idRaceString = (new String(idRace,StandardCharsets.UTF_8).replaceAll("\\s",""));
                                results.setIdRace(idRaceString);
                                results.setStatus("unofficial");
                                deleteResult.setIdRace(idRaceString);
                                //System.out.println(result.getIdRace());
                            }
                            break;
                        case "52574d": //RWM
                            if (STX.equals("2")) {
                                //System.out.println("STX = " + STX);
                                //System.out.println("out wind");
                                byte[] wind = new byte[9];
                                in.read(wind);
                                results.setWind(new String(wind,StandardCharsets.UTF_8).trim().replaceAll(" +"," "));
                                //System.out.println(result.getWind());
                            }

                            break;
                        //RDC ak je RDC message 0 -> vymaž všetky výsledky
                        case "524443": //RDC
                            if (STX.equals("2")) {
                                //System.out.println("STX = " + STX);
                                //System.out.println("start of transfer ranking");
                                byte[] message = new byte[3];
                                in.read(message);
                                String test = (new String(message,StandardCharsets.UTF_8).replaceAll("\\s",""));
                                results.getResultArrayList().clear();
                                System.out.println("start transfer");
                                //System.out.println(test);
                            }

                            break;
                        case "524343": //RCC
                            if (STX.equals("2")) {
                                //System.out.println("STX = " + STX);
                                //System.out.println("result of ranked comp");
                                byte[] order = new byte[3];
                                byte[] bib = new byte[5];
                                byte[] lane = new byte[2];
                                byte[] time = new byte[12];
                                in.read(order);in.read(bib);in.read(lane);in.read(time);
                                Result result = new Result();
                                result.setOrd((new String(order,StandardCharsets.UTF_8)).replaceAll("\\s+",""));
                                result.setBib((new String(bib,StandardCharsets.UTF_8)).replaceAll("\\s+",""));
                                result.setLane((new String(lane,StandardCharsets.UTF_8)).replaceAll("\\s+",""));
                                result.setTime((new String(time,StandardCharsets.UTF_8)).replaceAll("\\s+",""));
                                results.getResultArrayList().add(result);
                                resultsService.setResults(results);
                                //System.out.println(result.toString());
                            }

                            break;
                        case "525443": //RTC
                            if (STX.equals("2")) {
                                // System.out.println("STX = " + STX);
                                //System.out.println("result of temp class");
                                byte[] message = new byte[26];
                                in.read(message);
                                String test = new String(message,StandardCharsets.UTF_8);
                                //System.out.println(test);
                            }
                            break;
                        case "524643": //RFC
                            if (STX.equals("4")) {
                                System.out.println(results.toString());
                                //System.out.println("EOT = " + STX);
                                //System.out.println("end of transfer ranking");
                                //System.out.println("end of transmission");
                                //nothing
                            }

                            break;
                        case "524346": //RCF
                            if (STX.equals("2")) {
                                //System.out.println("STX = " + STX);
                                //System.out.println("DVRCF");
                                byte[] message = new byte[16];
                                in.read(message);
                                String test = new String(message,StandardCharsets.UTF_8);
                                //System.out.println(test);
                                String nullByte = Integer.toHexString(in.readByte());
                                if(nullByte.equals("4")){
                                    //System.out.println("end of transmission");
                                    //System.out.println("correct ending");
                                    continue;
                                }

                            }
                            break;
                        case "52464a": //RFJ
                            if (STX.equals("2")) {
                                //System.out.println("STX = " + STX);
                                //System.out.println("end of judgement");
                                byte[] message = new byte[8];
                                in.read(message);
                                String test = new String(message,StandardCharsets.UTF_8);
                                results.setStatus("official");
                                System.out.println(results.toString());
                                //System.out.println(test);
                            }

                            break;
                        case "444643": // DFC
                        case "544643": //TFC
                            if (STX.equals("2")) {
                                //System.out.println("STX = " + STX);
                                //System.out.println("output day time");
                                byte[] message = new byte[13];
                                in.read(message);
                                //System.out.println(message);
                            }

                            break;
                        //System.out.println("unknown");
                        case "534344": //SCD
                        case "534348": //SCH
                            if (STX.equals("2")) {
                                while(!Integer.toHexString(in.readByte()).equals("4"));
                                System.out.println("STX = " + STX);
                                System.out.println("supplementary .. data");
                            }
                            break;

                        //System.out.println("supplementary .. header");
                        case "524356": //RCV
                            if (STX.equals("2")) {
                                //System.out.println("STX = " + STX);
                                //System.out.println("Result of competitor");
                                byte[] message = new byte[19];
                                in.read(message);
                                String test = new String(message,StandardCharsets.UTF_8);
                                //System.out.println(test);
                            }
                            break;
                        case "524353": //RCS
                            if (STX.equals("2")) {
                                //  System.out.println("STX = " + STX);
                                //  System.out.println("DVRCS");
                                byte[] bib = new byte[5];
                                byte[] lane = new byte[2];
                                in.read(bib);in.read(lane);
                                deleteResult.setBib((new String(bib,StandardCharsets.UTF_8).replaceAll("\\s+","")));
                                deleteResult.setLane((new String(lane,StandardCharsets.UTF_8).replaceAll("\\s+","")));
                                System.out.println(deleteResult.toString());
                            }
                            break;
                        default:
                            System.out.println("hello");
                    }
                    String EOT = Integer.toHexString(in.readByte());
                    if(EOT.equals("4")){
                        in.readByte();
                    }

                }
            }
        }
    }
    public void stop() throws IOException {
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
