Chess Board Game

Where to Find the Version for Demo 3:

All the classes required for demo 3 will be uploaded to our repository under the master branch. The classes are separated into 3 packages; the logic_package, text_package, and GUI_package. The GUIGame class is the main class used to run the program and can be found in the GUI_package. The TextGame class is the class used to run the text based version and can be found in the text_package.

Compiling Instructions for Text Based Version:
1. Download the ZIP file under the master branch containing all the classes and png images and place the file in your chosen directory
2. Navigate to the chosen directory
3. Compile the file(s) using the command: javac TextGame.java

Run Instructions for Text Based Version:
1. Run the program using the command: java TextGame 

Compiling Instructions for GUI Version:
1. Download the ZIP file under the master branch containing all the classes and png images and place the file in your chosen directory
2. Navigate to the chosen directory
3. Compile the file(s) using the command: javac GUI_package/\*.java logic_package/\*.java text_package/\*.java

Run Instructions for GUI version:
1. Run the program using the command: java GUIGame 

Compiling and Run Instructions for JUnit test PieceTest.java
1. Copy JUnit Jar files into JUnit Tests folder
2. Compile files with command: javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar *.java
3. Run test by using command: java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore PieceTest
