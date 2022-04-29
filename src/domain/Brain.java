package domain;

import repo.FileRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Brain {
    private final int neurons=20;
    private final double mutation_level= 0.01;
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
    //                                  node node node
    //                                  node node node  y
    // o                                node node node  y
    // o                                node node node  y
    // o                                node node node  y
    // o senses if it hits something    node node node  y
    //                                  node node node
    //                                  node node node
    //                                  node node node
    //                                  node node node
    //                                  node node node
    //                                  node node node

    //cell brain has one hidden layer with 7 neurons
    // need 7 array list of weights for each 7 neuron
    // need 7 array list of weights for each 5 options of decision.
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
    private double decisionN=0.0;
    private double decisionM=0.0;
    private double decisionE=0.0;
    private double decisionL=0.0;
    private double decisionR=0.0;
    private final FileRepo fileRepo=new FileRepo(neurons);
    //what cell sees in digits
    private final double[] inputLayer =new double[11];
    public void senseToDigits(List<CellEye> cellEyes, boolean cantMove, boolean cantEat, int cellFat){
        for(int i=0; i<cellEyes.size()-1;i++){
            switch (cellEyes.get(i).getEyeSees()){
                case 'p':
                    inputLayer[i]=sigmoid(10);
                    break;
                case 'w':
                    inputLayer[i]=sigmoid(-20);
                    break;
                case 's':
                    inputLayer[i]=sigmoid(40);
                    break;
                case 'c':
                    inputLayer[i]=sigmoid(70);
                    break;
                case 'f':
                    inputLayer[i]=sigmoid(100);
                    break;
                default:
                    inputLayer[i]=sigmoid(0.01);
                    break;
            }
            //System.out.print(cellEyes.get(i).getEyeSees()+" ");
        }
        //if hits something it will know about it
        if(cantMove){
            inputLayer[3]=sigmoid(200);
            //System.out.println("cantmove true");
        }else{
            inputLayer[3]=sigmoid(-200);
        }

        //if cell cant eat it something it will know

        if(cantEat){
            inputLayer[4]=sigmoid(400);
            //System.out.println("cantEat true");
        }else{
            inputLayer[4]=sigmoid(-400);
        }
        //it will know how hungry it is
        inputLayer[5]=sigmoid(cellFat);


        //it will know what it did last time, last decision
        inputLayer[6]=sigmoid(decisionM);
        inputLayer[7]=sigmoid(decisionE);
        inputLayer[8]=sigmoid(decisionL);
        inputLayer[9]=sigmoid(decisionR);
        inputLayer[10]=sigmoid(decisionN);

        //System.out.print("----------");
    }
    //weights from eyes to neurons
    private List<double[]> nW = new ArrayList<>();
    public void setnW(List<double[]> nW){
        this.nW=nW;
    }
    public List<double[]> getnW(){
        return nW;
    }
    private void nWListCreation(){
        if(fileRepo.getnW().isEmpty()){
            for(int i=0;i<neurons;i++){
                nW.add(new double[11]);
                //(float)Math.random();
                //System.out.print(nW.get(i)[z]+" ");
                Arrays.fill(nW.get(i), 0.0);
            }
        }else{
            nW=fileRepo.getnW();
        }

    }
    private final double[] resultList1=new double[neurons];

    // adding one more layer

    private List<double[]> nNW = new ArrayList<>();

    public void setnNW(List<double[]> nNW){
        this.nNW=nNW;
    }
    public List<double[]> getnNW(){
        return nNW;
    }

    private void nNWListCreation(){
        if(fileRepo.getnNW().isEmpty()){
            for(int i=0;i<neurons;i++){
                nNW.add(new double[neurons]);
                //(float)Math.random();
                Arrays.fill(nNW.get(i), 0.0);
            }
        }else{
            nNW=fileRepo.getnNW();
        }
    }

    //results to feed 7 neurons
    private final double[] resultList2 =new double[neurons];

    // adding one more layer

    private List<double[]> nMW = new ArrayList<>();

    public void setnMW(List<double[]> nMW){
        this.nMW=nMW;
    }
    public List<double[]> getnMW(){
        return nMW;
    }

    private void nMWListCreation(){
        if(fileRepo.getnMW().isEmpty()){
            for(int i=0;i<neurons;i++){
                nMW.add(new double[neurons]);
                //nMW.get(i)[z]=(float)(Math.random()*5-Math.random()*5);
                //(float)Math.random();
                //System.out.print(nMW.get(i)[z]+" ");
                Arrays.fill(nMW.get(i), 0.0);
            }
        }else{
            nMW=fileRepo.getnMW();
        }
    }

    //results to feed 7 neurons
    private final double[] resultList3 =new double[neurons];
    //weights from neurons to decision
    private List<double[]> nDW = new ArrayList<>();
    public void setnDW(List<double[]> nDW){
        this.nDW=nDW;
    }
    public List<double[]> getnDW(){
        return nDW;
    }
    private void nDWListCreation(){
        if(fileRepo.getnDW().isEmpty()){
            for(int i=0;i<5;i++){
                nDW.add(new double[neurons]);
                //nDW.get(i)[z]=(float)(Math.random()*5-Math.random()*5);
                //(float)Math.random();
                //System.out.print(nDW.get(i)[z]+" ");
                Arrays.fill(nDW.get(i), 0.0);
            }
        }else{
            nDW=fileRepo.getnDW();
        }
    }
    //results to feed 5 decisions
    private final double[] resultsForDecision=new double[5];

    //calculations

    private void calculation1a(){
        for(int i = 0; i< resultList1.length; i++){
            resultList1[i]=0;
            for(int k = 0; k< inputLayer.length; k++){
                double product= inputLayer[k]*nW.get(i)[k];
                resultList1[i]= resultList1[i]+product;
            }
            //sigmoid function
            //resultList1[i]= (1/( 1 + Math.pow(Math.E,(-1*resultList1[i]))));
        }
    }
    private void calculation1b(){
        for(int i = 0; i< resultList2.length; i++){
            resultList2[i]=0;
            for(int k=0; k<resultList1.length; k++){
                double product=resultList1[k]*nNW.get(i)[k];
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
                double product=resultList2[k]*nMW.get(i)[k];
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
                double product= resultList3[k]*nDW.get(i)[k];
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
        double max=0;
        int index=0;
        for(int i=0; i<resultsForDecision.length; i++){
            if(resultsForDecision[i]>max){
                max=resultsForDecision[i];
                index=i;
            }
        }
        decisionN=0.0;
        decisionM=0.0;
        decisionE=0.0;
        decisionL=0.0;
        decisionR=0.0;
        switch (index){
            case 0:
                decision='n';
                decisionN=300.0;
                break;
            case 1:
                decision='m';
                decisionM=300.0;
                break;
            case 2:
                decision='e';
                decisionE=300.0;
                break;
            case 3:
                decision='l';
                decisionL=300.0;
                break;
            case 4:
                decision='r';
                decisionR=300.0;
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
            nW.get(r)[c]=nW.get(r)[c] - mutation_level;
            //System.out.print("nW- "+r+" "+c+" ");
        }else{
            nW.get(r)[c]=nW.get(r)[c] + mutation_level;
            //System.out.print("nW+ "+r+" "+c+" ");
        }
    }

    public void randomMutationNNW(){
        int r=(int)(Math.random()*nNW.size());
        int c=(int)(Math.random()*nNW.get(0).length);
        if(Math.random()>0.5){
            nNW.get(r)[c]=nNW.get(r)[c] - mutation_level;
            //System.out.print("nNW- "+r+" "+c+" ");
        }else{
            nNW.get(r)[c]=nNW.get(r)[c] + mutation_level;
            //System.out.print("nNW+ "+r+" "+c+" ");
        }
    }

    public void randomMutationNDW(){
        int r=(int)(Math.random()*nDW.size());
        int c=(int)(Math.random()*nDW.get(0).length);
        if(Math.random()>0.5){
            nDW.get(r)[c]=nDW.get(r)[c] - mutation_level;
            //System.out.print("nDW- "+r+" "+c+" ");
        }else{
            nDW.get(r)[c]=nDW.get(r)[c] + mutation_level;
            //System.out.print("nDW+ "+r+" "+c+" ");
        }
    }

    public void randomMutationNMW(){
        int r=(int)(Math.random()*nMW.size());
        int c=(int)(Math.random()*nMW.get(0).length);
        if(Math.random()>0.5){
            nMW.get(r)[c]=nMW.get(r)[c] - mutation_level;
            //System.out.print("nMW- "+r+" "+c+" ");
        }else{
            nMW.get(r)[c]=nMW.get(r)[c] + mutation_level;
            //System.out.print("nMW+ "+r+" "+c+" ");
        }
    }
    public double sigmoid(double x) {
        return (1/( 1 + Math.pow(Math.E,(-1*x))));
    }
}
