#include "snakePart.h"

void move(int x, int y, snakePart snake)
{
  if(snake->tail == NULL && food == 0)
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

void eat(int ammount, snakePart snake)
{
  if(snake->tail == NULL)
    snake->food += ammount;
  else
    {
      eat(ammount, snake->tail);
      snake->food = 0;
    }
}

snakePart createSnake(int x, int y, int food)
{
  snakePart toReturn;
  toReturn->xPos = x;
  toReturn->yPos = y;
  toReturn->food = food;
  toReturn->tail = NULL;
}
