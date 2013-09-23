#include "snakePart.h"

void drawSnake(sSdlWrapper* wrap, snakePart* snake)
{
  drawBevel(wrap, snake->xPos * 10, snake->yPos * 10, 10, 10, makeColor(255, 25, 225,75), makeColor(255, 75, 175, 100));
  if(!snake->tail == NULL)
    drawSnake(wrap, snake->tail);
}

void move(int x, int y, snakePart* snake)
{
  if(snake->tail == NULL && snake->food == 0)
    return;
  if(snake->tail == NULL)
    {
      snake->tail = createSnake(snake->xPos, snake->yPos, snake->food);
      snake->food = 0;
    }
  move(snake->xPos, snake->yPos, snake->tail);
  snake->xPos = x;
  snake->yPos = y;
}

void eat(int ammount, snakePart* snake)
{
  if(snake->tail == NULL)
    snake->food += ammount;
  else
    {
      eat(ammount, snake->tail);
      snake->food = 0;
    }
}

snakePart* createSnake(int x, int y, int food)
{
  snakePart* toReturn = malloc(sizeof(snakePart));
  toReturn->xPos = x;
  toReturn->yPos = y;
  toReturn->food = food;
  toReturn->tail = NULL;
  return toReturn;
}

int colliding(int x, int y, snakePart* snake)
{
  if(snake->tail == NULL && !(snake->xPos == x && snake->yPos == y))
    return 0;
  else if(colliding(x,y,snake->tail) == 0 && !(snake->xPos == x && snake->yPos == y))
    return 0;
  else
    return 1;
}
