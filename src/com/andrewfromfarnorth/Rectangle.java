package com.andrewfromfarnorth;

import static java.lang.Math.*;

public class Rectangle extends Shape {
    private ShapeType shapeType = ShapeType.RECTANGLE;
    private double width;
    private double length;

    public Rectangle(double sideA, double sideB) throws IllegalShapeArgsException {
        if (sideA > 0 && sideB > 0) {
            if (sideA <= sideB) {
                this.width = sideA;
                this.length = sideB;
            } else {
                this.width = sideB;
                this.length = sideA;
            }
        } else {
            //add logging
            System.out.println("Размер как минимум одной из сторон меньше или равен 0");
            throw new IllegalShapeArgsException();
        }

        //add logging (and remove sout)
        System.out.println("It's a " + shapeType + " with width = " + this.width + " and length = " + this.length);
    }

    private double diagonale(double sideA, double sideB) {
        return sqrt((sideA * sideA) + (sideB * sideB));
    }

    @Override
    double area() {
        return this.width * this.length;
    }

    private double perimeter(double sideA, double sideB) {
        return (sideA + sideB) * 2;
    }



    @Override
    public String toString() {
        //
        return "Тип фигуры: " + shapeType.NAME + "\nПлощадь: " + area() + " кв. мм\nПериметр: " +
                perimeter(width, length) + " мм\nДиагональ: " + diagonale(width, length) + " мм\nДлина: " + length +
                " мм\nШирина: " + width;
    }

}
