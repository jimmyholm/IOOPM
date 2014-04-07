#ifndef ROGUELIKE_MAP_H
#define ROGUELIKE_MAP_H
#include "libtcod.hpp"
#include "actor.h"
#include "bsplistener.h"
struct STile
{
	bool canWalk; // Walkable tile?
  STile();
};

class CMap
{
protected:
	static const int ROOM_MAX_SIZE;
	static const int ROOM_MIN_SIZE;
	int m_height, m_width;
	STile* m_tiles;
	void SetWall(int, int);
	friend class BspListener;
	void Dig(int, int, int, int);
	void CreateRoom(bool, int, int, int, int);
public:
	CMap(int, int);
	~CMap();
	bool IsWall(int, int);
	void Render();
	int GetWidth();
	int GetHeight();
};
#endif // ROGUELIKE_MAP_H
