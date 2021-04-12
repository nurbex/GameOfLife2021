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
        float[] floatnW = new float[3];
        floatnW[0]=Float.parseFloat(tokens[1]);
        floatnW[1]=Float.parseFloat(tokens[2]);
        floatnW[2]=Float.parseFloat(tokens[3]);
        return floatnW;
    }

    public float[] parsenDW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        float[] floatnDW = new float[6];
        floatnDW[0]=Float.parseFloat(tokens[1]);
        floatnDW[1]=Float.parseFloat(tokens[2]);
        floatnDW[2]=Float.parseFloat(tokens[3]);
        floatnDW[3]=Float.parseFloat(tokens[4]);
        floatnDW[4]=Float.parseFloat(tokens[5]);
        floatnDW[5]=Float.parseFloat(tokens[6]);

        return floatnDW;
    }

    public float[] parsenMW(String line){
        // nW ; value0 ; value1 ;value2; value3; value4; value5; value6

        String[] tokens = line.split(";");
        float[] floatnMW = new float[6];
        floatnMW[0]=Float.parseFloat(tokens[1]);
        floatnMW[1]=Float.parseFloat(tokens[2]);
        floatnMW[2]=Float.parseFloat(tokens[3]);
        floatnMW[3]=Float.parseFloat(tokens[4]);
        floatnMW[4]=Float.parseFloat(tokens[5]);
        floatnMW[5]=Float.parseFloat(tokens[6]);

        return floatnMW;
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

    public void writeEverythingToFile(List<float[]> nW, List<float[]>nMW, List<float[]>nDW){
        this.nW=nW;
        this.nMW=nMW;
        this.nDW=nDW;

        StringBuilder content = new StringBuilder();
        for (float[] f : nW) {
            content.append("nW;"+f[0]+"; "+f[1]+"; "+f[2]+"\n");
        }

        for (float[] f : nMW) {
            content.append("nMW;"+f[0]+"; "+f[1]+"; "+f[2]+"; "+f[3]+"; "+f[4]+"; "+f[5]+"\n");
        }

        for (float[] f : nDW) {
            content.append("nDW;"+f[0]+"; "+f[1]+"; "+f[2]+"; "+f[3]+"; "+f[4]+"; "+f[5]+"\n");
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
            bw.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
