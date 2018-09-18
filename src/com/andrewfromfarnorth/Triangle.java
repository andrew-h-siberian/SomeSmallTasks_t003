package com.andrewfromfarnorth;

import static java.lang.Math.*;

public class Triangle extends Shape {
    private ShapeType shapeType = ShapeType.TRIANGLE;
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double sideA, double sideB, double sideC) throws IllegalShapeArgsException {
        if(sideA < sideB + sideC && sideB < sideA + sideC && sideC < sideA + sideB) {
            this.sideA = sideA;
            this.sideB = sideB;
            this.sideC = sideC;
            System.out.println("It's a " + shapeType + " with sides: sideA = " + this.sideA + ", sideB = " + this.sideB +
                    ", sideC = " + this.sideC);
        } else {
            //add logging
            System.out.println("Невозможно существование треугольника в котором одна из сторон больше или равна сумме " +
                    "двух других сторон");
            throw new IllegalShapeArgsException();
        }
    }

    private double perimeter(double sideA, double sideB, double sideC) {
        return sideA + sideB + sideC;
    }

    @Override
    double area() {
        double halfP = perimeter(this.sideA, this.sideB, this.sideC);
        return sqrt(halfP * (halfP - sideA) * (halfP - sideB) * (halfP - sideC));
    }

    private double oppAngle(double side1opposing, double side2, double side3) {
        //return Math.toDegrees(Math.acos((Math.pow(side2, 2) + Math.pow(side3, 2) - Math.pow(side1opposing, 2)) / (2 * side2 * side3)));
        return toDegrees(acos((pow(side2, 2) + pow(side3, 2) - pow(side1opposing, 2)) / (2 * side2 * side3)));
    }

    @Override
    public String toString() {
        return "Тип фигуры: " + shapeType.NAME + "\nПлощадь: " + area() +
                " кв. мм\nПериметр: " + perimeter(sideA, sideB, sideC) +
                " мм\nДлина стороны A: " + sideA + "мм\nПротиволежащий угол: " + oppAngle(sideA, sideB, sideC) +
                "°\nДлина стороны B: " + sideB + " мм\nПротиволежащий угол: " + oppAngle(sideB, sideA, sideC) +
                "°\nДлина стороны C: " + sideC + " мм\nПротиволежащий угол: " + oppAngle(sideC, sideB, sideA) + "°";
    }

}