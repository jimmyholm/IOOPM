CC = gcc

OBJ = SDLWrapper.o game.o snakePart.o main.o
TARGET = snake
SDL_CFLAGS := $(shell sdl2-config --cflags)
SDL_LIBS := $(shell sdl2-config --libs)
CFLAGS = -Wall -std=c99 -ggdb $(SDL_CFLAGS)

.PHONY : all
all: $(OBJ)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJ) $(SDL_LIBS) /lib/x86_64-linux-gnu/libm.so.6

%.o : %.c
	$(CC) -c $(CFLAGS) $<

.PHONY : clean
clean:
	-rm $(TARGET) *.o
