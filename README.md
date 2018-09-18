# SomeSmallTasks_t003

##  Shapes again (inheritance)
Make a command line app with OOD that outputs characteristics of a (geometry) shape.

  Input:
A type and it's options are read from a file. Options are different for different types of shape.
The coded type of a shape is placed at the first line of file. The second line is options delimited with space symbol.

  I.e.:
CIRCLE
5

  Output:
The application results are calculated characteristics of a shape. Characteristics differ depending on the type of the shape
and they depend on input options. Output is written to file or to console. Destination of output results is configured by
command-line arguments.

  I.e.:
Shape type: Circle
Area: 78.53 sq.mm
Perimeter: 31.42 mm
Radius: 5 mm
Diameter: 10 mm

  Conditions:
Units (of measurements) do not matter, while they are corresponding to each other and to input options. And they
have to be in the output. Application have to support shape types and their options from the table below.
Common options are:
 Name (of a type)
 Area
 Perimeter
  
  Table of options for different shape types
```
+-----------+-------------+----------------+--------------------------------+
| Name      | Type (code) | Options        | Output                         |
+-----------+-------------+----------------+--------------------------------+
| Circle    | CIRCLE      | Radius         | Radius                         |
|           |             | (two values)   | Diameter                       |
+-----------+-------------+----------------+--------------------------------+
| Rectangle | RECTANGLE   | Side lengths   | Length of the diagonal         |
|           |             | (two values)   | Length (of the long side)      |
|           |             |                | Width (size of the short side) |
+-----------+-------------+----------------+--------------------------------+
| Triangle  | TRIANGLE    | Side lengths   | For each of the three sides:   |
|           |             | (three values) | Length and opposite angle      |
+-----------+-------------+----------------+--------------------------------+
```
