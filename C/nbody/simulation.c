#include <stdlib.h>
#include <math.h>
#include "simulation.h"

typedef struct sSimulation
{
  unsigned int NumStars;
  sStar** Stars;
} sSimulation;


/* Creates an instance of the simulation type and populates it with initialized simulated bodies */
sSimulation* createSimulation(unsigned int stars)
{
  sSimulation* ret = malloc(sizeof(sSimulation));
  ret->NumStars = stars;
  ret->Stars = malloc(sizeof(sStar*)*stars);
  float x =  .0f;
  float y =  .0f;
  float vx = .0f;
  float vy = .0f;
  float mass = .0f;
  float t = .0f;
  float u = .0f;
  int maxRadius = HEIGHT/2;
  for(int i = 0; i < stars; i++)
  {
    // Create a spiral-shaped galaxy of stars.
    t += RandFloat(0.523f, 1.1775f);
    u += RandFloat(1.5f, 3.0f);
    if(u >= maxRadius)
      u -= maxRadius;
    x = 400 + (u*cos(t));
    y = 300 + (u*sin(t));
    float V = RandFloat(0.0f, 2.5f);
    vx = V * cos(t);
    vy = V * sin(t);
    mass = RandFloat(0.5f, 1.0f) * MAXMASS;
    ret->Stars[i] = createStar(x, y, vx, vy, mass);
  }
  return ret;
}

void destroySimulation(sSimulation* sim)
{
  // Sanity check.
  if(sim == NULL)
    return;
  // Free up all the memory used by the stars
  for(int i = 0; i < sim->NumStars; i++)
  {
    destroyStar(sim->Stars[i]);
  }
  // And free up the simulation itself.
  free(sim);
}

void calculateNextFrame(sSimulation* sim)
{
  // Sanity check.
  if(sim == NULL)
    return;
  int Stars = sim->NumStars;
  // Reset forces prior to calculating the next frame
  for(int i = 0; i < Stars; i++)
  {
    sim->Stars[i]->fX = 0.0f;
    sim->Stars[i]->fY = 0.0f;
  }
  // For each star in the simulation, we need to calculate the force acted upon it by every other star in the simulation. 
  for(int i = 0; i < Stars-1; i++)
  {
    for(int j = i+1; j < Stars; j++)
    {
      calculateForce(sim->Stars[i], sim->Stars[j]);
    }
  }
}

void updateSimulation(sSimulation* sim, float timeElapsed)
{
  if(sim == NULL)
    return;
  sStar* star = NULL;
  float t2 = timeElapsed * timeElapsed;
  for(int i = 0; i < sim->NumStars; i++)
  {
    star = sim->Stars[i];
    // Calculate the acceleration using F = MA <=> F/M = A
    star->aX = (star->fX / (star->Mass));
    star->aY = (star->fY / (star->Mass));
    star->vX += (star->aX * timeElapsed);
    star->vY += (star->aY * timeElapsed);
    star->X += (star->vX * timeElapsed) + ((star->aX * t2) / 2);
    star->Y += (star->vY * timeElapsed) + ((star->aY * t2) / 2);
  }
}

void drawSimulation(sSimulation* sim, Uint32* Pixels)
{
  if(sim == NULL || Pixels == NULL)
    return;
  sStar* star = NULL;
  for(int i = 0; i < sim->NumStars; i++)
  {
    star = sim->Stars[i];
    drawStar(star, Pixels);
  }
}
