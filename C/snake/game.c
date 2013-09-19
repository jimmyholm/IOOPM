#include "game.h"
#include "keyInterface.h" //Some file to manage keypresses

typedef struct game
{
  int score;
  snakePart* snake;
} game;

void tick()
{
  char movement = getChar() // get the currently pressed char from the keyInterface
  int x = 0;
  int y = 0;
  if(movement == 'a')
    x = -1;
  else if(movement == 'd')
    x = 1;
  else if(movement == 'w')
    y = -1;
  else if(movement == 's')
    y = 1;
  move(snake->xPos+x, snake->yPos+y, snake);
}
