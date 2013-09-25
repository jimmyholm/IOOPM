#include "SDLWrapper.h"
#define KEYDELAY 100
static int SDLWrapperInitialized = 0;
typedef struct sSdlWrapper
{
  int Width;  // | 
  int Height; // |-- Dimensions and colour depth of the SDL window
  int Depth;  // |
  SDL_Window* Window; // Pointer to our main window
  SDL_Renderer* Renderer; // Pointer to our main window renderer
  Uint32* Pixels; // The pixels that make up the system-memory side of the texture
  SDL_Texture* Texture; // The texture, 
  int Running; // Boolean value; 1 for true, 0 for false.
  Uint8 PrevKeys[101]; // Keys pressed down during the last frame
  const Uint8* Keys; // Keys pressed down this frame.
  Uint32 LastTick; // The last recorded SDL Tick
  Uint32 ElapsedTime; // Elapsed SLD ticks between frames.
  int KeyDelay; // Delay between updating keys.
} sSdlWrapper;

sSdlWrapper* initializeSDLWrapper(const char* Title, int Width, int Height, int Depth, int Accelerated, int SoftwareFallback)
{
  if(SDLWrapperInitialized)
  {
    puts("SDL Wrapper already initialized!");
    return NULL;
  }
  sSdlWrapper* ret = NULL;
  if(SDL_Init(SDL_INIT_VIDEO))
  {
    printf("Unable to initialize SDL: %s\n", SDL_GetError());
    return NULL;
  }
  ret = malloc(sizeof(sSdlWrapper));
  ret->Width = Width;
  ret->Height = Height;
  ret->Window = SDL_CreateWindow(Title, SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, Width, Height, SDL_WINDOW_SHOWN);
  if(!ret->Window)
  {
    printf("Failed to create window!\n%s\n", SDL_GetError());
    SDL_Quit();
    free(ret);
    return NULL;
  }
  if(Accelerated == 1)
  {
    ret->Renderer = SDL_CreateRenderer(ret->Window, -1, SDL_RENDERER_ACCELERATED);
    if(!ret->Renderer)
    {
      if(SoftwareFallback)
      {
	puts("Failed to create accelerated renderer - falling back on software.");
	ret->Renderer = SDL_CreateRenderer(ret->Window, -1, SDL_RENDERER_SOFTWARE);
	if(!ret->Renderer)
	{
	  puts("Failed to create software renderer; shutting down!");
	  SDL_DestroyWindow(ret->Window);
	  SDL_Quit();
	  free(ret);
	  return NULL;
	}
      }
      else
      {
	puts("Failed to create accelerated renderer and software fallback renderer is not allowed. Shutting down.");
	SDL_DestroyWindow(ret->Window);
	SDL_Quit();
	free(ret);
	return NULL;
      }
    }
  }
  else
  {
    ret->Renderer = SDL_CreateRenderer(ret->Window, -1, SDL_RENDERER_SOFTWARE);
    if(!ret->Renderer)
    {
      puts("Failed to create software renderer; shutting down!");
      SDL_DestroyWindow(ret->Window);
      SDL_Quit();
      free(ret);
      return NULL;
    }
  }
  ret->Texture = SDL_CreateTexture(ret->Renderer, SDL_PIXELFORMAT_ARGB8888,
				   SDL_TEXTUREACCESS_STREAMING,
				   ret->Width, ret->Height);
  ret->Pixels = malloc(sizeof(Uint32)*ret->Width*ret->Height);
  memset(ret->Pixels, 0, sizeof(Uint32)*ret->Width*ret->Height);
  memset(ret->PrevKeys, 0, sizeof(Uint8)*101);
  //memset(ret->Keys, 0, sizeof(Uint8)*101);
  ret->Keys = SDL_GetKeyboardState(NULL);
  ret->Running = 1;
  SDLWrapperInitialized = 1;
  return ret;
}

int isRunning(sSdlWrapper* Wrapper)
{
  if(!SDLWrapperInitialized)
    return 0;
  if(Wrapper == NULL)
    return 0;
  return Wrapper->Running;
}

void beginFrame(sSdlWrapper* Wrapper)
{
  if(!SDLWrapperInitialized)
    return;
  if(Wrapper == NULL)
    return;
  Uint32 Tick = SDL_GetTicks();
  Uint32 Elapsed = Tick - Wrapper->LastTick;
  Wrapper->ElapsedTime = Elapsed;
  SDL_Event event;
  while(SDL_PollEvent(&event))
  {
    if(event.type == SDL_QUIT)
    {
      Wrapper->Running = 0;
      return;
    }
  }
  Wrapper->LastTick = Tick;
  Wrapper->KeyDelay -= Elapsed;
  if(Wrapper->KeyDelay <= 0)
  {
    memset(Wrapper->Pixels, 0, sizeof(Uint32)*Wrapper->Width*Wrapper->Height);
    memcpy(Wrapper->PrevKeys, Wrapper->Keys, sizeof(Uint8)*101);
    Wrapper->Keys = SDL_GetKeyboardState(NULL);
    Wrapper->KeyDelay = KEYDELAY + Wrapper->KeyDelay; // Wrapper->Key delay will be either 0 or negative; adding it to the macro KEYDELAY will keep keyboard checking at consistently once every 1/10 seconds.
  }
}

