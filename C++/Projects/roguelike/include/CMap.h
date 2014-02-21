#ifndef CMAP_H
#define CMAP_H
#include "libtcod.hpp"

static const int ROOM_MAX_SIZE = 12;
static const int ROOM_MIN_SIZE = 6;
static const int MAX_ROOM_MONSTERS = 3;
class CMap;
class BspListener : public ITCODBspCallback
{
public:
    BspListener(CMap& map);
    bool visitNode(TCODBsp* node, void* data);
private :
    CMap &map; // a map to dig
    int roomNum; // room number
    int lastx,lasty; // center of the last room
};

struct STile
{
    bool Explored;
    STile() : Explored(false) {}
};

class CMap
{
    public:
        CMap(int Width, int Height);
        virtual ~CMap();
        int  Getwidth() { return width; }
        void Setwidth(int val) { width = val; }
        int  Getheight() { return height; }
        void Setheight(int val) { height = val; }
        bool IsInFOV(int x, int y) const;
        bool IsExplored(int x, int y) const;
        bool IsWall(int x, int y);
        void ComputeFOV();
        int Render();
        bool CanWalk(int x, int y);
        void AddMonster(int x, int y);
    protected:
        friend class BspListener;
        STile* tiles;
        int width;
        int height;
        TCODMap* map;
        void dig(int x1, int y1, int x2, int y2);
        void createRoom(bool first, int x1, int y1, int x2, int y2);
};

#endif // CMAP_H
