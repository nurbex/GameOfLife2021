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
    private int foodAmount=10;
    private int poisonAmount=10;
    private int stones=10;
    @FXML
    private Pane gameArena = new Pane();

    @FXML
    private Button startButton = new Button();

    private List<GameObject> allGameObjects= new ArrayList<>();

    private void startingContent(){
        //creating cell population
        int c = 0;
        while(c<cellPopulation){
            boolean match=false;
            int x= ((int) (Math.random()*gameArena.getPrefWidth()/10))*10;
            int y= ((int) (Math.random()*gameArena.getPrefHeight()/10))*10;
            for(GameObject e: allGameObjects){
                if((e.getShapeR().getX() == x)&&(e.getShapeR().getY() == y)){
                    match=true;
                }
            }
            if(!match){
                allGameObjects.add(new CellLife(x,y));
                c++;
                System.out.println(c + " c " + x + "," + y);
            }
        }
        //creating food amount
        int f = 0;
        while(f<cellPopulation){
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
                System.out.println(f + " f " + x + "," + y);
            }
        }
        //creating poison amount
        int p = 0;
        while(p<cellPopulation){
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
                System.out.println(p + " p " + x + "," + y);
            }
        }
        //creating stones
        int s = 0;
        while(s<cellPopulation){
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
                System.out.println(s + " s " + x + "," + y);
            }
        }

        System.out.println("current arraylist size: "+allGameObjects.size());
        for(GameObject e: allGameObjects){
            gameArena.getChildren().add(e.getShapeR());
        }

        /*for(int i=0;i<30;i++){
            for(int j=0;j<30;j++){
                gameArena.getChildren().add(new CellLife(i*10,j*10).getShapeR());
            }
        }*/
    }

    private void createContent(){

    }

    private void removeContent(){
        allGameObjects.clear();
        gameArena.getChildren().remove(0,gameArena.getChildren().size());
    }

    private Boolean contentIsEmpty(){
        return gameArena.getChildren().isEmpty();
    }

    private void onUpdate(){
        if(contentIsEmpty()){
            startingContent();
        } else {
            removeContent();
            startingContent();
        }
        createContent();
    }
    @FXML
    private void startButtonOnAction(){
        //startButton.setDisable(true);
 /*       AnimationTimer timer= new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(i==1){
                    onUpdate();
                    i++;
                }
            }
        };
        timer.start();*/
        onUpdate();
    }
}