void endFrame(sSdlWrapper* Wrapper)
{
  if(!SDLWrapperInitialized)
    return;
  if(Wrapper == NULL)
    return;
  SDL_UpdateTexture(Wrapper->Texture, NULL, Wrapper->Pixels, Wrapper->Width * sizeof(Uint32));
  SDL_SetRenderDrawColor(Wrapper->Renderer, 0, 0, 0, 255);
  SDL_RenderClear(Wrapper->Renderer);
  SDL_RenderCopy(Wrapper->Renderer, Wrapper->Texture, NULL, NULL);
  SDL_RenderPresent(Wrapper->Renderer);
  return;
}

void deinitializeWrapper(sSdlWrapper* Wrapper)
{
  if(!SDLWrapperInitialized)
    return;
  if(Wrapper == NULL)
    return;
  free(Wrapper->Pixels);
  SDL_DestroyTexture(Wrapper->Texture);
  SDL_DestroyRenderer(Wrapper->Renderer);
  SDL_DestroyWindow(Wrapper->Window);
  SDL_Quit();
  SDLWrapperInitialized = 0;
}

Uint32 makeColor(Uint8 a, Uint8 r, Uint8 g, Uint8 b)
{
  Uint32 Color = (a << 24) +  (r << 16) + (g << 8) + b;
  return Color;
}

void drawRect(sSdlWrapper* Wrapper, int X, int Y, int W, int H, Uint32 Color)
{
  if(!SDLWrapperInitialized)
    return;
  if(Wrapper == NULL)
    return;
  if(X >= Wrapper->Width)
    return;
  if(Y >= Wrapper->Height)
    return;
  while(X < 0)
  {
    X++;
    W--;
  }
  while(X+W >= Wrapper->Width)
  {
    W--;
  }
  while(Y < 0)
  {
    Y++;
    H--;
  }
  while(Y+H >= Wrapper->Height)
  {
    H--;
  }
  int index = 0;
  for(int x = X; x < X+W; x++)
  {
    for(int y = Y; y < Y+W; y++)
    {
      index = x + y*Wrapper->Width;
      Wrapper->Pixels[index] = Color;
    }
  }
}

void drawBevel(sSdlWrapper* Wrapper, int X, int Y, int W, int H, Uint32 Color, Uint32 BorderColor)
{
  if(!SDLWrapperInitialized)
    return;
  if(Wrapper == NULL)
    return;
  if(X >= Wrapper->Width)
    return;
  if(Y >= Wrapper->Height)
    return;
  while(X < 0)
  {
    X++;
    W--;
  }
  while(X+W >= Wrapper->Width)
  {
    W--;
  }
  while(Y < 0)
  {
    Y++;
    H--;
  }
  while(Y+H >= Wrapper->Height)
  {
    H--;
  }
  int VScale = W/5;
  int HScale = H/5;
  drawRect(Wrapper, X, Y, W, H, Color);
  drawRect(Wrapper, X+VScale, Y+HScale, W-2*HScale, H-2*HScale, BorderColor);
  drawRect(Wrapper, X+2*VScale, Y+2*HScale, W-4*HScale, H-2*HScale, Color);
}

int keyPressed(sSdlWrapper* Wrapper, SDL_Keycode Key)
{
  if(!SDLWrapperInitialized)
    return 0;
  if(Wrapper == NULL)
    return 0;
  SDL_Scancode code = SDL_GetScancodeFromKey(Key);
  return (Wrapper->Keys[code] == 1 && Wrapper->PrevKeys[code] == 0) ? 1 : 0;
}

int keyDown(sSdlWrapper* Wrapper, SDL_Keycode Key)
{
  if(!SDLWrapperInitialized)
    return 0;
  if(Wrapper == NULL)
    return 0;
  SDL_Scancode code = SDL_GetScancodeFromKey(Key);
  return (Wrapper->Keys[code] == 1 && Wrapper->PrevKeys[code] == 1) ? 1 : 0;
}

int keyUp(sSdlWrapper* Wrapper, SDL_Keycode Key)
{
  if(!SDLWrapperInitialized)
    return 0;
  if(Wrapper == NULL)
    return 0;
  SDL_Scancode code = SDL_GetScancodeFromKey(Key);
  return (Wrapper->Keys[code] == 0 && Wrapper->PrevKeys[code] == 1) ? 1 : 0;
}

void toggleRunning(sSdlWrapper* Wrapper)
{
  if(!SDLWrapperInitialized)
    return;
  if(Wrapper == NULL)
    return;
  Wrapper->Running = (Wrapper->Running) ? 0 : 1;
}

Uint32 elapsedTime(sSdlWrapper* Wrapper)
{
  if(Wrapper == NULL)
    return 0;
  return Wrapper->ElapsedTime;
}
