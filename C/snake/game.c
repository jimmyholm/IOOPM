#include "SDLWrapper.h"
#include "game.h"
#include "LinkedList.h"

typedef struct point
{
  int xPos;
  int yPos;
} point;

point* createPoint(int x, int y)
{
  point* point = malloc(sizeof(point));
  point->xPos = x;
  point->yPos = y;
  return point;
}

typedef struct snake
{
  int food;
  sLinkedList* snakeParts;
  sListIterator* it;
} snake;

typedef struct foodList
{
  int foodCount;
  int scoreCount;
  sLinkedList* list;
  sListIterator* it;
} foodList;

typedef struct wallList
{
  sLinkedList* list;
  sListIterator* it;
} wallList;

typedef struct game
{
  int score;
  snake* snake;
  foodList* foodList;
  wallList* wallList;
  sSdlWrapper* wrap;
} game;

void repaint(sSdlWrapper* wrap, sLinkedList* list, sListIterator* it, Uint32 color, Uint32 borderColor)
{
  if(listEmpty(list))
    return;
  listHead(list, &it);
  while(!listIteratorEnd(it))
  {
    point* toDraw = listGet(it);
    drawBevel(wrap, toDraw->xPos*25, toDraw->yPos*25, 25, 25, color, borderColor);
    listIteratorNext(it);
  }
}

void moveSnake(snake* snake, int x, int y)
{
  point* toInsert = createPoint(x,y);
  listPushFront(snake->snakeParts, toInsert);
  free(toInsert);
  if(snake->food <= 0)
  {
    listPopBack(snake->snakeParts);
    return;
  }
  snake->food--;
}

game* initGame(sSdlWrapper* wrapper, int width, int height)
{
  game* ret = malloc(sizeof(game));
  ret->score = 0;
  ret->wrap = wrapper;
  ret->snake = malloc(sizeof(snake));
  ret->snake->food = 0;
  ret->snake->it = 0;
  ret->snake->snakeParts = 0;

  ret->foodList = malloc(sizeof(foodList));
  ret->foodList->list = 0;
  listInitialize(&(ret->foodList->list), sizeof(point), NULL);

  ret->wallList = malloc(sizeof(wallList));
  ret->wallList->list = 0;
  listInitialize(&(ret->wallList->list), sizeof(point), NULL);
  for(int x = 0; x < width; x++)
    for(int y = 0; y < height; y++)
      if(x == 0 || x == (width-1) || y == 0 || y == (height-1))
      {
	point* toAdd = createPoint(x,y);
	listPushFront(ret->wallList->list, toAdd);
	free(toAdd);
      }


  listInitialize(&(ret->snake->snakeParts), sizeof(point), NULL);
  listPushFront(ret->snake->snakeParts, createPoint(5,5));
  listPushFront(ret->snake->snakeParts, createPoint(5,6));
  return ret;
}

void tick(game* game)
{
  if(listEmpty(game->snake->snakeParts))
    return;
  int dX = 0;
  int dY = 0;
  listHead(game->snake->snakeParts, &(game->snake->it));
  point* head = listGet(game->snake->it);
  listIteratorNext(game->snake->it);
  if(listIteratorEnd(game->snake->it))
    return;
  point* tail = listGet(game->snake->it);

  dX = head->xPos - tail->xPos;
  dY = head->yPos - tail->yPos;

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
  moveSnake(game->snake, head->xPos + dX, head->yPos + dY);
  
  repaint(game->wrap, game->snake->snakeParts, game->snake->it, makeColor(255, 75, 175, 100), makeColor(255, 100, 200, 125));

  repaint(game->wrap, game->foodList->list, game->foodList->it, makeColor(255, 175, 100, 75), makeColor(255, 200, 125, 100));

  repaint(game->wrap, game->wallList->list, game->wallList->it, makeColor(255, 50, 25, 175), makeColor(255, 75, 50, 200));
}
