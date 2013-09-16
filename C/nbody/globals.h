#ifndef NBODY_GLOBALS_H
#define NBODY_GLOBALS_H

//  Width and height constants to define the size of the render-window
#define WIDTH 800
#define HEIGHT 600

// In order to get workable values for G and our masses, we've chosen to scale
// the numbers to make them sensible!
// Interpretation of the Gravitational constant (6.673 * 10 ^ -11)
#define G_CONST 66.73f
// Maximum mass of a star 2 * the scaled mass of our sun
#define MAXMASS 39780000.2f

// Functions for creating pseudo-randomized integers and floating point values.
int RandInt(int min, int max);
float RandFloat(float min, float max);
// Draw a circle of given radius and color. Super slow.
void drawCircle(int x, int y, unsigned int color, int radius, unsigned int* Pixels);

#endif
