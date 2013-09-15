#include "globals.h"
#include <stdlib.h>

int RandInt(int min, int max)
{
  float rnd = (float)rand() / (float)RAND_MAX;
  return (int)(min + (rnd*(max-min)));
}

float RandFloat(float min, float max)
{
  float rnd = (float)rand() / ((float)RAND_MAX/(max-min));
  return(min + rnd);
}
void drawCircle(int x, int y, unsigned int color, int radius, unsigned int* Pixels)
{
  if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
    return;
  if(radius == 1)
    Pixels[x + (WIDTH*y)] = color;
    
  for(int Y=-radius;Y <=radius; Y++)
    for(int X=-radius; X<=radius; X++)
        if(X*X+Y*Y <= radius*radius + radius*0.8f)
	{
	  int xC = x+X;
	  int yC = y+Y;
	  if(xC >= 0 && xC < WIDTH && yC >= 0 && yC < HEIGHT)
	  {
	    int index = xC + (WIDTH * yC);
	    Pixels[index] = color;
	  }
	}
}
