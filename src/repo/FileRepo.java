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
        float[] floatnDW = new float[7];
        floatnDW[0]=Float.parseFloat(tokens[1]);
        floatnDW[1]=Float.parseFloat(tokens[2]);
        floatnDW[2]=Float.parseFloat(tokens[3]);
        floatnDW[3]=Float.parseFloat(tokens[4]);
        floatnDW[4]=Float.parseFloat(tokens[5]);
        floatnDW[5]=Float.parseFloat(tokens[6]);
        floatnDW[6]=Float.parseFloat(tokens[7]);

        return floatnDW;
    }

    public List<float[]> getnW(){
        return nW;
    }
    public List<float[]> getnDW(){
        return nDW;
    }

    public void writeEverythingToFile(List<float[]> nW, List<float[]>nDW){
        this.nW=nW;
        this.nDW=nDW;

        StringBuilder content = new StringBuilder();
        for (float[] f : nW) {
            content.append("nW;"+f[0]+"; "+f[1]+"; "+f[2]+"\n");
        }

        for (float[] f : nDW) {
            content.append("nDW;"+f[0]+"; "+f[1]+"; "+f[2]+"; "+f[3]+"; "+f[4]+"; "+f[5]+"; "+f[6]+"\n");
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
            bw.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
