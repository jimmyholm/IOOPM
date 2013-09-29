#ifndef SNAKE_GAME_H
#define SNAKE_GAME_H
//#include "snakePart.h"
typedef struct point point;
typedef struct game game;

//Steps the game one tick forward
void tick(game* game);
point* createPoint(int x, int y);
size_t sizeofPoint();
void pointGetPos(point* p, int* x, int* y);
//Initializes the map and parses the wrapper to the game
game* initGame(sSdlWrapper* wrapper, int width, int height);
// Do setup and (re-)initialize the game.
void setupGame(game* game);
#endif
