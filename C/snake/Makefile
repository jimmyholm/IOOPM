CC = gcc

OBJ = main.o SDLWrapper.o
TARGET = snake
SDL_CFLAGS := $(shell sdl2-config --cflags)
SDL_LIBS := $(shell sdl2-config --libs)
CFLAGS = -Wall -std=c99 -ggdb $(SDL_CFLAGS)
.PHONY : debug
debug: $(OBJ)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJ) $(SDL_LIBS) /lib/x86_64-linux-gnu/libm.so.6

.PHONY : release
release: $(OBJ)
	$(CC) -O3 $(CFLAGS) -o $(TARGET) $(OBJ) $(SDL_LIBS) /lib/x86_64-linux-gnu/libm.so.6

main.o: SDLWrapper.o
	$(CC) -c $(CFLAGS) -o main.o main.c

SDLWrapper.o: SDLWrapper.h
	$(CC) -c $(CFLAGS) -o SDLWrapper.o SDLWrapper.c
game.o: game.h
	$(CC) -c $(CFLAGS) -o game.o game.c
snakePart.o: snakePart.h
	$(CC) -c $(CFLAGS) -o snakePart.o snakePart.c
.PHONY : clean
clean:
	 -rm $(TARGET) *.o
