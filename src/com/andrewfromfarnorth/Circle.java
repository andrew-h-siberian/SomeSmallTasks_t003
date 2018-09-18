package com.andrewfromfarnorth;

import static java.lang.Math.PI;

class Circle extends Shape {
    private ShapeType shapeType = ShapeType.CIRCLE;
    private double radius;
    private double diameter;

    public Circle(double radius) {
        this.radius = radius;
        this.diameter = radius * 2;

        //add logging (and remove sout)
        System.out.println("It's a " + shapeType + " with a radius = " + this.radius + " (and diameter = " +
                this.diameter + ")");
    }

    @Override
    double area() {
        return PI * this.radius * this.radius;
    }

    private double perimeter(double radius) {
        return 2 * PI * radius;
    }

    public String toString() {
        return "Тип фигуры: " + shapeType.NAME + "\nПлощадь: " + area() + " кв. мм\nПериметр: " + perimeter(radius) +
                " мм\nРадиус: " + radius + " мм\nДиаметр: " + diameter + " мм\n";
    }
}
