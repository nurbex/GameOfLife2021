package domain;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CellLife extends GameObject{
    public CellLife(){}
    public CellLife(int x, int y){
        super.getShapeR().setArcHeight(3.0);
        super.getShapeR().setArcWidth(3);
        super.getShapeR().setFill(Color.color(0.3,0.7,0.0));
        super.getShapeR().setX(x);
        super.getShapeR().setY(y);
        super.setTypeO('c');
        cellEyes.add(new CellEye());
        cellEyes.add(new CellEye());
        cellEyes.add(new CellEye());
    }
    //creating cell properties
    private int cellGeneration;
    private int lifeTime=0;
    private int cellMaxFat=400; //max cell fat
    private int cellFat =20; //cell fat at the beginning
    private final int foodCalories =6;
    private int poisonEffect=10;
    private final int stoneEffect=1;
    private char isLooking = 'n';
    private boolean cantMove=false;
    private boolean cantEat=false;
    //getting data from GUI controller
    private List<CellEye> cellEyes=new ArrayList<>();
    //0.eyeN 1.eyeNN 2.eyeNW 3.eyeNE 4.eyeW 5.eyeE 6.eyeS
    // cells eyes scheme, o for eyes, @ for cell it self, looking north(n)
    //     o
    //   o @ o

    //creating brain of cell
    private Brain brain=new Brain();


    //creating getters and setters
    public void setLifeTime(int lifeTime){
        this.lifeTime=lifeTime;
    }
    public int getLifeTime(){
        return lifeTime;
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
    public void setCantMoveFalse(){
        cantMove=false;
    }
    public void setCantEatFalse(){
        cantEat=false;
    }

    //methods
    public void cellSees(List<GameObject> allGameObjects, List<CellLife> allCells, Pane gameArena){
        //data set
        char faceDirection=getIsLooking();
        double x = super.getShapeR().getX();
        double y = super.getShapeR().getY();
        //action
        switch (faceDirection){
            case 'n':
                cellEyes.get(0).setXY(x,y-10);
                cellEyes.get(1).setXY(x-10,y);
                cellEyes.get(2).setXY(x+10,y);
                //correct
                break;
            case 'e':
                cellEyes.get(0).setXY(x+10,y);
                cellEyes.get(1).setXY(x,y-10);
                cellEyes.get(2).setXY(x,y+10);
                //correct
                break;
            case 's':
                cellEyes.get(0).setXY(x,y+10);
                cellEyes.get(1).setXY(x+10,y);
                cellEyes.get(2).setXY(x-10,y);
                //correct
                break;
            case 'w':
                cellEyes.get(0).setXY(x-10,y);
                cellEyes.get(1).setXY(x,y+10);
                cellEyes.get(2).setXY(x,y-10);
                break;
        }

        //checking if cell eye sees wall
        for(CellEye e: cellEyes){
            if((e.getX()<0)||(e.getX()>(gameArena.getPrefWidth()-10))||(e.getY()<0)||(e.getY()>(gameArena.getPrefHeight()-10))){
                e.setEyeSees('w');
            }else{
                e.setEyeSees('n');
            }
            //System.out.println(e.getX()+" "+ e.getY()+" cell sees "+e.getEyeSees());
        }
        //checking if cell eye sees any gameObject

        for(CellEye e: cellEyes){
            if(e.getEyeSees()=='n'){
                //checking if cell eye sees any other cells
                for(CellLife o: allCells){
                    if((e.getX() == o.getShapeR().getX())&&(e.getY() == o.getShapeR().getY())){
                        e.setEyeSees(o.getTypeO());
                    }
                }
                //checking if cell eye sees other gameObject
                for(GameObject g: allGameObjects){
                    if((e.getX() == g.getShapeR().getX())&&(e.getY() == g.getShapeR().getY())){
                        e.setEyeSees(g.getTypeO());
                    }
                }
            }
        }
    }

    public void cellMoves(List<GameObject> allGameObjects, List<CellLife> allCells, Pane gameArena){
        //data set
        double x = super.getShapeR().getX();
        double y = super.getShapeR().getY();
        boolean hitWall=false;
        boolean match=false;
        //action
        switch (isLooking){
            case 'n':
                if(y<=0){
                    y=0;
                    hitWall=true;
                }else{
                    y=y-10;
                }
                break;
            case 'e':
                if(x>=(gameArena.getPrefWidth()-10)){
                    x=(gameArena.getPrefWidth()-10);
                    hitWall=true;
                }else{
                    x=x+10;
                }
                break;
            case 's':
                if(y>=(gameArena.getPrefHeight()-10)){
                    y=(gameArena.getPrefHeight()-10);
                    hitWall=true;
                }else{
                    y=y+10;
                }
                break;
            case 'w':
                if(x<=0){
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
            //if cell hits nothing, it moves
            if(!match){
                super.getShapeR().setX(x);
                super.getShapeR().setY(y);
            /*if(getCellFat()<0) {
                setIsDead(true);
            }*/
                cantMove=false;
            }else{
                cantMove=true;
                //System.out.println("cantMove11111");
            }
        }else{
            cantMove=true;
        }
    }

    public void cellEats(List<GameObject> allGameObjects, List<CellLife> allCells, Pane gameArena){
        //data set
        double x = super.getShapeR().getX();
        double y = super.getShapeR().getY();
        boolean hitWall=false;
        boolean match=false;
        //action
        switch (isLooking){
            case 'n':
                if(y<=0){
                    y=0;
                    hitWall=true;
                }else{
                    y=y-10;
                }
                break;
            case 'e':
                if(x>=(gameArena.getPrefWidth()-10)){
                    x=(gameArena.getPrefWidth()-10);
                    hitWall=true;
                }else{
                    x=x+10;
                }
                break;
            case 's':
                if(y>=(gameArena.getPrefHeight()-10)){
                    y=(gameArena.getPrefHeight()-10);
                    hitWall=true;
                }else{
                    y=y+10;
                }
                break;
            case 'w':
                if(x<=0){
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
                        cellEyes.get(0).setEyeSees('n');
                        g.setIsDead(true);
                        if(getCellFat()<=cellMaxFat){
                            setCellFat(getCellFat() + foodCalories);
                        }
                        match=true;
                    }
                    if(g.getTypeO() == 'p'){
                        //cell eats poison and dies
                        setIsDead(true);
                        //g.setIsDead(true);
                        //setCellFat(getCellFat() - poisonEffect);
                        match=true;
                    }
                    if(g.getTypeO() == 's'){
                        //cell eats poison and dies
                        //setIsDead(true);
                        setCellFat(getCellFat() - stoneEffect);
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
            //if cell hits nothing, it moves
            if(!match){
                cantEat=false;
            }else{
                cantEat=true;
                //System.out.println("cantMove11111");
            }
        }else{
            cantEat=true;
        }

        //removes all dead gameObjects
        allGameObjects.removeIf(GameObject::getIsDead);
    }

    public void cellTurnLeft(){
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
        //System.out.println("left");
    }

    public void cellTurnRight(){
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
        //System.out.println("right");
    }

    //what cell sees and does something, this code is not used, I forgot why
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
    }

    //what cell sees and does
    //cell makes decision
    private char decision='n';
    //decisions:
    //0. n for do nothing- by default
    //1. m for move
    //2. e for eat
    //3. l for turn left
    //4. r for turn right
    public char cellsThinkAndAct(){
        brain.senseToDigits(cellEyes, cantMove, cantEat, cellFat);
        decision= brain.cellThinking();
        return decision;
    }

    public Brain getCellBrain(){
        return brain;
    }
    public void cellMutates(){
        getCellBrain().brainWRandom();
    }
    public  void cellBrainToFile(){
        getCellBrain().brainWriteToFile();
    }
}
