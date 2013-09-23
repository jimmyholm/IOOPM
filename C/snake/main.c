#include <SDL.h>
#include "SDLWrapper.h"
#include "game.h"

int main(int argc, char* argv[])
{
  sSdlWrapper* wrap = initializeSDLWrapper("Test", 800, 600, 32, 1, 1);
  game* gameEngine = initGame(wrap);
  while(isRunning(wrap))
  {
    beginFrame(wrap);
    //drawBevel(wrap, 396,296, 10, 10, makeColor(255, 0, 200,200), makeColor(255,200,200,200));

    tick(gameEngine);
    repaint(gameEngine);
    sleep(1000);
    
    if(keyUp(wrap, SDLK_ESCAPE))
      toggleRunning(wrap);
    endFrame(wrap);
  }
  deinitializeWrapper(wrap);
  return 0;
}
