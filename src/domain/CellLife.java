package domain;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CellLife extends GameObject{
    public CellLife(){}
    public CellLife(int x, int y){
        super.getShapeR().setArcHeight(3.0);
        super.getShapeR().setArcWidth(3);
        super.getShapeR().setFill(Color.color(0.3,0.7,0.0));
        super.getShapeR().setX(x);
        super.getShapeR().setY(y);
        super.setTypeO('c');
    }
    //creating cell properties
    private String cellID= UUID.randomUUID().toString();
    private int cellGeneration;
    private int cellFat =200;
    private char isLooking = 'n';
    //getting data from GUI controller
    private Pane gameArena = new Pane();
    private List<GameObject> allGameObjects= new ArrayList<>();
    private List<CellLife> allCells= new ArrayList<>();
    private boolean hitWall=false;
    private List<CellEye> cellEyes=new ArrayList<>();
    //0.eyeN 1.eyeNN 2.eyeNW 3.eyeNE 4.eyeW 5.eyeE 6.eyeS
    // cells eyes scheme, o for eyes, @ for cell it self, looking north(n)
    //     o
    //   o o o
    //   o @ o
    //     o

    //creating brain of cell
    private Brain brain=new Brain(cellEyes);


    //creating getters and setters
    public String getCellID(){
        return cellID;
    }
    public void setCellGeneration(int g){
        cellGeneration=g;
    }
    public int getCellGeneration(){
        return cellGeneration;
    }
    public int getCellFat(){
        return cellFat;
    }
    public void setCellFat(int life){
        cellFat =life;
    }
    public char getIsLooking(){
        return isLooking;
    }
    public void setIsLooking(char isLooking){
        this.isLooking=isLooking;
    }
    public void setRandomIsLooking(){
        int j=(int)(Math.random()*4);
        switch (j){
            case 0:
                isLooking='e';
                break;
            case 1:
                isLooking='n';
                break;
            case 2:
                isLooking='w';
                break;
            case 3:
                isLooking='s';
                break;
        }
    }
    public List<CellEye> getCellEyes(){
        return cellEyes;
    }

    //methods
    public void cellSeesAndActs(List<GameObject> allGameObjects, List<CellLife> allCells, Pane gameArena){
        this.allGameObjects=allGameObjects;
        this.allCells=allCells;
        this.gameArena=gameArena;
        //data set
        char faceDirection=getIsLooking();
        double x = super.getShapeR().getX();
        double y = super.getShapeR().getY();
        //action
        switch (faceDirection){
            case 'n':
                cellEyes.add(new CellEye(x,y-10));
                cellEyes.add(new CellEye(x,y-20));
                cellEyes.add(new CellEye(x-10,y-10));
                cellEyes.add(new CellEye(x+10,y-10));
                cellEyes.add(new CellEye(x-10,y));
                cellEyes.add(new CellEye(x+10,y));
                cellEyes.add(new CellEye(x,y+10));
                break;
            case 'e':
                cellEyes.add(new CellEye(x+10,y));
                cellEyes.add(new CellEye(x+20,y));
                cellEyes.add(new CellEye(x+10,y-10));
                cellEyes.add(new CellEye(x+10,y+10));
                cellEyes.add(new CellEye(x,y-10));
                cellEyes.add(new CellEye(x,y+10));
                cellEyes.add(new CellEye(x-10,y));
                break;
            case 's':
                cellEyes.add(new CellEye(x,y+10));
                cellEyes.add(new CellEye(x,y+20));
                cellEyes.add(new CellEye(x+10,y+10));
                cellEyes.add(new CellEye(x-10,y+10));
                cellEyes.add(new CellEye(x+10,y));
                cellEyes.add(new CellEye(x-10,y));
                cellEyes.add(new CellEye(x,y-10));
                break;
            case 'w':
                cellEyes.add(new CellEye(x-10,y));
                cellEyes.add(new CellEye(x-20,y));
                cellEyes.add(new CellEye(x-10,y+10));
                cellEyes.add(new CellEye(x-10,y-10));
                cellEyes.add(new CellEye(x,y+10));
                cellEyes.add(new CellEye(x,y-10));
                cellEyes.add(new CellEye(x+10,y));
                break;
        }
        //checking if cell eye sees wall
        for(CellEye e: cellEyes){
            if((e.getX()<=0)||(e.getX()>=(gameArena.getPrefWidth()-10))||(e.getY()<=0)||(e.getY()>=(gameArena.getPrefHeight()-10))){
                e.setEyeSees('w');
            }
        }
        //checking if cell eye sees any gameObject
        for(CellLife c: allCells){
            for(CellEye e: c.getCellEyes()){
                if(e.getEyeSees()=='n'){
                    //checking if cell eye sees any other cells
                    for(CellLife o: allCells){
                        if((x == o.getShapeR().getX())&&(y == o.getShapeR().getY())){
                            e.setEyeSees(o.getTypeO());
                        }
                    }
                    //checking if cell eye sees other gameObject
                    for(GameObject g: allGameObjects){
                        if((x == g.getShapeR().getX())&&(y == g.getShapeR().getY())){
                            e.setEyeSees(g.getTypeO());
                        }
                    }
                }
            }
        }


    }

    public void cellMoves(){
        //data set
        char faceDirection=getIsLooking();
        double x = super.getShapeR().getX();
        double y = super.getShapeR().getY();
        boolean hitWall=false;
        boolean match=false;
        //action
        switch (faceDirection){
            case 'n':
                if(y==0){
                    y=0;
                    hitWall=true;
                }else{
                    y=y-10;
                }
                break;
            case 'w':
                if(x==(gameArena.getPrefWidth()-10)){
                    x=(gameArena.getPrefWidth()-10);
                    hitWall=true;
                }else{
                    x=x+10;
                }
                break;
            case 's':
                if(y==(gameArena.getPrefHeight()-10)){
                    y=(gameArena.getPrefHeight()-10);
                    hitWall=true;
                }else{
                    y=y+10;
                }
                break;
            case 'e':
                if(x==0){
                    x=0;
                    hitWall=true;
                }else{
                    x=x-10;
                }
                break;
        }
        //if cell hits no wall, it checks for other gameObjects or other cells
        if(!hitWall){
            for(GameObject g: allGameObjects){
                if((x == g.getShapeR().getX())&&(y == g.getShapeR().getY())){
                    match=true;
                }
            }
            if(!match){
                for(CellLife c: allCells){
                    if((x == c.getShapeR().getX())&&(y == c.getShapeR().getY())){
                        match=true;
                    }
                }
            }
        }
        //if cell hits nothing, it moves
        if(!match){
            super.getShapeR().setX(x);
            super.getShapeR().setY(y);
            if(getCellFat()>=0) {
                setCellFat(getCellFat()-1);
            }else{
                setIsDead(true);
            }
        }
    }

    private void cellEats(){
        //data set
        char faceDirection=getIsLooking();
        double x = super.getShapeR().getX();
        double y = super.getShapeR().getY();
        boolean hitWall=false;
        boolean match=false;
        //action
        switch (faceDirection){
            case 'n':
                if(y==0){
                    y=0;
                    hitWall=true;
                }else{
                    y=y-10;
                }
                break;
            case 'w':
                if(x==(gameArena.getPrefWidth()-10)){
                    x=(gameArena.getPrefWidth()-10);
                    hitWall=true;
                }else{
                    x=x+10;
                }
                break;
            case 's':
                if(y==(gameArena.getPrefHeight()-10)){
                    y=(gameArena.getPrefHeight()-10);
                    hitWall=true;
                }else{
                    y=y+10;
                }
                break;
            case 'e':
                if(x==0){
                    x=0;
                    hitWall=true;
                }else{
                    x=x-10;
                }
                break;
        }
        //if cell hits no wall, it checks for other gameObjects or other cells
        if(!hitWall){
            for(GameObject g: allGameObjects){
                if((x == g.getShapeR().getX())&&(y == g.getShapeR().getY())){
                    if(g.getTypeO() == 'f'){
                        //cell eats food and gets fat
                        g.setIsDead(true);
                        setCellFat(getCellFat()+100);
                    }
                    if(g.getTypeO() == 'p'){
                        //cell eats poison and dies
                        setIsDead(true);
                    }
                }
            }
        }
        //removes all dead gameObjects
        allGameObjects.removeIf(GameObject::getIsDead);
    }

    private void cellTurnLeft(){
        switch (isLooking){
            case 'n':
                isLooking='w';
                break;
            case 'e':
                isLooking='n';
                break;
            case 's':
                isLooking='e';
                break;
            case 'w':
                isLooking='s';
                break;
        }
    }

    private void cellTurnRight(){
        switch (isLooking){
            case 'n':
                isLooking='e';
                break;
            case 'e':
                isLooking='s';
                break;
            case 's':
                isLooking='w';
                break;
            case 'w':
                isLooking='n';
                break;
        }
    }

/*    //what cell sees and does
    public void cellsAction(List<GameObject> allGameObjects, List<CellLife> allCells, Pane gameArena){
            //data set
            char faceDirection=getIsLooking();
            double x = super.getShapeR().getX();
            double y = super.getShapeR().getY();
            boolean hitWall=false;
            boolean match=false;
            //action
            switch (faceDirection){
                case 'n':
                    if(y==0){
                        y=0;
                        hitWall=true;
                    }else{
                        y=y-10;
                    }
                    break;
                case 'w':
                    if(x==(gameArena.getPrefWidth()-10)){
                        x=(gameArena.getPrefWidth()-10);
                        hitWall=true;
                    }else{
                        x=x+10;
                    }
                    break;
                case 's':
                    if(y==(gameArena.getPrefHeight()-10)){
                        y=(gameArena.getPrefHeight()-10);
                        hitWall=true;
                    }else{
                        y=y+10;
                    }
                    break;
                case 'e':
                    if(x==0){
                        x=0;
                        hitWall=true;
                    }else{
                        x=x-10;
                    }
                    break;
            }
            //if cell hits no wall, it checks for other gameObjects or other cells
            if(!hitWall){
                for(GameObject g: allGameObjects){
                    if((x == g.getShapeR().getX())&&(y == g.getShapeR().getY())){
                        if(g.getTypeO() == 'f'){
                            //cell eats food and gets fat
                            g.setIsDead(true);
                            setCellFat(getCellFat()+100);
                        }else{
                            match=true;
                        }
                    }
                }
                if(!match){
                    for(CellLife c: allCells){
                        if((x == c.getShapeR().getX())&&(y == c.getShapeR().getY())){
                            match=true;
                        }
                    }
                }
            }
            //if cell hits nothing, it moves
            if(!match){
                super.getShapeR().setX(x);
                super.getShapeR().setY(y);
                if(getCellFat()>=0) {
                    setCellFat(getCellFat()-1);
                }else{
                    setIsDead(true);
                }
            }
            //if cell hits wall cell randomly turns left or right
            if(match||hitWall){
                int j=(int)(Math.random()*2);
                switch (j){
                    case 0:
                        cellTurnLeft();
                        if(getCellFat()>=0) {
                            setCellFat(getCellFat()-1);
                        }else{
                            setIsDead(true);
                        }
                        break;
                    case 1:
                        cellTurnRight();
                        if(getCellFat()>=0) {
                            setCellFat(getCellFat()-1);
                        }else{
                            setIsDead(true);
                        }
                        break;
                }
            }
            //removes all dead gameObjects
            allGameObjects.removeIf(GameObject::getIsDead);
    }*/

    //what cell sees and does
    //cell makes decision
    private char decision='n';
    //decisions:
    //0. n for do nothing- by default
    //1. m for move
    //2. e for eat
    //3. l for turn left
    //4. r for turn right
    public void cellsThinkAndAct(){
        decision= brain.cellThinking();
        switch (decision){
            case 'n':
                cellFat--;
                break;
            case 'm':
                cellMoves();
                cellFat--;
                break;
            case 'e':
                cellEats();
                cellFat--;
                break;
            case 'l':
                cellTurnLeft();
                cellFat--;
                break;
            case 'r':
                cellFat--;
                cellTurnRight();
                break;
        }
    }
}
