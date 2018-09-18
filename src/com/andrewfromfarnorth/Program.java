package com.andrewfromfarnorth;

import java.awt.*;
import java.io.*;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.nio.file.Files;
//import java.util.List;

/*Format of args as follows: program inputFile [c|f] [outputFile]*/
//ToImprove: Вынести ресурсы для локализации

public class Program {

    public static final byte MIN_NUMBER_OF_ARGS = 1;
    public static final byte MAX_NUMBER_OF_ARGS = 3;
    public static final byte INDEX_OF_ARG_INPUT_FILE = 0;
    public static final byte INDEX_OF_ARG_OUTPUT_FILE = 2;
    public static final byte INDEX_OF_ARG_TO_SET_OUTPUT = 1;
    //Next are 2 case insensitive arguments ("f" to file | "c" to console) to configure output
    public static final String ARG_TO_SET_OUTPUT_TO_FILE = "f";
    public static final String ARG_TO_SET_OUTPUT_TO_CONSOLE = "c";
    public static final boolean OUTPUT_TO_CONSOLE_IF_NO_ARG_PASSED = true;
    public static final String HELP_MESSAGE = "Запустите \"program -h\" для получения дополнительной информации";
    public static final int LINES_TO_READ_FROM_FILE = 2;
    //Максимальное количество параметров фигуры (3 - для треугольника), для чтения/парсинга из файла
    //public static final int MAX_NUM_OF_SHAPE_PARAMS = 3;
    //variable to keep information about choosen output, boolean is enough for now (true - file, false - console)
    private static boolean flagOutputToFile;
    private static File fileToRead;
    //private static String stringToOutput;
    //THINK if it's better to declare only a String... no it's already available

    public static void main(String[] args) {
        //add logging (and remove sout)
        /*System.out.println("Приложение запущено. Аргументы переданные в командной строке:");
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i] + " ");
        }
        System.out.println();*/

        if (parseArgs(args)) {
            //add logging (and remove sout)
            System.out.println("Введенные аргументы верны. Ура.");
            System.out.println("Вывод в файл - " + flagOutputToFile);

            //String[] optionsToBuildFrom = new String[5];
            //Или 3: "Ответ", фигура, строка параметров
            String[] optionsToBuildFrom = readFigureFile(args[INDEX_OF_ARG_INPUT_FILE]);
            if (optionsToBuildFrom[0].equals("Параметры из файла считаны успешно")) {
                try {
                    ShapeType shapeTypeChoosen = ShapeType.valueOf(optionsToBuildFrom[1]);
                    Shape objectToOutput = null;
                    //to check
                    System.out.println("The shape read from file is " + shapeTypeChoosen);
                    switch (shapeTypeChoosen) {
                        case CIRCLE:
                            //add logging (remove sout)
                            System.out.println(shapeTypeChoosen + " is a known shape");
                            double[] circleParameters = getParameters(optionsToBuildFrom[2], 1);
                            objectToOutput = new Circle(circleParameters[0]);
                            break;
                        case RECTANGLE:
                            //add logging (remove sout)
                            System.out.println(shapeTypeChoosen + " is a known shape");
                            double[] rectangleParameters = getParameters(optionsToBuildFrom[2], 2);
                            objectToOutput = new Rectangle(rectangleParameters[0], rectangleParameters[1]);
                            break;
                        case TRIANGLE:
                            //add logging (remove sout)
                            System.out.println(shapeTypeChoosen + " is a known shape");
                            double[] triangleParameters = getParameters(optionsToBuildFrom[2], 3);
                            objectToOutput = new Triangle(triangleParameters[0], triangleParameters[1],
                                    triangleParameters[2]);
                            break;
                    }
                    if (flagOutputToFile) {
                        //add logging (and remove sout)
                        System.out.println("Готовимся вывести параметры и характеристики фигуры в заданный выходной файл");
                        writeShapeToFile(objectToOutput, args[INDEX_OF_ARG_OUTPUT_FILE]);
                    } else {
                        //add logging (and remove sout)
                        System.out.println("Готовимся вывести параметры и характеристики фигуры в консоль");
                        System.out.println(objectToOutput);
                    }
                } catch (IllegalShapeArgsException e) {
                    //add logging
                    //или здесь вообще только "не удается считать параметры фигуры..."?
                    System.out.println("Невозможно создать фигуру со значениями параметров считанными из файла" +
                            " (или считать их)");
                } catch (IllegalArgumentException e) {
                    //add logging
                    System.out.println("Фигура считанная из файла неизвестна");
                }
            } else {
                //add logging
                System.out.println("При считывании параметров фигуры из входного файла произошла ошибка (после шага: " +
                        "\n\"" + optionsToBuildFrom[0] + "\")");
            }

        } else {
            //add logging (and remove sout)
            System.out.println("Введенные аргументы неверны (или запрошена помощь по параметрам командной строки).");
        }


        /*System.out.println();
        Shape circle1 = new Circle(1);
        System.out.println(circle1);
        System.out.println(new Circle(2));

        Shape rectangle1 = new Rectangle(4f, 2.5f);
        System.out.println(rectangle1);

        Shape triangle1 = new Triangle(3.0, 4.0, 5.0);
        System.out.println(triangle1);*/

    }

