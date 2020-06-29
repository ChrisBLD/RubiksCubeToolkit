# Rubik's Cube Tool - Final Year Project
A multi-purpose Rubik's Cube speedsolving tool. Final Year Project at the University of Birmingham, for which a obtained a First Class result (75%). Below is a full detailed list of the tools that are included in this system.

![Image of Homepage Screen](https://github.com/ChrisBLD/RubiksCubeToolkit/blob/master/src/resources/rct-homepage.png)

### Rubik's Cube Interactive Tutorial
This tool allows for you to take a physical Rubik's Cube that you own, enter its state using the input options available, and then progress through an interactive tutorial that teaches you how to solve the Rubik's Cube using your own puzzle as the example.

First, the tutorial system will ask if you would like to learn the *Notation for the Rubik's Cube*. This is a side-section that teaches basics moves that can be made to the puzzle. These are used throughout the tutorial to demonstrate the moves that must be made at each stage to complete it.

![Notation Screen](https://github.com/ChrisBLD/RubiksCubeToolkit/blob/master/src/resources/rct-notation.png)

Next, you will be asked if you have you own puzzle in front of you that you would like to use to learn how to solve it. If you don't you will be able to complete the tutorial using an example cube state. 

If you do want to use your own puzzle, you will be able to enter its state either as a written scramble (a time-saving option for returning users) or a colour palette entry system.

![Input Scramble](https://github.com/ChrisBLD/RubiksCubeToolkit/blob/master/src/resources/rct-input.png)

![Input Colour](https://github.com/ChrisBLD/RubiksCubeToolkit/blob/master/src/resources/rct-inputcolour.png)


The end result of either of these entry states will be this:

![Matching](https://github.com/ChrisBLD/RubiksCubeToolkit/blob/master/src/resources/rct-matching.jpg)

A Virtual Cube that matches your physical cube exactly.

The tutorial the begins and is broken down into a number of stages:
1) The Cross - placing the four white edges correctly on the white face.
2) First-Layer Corners - placing the four white corners correctly on the white face between the edges placed in 1.
3) Second-Layer Edges - placing the four second-layer edges correctly into the second layer.
4) Orientation of the Last Layer - Orienting all of the remaining unsolved pieces such that the yellow sticker on each of them is facing in the same direction.
5) Permutation of the Last Layer - Permuting the corners and then edges of the last layer to complete the Rubik's Cube.

Each of these stages has two sections - the tutorial phase and the demo phase.

The tutorial phase is a series of written paragraphs and diagrams demonstrating possible situations that can occur in the given stage and the different algorithms that can be used to solve them. This can be navigated through at one's own pace and also skipped if desired.

![Tutorial](https://github.com/ChrisBLD/RubiksCubeToolkit/blob/master/src/resources/rct-tutorial.png)

The demo phase is where your puzzle comes into it - the system will show you the moves that you need to make and allow you to navigate through them to see how they are applied to the virtual cube. The goal is to follow these moves to make your physical cube match the virtual cube as each move is applied to eventually complete each stage. At all times, your cube should match the virtual cube exactly.

![Demo](https://github.com/ChrisBLD/RubiksCubeToolkit/blob/master/src/resources/rct-demo.png)

#### Solving the Puzzle
This system uses the state of the puzzle you submit to derive a solution in its own notation that is passed throughout the program to each stage handler when necessary. It will follow the tutorial that will be shown to the user - cross, first-layer corners etc. - and solve each step using the same algorithms that are shown to the user in the tutorial phase. 

Effectively, this system contains a Rubik's Cube solver that *follows a natural human method to solve the puzzle*. This is, of course, much less efficient than an optimal solver, but this solution is ready to go and can be taught to the user bit-by-bit as they progress throughout the tutorial.


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

![AverageByAlgs Main](https://github.com/ChrisBLD/RubiksCubeToolkit/blob/master/src/resources/aba-main.png)


### Virtual Cube
This system also includes a simple Virtual Cube. This can be scrambled using a text scramble and solved using either the GUI (by clicking on the buttons to make moves) or using the CSTimer Virtual Cube keymapping (shown below). The solve is also timed, beginning when the first move is made and ending once the last is completed and the puzzle is solved. Alternatively, it can be used to learn the notation of the puzzle better and play around with algorithms you learn during the main tutorial.

![Virtual Cube Main](https://github.com/ChrisBLD/RubiksCubeToolkit/blob/master/src/resources/vc-main.png)


### Running this program

(This is the text I included when I submitted this to my University)

I believe this project should be runnable just with JavaFX installed on your computer. I remember I had a difficult time getting this to work with my IDE (Eclipse).
My project would only run in Eclipse when I added the following VM Argument: 
--module-path C:\Users\user\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.controls
Alternatively, when executing the JAR file (included with my project), I needed to use the following command in the terminal:
java --module-path C:\Users\user\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.controls -jar C:\Users\user\Desktop\RubiksCubeToolkit.jar
I’m not sure why this is and haven’t been able to find a fix for it to make the JAR file run on its own. Hopefully your understanding of Java/JavaFX is better than mine in this regard :)
Hopefully, the command line method works for you, with changes to the paths to point to your javafx-sdk library. 
Alternatively, if you copy this project into the Eclipse IDE, add the contents of the javafx-sdk/lib folder to your Build Path, and add the VM argument above in Run Configurations, this should work.
In any IDE, the “Home.java” class inside the “main” package is the main class that should be run.
