package domain;

import javafx.scene.paint.Color;

public class Poison extends GameObject{
    public Poison(int x, int y){
        super.getShapeR().setArcHeight(3.0);
        super.getShapeR().setArcWidth(3);
        super.getShapeR().setFill(Color.color(0.5,0.5,0.0));
        super.getShapeR().setX(x);
        super.getShapeR().setY(y);
        super.setTypeO('p');
    }
}
