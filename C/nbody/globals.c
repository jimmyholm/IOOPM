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
