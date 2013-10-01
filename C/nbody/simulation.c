#include <stdlib.h>
#include <math.h>
#include "simulation.h"
#include "LinkedList.h"

typedef struct sSimulation
{
  unsigned int NumStars;
  //sStar** Stars;
  sLinkedList* Stars;
} sSimulation;

/* Creates an instance of the simulation type and populates it with initialized simulated bodies */
sSimulation* createSimulation(unsigned int stars)
{
  sSimulation* ret = malloc(sizeof(sSimulation));
  ret->NumStars = stars;
  // ret->Stars = malloc(sizeof(sStar*)*stars);
  ret->Stars = 0;
  listInitialize(&ret->Stars, sizeof(sStar), NULL);
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
    mass = RandFloat(0.5f, 1.0f) * (MAXMASS);
    sStar* star = createStar(x, y, vx, vy, mass);
    listPushBack(ret->Stars, (void*)star);
    destroyStar(star);
  }
  return ret;
}

void destroySimulation(sSimulation* sim)
{
  // Sanity check.
  if(sim == NULL)
    return;
  // Free up all the memory used by the stars
  listDestroy(&sim->Stars);
  // And free up the simulation itself.
  free(sim);
}
void checkCollisions(sSimulation* sim)
{
// Sanity check.
  if(sim == NULL)
    return;
  int Stars = sim->NumStars;
  // Reset forces prior to calculating the next frame
  sListIterator* It = 0;
  sListIterator* It2 = 0;
  listHead(sim->Stars, &It);
  for(; !listIteratorEnd(It); listIteratorNext(It))
  {
    sStar* s = listGet(It);
    s->fX = 0.0f;
    s->fY = 0.0f;
  }
  // For each star in the simulation, we need to calculate the force acted upon it by every other star in the simulation. 
  listHead(sim->Stars, &It);
  for(int i = 0; i < Stars-1; i++)
  {
    listIteratorCopy(It, &It2);
    sStar* s1 = (sStar*)listGet(It);
    for(listIteratorNext(It2); !listIteratorEnd(It2);)
    {
      sStar* s2 = (sStar*)listGet(It2);
      if(collision(s1, s2))
      {
	float mass = s1->Mass + s2->Mass;
	if(s1->Mass > s2->Mass)
	{
	  listErase(It2);
	  s1->Mass = mass;
	  sim->NumStars = Stars = listSize(sim->Stars);
	  break;
	}
	else
	{
	  listErase(It);
	  s2->Mass = mass;
	  sim->NumStars = Stars = listSize(sim->Stars);
	  break;
	}
      }
      else
	listIteratorNext(It2);
    }
    listIteratorNext(It);
  }
  listIteratorDestroy(&It2);
  listIteratorDestroy(&It);
}
void calculateNextFrame(sSimulation* sim)
{
  // Sanity check.
  if(sim == NULL)
    return;
  int Stars = sim->NumStars;
  // Reset forces prior to calculating the next frame
  sListIterator* It = 0;
  sListIterator* It2 = 0;
  listHead(sim->Stars, &It);
  for(; !listIteratorEnd(It); listIteratorNext(It))
  {
    sStar* s = listGet(It);
    s->fX = 0.0f;
    s->fY = 0.0f;
  }
  // For each star in the simulation, we need to calculate the force acted upon it by every other star in the simulation. 
  listHead(sim->Stars, &It);
  for(int i = 0; i < Stars-1; i++)
  {
    listIteratorCopy(It, &It2);
    sStar* s1 = (sStar*)listGet(It);
    for(listIteratorNext(It2); !listIteratorEnd(It2);listIteratorNext(It2))
    {
      sStar* s2 = (sStar*)listGet(It2);
      calculateForce(s1, s2);
    }
    listIteratorNext(It);
  }
  listIteratorDestroy(&It2);
  listIteratorDestroy(&It);
}

void updateSimulation(sSimulation* sim, float timeElapsed)
{
  if(sim == NULL)
    return;
  sStar* star = NULL;
  float t2 = timeElapsed * timeElapsed;
  sListIterator* It = 0;
  for(listHead(sim->Stars, &It); !listIteratorEnd(It); listIteratorNext(It))
  {
    star = (sStar*)listGet(It);
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
  sListIterator* It = 0;
  for(listHead(sim->Stars, &It); !listIteratorEnd(It); listIteratorNext(It))
  {
    star = (sStar*)listGet(It);
    drawStar(star, Pixels);
  }
}

size_t numStars(sSimulation* sim)
{
  if(sim == NULL)
    return 0;
  return listSize(sim->Stars);
}
