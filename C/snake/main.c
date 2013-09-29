#include <SDL.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "SDLWrapper.h"
#include "LinkedList.h"
#include "game.h"
#include "global.h"

int main(int argc, char* argv[])
{
  sSdlWrapper* wrap = initializeSDLWrapper("Snake", 800, 600, 32, 1, 1);
  game* gameEngine  = initGame(wrap, 32, 24);
  int Selection = 0;
  sTextGFX* startUnsel = createText(wrap, "Start Game", 0xFFFFFFFF);
  sTextGFX* startSel   = createText(wrap, "Start Game", 0xFFFFF000);
  sTextGFX* exitUnsel  = createText(wrap, "Exit Game" , 0xFFFFFFFF);
  sTextGFX* exitSel    = createText(wrap, "Exit Game" , 0xFFFFF000);
  while(isRunning(wrap))
  {
    beginFrame(wrap);
    if(State == 1)
      tick(gameEngine);
    else
    {
      if(Selection == 0)
      {
	renderText(wrap, startSel, 400, 250);
	renderText(wrap, exitUnsel, 400, 300);
      }
      else
      {
	renderText(wrap, startUnsel, 400, 250);
	renderText(wrap, exitSel, 400, 300);
      }
      if(keyDown(wrap, SDLK_DOWN))
	Selection = 1;
      if(keyDown(wrap,SDLK_UP))
	Selection = 0;
      if(keyDown(wrap, SDLK_RETURN))
      {
	if(Selection == 0)
	{
	  State = 1;
	  setupGame(gameEngine);
	}
	else
	  toggleRunning(wrap);
      }
    }
    if(keyPressed(wrap, SDLK_ESCAPE))
      toggleRunning(wrap);
    endFrame(wrap);
  }
  destroyText(startUnsel);
  destroyText(startSel);
  destroyText(exitUnsel);
  destroyText(exitSel);
  deinitializeWrapper(wrap);
  free(gameEngine);
  free(wrap);
  return 0;
}