    private static void writeShapeToFile(Shape objectToOutput, String fileToWrite) {
        try (FileWriter writer = new FileWriter(fileToWrite, false)) {
            writer.write(objectToOutput.toString());
        } catch (IOException ex) {
            //add logging
            System.out.println("Проблема ввода/вывода (при записи в файл)");
        }
    }

    private static boolean parseArgs(String[] argsToParse) {

        //если отсутствуют аргументы переданные в командной строке или точнее их меньше необходимого количества
        if (argsToParse.length < MIN_NUMBER_OF_ARGS) {
            //add logging
            System.out.println("Вы должны передать хотя бы " + MIN_NUMBER_OF_ARGS + " парам. в командной строке.");
            System.out.println(HELP_MESSAGE);
            return false;

            //если аргументов в командной строке больше максимально возможного количества оных
        } else if (argsToParse.length > MAX_NUMBER_OF_ARGS) {
            //add logging
            System.out.println("Вы должны передать максимум" + MAX_NUMBER_OF_ARGS + " парам. в командной строке.");
            System.out.println(HELP_MESSAGE);
            return false;

            //если передано допустимое количество аргументов
        } else {
            /* если вместо inputFile (т.е. первого аргумента) передано "-h" - вывести "help"
            проверять можно и по INDEX_OF_ARG_INPUT_FILE, но это нелогично: вдруг он изменится, а -h всегда [0]
            и вообще-то выводить help будем и при количестве аргументов больше 1, но главное, что первый -h */
            if (argsToParse[0].equalsIgnoreCase("-h")) {
                System.out.println("program inputFile [f|c] [outputFile]\n" +
                        "Example: program /home/someshape.shp\n" +
                        "with optional \"c\" argument: program otherfile.cfg c\n" +
                        "and with \"f\" argument and mandatory output file name: program in.shp f out.res");

                //если первый аргумент не "-h"
            } else {

                //проверяем есть ли такой файл (как переданный первым аргументом в main), только if - "нет", else - "есть"
                if (!(new File(argsToParse[INDEX_OF_ARG_INPUT_FILE]).exists() &&
                        new File(argsToParse[INDEX_OF_ARG_INPUT_FILE]).isFile())) {
                    //add logging
                    //Think (read) which exception are (highly/very) possible here
                    System.out.println("Первым параметром нужно передать имя входного файла. Такой (" +
                            argsToParse[INDEX_OF_ARG_INPUT_FILE] + ") файл не существует. Увы.");
                    System.out.println(HELP_MESSAGE);
                    return false;

                    //в случае если указанный входной файл существует (на момент проверки)
                } else {
                    //add logging (and remove sout)
                    //THINK Может вынести это вверх, за начало if (и уже в if проверять на существование этот File)?
                    fileToRead = new File(argsToParse[INDEX_OF_ARG_INPUT_FILE]);
                    System.out.println("Такой входной файл существует. Ура.");
                    //теперь, если есть еще параметры, проверим их дальше (иначе вернем true, вывод в консоль)

                    //ВОТ 1 вариант с одним аргументом - именем существующего входного файла (вывод в консоль if flag)
                    if (argsToParse.length == 1) {
                        //add logging (if OUTPUT_TO_CONSOLE_IF_NO_ARG_PASSED else)
                        return OUTPUT_TO_CONSOLE_IF_NO_ARG_PASSED;

                        //проверяем остальные аргументы
                        //(не уверен, стоит ли argsToParse.length == 2 вынести в один if и в нем ниже делать if'ы на c|f|*
                    } else if (argsToParse.length == 2 &&
                            argsToParse[INDEX_OF_ARG_TO_SET_OUTPUT].equalsIgnoreCase(ARG_TO_SET_OUTPUT_TO_FILE)) {
                        //add logging (and remove sout)
                        System.out.println("Ошибка: задан аргумент \"" + ARG_TO_SET_OUTPUT_TO_FILE +
                                "\" для вывода в файл, но выходной файл не задан.");
                        System.out.println(HELP_MESSAGE);
                        return false;

                        //ВОТ 2 вариант 2-х правильно переданных параметров для вывода в консоль: файл есть, ключ "c"
                    } else if (argsToParse.length == 2 &&
                            argsToParse[INDEX_OF_ARG_TO_SET_OUTPUT].equalsIgnoreCase(ARG_TO_SET_OUTPUT_TO_CONSOLE)) {
                        //add logging (and remove sout)
                        System.out.println("Файл существует. Передан аргумент \"" + ARG_TO_SET_OUTPUT_TO_CONSOLE +
                                "\" для вывода в консоль");
                        return true;

                        //если вторым аргументом оказалось что-то не равное ARG_TO_SET_OUTPUT_TO_[FILE|CONSOLE]
                    } else if (argsToParse.length == 2) {
                        //add logging
                        System.out.println("Вы передали неверный второй аргумент в командной строке.\n" +
                                "Второй параметр должен быть либо \"" + ARG_TO_SET_OUTPUT_TO_CONSOLE +
                                "\", либо \"" + ARG_TO_SET_OUTPUT_TO_FILE + "\".");
                        return false;

                        //если три аргумента
                    } else if (argsToParse.length == 3) {

                        /*
                        //если заданный выходной файл уже существует
                        if (new File(argsToParse[INDEX_OF_ARG_OUTPUT_FILE]).exists()) {
                            //add logging
                            System.out.println("Заданный выходной файл уже существует. Выберите другой.");
                            return false;
                        //если заданный выходной файл не существует
                        } else {
                        */

                        //Уменьшить отступ, если обойдусь без закомментированных if/else

                        //если при трех переданных параметрах второй аргумент не равен ARG_TO_SET_OUTPUT_TO_FILE
                        if (!argsToParse[INDEX_OF_ARG_TO_SET_OUTPUT].equalsIgnoreCase(ARG_TO_SET_OUTPUT_TO_FILE)) {
                            //add logging (and probably remove help message?)
                            System.out.println("Вы ввели 3 аргумента в командной строке, но второй при этом не \"" +
                                    ARG_TO_SET_OUTPUT_TO_FILE + "\", что недопустимо");
                            System.out.println(HELP_MESSAGE);

                            //если второй аргумент равен ARG_TO_SET_OUTPUT_TO_FILE
                        } else {
                            try {
                                File outFile = new File(argsToParse[INDEX_OF_ARG_OUTPUT_FILE]);
                                outFile = outFile.getCanonicalFile();
                                if (outFile.exists()) {
                                    //add logging
                                    System.out.println("Заданный выходной файл уже существует. Выберите другой.");
                                    return false;

                                    //ВОТ ветка, когда введено три правильных аргумента (без учета возможности создания ф.)
                                } else {
                                    //add logging
                                    flagOutputToFile = true;
                                    return true;
                                }

                            } catch (IOException e) {
                                //add logging
                                System.out.println("Выходной файл или путь к нему задан неверно. " +
                                        "Исправьте и попробуйте снова.");
                                System.out.println(HELP_MESSAGE);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        //Add logging? (it should be unreacheable)
        return false;
    }

    private static String[] readFigureFile(String arg) {
        String[] result = new String[1 + LINES_TO_READ_FROM_FILE];
        result[0] = "Готовимся открыть файл и считать параметры фигуры";
        try (BufferedReader in = new BufferedReader(new FileReader(fileToRead.getAbsoluteFile()))) {
            String s;
            //считываем то кол-во строк, которое задано константой LINES_TO_READ_FROM_FILE
            for (int i = 1; i <= LINES_TO_READ_FROM_FILE; i++) {
                if ((s = in.readLine()) != null) {
                    result[i] = s;
                    result[0] = "Из входного файла успешно считана строка номер " + i;
                } else {
                    result[0] += "В файле отсутствует строка номер " + i;
                    return result;
                }
            }
            result[0] = "Параметры из файла считаны успешно";
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result[0] += ". Ошибка при чтении параметров из файла (файл не найден)";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result[0] += ". Ошибка при чтении параметров из файла (проверьте его доступность)";
            return result;
        }
    }

    private static double[] getParameters(String stringToParse, int numberOfParameters) {
        double[] result = new double[numberOfParameters];
        String[] parameters = stringToParse.split(" ");
        if (parameters.length >= numberOfParameters) {
            for (int i = 0; i < numberOfParameters; i++) {
                try {
                    result[i] = Double.parseDouble(parameters[i]);
                } catch (NumberFormatException e) {
                    //add logging [i]
                    throw new IllegalShapeArgsException();
                }
            }
            return result;
        } else {
            //add logging
            System.out.println("Задано недостаточное количество параметров фигуры во входном файле");
            throw new IllegalShapeArgsException();
            //return new double[0];
        }
    }

}

