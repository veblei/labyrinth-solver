# Labyrinth Solver

This project was developed in Windows 10 using the "Project Manager for Java" extension for Visual Studio Code.

## Dependencies

- JDK 22
- JavaFX 22

## Run from command line

    java -cp ./bin --module-path "path/to/javafx/lib" --add-modules javafx.controls --enable-preview labyrinthsolver.Main

Insert your own path for the javafx/lib folder.

## Usage

Once you execute the above command, the file explorer will open inside the "inputs" folder. Choose one of the 7 .in-files, and the JavaFX GUI should start.

Once the labyrinth is displayed, click on any white square within the labyrinth and the program should automatically find the shortest way out of the labyrinth from the chosen square.