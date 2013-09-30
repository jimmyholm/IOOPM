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

size_t sizeofPoint()
{
  return sizeof(point);
}

void pointGetPos(point* p, int* x, int* y)
{
  if(x != NULL)
    *x = p->xPos;
  if(y != NULL)
    *y = p->yPos;
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
  sTextGFX* scoreText;
  sTextGFX* highScoreText;
  unsigned int score;
  unsigned int highScore;
  int running;
  int width;
  int height;
  snake* snake;
  foodList* foodList;
  wallList* wallList;
  sSdlWrapper* wrap;
} game;

void loadHighscore(game* Game)
{
  FILE* f = fopen("highscore.dat", "rb");
  if(f == NULL)
  {
    Game->highScore = 0;
    return;
  }
  fread(&Game->highScore, sizeof(unsigned int), 1, f);
  fclose(f);  
}

void saveHighscore(game* Game)
{
  FILE* f = fopen("highscore.dat", "wb");
  if(f == NULL)
  {
    return;
  }
  fwrite(&Game->highScore, sizeof(unsigned int), 1, f);
  fclose(f);
}

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
  Game->foodList->scoreCount = 100;
  listClear(Game->foodList->list);
  listClear(Game->snake->snakeParts);
  point* tail = createPoint(16,11);
  listPushFront(Game->snake->snakeParts, tail);
  free(tail);
  point* head = createPoint(16,10);
  listPushFront(Game->snake->snakeParts, head);
  free(head);
  char score[20];
  sprintf(score, "Score: %06d", Game->score);
  Game->scoreText = createText(Game->wrap, score, 0xFF00FF00);
  loadHighscore(Game);
  sprintf(score, "High Score: %06d", Game->highScore);
  Game->highScoreText = createText(Game->wrap, score, 0xFFFF0000);
}

void destroyGame(game* Game)
{
  listClear(Game->wallList->list);
  free(Game->wallList->list);
  free(Game->wallList->it);
  free(Game->wallList);

  listClear(Game->foodList->list);
  free(Game->foodList->list);
  free(Game->foodList->it);
  free(Game->foodList);

  listClear(Game->snake->snakeParts);
  free(Game->snake->snakeParts);
  free(Game->snake->it);
  free(Game->snake);

  destroyText(Game->scoreText);
  free(Game);
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

void tick(game* Game)
{
  if(!Game->running)
    return;

  if(listEmpty(Game->snake->snakeParts))
    return;
  
  if(listEmpty(Game->foodList->list))
  {
    int x = 1+rand()%(Game->width-2);
    int y = 1+rand()%(Game->height-2);
    point* toAdd = createPoint(x, y);
    while(colliding(Game->snake->snakeParts, Game->snake->it, toAdd))
    {
      free(toAdd);
      x = 1+rand()%(Game->width-2);
      y = 1+rand()%(Game->height-2);
      toAdd = createPoint(x,y);
    }
    listPushFront(Game->foodList->list, toAdd);
    free(toAdd);
  }

  int dX = 0;
  int dY = 0;
//Get the first and second part of the snake
  listHead(Game->snake->snakeParts, &(Game->snake->it));
  point* head = listGet(Game->snake->it);
  listIteratorNext(Game->snake->it);
  if(listIteratorEnd(Game->snake->it))
    return;
  point* tail = listGet(Game->snake->it);

//Calculate the next snake position based on previous position
  dX = head->xPos - tail->xPos;
  dY = head->yPos - tail->yPos;

//Check keyboard input
  int dirX = 0;
  int dirY = 0;
  if(keyDown(Game->wrap, SDLK_a) && dX != 1 )
    dirX = -1;
  else if(keyDown(Game->wrap, SDLK_d) && dX != -1)
    dirX = 1;
  else if(keyDown(Game->wrap, SDLK_w) && dY != 1)
    dirY = -1;
  else if(keyDown(Game->wrap, SDLK_s) && dY != -1)
    dirY = 1;
  if(dirX != 0 || dirY != 0)
  {
    dX = dirX;
    dY = dirY;
  }
  Uint32 elapsed = elapsedTime(Game->wrap);
  Game->snake->move += elapsed;
  while(Game->snake->move >= Game->snake->velocity)
  {
//Move snake
    moveSnake(Game->snake, head->xPos + dX, head->yPos + dY);
    Game->snake->move -= Game->snake->velocity;
  }

//Check collision with walls, and stop running if collision detected
  listHead(Game->snake->snakeParts, &(Game->snake->it));
  head = listGet(Game->snake->it);
  if(colliding(Game->wallList->list, Game->wallList->it, head))
  {
    // Destroy Game
    saveHighscore(Game);
    Game->running = 0;
    State = 0;
  }
  //listIteratorNext(Game->
  if(colliding(Game->snake->snakeParts, Game->snake->it, head))
  {
    // Destroy Game
    saveHighscore(Game);
    Game->running = 0;
    State = 0;
  }

//Check collision with food, and let the snake grow if collision detected
  if(colliding(Game->foodList->list, Game->foodList->it, head))
  {
    int fc = (int)((float)(listSize(Game->snake->snakeParts))/10.0f);
    Game->snake->food += (int)fc+1;
    if(fc > 0)
      Game->snake->velocity = 333 / (1.5*fc);
    Game->score += Game->foodList->scoreCount * ((1.5*fc)+1);
    destroyText(Game->scoreText);
    char score[20];
    sprintf(score, "Score: %06d", Game->score);
    Game->scoreText = createText(Game->wrap, score, 0xFF00FF00);
    if(Game->score > Game->highScore)
    {
      Game->highScore = Game->score;
      sprintf(score, "High Score: %06d", Game->highScore);
      Game->highScoreText = createText(Game->wrap, score, 0xFFFF0000);
    }
    listClear(Game->foodList->list);
  }

//Paint the snake
  repaint(Game->wrap, Game->snake->snakeParts, Game->snake->it, makeColor(255, 75, 175, 100), makeColor(255, 100, 200, 125));

//Paint the food
  repaint(Game->wrap, Game->foodList->list, Game->foodList->it, makeColor(255, 175, 100, 75), makeColor(255, 200, 125, 100));

//Paint the walls
  repaint(Game->wrap, Game->wallList->list, Game->wallList->it, makeColor(255, 50, 25, 175), makeColor(255, 75, 50, 200));

//Paint the score counter
  renderText(Game->wrap, Game->scoreText, 700, 12);
//Paint the high score counter
  renderText(Game->wrap, Game->highScoreText, 120, 12);

}
