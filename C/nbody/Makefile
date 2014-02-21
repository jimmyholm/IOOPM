CC = gcc
OBJ = globals.o LinkedList.o star.o simulation.o main.o
TARGET = nbody
SDL_CFLAGS := $(shell sdl2-config --cflags)
SDL_LIBS := $(shell sdl2-config --libs)  /lib/x86_64-linux-gnu/libm.so.6
CFLAGS = -Wall -std=c99 -ggdb $(SDL_CFLAGS)
PROFILE = 
release: CFLAGS = -O3 -std=c99 $(SDL_CFLAGS)
profile: PROFILE = -pg
.PHONY : all
all: $(OBJ)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJ) $(SDL_LIBS) $(PROFILE)

.PHONY : profile
profile: all
.PHONY : release
release: all
%.o : %.c
	$(CC) -c $(CFLAGS) $< $(PROFILE)

.PHONY : clean
clean:
	-rm $(TARGET) *.o
