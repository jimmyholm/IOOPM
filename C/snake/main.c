#include <SDL.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "SDLWrapper.h"
#include "LinkedList.h"
#include "game.h"
#include "global.h"

void readTitleFile(sLinkedList* list, FILE* file)
{
  char str[10];
  if(feof(file))
  {
    State = 0;
    return;
  }
  fscanf(file, "%s\n", str);
  if(strcmp(str, "#end") == 0)
  {
    State = 0;
    return;
  }
  int x = 0;
  int y = 0;
  if(str[0] != '#')
  {
    sscanf(str, "%d,%d", &x, &y);
    point* p = createPoint(x, y);
    listPushBack(list, (void*)p);
    free(p);
  }
}

void renderList(sLinkedList* list, sSdlWrapper* wrap)
{
  Uint32 bg = makeColor(255, 50, 25, 175);
  Uint32 fg = makeColor(255, 75, 50, 200);
  if(listEmpty(list))
    return;
  sListIterator* it = 0;
  listHead(list, &it);
  point* toDraw = 0;
  int x = 0;
  int y = 0;
  while(!listIteratorEnd(it))
  {
    toDraw = (point*)listGet(it);
    pointGetPos(toDraw, &x, &y);
    drawBevel(wrap, x*25, y*25, 25, 25, bg, fg);
    listIteratorNext(it);
  }
  free(it);
}

int main(int argc, char* argv[])
{
  sSdlWrapper* wrap = initializeSDLWrapper("Snake", 800, 600, 32, 1, 1);
  game* gameEngine  = initGame(wrap, 32, 24);
  int Selection = 0;
  sTextGFX* startUnsel = createText(wrap, "Start Game", 0xFFFFFFFF);
  sTextGFX* startSel   = createText(wrap, "Start Game", 0xFFFFF000);
  sTextGFX* exitUnsel  = createText(wrap, "Exit Game" , 0xFFFFFFFF);
  sTextGFX* exitSel    = createText(wrap, "Exit Game" , 0xFFFFF000);
  sLinkedList* titleList = 0;
  FILE* titleFile = fopen("snake.pic", "r");
  listInitialize(&titleList, sizeofPoint(), NULL);
  for(int x = 0; x < 32; x++)
    for(int y = 0; y < 24; y++)
      if(x == 0 || x == (31) || y == 0 || y == (23))
      {
	point* toAdd = createPoint(x,y);
	listPushFront(titleList, (void*)toAdd);
	free(toAdd);
      }
  while(isRunning(wrap))
  {
    beginFrame(wrap);
    if(State == -1)
    {
      readTitleFile(titleList, titleFile);
      renderList(titleList, wrap);
    }
    else if(State == 1)
      tick(gameEngine);
    else
    {
      if(Selection == 0)
      {
	renderText(wrap, startSel, 400, 300);
	renderText(wrap, exitUnsel, 400, 325);
      }
      else
      {
	renderText(wrap, startUnsel, 400, 300);
	renderText(wrap, exitSel, 400, 325);
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
      renderList(titleList, wrap);
    }
    if(keyPressed(wrap, SDLK_ESCAPE))
      toggleRunning(wrap);
    endFrame(wrap);
  }
  listClear(titleList);
  free(titleList);
  destroyText(startUnsel);
  destroyText(startSel);
  destroyText(exitUnsel);
  destroyText(exitSel);
  deinitializeWrapper(wrap);
  destroyGame(gameEngine);
  free(wrap);
  return 0;
}
