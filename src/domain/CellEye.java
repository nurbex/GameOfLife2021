package domain;

import javafx.scene.paint.Color;

public class CellEye extends GameObject {
    //cell eye can see followings:
    //1. n for nothing
    //2. w for wall
    //3. f for food
    //4. p for poison
    //5. c for other cells
    //6. s for stones
    private char eyeSees='n';
    private double x;
    private double y;

    public CellEye() {
    }
    public CellEye(double x, double y){
        this.x= x;
        this.y= y;
        super.getShapeR().setArcHeight(3.0);
        super.getShapeR().setArcWidth(3);
        super.getShapeR().setFill(Color.color(0.5,0.5,0.0));
        super.setTypeO('p');
    }
    public void setEyeSees(char eyeSees){
        this.eyeSees=eyeSees;
    }
    public char getEyeSees(){
        return eyeSees;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setXY(double x, double y){
        this.x= x;
        this.y= y;
    }
}
