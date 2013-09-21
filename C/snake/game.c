#include "SDLWrapper.h"
#include "game.h"

typedef struct game
{
  int score;
  snakePart* snake;
} game;

void tick()
{
//Still don't understand what to do here
  char movement = getChar() // get the currently pressed char from the keyInterface
  int x = 0;
  int y = 0;
//check for user input and edit snake movement accordingly
  if(keyPressed('a') == 1)
    x = -1;
  else if(movement == 'd')
    x = 1;
  else if(movement == 'w')
    y = -1;
  else if(movement == 's')
    y = 1;
//if no input was detected either move down or move the way the snake is facing
  if(x == 0 && y == 0)
  {
     if(snake->tail == NULL)
       y = 1;
     else
     {
       x = snake->tail->xPos - snake->xPos;
       y = snake->tail->yPos - snake->yPos;
     }
  }
//calculate the new position
  x += snake->xPos;
  y += snake->yPos;
//check if the new position is a snake
  if(colliding(x, y, snake) == 0)
    move(x, y, snake);
}
