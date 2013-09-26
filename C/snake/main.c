#include <SDL.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "SDLWrapper.h"
#include "LinkedList.h"
#include "game.h"
#include <unistd.h>

int main(int argc, char* argv[])
{
  Uint32 framerate = 40;
  Uint32 elapsed = 0;
  sSdlWrapper* wrap = initializeSDLWrapper("Test", 800, 600, 32, 1, 1);
  game* gameEngine = initGame(wrap, 20, 20);
  while(isRunning(wrap))
  {
    beginFrame(wrap);

    tick(gameEngine);
    
    if(keyUp(wrap, SDLK_ESCAPE))
      toggleRunning(wrap);
    endFrame(wrap);
    elapsed = elapsedTime(wrap);
    /* if(elapsed < framerate)
    {
      SDL_Delay(framerate - elapsed);
      }*/
  }
  deinitializeWrapper(wrap);
  return 0;
}
