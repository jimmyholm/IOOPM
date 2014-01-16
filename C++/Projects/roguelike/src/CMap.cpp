#include "CMap.h"
#include "CGame.h"
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
    TCODBsp bsp(0, 0, width, height);
    bsp.splitRecursive(NULL,8,ROOM_MAX_SIZE,ROOM_MAX_SIZE,1.5f,1.5f);
    BspListener listener(*this);
    bsp.traverseInvertedLevelOrder(&listener,NULL);
}

CMap::~CMap()
{
    delete[] tiles;
}

bool CMap::IsWall(int x, int y)
{
    if(x >= width || x < 0 || y < 0 || y > height)
        return false;
    return !tiles[x+y*width].CanWalk;
}

void CMap::dig(int x1, int x2, int y1, int y2)
{
    if(x2 < x1)
    {
        int tmp = x2;
        x2 = x1;
        x1 = tmp;
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
            tiles[tilex+tiley*width].CanWalk=true;
        }
    }
}

void CMap::createRoom(bool first, int x1, int y1, int x2, int y2)
{
    dig (x1,y1,x2,y2);
    if ( first )
    {
        // put the player in the first room
        CGame::GetInstance()->player->Setx((x1+x2)/2);
        CGame::GetInstance()->player->Sety((y1+y2)/2);
    }
    else
    {
        TCODRandom *rng=TCODRandom::getInstance();
        if ( rng->getInt(0,3)==0 )
        {
            CGame::GetInstance()->actors.push(new CActor((x1+x2)/2,(y1+y2)/2,'@', TCODColor::yellow));
        }
    }
}

int CMap::Render()
{
    static const TCODColor darkWall(0,0,100);
    static const TCODColor darkGround(50,50,150);

    for(int y = 0; y < height; y++)
    {
        for(int x = 0; x < width; x++)
        {
            TCODConsole::root->setCharBackground(x, y, (IsWall(x,y)) ? darkWall : darkGround);
        }
    }
    return 0;
}
