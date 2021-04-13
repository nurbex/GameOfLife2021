package gui;

import domain.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GUIcontroller<i> {
    private Boolean start = true;
    private int cellPopulation=20;
    private int foodAmount=160;
    private int poisonAmount=15;
    private int stones=15;
    private int generation=0;
    private int maxLifeTime=100;
    @FXML
    private Pane gameArena = new Pane();

    @FXML
    private Pane cellZeroSees= new Pane();
    @FXML
    private Label cellDo=new Label();

    @FXML
    private NumberAxis generationOfCells= new NumberAxis();

    @FXML
    private NumberAxis maxLifeTotal= new NumberAxis();

    @FXML
    private AreaChart <Number, Number> generationChart = new AreaChart<Number, Number>(generationOfCells, maxLifeTotal);

    private XYChart.Series<Number, Number> generationSeries = new XYChart.Series<Number, Number>();

    @FXML
    private Button startButton = new Button();

    private List<GameObject> allGameObjects= new ArrayList<>();
    private List<CellLife> allCells= new ArrayList<>();
    private List<CellLife> allDeadCells= new ArrayList<>();
    private CellLife blueCell= new CellLife(40,40);

    //eyes of cell zero
    private Rectangle nEye= new Rectangle(10,10);
    private Rectangle eEye= new Rectangle(10,10);
    private Rectangle wEye= new Rectangle(10,10);
    private Rectangle blueCellRectangle= new Rectangle(10,10);
    char nEyeSees;
    char eEyeSees;
    char wEyeSees;

    //what cell sees and does
    private void cellsLiving(){
        if(!(allCells.isEmpty())){
            allCells.get(0).getShapeR().setFill(Color.color(0,0.5,1));
            cellZSees();
            for(CellLife e: allCells){
                e.cellSees(allGameObjects, allCells, gameArena);
                switch (e.cellsThinkAndAct()){
                    case 'n':
                        e.setCellFat(e.getCellFat()-1);
                        if(e.getCellFat()<=0){
                            e.setIsDead(true);
                        }
                        break;
                    case 'm':
                        e.cellMoves(allGameObjects, allCells, gameArena);
                        e.setCellFat(e.getCellFat()-1);
                        if(e.getCellFat()<=0){
                            e.setIsDead(true);
                        }
                        break;
                    case 'e':
                        e.cellEats(allGameObjects, allCells, gameArena);
                        e.setCellFat(e.getCellFat()-1);
                        if(e.getCellFat()<=0){
                            e.setIsDead(true);
                            //adding new food
                            boolean match=false;
                            boolean matchCellList=false;
                            int x= ((int) (Math.random()*gameArena.getPrefWidth()/10))*10;
                            int y= ((int) (Math.random()*gameArena.getPrefHeight()/10))*10;

                            for(GameObject q: allGameObjects){
                                if((q.getShapeR().getX() == x)&&(q.getShapeR().getY() == y)){
                                    match=true;
                                }
                            }
                            for(CellLife p: allCells){
                                if((p.getShapeR().getX() == x)&&(p.getShapeR().getY() == y)){
                                    matchCellList=true;
                                }
                            }
                            if((!match)&&(!matchCellList)){
                                allGameObjects.add(new Food(x,y));
                                match=false;
                                matchCellList=false;
                                //System.out.println("food added");
                            }
                        }
                        allGameObjects.removeIf(GameObject::getIsDead);
                        break;
                    case 'l':
                        e.cellTurnLeft();
                        e.setCellFat(e.getCellFat()-1);
                        if(e.getCellFat()<=0){
                            e.setIsDead(true);
                        }
                        break;
                    case 'r':
                        e.cellTurnRight();
                        e.setCellFat(e.getCellFat()-1);
                        if(e.getCellFat()<=0){
                            e.setIsDead(true);
                        }
                        break;
                    default:
                        System.out.println("Doing default");
                }
                /*System.out.print(e.cellsThinkAndAct() + " llllll " + e.getCellFat()+" ");
                for(int p=0;p<e.getCellEyes().size();p++){
                    System.out.print(e.getCellEyes().get(p).getEyeSees() +" ");
                }
                System.out.println(" ");*/
                e.setLifeTime(e.getLifeTime()+1);
                //System.out.println("this is each cell lifetime"+e.getLifeTime());
            }

            allDeadCells=allCells.stream().filter(CellLife::getIsDead).collect(Collectors.toList());
            //System.out.println(" Dead Cell size"+allDeadCells.size());
            allCells.removeIf(CellLife::getIsDead);
        }

        /*if(!allCells.isEmpty()){
            System.out.println(" what cell zero sees "+allCells.get(0).getCellEyes().get(0).getEyeSees()+" "+allCells.get(0).getCellEyes().get(1).getEyeSees()+" "+allCells.get(0).getCellEyes().get(2).getEyeSees()+" ");
        }*/
        //System.out.println("food amount: "+ allGameObjects.stream().filter(f -> f.getTypeO()=='f').collect(Collectors.toList()).size());
    }

    private void startingContent(){
        if(start){
            createChart();
        }
        //creating stones
        int s = 0;
        while(s<stones){
            boolean match=false;
            int x= ((int) (Math.random()*gameArena.getPrefWidth()/10))*10;
            int y= ((int) (Math.random()*gameArena.getPrefHeight()/10))*10;
            for(GameObject e: allGameObjects){
                if((e.getShapeR().getX() == x)&&(e.getShapeR().getY() == y)){
                    match=true;
                }
            }
            if(!match){
                allGameObjects.add(new Stone(x,y));
                s++;
                //System.out.println(s + " s " + x + "," + y);
            }
        }
        //creating food amount
        int f = 0;
        while(f<foodAmount){
            boolean match=false;
            int x= ((int) (Math.random()*gameArena.getPrefWidth()/10))*10;
            int y= ((int) (Math.random()*gameArena.getPrefHeight()/10))*10;
            for(GameObject e: allGameObjects){
                if((e.getShapeR().getX() == x)&&(e.getShapeR().getY() == y)){
                    match=true;
                }
            }
            if(!match){
                allGameObjects.add(new Food(x,y));
                f++;
                //System.out.println(f + " f " + x + "," + y);
            }
        }
        //creating poison amount
        int p = 0;
        while(p<poisonAmount){
            boolean match=false;
            int x= ((int) (Math.random()*gameArena.getPrefWidth()/10))*10;
            int y= ((int) (Math.random()*gameArena.getPrefHeight()/10))*10;
            for(GameObject e: allGameObjects){
                if((e.getShapeR().getX() == x)&&(e.getShapeR().getY() == y)){
                    match=true;
                }
            }
            if(!match){
                allGameObjects.add(new Poison(x,y));
                p++;
                //System.out.println(p + " p " + x + "," + y);
            }
        }
        //creating cell population
        int c = 0;
        while(c<cellPopulation){
            boolean match=false;
            boolean matchCellList=false;
            int x= ((int) (Math.random()*gameArena.getPrefWidth()/10))*10;
            int y= ((int) (Math.random()*gameArena.getPrefHeight()/10))*10;
            for(GameObject e: allGameObjects){
                if((e.getShapeR().getX() == x)&&(e.getShapeR().getY() == y)){
                    match=true;
                }
            }
            for(CellLife e: allCells){
                if((e.getShapeR().getX() == x)&&(e.getShapeR().getY() == y)){
                    matchCellList=true;
                }
            }
            if((!match)&&(!matchCellList)){
                allCells.add(new CellLife(x,y));
                allCells.get(allCells.size()-1).setRandomIsLooking();
                c++;
                //System.out.println(allCells.get(allCells.size()-1).getIsLooking());
            }
        }

        if(!start){

            int max=0;
            CellLife theLastHero = new CellLife();
            for(CellLife w:allDeadCells){
                if(w.getLifeTime()>max){
                    max=w.getLifeTime();
                    theLastHero=w;
                }
            }
            if(maxLifeTime<max){
                maxLifeTime=max;
                theLastHero.cellBrainToFile();
                System.out.println("-----------------------------------weights are recorded----------------------------------");
                System.out.println("this is max "+max);
                System.out.println("generation: "+ generation);
                generationSeries.getData().add(new XYChart.Data<Number, Number>(generation,maxLifeTime));

                for(int t=0;t<(cellPopulation/2);t++){
                    allCells.get(t).getCellBrain().setnW(theLastHero.getCellBrain().getnW());
                    allCells.get(t).getCellBrain().setnMW(theLastHero.getCellBrain().getnMW());
                    allCells.get(t).getCellBrain().setnDW(theLastHero.getCellBrain().getnDW());
                }

            }
            theLastHero.cellMutates();
            for(int t=cellPopulation/2;t<cellPopulation;t++){
                allCells.get(t).getCellBrain().setnW(theLastHero.getCellBrain().getnW());
                allCells.get(t).getCellBrain().setnMW(theLastHero.getCellBrain().getnMW());
                allCells.get(t).getCellBrain().setnDW(theLastHero.getCellBrain().getnDW());
            }
            /*allCells.get(0).cellMutates();
            for(int t=cellPopulation-6;t<cellPopulation;t++){
                allCells.get(t).getCellBrain().setnW(allCells.get(0).getCellBrain().getnW());
                allCells.get(t).getCellBrain().setnMW(allCells.get(0).getCellBrain().getnMW());
                allCells.get(t).getCellBrain().setnDW(allCells.get(0).getCellBrain().getnDW());
            }*/

            allDeadCells.clear();

            //System.out.println("this is max "+max);
        }
        generation++;
        maxLifeTime--;
        //System.out.println("generation: "+ generation);
        for(GameObject e: allGameObjects){
            gameArena.getChildren().add(e.getShapeR());
        }
        for(CellLife e: allCells){
            gameArena.getChildren().add(e.getShapeR());
        }
        if(!allCells.isEmpty()){
            allCells.get(0).getShapeR().setFill(Color.color(0,0.5,1));
            cellZSees();
        }
        start=false;
    }

    private void createContent(){
        cellsLiving();
        for(GameObject e: allGameObjects){
            gameArena.getChildren().add(e.getShapeR());
        }
        for(CellLife e: allCells){
            gameArena.getChildren().add(e.getShapeR());
        }
    }

    private void removeContent(){
        allGameObjects.clear();
        gameArena.getChildren().remove(0,gameArena.getChildren().size());
    }

    private void onUpdate(){
        if(allCells.isEmpty()){
            removeContent();
            startingContent();
            //cellZeroSees.getChildren().remove(0,gameArena.getChildren().size());
        }else{
            gameArena.getChildren().remove(0,gameArena.getChildren().size());
            //cellZeroSees.getChildren().remove(0,gameArena.getChildren().size());
            createContent();
        }
    }
    //what cell sees
    private void cellZSees(){
        if(!cellZeroSees.getChildren().isEmpty()){
            cellZeroSees.getChildren().remove(0,cellZeroSees.getChildren().size());
        }
        cellDo.setText(allCells.get(0).getCellBrain().cellThinking()+"\n"+allCells.get(0).getIsLooking()+"\n");

        nEyeSees= allCells.get(0).getCellEyes().get(0).getEyeSees();
        wEyeSees=allCells.get(0).getCellEyes().get(1).getEyeSees();
        eEyeSees=allCells.get(0).getCellEyes().get(2).getEyeSees();

        switch (nEyeSees){
            case 'p':
                nEye.setFill(Color.color(0.5,0.5,0.0));
                break;
            case 'w':
                nEye.setFill(Color.color(0.7,0.0,0.7));
                break;
            case 's':
                nEye.setFill(Color.color(0.3,0.3,0.3));
                break;
            case 'c':
                nEye.setFill(Color.color(0.3,0.7,0.0));
                break;
            case 'f':
                nEye.setFill(Color.color(0.8,0.5,0.0));
                break;
            default:
                nEye.setFill(Color.color(0.6,0.6,0.6));
                break;
            }
        switch (eEyeSees){
            case 'p':
                eEye.setFill(Color.color(0.5,0.5,0.0));
                break;
            case 'w':
                eEye.setFill(Color.color(0.7,0.0,0.7));
                break;
            case 's':
                eEye.setFill(Color.color(0.3,0.3,0.3));
                break;
            case 'c':
                eEye.setFill(Color.color(0.3,0.7,0.0));
                break;
            case 'f':
                eEye.setFill(Color.color(0.8,0.5,0.0));
                break;
            default:
                eEye.setFill(Color.color(0.6,0.6,0.6));
                break;
        }
        switch (wEyeSees){
            case 'p':
                wEye.setFill(Color.color(0.5,0.5,0.0));
                break;
            case 'w':
                wEye.setFill(Color.color(0.7,0.0,0.7));
                break;
            case 's':
                wEye.setFill(Color.color(0.3,0.3,0.3));
                break;
            case 'c':
                wEye.setFill(Color.color(0.3,0.7,0.0));
                break;
            case 'f':
                wEye.setFill(Color.color(0.8,0.5,0.0));
                break;
            default:
                wEye.setFill(Color.color(0.6,0.6,0.6));
                break;
        }

        int x=20;
        int y=20;
        blueCellRectangle.setFill(Color.color(0,0.5,1));
        blueCellRectangle.setX(x);
        blueCellRectangle.setY(y);
        nEye.setX(40);
        nEye.setY(30);

        wEye.setX(30);
        wEye.setY(40);

        eEye.setX(50);
        eEye.setY(40);

        switch (allCells.get(0).getIsLooking()){
            case 'n':

                nEye.setX(x);
                nEye.setY(y-10);

                wEye.setX(x-10);
                wEye.setY(y);

                eEye.setX(x+10);
                eEye.setY(y);

                break;
            case 'e':

                nEye.setX(x+10);
                nEye.setY(y);

                wEye.setX(x);
                wEye.setY(y-10);

                eEye.setX(x);
                eEye.setY(y+10);

                break;
            case 's':

                nEye.setX(x);
                nEye.setY(y+10);

                wEye.setX(x+10);
                wEye.setY(y);

                eEye.setX(x-10);
                eEye.setY(y);

                break;
            case 'w':

                nEye.setX(x-10);
                nEye.setY(y);

                wEye.setX(x);
                wEye.setY(y+10);

                eEye.setX(x);
                eEye.setY(y-10);

                break;
        }

        cellZeroSees.getChildren().add(nEye);
        cellZeroSees.getChildren().add(eEye);
        cellZeroSees.getChildren().add(wEye);
        cellZeroSees.getChildren().add(blueCellRectangle);
    }

    //create chart
    private void createChart(){
        generationSeries.setName("Survived cells");
        generationChart.setCreateSymbols(false);
        generationChart.getData().add(generationSeries);
    }

    @FXML
    private void startButtonOnAction(){
        startButton.setDisable(true);
        AnimationTimer timer= new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
    }
}
