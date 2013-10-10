CC = javac
OBJ = Car.class Lane.class Light.class Simulation.class TrafficSystem.class

.PHONY : all
all:
	$(CC) *.java

%.class : %.java
	$(CC) $<

clean:
	rm *.class
