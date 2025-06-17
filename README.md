# Labyrinth Solver

A Java program that takes a labyrinth as input and finds a route out of it from a given square. Utilizes linked lists and threads to solve the labyrinths.

The input files are stored in the *inputs*-folder. The first line specify the amount of columns and rows in the labyrinth. The remaining file consists of symbols. The #-symbol signifies a black square, i.e. a wall in the labyrinth. The .-symbol signifies a white square, i.e. an available square that can be used to find a route through the labyrinth.

Once the program is launched, the file explorer opens inside the *inputs*-folder. Pick one of the files and the program will open it in the JavaFX GUI. Once launched, you can click on any white square within the labyrinth and the program will automatically find the shortest available route out of the labyrinth from the given square.

<img src="image.png" height="500px"/>

## Dependencies

- Java Development Kit (e.g. JDK 22)
- JavaFX (e.g. JavaFX 22)

## Run from command line
```
java -cp ./bin --module-path <JavaFX_lib_path> --add-modules javafx.controls --enable-preview labyrinthsolver.Main
```
Insert your own path for the javafx lib folder.
