How to build:
I built this project in Java using Eclipse as my IDE. Ideally you can open up the project file in eclipse and run the ElevatorDriver with the tests cases you would like to use. 

Files can be located in the SRC/ folder
Alternatively in terminal with a Java compiler:
javac ElevatorDriver.java
java ElevatorDrive


Overview:
This project was created as a part of the Mesosphere Internship interview proccess. The task is to design and implement an elevator control system which can handle up to 16 elevators. The specification asks to implement an interface of the following 4 functions:
status(): Seq[(Int, Int, Int)] //Output ID, Location, Goal
update(Int, Int, Int) //Given an ID, update the floor, update goal floor
pickup(Int, Int) //Set a pickup request from current floor to goal floor
step() //Step once time cycle

I didnt opt to use the update method as it's functionality was handled by a few other methods within my Elevator and ControlSystem class.

How I tackled the problem:
I taclked this problem by creating 3 classes.
-ControlSystem
	-Implements the methods required
	-Acts as the main building object
-Passenger
	-Small class to represent the data of a passenger
	-Stores destination, if it is riding, direction its heading, elevator currently coming to it.
-Elevator
	-Object which can store passengers
	-Can have a goal floor and maintains a direction

The way that I have ControlSystem setup is that it takes in two parameters for its contructor. One is the number of elevators and the other is the number of floors for this building. I opted to set the number of floors as they exist as a List of List which contain passengers within the ControlSystem object. Each list of passengers inside the list represents the passengers on that floor(the current index of the outside list).

I could have created a seperate class for Floors but the list of list proved better given the time constraints of this question.  

During each step the following processes happens:

	Each elevator is checked for the following conditions:
		-If there are passengers at the current floor and the elevator is going in the same direction they want to go they are loaded on. This takes the entire turn for the elevator.
		-If there are passengers on the elevator who want to get off at the current floor they are let off. This takes the entire turn.
		-If there are passengers in the elevator they will continue

	Each floor is checked for a new passenger:
		-If there is a new passenger without an elevator coming for it then the closest not in use elevator is assigned
		-If an elevator passes by the floor with a passenger it picks it up and deschedules the elevator coming for it. 

This is an improvement on the idea of FCFS because it takes advantage of the current elevators that are in use and moving throughout the building. By being able to add more passengers onto the elevators that are in use we can leave more elevators open for new passengers that appear and maximize the amount of passengers we can serve at once. Also by having the elevators deschedule if a passing elevator picks them up it allows for more natural random positioning of elevators in an idle position throughout the building.

Ideally this could be further implemented that an elevator in use would be reset and moved to an idle position in a section of the building without any idle elevators.