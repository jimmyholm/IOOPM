#ifndef NBODY_GLOBALS_H
#define NBODY_GLOBALS_H

//  Width and height constants to define the size of the render-window
#define WIDTH 800
#define HEIGHT 600

// Interpretation of the Gravitational constant (6.673 * 10 ^ -11)
#define G_CONST 0.06673f
// Maximum mass of a star
#define MAXMASS 2500.0f

// Functions for creating pseudo-randomized integers and floating point values.
int RandInt(int min, int max);
float RandFloat(float min, float max);

#endif
