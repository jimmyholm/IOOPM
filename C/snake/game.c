#include "SDLWrapper.h"
#include "game.h"

typedef struct game
{
  int score;
  snakePart* snake;
  sSdlWrapper* wrap;
} game;

void repaint(game* game)
{
  //drawBevel(game->wrap, 210, 210, 10, 10, makeColor(255,0,200, 255), makeColor(255,200,200,200));
  drawSnake(game->wrap, game->snake);
}

game* initGame(sSdlWrapper* wrapper)
{
  game* ret = malloc(sizeof(game));
  ret->score = 0;
  ret->wrap = wrapper;
  ret->snake = createSnake(2, 2, 0);
  ret->snake->tail = createSnake(2,1,3);
  return ret;
}

void tick(game* game)
{
  int x = 0;
  int y = 0;
  snakePart* snake = game->snake;
  snakePart* tail = (snakePart*)snake->tail;
  //check for user input and edit snake movement accordingly
  if(keyDown(game->wrap, SDLK_a))
    x = -1;
  else if(keyDown(game->wrap, SDLK_d))
    x = 1;
  else if(keyDown(game->wrap, SDLK_w))
    y = -1;
  else if(keyDown(game->wrap, SDLK_s))
    y = 1;
//if no input was detected either move down or move the way the snake is facing
  if(x == 0 && y == 0)
  {
     if(tail == NULL)
       y = 1;
     else
     {
       x = tail->xPos - snake->xPos;
       y = tail->yPos - snake->yPos;
     }
  }
//calculate the new position
  x += snake->xPos;
  y += snake->yPos;
//check if the new position is a snake
  // if(colliding(x, y, snake) == 0)
  move(x, y, snake);
}
