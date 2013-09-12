#include "globals.h"
#include "star.h"

sStar* createStar(float x, float y, float vx, float vy, float mass)
{
  sStar* ret = malloc(sizeof(sStar));
  ret->X = x;
  ret->Y = y;
  ret->Mass = mass;
  ret->aX = .0f;
  ret->aY = .0f;
  ret->vX = vx;
  ret->vY = vy;
  ret->fX = .0f;
  ret->fY = .0f;
  // Set the colour of the star relative to it's mass - heavier stars are red, lighter stars are green.
  Uint8 red   = (Uint32)(255.0f*mass);
  Uint8 green = (Uint32)(255.0f - red);
  Uint8 blue = 0;
  Uint8 alpha = 255;
  // Pack the colors into an unsigned, 32 bit integer (a Uint32)
  // in this order: msb : AAAAAAAA RRRRRRRRR GGGGGGGG BBBBBBBB : lsb
  ret->Color = (alpha << 24) +  (red << 16) + (green << 8) + blue;
  return ret;
}

// Simple wrapper for freeing the memory used by a star; wrapped in case one should want to store dynamic data inside a star that would itself need to be freed as well.
void destroyStar(sStar* star)
{
  free(star);
}

// Draw star. Pixels is a dynamically allocated array of color-values, describing the color of any individual pixel in the window's drawing space.
void drawStar(sStar* star, Uint32* Pixels)
{
  // Sanity check
  if(star == NULL)
    return;
  int x = (int)star->X;
  int y = (int)star->Y;
  // Can't draw outside of window boundaries.
  if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
    return;
  // Index into the one dimensional pixel array using the x & y coordinates of the star.
  size_t index = x + (WIDTH * y);
  Uint32 color = star->Color;
  // Colorise the center of the star, the actual x & y position.
  Pixels[index] = color;
  // Colorise a "cross" around the center pixel to enhance visibility - that is one pixel directly above and one below as well as one to the left and one to the right.
  if(x > 0)
  {
    index = (x-1) + (WIDTH * y);
    Pixels[index] = color;
  }
  if(x < WIDTH-1)
  {
    index = (x+1) + (WIDTH * y);
    Pixels[index] = color;
  }
  if(y > 0)
  {
    index = x + (WIDTH * (y-1));
    Pixels[index] = color;
  }
  if(y < HEIGHT-1)
  {
    index = x + (WIDTH * (y+1));
    Pixels[index] = color;
  }
}

float calcDistance(sStar* s1, sStar* s2)
{
  float dx = s2->X - s1->X;
  float dy = s1->Y - s2->Y;
  return sqrt(dx*dx + dy*dy);
}

void calculateForce(sStar* s1, sStar* s2)
{
  float dist = calcDistance(s1, s2);
  float dx = s2->X - s1->X;
  float dy = s2->Y - s1->Y;
  float angle = atan2(dy, dx);
  float xMul = (dx < 0) ? -1.0f : 1.0f;
  float yMul = (dy < 0) ? -1.0f : 1.0f;
  float m1 = s1->Mass;
  float m2 = s2->Mass;
  float m = m1 * m2;
  float F = (G_CONST * m) / (dist*dist);
  if(F > 100.0f)
    F = 100.0f;
     printf("Distance:\t%f\ndx:\t%f\ndy:\t%f\nangle:\t%f\nxMul:\t%f\nyMul:\t%f\nm1:\t%f\nm2:\t%f\nm:\t%f\nF:\t%f\nc(a):\t%f\ns(a):\t%f----\n",
	    dist, dx, dy, angle, xMul, yMul, m1, m2, m, F, cos(angle), sn(angle));
 
  s1->fX += F * cos(angle) * xMul;
  s1->fY += F * sin(angle) * yMul;
  s2->fX += -1.0f * s1->fX;
  s2->fY += -1.0f * s1->fY;
}


// Make use of the Newtonian Gravitational formula, using the G constant defined in globals.h, and use the distance on the X-axis as basis for the distance between the two bodies.
// Uses the law of the inverse square for gravitational fall-off - that is, greater distances means exponentially weaker gravity.
float calculateHorizontalForce(sStar* s1, sStar* s2)
{
  int dx = (int)(s2->X - s1->X);
  if(dx == 0)
    return 0.1f;
  // We need to keep track of whether this is a negative or positive force (going left or right), since when we square the distance, the sign will be lost.
  float mul = (dx < 0) ? -1.0f : 1.0f;
  float mass1 = s1->Mass;
  float mass2 = s2->Mass;
  return G_CONST * ((mass1 * mass2) / (dx*dx))*mul;
}

// See "calculateHorizontalForce"
float calculateVerticalForce(sStar* s1, sStar* s2)
{
  int dy = (int)(s2->Y - s1->Y);
  if(dy == 0)
    return 0.1f;
  int mul = (dy < 0) ? -1.0f : 1.0f;
  float mass1 = s1->Mass;
  float mass2 = s2->Mass;
  return G_CONST * ((mass1 * mass2) / (dy*dy))*mul;
}
