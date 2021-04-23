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
        float[] floatnW = new float[4];
        floatnW[0]=Float.parseFloat(tokens[1]);
        floatnW[1]=Float.parseFloat(tokens[2]);
        floatnW[2]=Float.parseFloat(tokens[3]);
        floatnW[3]=Float.parseFloat(tokens[4]);
        return floatnW;
    }

    public float[] parsenDW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        float[] floatnDW = new float[12];
        floatnDW[0]=Float.parseFloat(tokens[1]);
        floatnDW[1]=Float.parseFloat(tokens[2]);
        floatnDW[2]=Float.parseFloat(tokens[3]);
        floatnDW[3]=Float.parseFloat(tokens[4]);
        floatnDW[4]=Float.parseFloat(tokens[5]);
        floatnDW[5]=Float.parseFloat(tokens[6]);
        floatnDW[6]=Float.parseFloat(tokens[7]);
        floatnDW[7]=Float.parseFloat(tokens[8]);
        floatnDW[8]=Float.parseFloat(tokens[9]);
        floatnDW[9]=Float.parseFloat(tokens[10]);
        floatnDW[10]=Float.parseFloat(tokens[11]);
        floatnDW[11]=Float.parseFloat(tokens[12]);

        return floatnDW;
    }

    public float[] parsenMW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        float[] floatnMW = new float[12];
        floatnMW[0]=Float.parseFloat(tokens[1]);
        floatnMW[1]=Float.parseFloat(tokens[2]);
        floatnMW[2]=Float.parseFloat(tokens[3]);
        floatnMW[3]=Float.parseFloat(tokens[4]);
        floatnMW[4]=Float.parseFloat(tokens[5]);
        floatnMW[5]=Float.parseFloat(tokens[6]);
        floatnMW[6]=Float.parseFloat(tokens[7]);
        floatnMW[7]=Float.parseFloat(tokens[8]);
        floatnMW[8]=Float.parseFloat(tokens[9]);
        floatnMW[9]=Float.parseFloat(tokens[10]);
        floatnMW[10]=Float.parseFloat(tokens[11]);
        floatnMW[11]=Float.parseFloat(tokens[12]);

        return floatnMW;
    }

    public float[] parsenNW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        float[] floatnNW = new float[12];
        floatnNW[0]=Float.parseFloat(tokens[1]);
        floatnNW[1]=Float.parseFloat(tokens[2]);
        floatnNW[2]=Float.parseFloat(tokens[3]);
        floatnNW[3]=Float.parseFloat(tokens[4]);
        floatnNW[4]=Float.parseFloat(tokens[5]);
        floatnNW[5]=Float.parseFloat(tokens[6]);
        floatnNW[6]=Float.parseFloat(tokens[7]);
        floatnNW[7]=Float.parseFloat(tokens[8]);
        floatnNW[8]=Float.parseFloat(tokens[9]);
        floatnNW[9]=Float.parseFloat(tokens[10]);
        floatnNW[10]=Float.parseFloat(tokens[11]);
        floatnNW[11]=Float.parseFloat(tokens[12]);

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
            content.append("nW;"+f[0]+"; "+f[1]+"; "+f[2]+";"+f[3]+"\n");
        }

        for (float[] f : nNW) {
            content.append("nNW;"+f[0]+"; "+f[1]+"; "+f[2]+"; "+f[3]+"; "+f[4]+"; "+f[5]+"; "+f[6]+"; "+f[7]+"; "+f[8]+"; "+f[9]+"; "+f[10]+"; "+f[11]+"\n");
        }

        for (float[] f : nMW) {
            content.append("nMW;"+f[0]+"; "+f[1]+"; "+f[2]+"; "+f[3]+"; "+f[4]+"; "+f[5]+"; "+f[6]+"; "+f[7]+"; "+f[8]+"; "+f[9]+"; "+f[10]+"; "+f[11]+"\n");
        }

        for (float[] f : nDW) {
            content.append("nDW;"+f[0]+"; "+f[1]+"; "+f[2]+"; "+f[3]+"; "+f[4]+"; "+f[5]+"; "+f[6]+"; "+f[7]+"; "+f[8]+"; "+f[9]+"; "+f[10]+"; "+f[11]+"\n");
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
            bw.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
