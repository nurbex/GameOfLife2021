package repo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileRepo {
    String fileName;
    private int neurons;

    private List<double[]> nW = new ArrayList<>();
    private List<double[]> nNW = new ArrayList<>();
    private List<double[]> nMW = new ArrayList<>();
    private List<double[]> nDW = new ArrayList<>();



    public FileRepo(int neurons){
        this.neurons=neurons;
        this.fileName = "brainActivity.txt";
        readAndParseData();
    }


    public void readAndParseData(){

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("nW")) {
                   nW.add(parsenW(line));
                }
                if (line.startsWith("nNW")) {
                    nNW.add(parsenNW(line));
                }
                if (line.startsWith("nMW")) {
                    nMW.add(parsenMW(line));
                }
                if (line.startsWith("nDW")) {
                    nDW.add(parsenDW(line));
                }
            }
        } catch (NoSuchFileException ex ){
            System.out.println("I can not find the file : " + fileName
                    + " please contact administrator! ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public double[] parsenW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        double[] floatnW = new double[11];
        for(int i=0; i < floatnW.length; i++){
            floatnW[i]=Double.parseDouble(tokens[i+1]);
        }
        return floatnW;
    }

    public double[] parsenDW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        double[] doublenDW = new double[neurons];
        for(int i=0; i < doublenDW.length; i++){
            doublenDW[i]=Double.parseDouble(tokens[i+1]);
        }
        return doublenDW;
    }

    public double[] parsenMW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        double[] doublenMW = new double[neurons];
        for(int i=0; i < doublenMW.length; i++){
            doublenMW[i]=Double.parseDouble(tokens[i+1]);
        }
        return doublenMW;
    }

    public double[] parsenNW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        double[] doublenNW = new double[neurons];
        for(int i=0; i < doublenNW.length; i++){
            doublenNW[i]=Double.parseDouble(tokens[i+1]);
        }
        return doublenNW;
    }


    public List<double[]> getnW(){
        return nW;
    }
    public List<double[]> getnDW(){
        return nDW;
    }
    public List<double[]> getnMW(){
        return nMW;
    }
    public List<double[]> getnNW(){
        return nNW;
    }

    public void writeEverythingToFile(List<double[]> nW,  List<double[]>nNW,List<double[]>nMW, List<double[]>nDW){
        this.nW=nW;
        this.nNW=nNW;
        this.nMW=nMW;
        this.nDW=nDW;

        StringBuilder content = new StringBuilder();
        for (double[] f : nW) {
            content.append("nW;");
            for(int i=0; i < f.length; i++){
                content.append(f[i]+";");
            }
            content.append("\n");
        }

        for (double[] f : nNW) {
            content.append("nNW;");
            for(int i=0; i < f.length; i++){
                content.append(f[i]+";");
            }
            content.append("\n");
        }

        for (double[] f : nMW) {
            content.append("nMW;");
            for(int i=0; i < f.length; i++){
                content.append(f[i]+";");
            }
            content.append("\n");
        }

        for (double[] f : nDW) {
            content.append("nDW;");
            for(int i=0; i < f.length; i++){
                content.append(f[i]+";");
            }
            content.append("\n");
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
            bw.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
