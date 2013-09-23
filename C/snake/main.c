#include <SDL.h>
#include <stdio.h>
#include <stdlib.h>
#include "SDLWrapper.h"
#include "LinkedList.h"
#include "game.h"

int main(int argc, char* argv[])
{
  
  sLinkedList* list = NULL;
  listInitialize(&list, sizeof(int));
  for(int i = 0; i < 10; i++)
  {
    listPushBack(list,(void*)&i);
  }
  
  int i = 0;
  sListIterator* It = 0;
  for(It = listHead(list); !listIteratorEnd(It); listIteratorNext(It))
  {
    i = *(int*)listGet(It);
    printf("%d\n", i);
  }
  free(It);
  listClear(list);
  free(list);
  

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
