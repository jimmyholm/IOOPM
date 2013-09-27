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
  sSdlWrapper* wrap = initializeSDLWrapper("Test", 800, 600, 32, 1, 1);
  game* gameEngine = initGame(wrap, 20, 20);
  //sTextGFX* text = createScore(wrap, 10, 6, makeColor(255, 255, 255, 255));
  while(isRunning(wrap))
  {
    beginFrame(wrap);

    tick(gameEngine);
    //renderText(wrap, text, 400, 300);
    if(keyUp(wrap, SDLK_ESCAPE))
      toggleRunning(wrap);
    endFrame(wrap);
  }
  //destroyText(text);
  deinitializeWrapper(wrap);
  return 0;
}
