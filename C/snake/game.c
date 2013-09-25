#include "SDLWrapper.h"
#include "game.h"
#include "LinkedList.h"

typedef struct snakePart
{
  int xPos;
  int yPos;
} snakePart;

snakePart* createSnakePart(int x, int y)
{
  snakePart* snake = malloc(sizeof(snakePart));
  snake->xPos = x;
  snake->yPos = y;
  return snake;
}

typedef struct snake
{
  int food;
  sLinkedList* snakeParts;
  sListIterator* it;
} snake;

typedef struct game
{
  int score;
  snake* snake;
  sSdlWrapper* wrap;
} game;

void repaint(game* game)
{
  if(listEmpty(game->snake->snakeParts))
    return;
  listHead(game->snake->snakeParts, &(game->snake->it));
  while(!listIteratorEnd(game->snake->it))
    {
      snakePart* snake = listGet(game->snake->it);
      drawBevel(game->wrap, snake->xPos*10, snake->yPos*10, 10, 10, makeColor(255, 50, 175, 100), makeColor(255, 75, 200, 125));
      listIteratorNext(game->snake->it);
    }
}

game* initGame(sSdlWrapper* wrapper)
{
  game* ret = malloc(sizeof(game));
  ret->score = 0;
  ret->wrap = wrapper;

  ret->snake->snakeParts = malloc(sizeof(sLinkedList*));

  listInitialize(&(ret->snake->snakeParts), sizeof(snakePart), NULL);
  listPushFront(ret->snake->snakeParts, createSnakePart(5,5));
  listPushFront(ret->snake->snakeParts, createSnakePart(5,6));
  return ret;
}

void tick(game* game)
{
  int dX = 0;
  int dY = 0;

  int dirX = 0;
  int dirY = 0;
  if(keyDown(game->wrap, SDLK_a))
    dirX = -1;
  else if(keyDown(game->wrap, SDLK_d))
    dirX = 1;
  else if(keyDown(game->wrap, SDLK_w))
    dirY = -1;
  else if(keyDown(game->wrap, SDLK_s))
    dirY = 1;
  if(dirX != 0 || dirY != 0)
  {
    dX = dirX;
    dY = dirY;
  }
  printf("%d:%d", dX, dY);
}
