package domain;

import java.util.ArrayList;
import java.util.List;

public class Brain {
    private List<CellEye> cellEyes=new ArrayList<>();
    public Brain(List<CellEye> cellEyes){
        this.cellEyes=cellEyes;
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
    //what cell sees in digits
    private int[] cellSeesInDigits=new int[7];
    private void cellSeesToDigits(){
        for(int i=0; i<cellEyes.size();i++){
            switch (cellEyes.get(i).getEyeSees()){
                case 'p':
                    cellSeesInDigits[i]=1;
                    break;
                case 'n':
                    cellSeesInDigits[i]=2;
                    break;
                case 'w':
                    cellSeesInDigits[i]=3;
                    break;
                case 's':
                    cellSeesInDigits[i]=4;
                    break;
                case 'c':
                    cellSeesInDigits[i]=5;
                    break;
                case 'f':
                    cellSeesInDigits[i]=6;
                    break;
            }
        }
    }
    //weights from eyes to neurons
    private List<int[]> nW = new ArrayList<>();
    private void nWListCreation(){
        for(int i=0;i<7;i++){
            nW.add(new int[7]);
            for(int z=0;z<nW.get(i).length;z++){
                nW.get(i)[z]=(int)(Math.random()*5);
            }
        }
    }
    //results to feed 7 neurons
    private int[] resultList=new int[7];
    //weights from neurons to decision
    private List<int[]> nDW = new ArrayList<>();
    private void nDWListCreation(){
        for(int i=0;i<5;i++){
            nDW.add(new int[7]);
            for(int z=0;z<nDW.get(i).length;z++){
                nDW.get(i)[z]=(int)(Math.random()*5);
            }
        }
    }
    //results to feed 5 decisions
    private int[] resultsForDecision=new int[5];

    //calculations
    private void calculation1(){
        for(int i=0;i<resultList.length; i++){
            resultList[i]=0;
            for(int k=0; k<cellSeesInDigits.length; k++){
                int product=cellSeesInDigits[k]*nW.get(i)[k];
                resultList[i]=resultList[i]+product;
            }
        }
    }
    private void calculation2(){
        for(int i=0; i<resultsForDecision.length; i++){
            resultsForDecision[i]=0;
            for(int k=0; k<resultList.length; k++){
                int product=resultList[k]*nDW.get(i)[k];
                resultsForDecision[i]=resultsForDecision[i]+product;
            }
        }
    }
    public char cellThinking(){
        cellSeesToDigits();
        calculation1();
        calculation2();
        int max=0;
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


}
