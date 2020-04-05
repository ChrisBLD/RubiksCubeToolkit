I believe this project should be runnable just with JavaFX installed on your computer. I remember I had a difficult time getting this to work with my IDE (Eclipse).
My project would only run in Eclipse when I added the following VM Argument: 
--module-path C:\Users\user\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.controls
Alternatively, when executing the JAR file (included with my project), I needed to use the following command in the terminal:
java --module-path C:\Users\user\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.controls -jar C:\Users\user\Desktop\RubiksCubeToolkit.jar
I’m not sure why this is and haven’t been able to find a fix for it to make the JAR file run on its own. Hopefully your understanding of Java/JavaFX is better than mine in this regard :)
Hopefully, the command line method works for you, with changes to the paths to point to your javafx-sdk library. 
Alternatively, if you copy this project into the Eclipse IDE, add the contents of the javafx-sdk/lib folder to your Build Path, and add the VM argument above in Run Configurations, this should work.
In any IDE, the “Home.java” class inside the “main” package is the main class that should be run.
