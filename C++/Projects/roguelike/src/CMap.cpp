#include "CMap.h"
#include "CGame.h"
#include "CActor.h"
#include <iostream>
BspListener::BspListener(CMap& Map) : map(Map), roomNum(0), lastx(0), lasty(0)
{

}

bool BspListener::visitNode(TCODBsp* node, void* data)
{
    if(node->isLeaf())
    {
        int x,y,w,h;
        // dig a room
        TCODRandom *rng=TCODRandom::getInstance();
        w=rng->getInt(ROOM_MIN_SIZE, node->w-2);
        h=rng->getInt(ROOM_MIN_SIZE, node->h-2);
        x=rng->getInt(node->x+1, node->x+node->w-w-1);
        y=rng->getInt(node->y+1, node->y+node->h-h-1);
        if(x < 0 || x >= map.width || x+w >= map.width || y < 0 || y >= map.height || y+h >= map.height)
            return false;
        map.createRoom(roomNum == 0, x, y, x+w-1, y+h-1);
        if ( roomNum != 0 )
        {
            // dig a corridor from last room
            map.dig(lastx,lasty,x+w/2,lasty);
            map.dig(x+w/2,lasty,x+w/2,y+h/2);
        }
        lastx=x+w/2;
        lasty=y+h/2;
        roomNum++;
    }
    return true;
}

CMap::CMap(int Width, int Height) : width(Width), height(Height)
{
    tiles = new STile[width*height];
    map = new TCODMap(width, height);
    TCODBsp* bsp = new TCODBsp(0, 0, width, height);
    bsp->splitRecursive(NULL,4,ROOM_MAX_SIZE,ROOM_MAX_SIZE,1.5f,1.5f);
    BspListener listener(*this);
    bsp->traverseInvertedLevelOrder(&listener, NULL);
    delete bsp;
}

CMap::~CMap()
{
    delete[] tiles;
    delete map;
}

bool CMap::IsWall(int x, int y)
{
    if(x >= width || x < 0 || y < 0 || y > height)
        return false;
    return !map->isWalkable(x, y);
}

bool CMap::IsInFOV(int x, int y) const
{
    if(map->isInFov(x,y))
    {
        tiles[x+y*width].Explored = true;
        return true;
    }
    return false;
}

void CMap::ComputeFOV()
{
    map->computeFov(CGame::GetInstance()->player->Getx(), CGame::GetInstance()->player->Gety(), CGame::GetInstance()->FOVRadius);
}

void CMap::dig(int x1, int y1, int x2, int y2)
{
    if ( x2 < x1 )
    {
        int tmp=x2;
        x2=x1;
        x1=tmp;
    }
    if ( y2 < y1 )
    {
        int tmp=y2;
        y2=y1;
        y1=tmp;
    }
    for (int tilex=x1; tilex <= x2; tilex++)
    {
        for (int tiley=y1; tiley <= y2; tiley++)
        {
            map->setProperties(tilex, tiley, true, true);
        }
    }
}

void CMap::createRoom(bool first, int x1, int y1, int x2, int y2)
{
    dig(x1,y1,x2,y2);
    if ( first )
    {
        // put the player in the first room
        CGame::GetInstance()->player->Setx((x1+x2)/2);
        CGame::GetInstance()->player->Sety((y1+y2)/2);
    }
}

bool CMap::IsExplored(int x, int y) const
{
    return tiles[x+y*width].Explored;
}

int CMap::Render()
{
    static const TCODColor darkWall(0,0,100);
    static const TCODColor darkGround(50,50,150);
    static const TCODColor lightWall(130,110,50);
    static const TCODColor lightGround(200,180,50);
    for(int y = 0; y < height; y++)
    {
        for(int x = 0; x < width; x++)
        {
            if(IsInFOV(x, y))
            {
                TCODConsole::root->setCharBackground(x, y, (IsWall(x,y)) ? lightWall : lightGround);
            }
            else if(IsExplored(x, y))
            {
                TCODConsole::root->setCharBackground(x, y, (IsWall(x,y)) ? darkWall : darkGround);
            }
        }
    }
    return 0;
}

bool CMap::CanWalk(int x, int y)
{
    if(IsWall(x, y))
        return false;
    for(CActor** it = CGame::GetInstance()->actors.begin(); it != CGame::GetInstance()->actors.end();it++)
    {
        CActor* actor = *it;
        if(actor->Getx() == x && actor->Gety() == y)
            return false;
    }
    return true;
}

void CMap::AddMonster(int x, int y)
{
    TCODRandom* rnd = TCODRandom::getInstance();
    if(rnd->getInt(0, 100) < 80)
    {
        CGame::GetInstance()->actors.push(new CActor(x,y,"orc",'o',TCODColor::desaturatedGreen));
    }
    else
    {
        CGame::GetInstance()->actors.push(new CActor(x,y,"troll",'T',TCODColor::darkerGreen));
    }
}
