#include <SDL.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "SDLWrapper.h"
//#include "LinkedList.h"
#include "game.h"
#include <unistd.h>

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
    
    if(keyUp(wrap, SDLK_ESCAPE))
      toggleRunning(wrap);
    endFrame(wrap);
    sleep(1);
  }
  deinitializeWrapper(wrap);*/
  return 0;
}
