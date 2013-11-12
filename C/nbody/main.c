/*
 N-Body simulation of Newtonian Gravitational interaction between stars, using approximations of Newtonian physics.

This program uses a third party, open source, multimedia library named SDL, for window management and graphics handling.

The program is divided into distinct structures for handling a simulation of multiple stars, as well as for defining the properties of these stars; for more information see respective header file.
 */
#include <stdio.h>
#include <SDL.h>
#include <stdbool.h>
#include <time.h>
#include "globals.h"
#include "star.h"
#include "simulation.h"

int main(int argc, char* argv[])
{
  // Seed our pseudo-random number generator with the current time, to avoid getting the same simulation every time.
  srand((unsigned int)time(0));
  int stars = 0;
  // Load number of stars from the command line, or set to a random number between 200 and 500.
  if(argc == 1)
    stars = RandInt(200,500);
  else
    stars = atoi(argv[1]);

  // Initialize the SDL library and create a titled window.
  SDL_Init(SDL_INIT_VIDEO);
  SDL_Window* screen = SDL_CreateWindow("N-Body Simulator",
					SDL_WINDOWPOS_UNDEFINED,
					SDL_WINDOWPOS_UNDEFINED,
					WIDTH, HEIGHT,
					SDL_WINDOW_SHOWN);
  // Error handling; make sure the window creation succeeded.
  if(!screen)
  {
    puts("Failed to create window!");
    SDL_Quit();
    return -1;
  }
  // Create a rendering context through which we may draw our graphics.
  SDL_Renderer* render = SDL_CreateRenderer(screen, -1, 
					    SDL_RENDERER_ACCELERATED);
  // Similarly, make sure the renderer gets creted properly
  if(!render)
  {
    puts("Hardware renderer not available; falling back on software.");
    render = SDL_CreateRenderer(screen, -1, SDL_RENDERER_SOFTWARE);
    if(!render)
    {
      puts("Failed to create renderer!");
      SDL_DestroyWindow(screen);
      SDL_Quit();
    }
  }
  // We'll be doing our drawing onto a texture, created here.
  SDL_Texture* Texture = SDL_CreateTexture(render, SDL_PIXELFORMAT_ARGB8888,
					   SDL_TEXTUREACCESS_STREAMING,
					   WIDTH, HEIGHT);
  // As this is a real-time simulation, use Running as a termination condition.
  bool Running = true;
  // Create space for enough pixels, representing our texture. This is what we will "draw to" before passing it along to the renderer.
  Uint32* Pixels = malloc(sizeof(Uint32)*WIDTH*HEIGHT);
  // Create an instance of the simulator and populate it with stars.
  sSimulation* sim = createSimulation(stars);
  // These values are used to count elapsed time and framerate.
  Uint32 ticks = SDL_GetTicks();
  Uint32 prevTicks = ticks;
  Uint32 fpsTicks = 0;
  Uint32 fps = 0;
  while(Running)
  {
    ticks = SDL_GetTicks();
    // Fraction of seconds to elapse since the last frame.
    float timeElapsed = (float)(ticks - prevTicks) / 1000.0f;
    // Clear our pixel-buffer.
    memset(Pixels, 0, sizeof(Uint32)*WIDTH*HEIGHT);
    // Check if the user requests the window be closed and if so, break out of the loop.
    SDL_Event e;
    while(SDL_PollEvent(&e))
    {
      if(e.type == SDL_QUIT)
	Running = false;
      continue;
    }
    // Check for star collisions
    //  checkCollisions(sim);
    // Do the necessary calculation to determine the velocity of this frame
    calculateNextFrame(sim);
    // Set new star-coordinates, based on speed and time to elapse since last frame.
    updateSimulation(sim, timeElapsed);
    // Draw the simulation to the pixel-buffer
    drawSimulation(sim, Pixels);
    // Copy the pixel buffer to video-memory, clear the window and draw the texture.
    SDL_UpdateTexture(Texture, NULL, Pixels, WIDTH*sizeof(Uint32));
    SDL_SetRenderDrawColor(render, 0, 0, 0, 255);
    SDL_RenderClear(render);
    SDL_RenderCopy(render, Texture, NULL, NULL);
    SDL_RenderPresent(render);
    // Calculate number of frames calculated and drawn per second.
    fpsTicks += ticks-prevTicks;
    if(fpsTicks < 1000)
      fps++;
    else
    {
      char str[100];
      sprintf(str, "N-Body Simulator (%d FPS) (%u stars)", fps, (unsigned int)numStars(sim));
      fps = 0;
      fpsTicks = 0;
      SDL_SetWindowTitle(screen, str);
    }
    prevTicks = ticks;
  }
  // Perform clean up before exiting program.
  destroySimulation(sim);
  free(Pixels);
  SDL_DestroyTexture(Texture);
  SDL_DestroyRenderer(render);
  SDL_DestroyWindow(screen);
  SDL_Quit();
  return 0;
}
