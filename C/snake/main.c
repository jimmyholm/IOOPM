#include <SDL.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "SDLWrapper.h"
#include "LinkedList.h"
#include "game.h"

int main(int argc, char* argv[])
{
  
  sLinkedList* list = NULL;
  listInitialize(&list, sizeof(int), NULL);
  for(int i = 0; i < 10; i++)
  {
    /*if(i == 2)
      continue;
    if(i == 3)
      listPushBack(list,(void*)&i);*/
    listPushBack(list,(void*)&i);
  }
  
  int i = 0;
  sListIterator* It = 0;
  listHead(list, &It);
  listIteratorNext(It);
  listIteratorNext(It);
  int in = 2;
  listInsert(It, (void*)&in);
  while(in != 3)
  {
    listIteratorNext(It);
    in = *(int*)listGet(It);
  }
  printf("Before erase, in = %d\t", in);
  listErase(It);
  in = *(int*)listGet(It);
  printf("After erase, in = %d\n", in);
  for(listHead(list, &It); !listIteratorEnd(It); )
  {
    i = *(int*)listGet(It);
    printf("%d\n", i);
    listErase(It);
  }
  free(It);
  free(list);
  /*

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
  deinitializeWrapper(wrap);*/
  return 0;
}
