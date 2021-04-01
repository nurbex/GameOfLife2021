package domain;

import javafx.scene.paint.Color;

public class CellLife extends GameObject{
    public CellLife(int x, int y){
        super.getShapeR().setArcHeight(3.0);
        super.getShapeR().setArcWidth(3);
        super.getShapeR().setFill(Color.color(0.3,0.7,0.0));
        super.getShapeR().setX(x);
        super.getShapeR().setY(y);
    }
}
