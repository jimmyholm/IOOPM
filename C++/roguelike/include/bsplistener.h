#ifndef ROGUELIKE_BSPLISTENER_H
#define ROGUELIKE_BSPLISTENER_H
#include "libtcod.hpp"
class CMap;
class BspListener : public ITCODBspCallback
{
private: 
	CMap& m_map; // Map to work with
	int m_roomNum; // Room number
	int m_lastX, m_lastY; // Last room's center coordinates.
public:
	BspListener(CMap&);
	bool visitNode(TCODBsp*, void*);
};
#endif
