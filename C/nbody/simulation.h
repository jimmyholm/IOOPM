#ifndef NBODY_SIMULATION_H
#define NBODY_SIMULATION_H
#include "globals.h"
#include "star.h"
// The definition of the simulation structure is never important outside the interface and as such is only forward declared in this file.
typedef struct sSimulation sSimulation;

// Create a simulation with stars number of randomized stars.
sSimulation* createSimulation(unsigned int stars);
// Free up the memory used by every star as well as the memory used by the simulation itself.
void destroySimulation(sSimulation* sim);
// Calculate forces, acceleration and velocities for the next frame of simulation.
void calculateNextFrame(sSimulation* sim);
// Update the positional values of all stars depending on velocity and elapsed time
void updateSimulation(sSimulation* sim, float timeElapsed);
// Draw the simulation to the window.
void drawSimulation(sSimulation* sim, Uint32* Pixels);

#endif
