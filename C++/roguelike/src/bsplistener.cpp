#include "bsplistener.h"
#include "map.h"
BspListener::BspListener(CMap& map) : m_map(map), m_roomNum(0)
{
}

bool BspListener::visitNode(TCODBsp* node, void* uData)
{
	if(!node->isLeaf())
		return false;
	int x=0, y=0, w=0, h=0;
	TCODRandom *rng=TCODRandom::getInstance();
	w=rng->getInt(CMap::ROOM_MIN_SIZE, node->w-2);
	h=rng->getInt(CMap::ROOM_MIN_SIZE, node->h-2);
	x=rng->getInt(node->x+1, node->x+node->w-w-1);
	y=rng->getInt(node->y+1, node->y+node->h-h-1);
	m_map.CreateRoom(m_roomNum == 0, x, y, x+w-1, y+h-1);
	
	if ( m_roomNum != 0 ) {
    // dig a corridor from last room
    m_map.Dig(m_lastX,m_lastY,x+w/2,m_lastY);
    m_map.Dig(x+w/2,m_lastY,x+w/2,y+h/2);
	}
	m_lastX = x+w/2;
	m_lastY = y+h/2;
	m_roomNum++;
	return true;
}
