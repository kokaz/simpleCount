# Choice of components


For this project, I used the built-in GUI library SWING
which is a Java wrapper of AWT.

For the result display I used a JLabel which match the window width and
with a comfortable height.

For the buttons, I split them into two categories : Number and Functions.
The function buttons includes all the math functions (`sin`, `cos`, `tan`, etc...)
and all the math operations (`add`, `sub`, etc...)

The number buttons are only the common digits and the dot : `[.0-9]`

# Design choices


The layout is a simple grid where related buttons is grouped.

The number buttons are placed like in your numeric pad or a physical calculator

Trigonometric functions are packed together, operations are also packed together
and all the other functions placed where there is space.

The layout of the program is really close to another GUI calculator which
helps any user new to this program to learn how to use it really fast.

# Conception


The program conception is a simple __MVC pattern__ (Model-View-Controller)
because this pattern help to organize the code in 3 distinct part.

The View is the window which also display the UI elements correctly.

The Model is here to handle user data here the calculus and notify the view
of data modifications.

The Controller is the bridge between the View and the Model

For more information, I suggestion you to have look in the __doc__ folder
or see the online doc at [https://kokaz.github.io/simpleCount](https://kokaz.github.io/simpleCount) where all the project *JavaDoc* is compiled