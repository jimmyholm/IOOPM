CC = gcc
OBJ = LinkedList.o SDLWrapper.o game.o main.o
TARGET = snake
SDL_CFLAGS := $(shell sdl2-config --cflags)
SDL_LIBS := $(shell sdl2-config --libs) -lSDL2_ttf
CFLAGS = -Wall -std=c99 -ggdb $(SDL_CFLAGS)
PROFILE = 
release: CFLAGS = -O3 -std=c99 $(SDL_CFLAGS)
profile: PROFILE = yes
.PHONY : all
all: $(OBJ)
ifneq ($(PROFILE),)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJ) $(SDL_LIBS)
else
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJ) $(SDL_LIBS) -pg
endif

.PHONY : profile
profile: all
.PHONY : release
release: all
%.o : %.c
ifneq ($(PROFILE),)
	$(CC) -c $(CFLAGS) $< -pg
else
	$(CC) -c $(CFLAGS) $< 
endif

.PHONY : clean
clean:
	-rm $(TARGET) *.o
