package domain;

import repo.FileRepo;

import java.util.ArrayList;
import java.util.List;

public class Brain {
    public Brain(){
        nWListCreation();
        nNWListCreation();
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
    //      node node node
    //      node node node  y
    // o    node node node  y
    // o    node node node  y
    // o    node node node  y
    //      node node node  y
    //      node node node
    //      node node node
    //      node node node
    //      node node node
    //      node node node
    //      node node node

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
    private float[] cellSensesInDigits =new float[4];
    public void cellSeesToDigits(List<CellEye> cellEyes, boolean cantMove){
        for(int i=0; i<cellEyes.size()-1;i++){
            switch (cellEyes.get(i).getEyeSees()){
                case 'p':
                    cellSensesInDigits[i]=10f;
                    break;
                case 'w':
                    cellSensesInDigits[i]=-20f;
                    break;
                case 's':
                    cellSensesInDigits[i]=40f;
                    break;
                case 'c':
                    cellSensesInDigits[i]=70f;
                    break;
                case 'f':
                    cellSensesInDigits[i]=100f;
                    break;
                default:
                    cellSensesInDigits[i]=0.01f;
                    break;
            }
            //System.out.print(cellEyes.get(i).getEyeSees()+" ");
        }
        if(cantMove){
            cellSensesInDigits[3]=200;
            //System.out.println("cantmove true");
        }else{
            cellSensesInDigits[3]=-200;
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
            for(int i=0;i<12;i++){
                nW.add(new float[4]);
                for(int z=0;z<nW.get(i).length;z++){

                    nW.get(i)[z]=0.0f; //(float)Math.random();
                    //System.out.print(nW.get(i)[z]+" ");
                }
            }
        }else{
            nW=fileRepo.getnW();
        }

    }
    private float[] resultList1=new float[12];

    // adding one more layer

    private List<float[]> nNW = new ArrayList<>();

    public void setnNW(List<float[]> nNW){
        this.nNW=nNW;
    }
    public List<float[]> getnNW(){
        return nNW;
    }

    private void nNWListCreation(){
        if(fileRepo.getnNW().isEmpty()){
            for(int i=0;i<12;i++){
                nNW.add(new float[12]);
                for(int z=0;z<nNW.get(i).length;z++){
                    nNW.get(i)[z]=0.0f; //(float)Math.random();
                }
            }
        }else{
            nNW=fileRepo.getnNW();
        }
    }

    //results to feed 7 neurons
    private float[] resultList2 =new float[12];

    // adding one more layer

    private List<float[]> nMW = new ArrayList<>();

    public void setnMW(List<float[]> nMW){
        this.nMW=nMW;
    }
    public List<float[]> getnMW(){
        return nMW;
    }

    private void nMWListCreation(){
        if(fileRepo.getnMW().isEmpty()){
            for(int i=0;i<12;i++){
                nMW.add(new float[12]);
                for(int z=0;z<nMW.get(i).length;z++){
                    //nMW.get(i)[z]=(float)(Math.random()*5-Math.random()*5);
                    nMW.get(i)[z]=0.0f; //(float)Math.random();
                    //System.out.print(nMW.get(i)[z]+" ");
                }
            }
        }else{
            nMW=fileRepo.getnMW();
        }
    }

    //results to feed 7 neurons
    private float[] resultList3 =new float[12];
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
                nDW.add(new float[12]);
                for(int z=0;z<nDW.get(i).length;z++){
                    //nDW.get(i)[z]=(float)(Math.random()*5-Math.random()*5);
                    nDW.get(i)[z]=0.0f; //(float)Math.random();
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
            for(int k = 0; k< cellSensesInDigits.length; k++){
                float product= cellSensesInDigits[k]*nW.get(i)[k];
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
                float product=resultList1[k]*nNW.get(i)[k];
                resultList2[i]= resultList2[i]+product;
            }
            //sigmoid function
            //resultList2[i]=(float) (1/( 1 + Math.pow(Math.E,(-1*resultList2[i]))));

            //Ral.U
            if (resultList2[i]<0) {
                resultList2[i]=0;
            }
        }
    }
    private void calculation1c(){
        for(int i = 0; i< resultList3.length; i++){
            resultList3[i]=0;
            for(int k=0; k<resultList2.length; k++){
                float product=resultList2[k]*nMW.get(i)[k];
                resultList3[i]= resultList3[i]+product;
            }
            //Ral.U
            if (resultList3[i]<0) {
                resultList3[i]=0;
            }
        }
    }
    private void calculation2(){
        for(int i=0; i<resultsForDecision.length; i++){
            resultsForDecision[i]=0;
            for(int k = 0; k< resultList3.length; k++){
                float product= resultList3[k]*nDW.get(i)[k];
                resultsForDecision[i]=resultsForDecision[i]+product;
            }
            //System.out.println(resultsForDecision[i]+" result3 for decision ");
        }
    }
    public char cellThinking(){
        calculation1a();
        calculation1b();
        calculation1c();
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

        switch ((int)(Math.random()*4)){
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
            case 3:
                randomMutationNNW();
                //System.out.println("nNW");
                break;
        }

    }
    public void brainWriteToFile(){
        fileRepo.writeEverythingToFile(nW, nNW, nMW, nDW);
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

    public void randomMutationNNW(){
        int r=(int)(Math.random()*nNW.size());
        int c=(int)(Math.random()*nNW.get(0).length);
        if(Math.random()>0.5){
            nNW.get(r)[c]=nNW.get(r)[c] - 0.1f;
            //System.out.print("nNW- "+r+" "+c+" ");
        }else{
            nNW.get(r)[c]=nNW.get(r)[c] + 0.1f;
            //System.out.print("nNW+ "+r+" "+c+" ");
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
