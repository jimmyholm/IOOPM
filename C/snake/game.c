#include "SDLWrapper.h"
#include "game.h"
#include "LinkedList.h"
#include "global.h"
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
  Uint32 velocity;
  Uint32 move;
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
  int running;
  int width;
  int height;
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
  point* toDraw = 0;
  while(!listIteratorEnd(it))
  {
    toDraw = (point*)listGet(it);
    drawBevel(wrap, toDraw->xPos*25, toDraw->yPos*25, 25, 25, color, borderColor);
    listIteratorNext(it);
  }
}

int colliding(sLinkedList* list, sListIterator* it, point* p)
{
  if(listEmpty(list))
    return 0;
  listHead(list, &it);
  point* comp = 0;
  while(!listIteratorEnd(it))
  {
    comp = listGet(it);
    if(p != comp)
      if(comp->xPos == p->xPos && comp->yPos == p->yPos)
	return 1;
    listIteratorNext(it);
  }
  return 0;
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

void setupGame(game* Game)
{
  Game->running = 1;
  Game->score = 0;
  Game->snake->food = 0;
  Game->snake->velocity = 333;
  Game->snake->move = 0;
  Game->foodList->foodCount = 1;
  Game->foodList->scoreCount = 100;
  listClear(Game->foodList->list);
  listClear(Game->snake->snakeParts);
  point* tail = createPoint(16,11);
  listPushFront(Game->snake->snakeParts, tail);
  free(tail);
  point* head = createPoint(16,10);
  listPushFront(Game->snake->snakeParts, head);
  free(head);
}
game* initGame(sSdlWrapper* wrapper, int width, int height)
{
  game* ret = malloc(sizeof(game));
  ret->wrap = wrapper;
  ret->width = width;
  ret->height = height;
  ret->snake = malloc(sizeof(snake));
  ret->foodList = malloc(sizeof(foodList));
  ret->wallList = malloc(sizeof(wallList));
  ret->snake->it = 0;
  ret->snake->snakeParts = 0;
  ret->foodList->list = 0;
  ret->foodList->it = 0;
  ret->wallList->list = 0;
  ret->wallList->it = 0;
  listInitialize(&(ret->foodList->list), sizeof(point), NULL);
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
  setupGame(ret);
  return ret;
}

void tick(game* game)
{
  if(!game->running)
    return;

  if(listEmpty(game->snake->snakeParts))
    return;
  
  if(listEmpty(game->foodList->list))
  {
    int x = 1+rand()%(game->width-2);
    int y = 1+rand()%(game->height-2);
    point* toAdd = createPoint(x, y);
    listPushFront(game->foodList->list, toAdd);
    free(toAdd);
  }

  int dX = 0;
  int dY = 0;
//Get the first and second part of the snake
  listHead(game->snake->snakeParts, &(game->snake->it));
  point* head = listGet(game->snake->it);
  listIteratorNext(game->snake->it);
  if(listIteratorEnd(game->snake->it))
    return;
  point* tail = listGet(game->snake->it);

//Calculate the next snake position based on previous position
  dX = head->xPos - tail->xPos;
  dY = head->yPos - tail->yPos;

//Check keyboard input
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
  Uint32 elapsed = elapsedTime(game->wrap);
  game->snake->move += elapsed;
  while(game->snake->move >= game->snake->velocity)
  {
//Move snake
    moveSnake(game->snake, head->xPos + dX, head->yPos + dY);
    game->snake->move -= game->snake->velocity;
  }

//Check collision with walls, and stop running if collision detected
  listHead(game->snake->snakeParts, &(game->snake->it));
  head = listGet(game->snake->it);
  if(colliding(game->wallList->list, game->wallList->it, head))
  {
    // Destroy game
    game->running = 0;
    State = 0;
  }
  //listIteratorNext(game->
  if(colliding(game->snake->snakeParts, game->snake->it, head))
  {
    // Destroy game
    game->running = 0;
    State = 0;
  }

//Check collision with food, and let the snake grow if collision detected
  if(colliding(game->foodList->list, game->foodList->it, head))
  {
    game->snake->food += game->foodList->foodCount;
    game->score += game->foodList->scoreCount;
    listClear(game->foodList->list);
  }

//Paint the snake
  repaint(game->wrap, game->snake->snakeParts, game->snake->it, makeColor(255, 75, 175, 100), makeColor(255, 100, 200, 125));

//Paint the food
  repaint(game->wrap, game->foodList->list, game->foodList->it, makeColor(255, 175, 100, 75), makeColor(255, 200, 125, 100));

//Paint the walls
  repaint(game->wrap, game->wallList->list, game->wallList->it, makeColor(255, 50, 25, 175), makeColor(255, 75, 50, 200));
}
