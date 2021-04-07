package gui;

import domain.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIcontroller<i> {
    private Boolean start = true;
    private int cellPopulation=20;
    private int foodAmount=30;
    private int poisonAmount=5;
    private int stones=5;
    private int generation=0;
    @FXML
    private Pane gameArena = new Pane();

    @FXML
    private Pane cellZeroSees= new Pane();

    @FXML
    private Button startButton = new Button();

    private List<GameObject> allGameObjects= new ArrayList<>();
    private List<CellLife> allCells= new ArrayList<>();
    private List<CellLife> allDeadCells= new ArrayList<>();
    private CellLife blueCell= new CellLife(40,40);

    //what cell sees and does
    private void cellsLiving(){
        if(!(allCells.isEmpty())){
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
                        }

                        boolean match=false;
                        boolean matchCellList=true;
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
                        }

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
    }

    private void startingContent(){

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
            theLastHero.cellBrainToFile();
            allDeadCells.clear();
            for(int t=0;t<(cellPopulation/2);t++){
                allCells.get(t).getCellBrain().setnW(theLastHero.getCellBrain().getnW());
                allCells.get(t).getCellBrain().setnDW(theLastHero.getCellBrain().getnDW());
            }
            theLastHero.cellMutates();
            for(int t=cellPopulation/2;t<cellPopulation;t++){
                allCells.get(t).getCellBrain().setnW(theLastHero.getCellBrain().getnW());
                allCells.get(t).getCellBrain().setnDW(theLastHero.getCellBrain().getnDW());
            }
            System.out.println("this is max "+max);
        }
        generation++;
        allCells.get(0).getShapeR().setFill(Color.color(0,0.5,1));

        System.out.println("generation: "+ generation);
        for(GameObject e: allGameObjects){
            gameArena.getChildren().add(e.getShapeR());
        }
        for(CellLife e: allCells){
            gameArena.getChildren().add(e.getShapeR());
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
        }else{
            gameArena.getChildren().remove(0,gameArena.getChildren().size());
            //cellZeroSees.getChildren().remove(0,gameArena.getChildren().size());
            createContent();

        }
    }
    //what cell sees
    private void cellZSees(){
        blueCell.getCellEyes().get(0).setEyeSees(allCells.get(0).getCellEyes().get(0).getEyeSees());
        blueCell.getCellEyes().get(1).setEyeSees(allCells.get(0).getCellEyes().get(1).getEyeSees());
        blueCell.getCellEyes().get(2).setEyeSees(allCells.get(0).getCellEyes().get(2).getEyeSees());
        blueCell.getCellEyes().get(3).setEyeSees(allCells.get(0).getCellEyes().get(3).getEyeSees());
        blueCell.getCellEyes().get(4).setEyeSees(allCells.get(0).getCellEyes().get(4).getEyeSees());
        blueCell.getCellEyes().get(5).setEyeSees(allCells.get(0).getCellEyes().get(5).getEyeSees());
        blueCell.getCellEyes().get(6).setEyeSees(allCells.get(0).getCellEyes().get(6).getEyeSees());

        for(CellEye e: blueCell.getCellEyes()){
            switch (e.getEyeSees()){
                case 'p':
                    e.getShapeR().setFill(Color.color(0.5,0.5,0.0));
                    break;
                case 'w':
                    e.getShapeR().setFill(Color.color(0.5,0.5,0.5));
                    break;
                case 's':
                    e.getShapeR().setFill(Color.color(0.3,0.3,0.3));
                    break;
                case 'c':
                    e.getShapeR().setFill(Color.color(0.3,0.7,0.0));
                    break;
                case 'f':
                    e.getShapeR().setFill(Color.color(0.8,0.5,0.0));
                    break;
                default:
                    e.getShapeR().setFill(Color.color(0.7,0.7,0.7));
                    break;
            }

        }
        blueCell.getCellEyes().get(0).getShapeR().setX(40);
        blueCell.getCellEyes().get(0).getShapeR().setY(30);

        blueCell.getCellEyes().get(1).getShapeR().setX(40);
        blueCell.getCellEyes().get(1).getShapeR().setY(20);

        blueCell.getCellEyes().get(2).getShapeR().setX(30);
        blueCell.getCellEyes().get(2).getShapeR().setY(30);

        blueCell.getCellEyes().get(3).getShapeR().setX(50);
        blueCell.getCellEyes().get(3).getShapeR().setY(30);

        blueCell.getCellEyes().get(4).getShapeR().setX(30);
        blueCell.getCellEyes().get(4).getShapeR().setY(40);

        blueCell.getCellEyes().get(5).getShapeR().setX(50);
        blueCell.getCellEyes().get(5).getShapeR().setY(40);

        blueCell.getCellEyes().get(6).getShapeR().setX(40);
        blueCell.getCellEyes().get(6).getShapeR().setY(50);

        cellZeroSees.getChildren().add(blueCell.getShapeR());

        cellZeroSees.getChildren().add(blueCell.getCellEyes().get(0).getShapeR());
        cellZeroSees.getChildren().add(blueCell.getCellEyes().get(1).getShapeR());
        cellZeroSees.getChildren().add(blueCell.getCellEyes().get(2).getShapeR());
        cellZeroSees.getChildren().add(blueCell.getCellEyes().get(3).getShapeR());
        cellZeroSees.getChildren().add(blueCell.getCellEyes().get(4).getShapeR());
        cellZeroSees.getChildren().add(blueCell.getCellEyes().get(5).getShapeR());
        cellZeroSees.getChildren().add(blueCell.getCellEyes().get(6).getShapeR());
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
