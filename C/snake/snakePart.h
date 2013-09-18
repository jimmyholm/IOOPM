#ifndef SNAKE_SNAKEPART_H
#define SNAKE_SNAKEPART_H

typedef struct snakePart
{
  unsigned int xPos;
  unsigned int yPos;
  unsigned int food;
  snakePart* tail;
} snakePart;

snakePart createSnake(int x, int y, int food);

void eat(int ammount, snakePart snake);

void move(int x, int y, snakePart snake);

#endif
