package domain;

import repo.FileRepo;

import java.util.ArrayList;
import java.util.List;

public class Brain {
    public Brain(){
        nWListCreation();
        nMWListCreation();
        nDWListCreation();
    }
    //cell has 3 eye
    //0.eyeN 1.eyeNN 2.eyeNW 3.eyeNE 4.eyeW 5.eyeE 6.eyeS
    // cells eyes scheme, o for eyes, @ for cell it self, looking north(n)

    //     o
    //   o @ o

    //cell has to decide what to do according what it sees
    //cell eye can see followings:
    //1. p for poison
    //2. n for nothing
    //3. w for wall
    //4. s for stones
    //5. c for other cells
    //6. f for food
    //      node node
    //      node node   y
    // o    node node   y
    // o    node node   y
    // o    node node   y
    //      node node   y

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
    private float[] cellSeesInDigits=new float[3];
    public void cellSeesToDigits(List<CellEye> cellEyes){
        for(int i=0; i<cellEyes.size();i++){
            switch (cellEyes.get(i).getEyeSees()){
                case 'p':
                    cellSeesInDigits[i]=10f;
                    break;
                case 'w':
                    cellSeesInDigits[i]=-20f;
                    break;
                case 's':
                    cellSeesInDigits[i]=40f;
                    break;
                case 'c':
                    cellSeesInDigits[i]=70f;
                    break;
                case 'f':
                    cellSeesInDigits[i]=100f;
                    break;
                default:
                    cellSeesInDigits[i]=0.01f;
                    break;
            }
            //System.out.print(cellEyes.get(i).getEyeSees()+" ");
        }
        //System.out.print("----------");
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
            for(int i=0;i<6;i++){
                nW.add(new float[3]);
                for(int z=0;z<nW.get(i).length;z++){

                    nW.get(i)[z]=(float)Math.random();
                    //System.out.print(nW.get(i)[z]+" ");
                }
            }
        }else{
            nW=fileRepo.getnW();
        }

    }
    private float[] resultList1=new float[6];

    // adding one more layer
    //weights from neurons to decision
    private List<float[]> nMW = new ArrayList<>();

    public void setnMW(List<float[]> nMW){
        this.nMW=nMW;
    }
    public List<float[]> getnMW(){
        return nMW;
    }

    private void nMWListCreation(){
        if(fileRepo.getnMW().isEmpty()){
            for(int i=0;i<6;i++){
                nMW.add(new float[6]);
                for(int z=0;z<nMW.get(i).length;z++){
                    //nMW.get(i)[z]=(float)(Math.random()*5-Math.random()*5);
                    nMW.get(i)[z]=(float)Math.random();
                    //System.out.print(nMW.get(i)[z]+" ");
                }
            }
        }else{
            nMW=fileRepo.getnMW();
        }
    }

    //results to feed 7 neurons
    private float[] resultList2 =new float[6];
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
                nDW.add(new float[6]);
                for(int z=0;z<nDW.get(i).length;z++){
                    //nDW.get(i)[z]=(float)(Math.random()*5-Math.random()*5);
                    nDW.get(i)[z]=(float)Math.random();
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

    private void calculation1a(){
        for(int i = 0; i< resultList1.length; i++){
            resultList1[i]=0;
            for(int k=0; k<cellSeesInDigits.length; k++){
                float product=cellSeesInDigits[k]*nW.get(i)[k];
                resultList1[i]= resultList1[i]+product;
            }
            //sigmoid function
            resultList1[i]=(float) (1/( 1 + Math.pow(Math.E,(-1*resultList1[i]))));
        }
    }

    private void calculation1b(){
        for(int i = 0; i< resultList2.length; i++){
            resultList2[i]=0;
            for(int k=0; k<resultList1.length; k++){
                float product=resultList1[k]*nMW.get(i)[k];
                resultList2[i]= resultList2[i]+product;
            }
            //Ral.U
            if (resultList2[i]<0) {
                resultList2[i]=0;
            }
        }
    }
    private void calculation2(){
        for(int i=0; i<resultsForDecision.length; i++){
            resultsForDecision[i]=0;
            for(int k = 0; k< resultList2.length; k++){
                float product= resultList2[k]*nDW.get(i)[k];
                resultsForDecision[i]=resultsForDecision[i]+product;
            }
            //System.out.println(resultsForDecision[i]+" result2 for decision ");
        }
    }
    public char cellThinking(){
        calculation1a();
        calculation1b();
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

        switch ((int)(Math.random()*3)){
            case 0:
                randomMutationNW();
                //System.out.println("nW");
                break;
            case 1:
                randomMutationNMW();
                //System.out.println("nMW");
                break;
            case 2:
                randomMutationNDW();
                //System.out.println("nDW");
                break;
        }

    }
    public void brainWriteToFile(){
        fileRepo.writeEverythingToFile(nW, nMW, nDW);
    }

    public void randomMutationNW(){
        int r=(int)(Math.random()*nW.size());
        int c=(int)(Math.random()*nW.get(0).length);
        if(Math.random()>0.5){
            nW.get(r)[c]=nW.get(r)[c] - 0.1f;
            //System.out.print("nW- "+r+" "+c+" ");
        }else{
            nW.get(r)[c]=nW.get(r)[c] + 0.1f;
            //System.out.print("nW+ "+r+" "+c+" ");
        }
    }
    public void randomMutationNDW(){
        int r=(int)(Math.random()*nDW.size());
        int c=(int)(Math.random()*nDW.get(0).length);
        if(Math.random()>0.5){
            nDW.get(r)[c]=nDW.get(r)[c] - 0.1f;
            //System.out.print("nDW- "+r+" "+c+" ");
        }else{
            nDW.get(r)[c]=nDW.get(r)[c] + 0.1f;
            //System.out.print("nDW+ "+r+" "+c+" ");
        }
    }

    public void randomMutationNMW(){
        int r=(int)(Math.random()*nMW.size());
        int c=(int)(Math.random()*nMW.get(0).length);
        if(Math.random()>0.5){
            nMW.get(r)[c]=nMW.get(r)[c] - 0.1f;
            //System.out.print("nMW- "+r+" "+c+" ");
        }else{
            nMW.get(r)[c]=nMW.get(r)[c] + 0.1f;
            //System.out.print("nMW+ "+r+" "+c+" ");
        }
    }
}
