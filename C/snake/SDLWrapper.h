#ifndef SDLWRAPPER_H
#define SDLWRAPPER_H
#include <SDL.h>
#include <stdio.h>

// Wrapper around SDL data and functions necessary for the operation an SDL accelerated window.
typedef struct sSdlWrapper sSdlWrapper;

// Create a wrapper object for the SDL system and create a window with the requested attributes. Returns NULL on error.
sSdlWrapper* initializeSDLWrapper(const char* Title, int Width, int Height, int Depth, int Accelerated, int SoftwareFallback);

// Determine if the SDL window is still running.
int isRunning(sSdlWrapper* Wrapper);

// Perform a clean of the window drawing-surface and prepare for a new frame.
// All drawing should take place between beginFrame and endFrame!
void beginFrame(sSdlWrapper* Wrapper);

// Flip rendering buffers and present the newly drawn frame to the screen.
void endFrame(sSdlWrapper* Wrapper);

// Deinitialize the wrapper when finished; never use a wrapper pointer that has been deinitialized!
void deinitializeWrapper(sSdlWrapper* Wrapper);

// Pack ARGB values into a color integer.
Uint32 makeColor(Uint8 a, Uint8 r, Uint8 g, Uint8 b);

// Draw a rectangle on the screen
void drawRect(sSdlWrapper* Wrapper, int X, int Y, int W, int H, Uint32 Color);

// Draw a bevelled rectangle to the screen
void drawBevel(sSdlWrapper* Wrapper, int X, int Y, int W, int H, Uint32 Color, Uint32 BorderColor);

// Return 1 if the given key was pressed, but is not being held, 0 otherwise.
int keyPressed(sSdlWrapper* Wrapper, SDL_Keycode Key);

// Return 1 if the given key is held down, 0 otherwise.
int keyDown(sSdlWrapper* Wrapper, SDL_Keycode Key);

// Return 1 if the given key was just released, 0 otherwise.
int keyUp(sSdlWrapper* Wrappr, SDL_Keycode Key);

// Access the dimensions of our main window
int getWindowDims(int* X, int* Y);

// Tell the window to shut down.
void toggleRunning(sSdlWrapper* Wrapper);

// Get the elapsed number of milliseconds between frames.
Uint32 elapsedTime(sSdlWrapper* Wrapper);

#endif
