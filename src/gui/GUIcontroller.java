package gui;

import domain.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class GUIcontroller<i> {
    private Boolean start = true;
    private int cellPopulation=10;
    private int foodAmount=100;
    private int poisonAmount=10;
    private int stones=40;
    @FXML
    private Pane gameArena = new Pane();

    @FXML
    private Button startButton = new Button();

    private List<GameObject> allGameObjects= new ArrayList<>();
    private List<CellLife> allCells= new ArrayList<>();

    //what cell sees and does
    private void cellsLiving(){
        if(!(allCells.isEmpty())){
            for(CellLife e: allCells){
                e.cellSeesAndActs(allGameObjects, allCells, gameArena);
            }
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

        System.out.println("current arraylist size: "+allGameObjects.size());
        for(GameObject e: allGameObjects){
            gameArena.getChildren().add(e.getShapeR());
        }
        for(CellLife e: allCells){
            gameArena.getChildren().add(e.getShapeR());
        }
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
            createContent();
        }
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
