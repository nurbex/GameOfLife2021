package domain;

import repo.FileRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Brain {
    public Brain(){
        nWListCreation();
        nDWListCreation();
    }
    //cell has 7 eye
    //0.eyeN 1.eyeNN 2.eyeNW 3.eyeNE 4.eyeW 5.eyeE 6.eyeS
    // cells eyes scheme, o for eyes, @ for cell it self, looking north(n)
    //     o
    //   o o o
    //   o @ o
    //     o
    //cell has to decide what to do according what it sees
    //cell eye can see followings:
    //1. p for poison
    //2. n for nothing
    //3. w for wall
    //4. s for stones
    //5. c for other cells
    //6. f for food
    //cell brain has one hidden layer with 7 neurons
    // need 7 array list of weights for each 7 neuron
    // need 7 array list of weights for each 5 options of decision
    //decisions:
    //0. n for do nothing- by default
    //1. m for move
    //2. e for eat
    //3. l for turn left
    //4. r for turn right
    //for each action cell will lose energy
    //if cell eats food it gets energy
    //if cell eats poison it dies
    //if cell tries to it stone or wall or nothing it will lose energy

    //decision
    private char decision='n';
    private FileRepo fileRepo=new FileRepo();
    //what cell sees in digits
    private float[] cellSeesInDigits=new float[7];
    public void cellSeesToDigits(List<CellEye> cellEyes){
        for(int i=0; i<cellEyes.size();i++){
            switch (cellEyes.get(i).getEyeSees()){
                case 'p':
                    cellSeesInDigits[i]=0.50f;
                    break;
                case 'w':
                    cellSeesInDigits[i]=1.22f;
                    break;
                case 's':
                    cellSeesInDigits[i]=1.33f;
                    break;
                case 'c':
                    cellSeesInDigits[i]=1.44f;
                    break;
                case 'f':
                    cellSeesInDigits[i]=2.00f;
                    break;
                default:
                    cellSeesInDigits[i]=0.00f;
                    break;
            }
            //System.out.print(cellEyes.get(i).getEyeSees()+" ");
        }
       // System.out.print("----------");
    }
    //weights from eyes to neurons
    private List<float[]> nW = new ArrayList<>();
    public void setnW(List<float[]> nW){
        this.nW=nW;
    }
    public List<float[]> getnW(){
        return nW;
    }
    private void nWListCreation(){
        if(fileRepo.getnW().isEmpty()){
            for(int i=0;i<7;i++){
                nW.add(new float[7]);
                for(int z=0;z<nW.get(i).length;z++){
                    nW.get(i)[z]=(float) (Math.random()*5-Math.random()*5);
                    //System.out.print(nW.get(i)[z]+" ");
                }
            }
        }else{
            nW=fileRepo.getnW();
        }

    }
    //results to feed 7 neurons
    private float[] resultList=new float[7];
    //weights from neurons to decision
    private List<float[]> nDW = new ArrayList<>();
    public void setnDW(List<float[]> nDW){
        this.nDW=nDW;
    }
    public List<float[]> getnDW(){
        return nDW;
    }
    private void nDWListCreation(){
        if(fileRepo.getnDW().isEmpty()){
            for(int i=0;i<5;i++){
                nDW.add(new float[7]);
                for(int z=0;z<nDW.get(i).length;z++){
                    nDW.get(i)[z]=(float) (Math.random()*5-Math.random()*5);
                    //System.out.print(nDW.get(i)[z]+" ");
                }
            }
        }else{
            nDW=fileRepo.getnDW();
        }


    }
    //results to feed 5 decisions
    private float[] resultsForDecision=new float[5];

    //calculations
    private void calculation1(){
        for(int i=0;i<resultList.length; i++){
            resultList[i]=0;
            for(int k=0; k<cellSeesInDigits.length; k++){
                float product=cellSeesInDigits[k]*nW.get(i)[k];
                resultList[i]=resultList[i]+product;
            }
        }
    }
    private void calculation2(){
        for(int i=0; i<resultsForDecision.length; i++){
            resultsForDecision[i]=0;
            for(int k=0; k<resultList.length; k++){
                float product=resultList[k]*nDW.get(i)[k];
                resultsForDecision[i]=resultsForDecision[i]+product;
            }
            //System.out.print(resultsForDecision[i]+" ");
        }
    }
    public char cellThinking(){
        calculation1();
        calculation2();
        float max=0;
        int index=0;
        for(int i=0; i<resultsForDecision.length; i++){
            if(resultsForDecision[i]>max){
                max=resultsForDecision[i];
                index=i;
            }
        }
        switch (index){
            case 0:
                decision='n';
                break;
            case 1:
                decision='m';
                break;
            case 2:
                decision='e';
                break;
            case 3:
                decision='l';
                break;
            case 4:
                decision='r';
                break;
        }
        return decision;
    }

    public void brainWRandom(){
        if(Math.random()>0.5){
            randomMutationNW();
        }else{
            randomMutationNDW();
        }
        fileRepo.writeEverythingToFile(nW,nDW);
    }

    public void randomMutationNW(){
        int r=(int)(Math.random()*nW.size());
        int c=(int)(Math.random()*nW.get(0).length);
        if(Math.random()>0.5){
            nW.get(r)[c]=nW.get(r)[c] - 0.01f;
            System.out.print("nW- "+r+" "+c+" ");
        }else{
            nW.get(r)[c]=nW.get(r)[c] + 0.01f;
            System.out.print("nW+ "+r+" "+c+" ");
        }
    }
    public void randomMutationNDW(){
        int r=(int)(Math.random()*nDW.size());
        int c=(int)(Math.random()*nDW.get(0).length);
        if(Math.random()>0.5){
            nDW.get(r)[c]=nDW.get(r)[c] - 0.01f;
            System.out.print("nDW- "+r+" "+c+" ");
        }else{
            nDW.get(r)[c]=nDW.get(r)[c] + 0.01f;
            System.out.print("nDW+ "+r+" "+c+" ");
        }
    }
}
