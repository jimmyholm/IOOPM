// Include guard
#ifndef NBODY_STAR_H
#define NBODY_STAR_H
#include <SDL.h>

// Structure for defining what a star is in the context of the simulation 
typedef struct sStar
{
  float X; // Horizontal position on the screen. 
  float Y; // Vertical position on the screen. Position is expressed as a float
           // in order to allow for fractions of a pixel's movement during an update.
  float Mass; // Mass of the star, used in Newton's formula.
  float aX; // horizontal acceleration, in pixels.
  float aY; // vertical acceleration, in pixels.
  float vX; // Horizontal velocity
  float vY; // Vertical Velocity
  float fX; // Horizontal Force
  float fY; // Vertical Force
  Uint32 Color;
} sStar;

// Create a star with a given location, velocity and mass - initializing the other member values to their defaults.
sStar* createStar(float x, float y, float vx, float vy, float mass);

// Free the memory used by a star.
void destroyStar(sStar* st);

// Render a star as a coloured cross where the colour is dependent on the star's mass.
void drawStar(sStar* st, Uint32* Pixels);

// Calculate force acted upon star one by star two along the X-axis.
float calculateHorizontalForce(sStar* s1, sStar* s2);

// Calculate the force acted upon start one by star two along the Y-axis.
float calculateVerticalForce(sStar* s1, sStar* s2);

// Calculate distance between two stars
float calculateDistance(sStar* s1, sStar* s2);

// Calculate forces acted on s1 by s2
void calculateForce(sStar* s1, sStar* s2);

#endif
