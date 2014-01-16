#include <iostream>
#include "CGame.h"

CGame* CGame::Instance = 0;

CGame* CGame::GetInstance()
{
    if(Instance == 0)
        Instance = new CGame();
    return Instance;
}

CGame::CGame()
{
    //ctor
}

CGame::~CGame()
{
    //dtor
}

int CGame::Initialize(int width, int height, const char* title, bool fullscreen)
{
    TCODConsole::initRoot(width, height,title,fullscreen);
    player = new CActor(width/2, height/2, (int)'@', TCODColor::white);
    actors.push(player);
    map = new CMap(80, 45);
    return 0;
}

int CGame::StartMainLoop()
{
    while(!TCODConsole::isWindowClosed())
    {
        Update();
        TCODConsole::root->clear();
        map->Render();
        for(CActor ** it = actors.begin(); it != actors.end(); it++)
        {
            (*it)->Render();
        }
        TCODConsole::flush();
    }
    return 0;
}

void CGame::Update()
{
    TCOD_key_t key;
    TCODSystem::checkForEvent(TCOD_EVENT_KEY_PRESS,&key,NULL);
    switch(key.vk) {
        case TCODK_UP :
            if ( !map->IsWall(player->Getx(),player->Gety()-1)) {
                player->Sety(player->Gety()-1);
            }
        break;
        case TCODK_DOWN :
            if ( ! map->IsWall(player->Getx(),player->Gety()+1)) {
                player->Sety(player->Gety()+1);
            }
        break;
        case TCODK_LEFT :
            if ( ! map->IsWall(player->Getx()-1,player->Gety())) {
                player->Setx(player->Getx()-1);
            }
        break;
        case TCODK_RIGHT :
            if ( ! map->IsWall(player->Getx()+1,player->Gety())) {
                player->Setx(player->Getx()+1);
            }
        break;
        default:break;
    }
}

int CGame::ShutDown()
{
    Instance->actors.clearAndDelete();
    delete Instance->map;
    delete Instance;
    return 0;
}
