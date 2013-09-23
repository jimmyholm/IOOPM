#ifndef SNAKE_GAME_H
#define SNAKE_GAME_H
#include "snakePart.h"

typedef struct game game;

//Steps the game one tick forward
void tick(game* game);

//Initializes the map and parses the wrapper to the game
game* initGame(sSdlWrapper* wrapper);

void repaint(game* game);
#endif
