package domain;

import javafx.scene.shape.Rectangle;

public class GameObject {
    private Rectangle shapeR= new Rectangle(10,10);
    public Rectangle getShapeR(){
        return shapeR;
    }
    private char typeO='o';
    private boolean isDead =false;
    public void setIsDead(boolean isDead){
        this.isDead =isDead;
    }
    public boolean getIsDead(){
        return isDead;
    }
    public void setTypeO(char typeO){
        this.typeO=typeO;
    }
    public char getTypeO(){
        return typeO;
    }
}
