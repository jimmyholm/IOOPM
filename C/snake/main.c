#include <SDL.h>
#include "SDLWrapper.h"

int main(int argc, char* argv[])
{
  sSdlWrapper* wrap = initializeSDLWrapper("Test", 800, 600, 32, 1, 1);
  while(isRunning(wrap))
  {
    beginFrame(wrap);
    drawBevel(wrap, 396,296, 8, 8, makeColor(255, 0, 200,200), makeColor(255,200,200,200));
    if(keyUp(wrap, SDLK_ESCAPE))
      toggleRunning(wrap);
    endFrame(wrap);
  }
  deinitializeWrapper(wrap);
  return 0;
}
