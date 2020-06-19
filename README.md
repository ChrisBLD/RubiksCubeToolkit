# Rubik's Cube Tool - Final Year Project
A multi-purpose Rubik's Cube speedsolving tool. Final Year Project at the University of Birmingham. Below is a full detailed list of the tools that are included in this system.

### Average-By-Algs - a Rubik's Cube Blindfolded 3-Style Tool
The Average-By-Algs tool is designed to be used by 3x3 Blindfolded solvers who use the 3-Style method. This program will take some 
basic parameters in order to output the number of targets and algorithms a 3BLD scramble has.
The parameters that it requests are as follows:
1) Your solving orientation (which colour centre is on the top and front of the puzzle when you memorise)
2) Your edge and corner buffer stickers.
3) Yes/No answer to whether you use parity avoidance via mental edge flipping, whether or not you know and use quad-edge flipping algorithms, and how you solve corner twists.

Once all of this is entered, the user can press Enter to submit the scramble. The scrambled cube state will be shown on the cube net at the bottom of the screen.

The number of edge/corner targets and the algorithm count are given as follows:

Number of algorithms: n

Targets: xa/yb

Where: 
n is a number usually between 7 and 13 (it can be said that a scramble has n algorithms)
x is a number between 0 and 16 (it can be said that a scramble has x edge targets)
y is a number between 0 and 10 (it can be said that a scramble has y corner targets)
a and b are a string of zero or more apostrophes representing the number of flipped edges (a) and twisted corners (b) (it can be said that a scramble has a flipped edges and b twisted corners).

This notation is commonly used to rate scrambles on a pure by-target basis. Whilst the number of algorithms is certainly useful, there can exist big differences within a fixed n algorithm count depending on individual preferences, therefore both pieces of information are visible.






### Running this program


I believe this project should be runnable just with JavaFX installed on your computer. I remember I had a difficult time getting this to work with my IDE (Eclipse).
My project would only run in Eclipse when I added the following VM Argument: 
--module-path C:\Users\user\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.controls
Alternatively, when executing the JAR file (included with my project), I needed to use the following command in the terminal:
java --module-path C:\Users\user\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.controls -jar C:\Users\user\Desktop\RubiksCubeToolkit.jar
I’m not sure why this is and haven’t been able to find a fix for it to make the JAR file run on its own. Hopefully your understanding of Java/JavaFX is better than mine in this regard :)
Hopefully, the command line method works for you, with changes to the paths to point to your javafx-sdk library. 
Alternatively, if you copy this project into the Eclipse IDE, add the contents of the javafx-sdk/lib folder to your Build Path, and add the VM argument above in Run Configurations, this should work.
In any IDE, the “Home.java” class inside the “main” package is the main class that should be run.
