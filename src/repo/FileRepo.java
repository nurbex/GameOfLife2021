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

    private List<float[]> nW = new ArrayList<>();
    private List<float[]> nNW = new ArrayList<>();
    private List<float[]> nMW = new ArrayList<>();
    private List<float[]> nDW = new ArrayList<>();



    public FileRepo(){
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


    public float[] parsenW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        float[] floatnW = new float[10];
        for(int i=0; i < floatnW.length; i++){
            floatnW[i]=Float.parseFloat(tokens[i+1]);
        }
        return floatnW;
    }

    public float[] parsenDW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        float[] floatnDW = new float[30];
        for(int i=0; i < floatnDW.length; i++){
            floatnDW[i]=Float.parseFloat(tokens[i+1]);
        }
        return floatnDW;
    }

    public float[] parsenMW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        float[] floatnMW = new float[30];
        for(int i=0; i < floatnMW.length; i++){
            floatnMW[i]=Float.parseFloat(tokens[i+1]);
        }
        return floatnMW;
    }

    public float[] parsenNW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        float[] floatnNW = new float[30];
        for(int i=0; i < floatnNW.length; i++){
            floatnNW[i]=Float.parseFloat(tokens[i+1]);
        }
        return floatnNW;
    }


    public List<float[]> getnW(){
        return nW;
    }
    public List<float[]> getnDW(){
        return nDW;
    }
    public List<float[]> getnMW(){
        return nMW;
    }
    public List<float[]> getnNW(){
        return nNW;
    }

    public void writeEverythingToFile(List<float[]> nW,  List<float[]>nNW,List<float[]>nMW, List<float[]>nDW){
        this.nW=nW;
        this.nNW=nNW;
        this.nMW=nMW;
        this.nDW=nDW;

        StringBuilder content = new StringBuilder();
        for (float[] f : nW) {
            content.append("nW;");
            for(int i=0; i < f.length; i++){
                content.append(f[i]+";");
            }
            content.append("\n");
        }

        for (float[] f : nNW) {
            content.append("nNW;");
            for(int i=0; i < f.length; i++){
                content.append(f[i]+";");
            }
            content.append("\n");
        }

        for (float[] f : nMW) {
            content.append("nMW;");
            for(int i=0; i < f.length; i++){
                content.append(f[i]+";");
            }
            content.append("\n");
        }

        for (float[] f : nDW) {
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
