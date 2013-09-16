CC = gcc

OBJ = main.o star.o simulation.o globals.o
TARGET = nbody
SDL_CFLAGS := $(shell sdl2-config --cflags)
SDL_LIBS := $(shell sdl2-config --libs)
CFLAGS = -Wall -std=c99 -ggdb $(SDL_CFLAGS)
.PHONY : debug
debug: $(OBJ)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJ) $(SDL_LIBS) /lib/x86_64-linux-gnu/libm.so.6

.PHONY : release
release: $(OBJ)
	$(CC) -O3 $(CFLAGS) -o $(TARGET) $(OBJ) $(SDL_LIBS) /lib/x86_64-linux-gnu/libm.so.6

main.o: star.h simulation.h
	$(CC) -c $(CFLAGS) -o main.o main.c

star.o: star.h
	$(CC) -c $(CFLAGS) -o star.o star.c
simulation.o: star.h simulation.h
	$(CC) -c $(CFLAGS) -o simulation.o simulation.c
globals.o: globals.h
	$(CC) -c $(CFLAGS) -o globals.o globals.c
.PHONY : clean
clean:
	 -rm $(TARGET) *.o
