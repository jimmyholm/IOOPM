#ifndef SNAKE_SNAKEPART_H
#define SNAKE_SNAKEPART_H

typedef struct snakePart
{
  unsigned int xPos;
  unsigned int yPos;
  unsigned int food;
  snakePart* tail;
} snakePart;

//Return 1 if any part of the snake is located on the point (x,y) else 0
int colliding(int x, int y, snakePart snake);

//Creates a snake at the position (x,y) with food
snakePart createSnake(int x, int y, int food);

//Makes the snake eat and pass the food down to its tail
void eat(int ammount, snakePart snake);

//Move the snake, and spawn a new snakepart at its tail if the tail have food in it
void move(int x, int y, snakePart snake);

#endif
