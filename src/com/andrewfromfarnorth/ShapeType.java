package com.andrewfromfarnorth;

public enum ShapeType {
    CIRCLE("Круг"), RECTANGLE("Прямоугольник"), TRIANGLE("Треугольник");
    public final String NAME;

    ShapeType(String name) {
        this.NAME = name;
    }

    /*@Override
         public String toString() {
        //only capitalize the first letter
        String s = super.toString();
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }*/

}