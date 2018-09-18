package com.andrewfromfarnorth;

public abstract class Shape {
    protected ShapeType shapeType;
    /*protected int area;
    protected int perimeter;*/

    public Shape() {
        //add logging (and remove sout)
        System.out.println("Создана фигура.");
    }

    abstract double area();

    abstract public String toString();
}