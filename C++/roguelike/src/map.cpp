#include "map.h"
#include "engine.h"

const int CMap::ROOM_MAX_SIZE = 12;
const int CMap::ROOM_MIN_SIZE = 6;

STile::STile() : canWalk(false) {}


CMap::CMap(int width, int height) : m_height(height), m_width(width) 
{
	m_tiles = new STile[m_width*m_height];
	TCODBsp bsp(0,0,width,height);
	bsp.splitRecursive(NULL,8,CMap::ROOM_MAX_SIZE,CMap::ROOM_MAX_SIZE,1.5f,1.5f);
	BspListener listener(*this);
	bsp.traverseInvertedLevelOrder(&listener,NULL);
}

CMap::~CMap()
{
	delete[] m_tiles;
}

bool CMap::IsWall(int x, int y)
{
	if(x < 0 || x >= m_width || y < 0 || y >= m_height)
		return true;
	return m_tiles[x+y*m_width].canWalk;
}

void CMap::SetWall(int x, int y)
{
	if(x < 0 || x >= m_width || y < 0 || y >= m_height)
		return;
	m_tiles[x+y*m_width].canWalk = false;
}

void CMap::Render()
{
	static const TCODColor darkWall(200,200,100);
	static const TCODColor darkGround(0,0, 0);
	for(int y = 0; y < m_height; y++)
	{
		for(int x = 0; x < m_width; x++)
		{
			TCODConsole::root->setCharBackground(x, y, 
																					 IsWall(x, y) ? darkWall : darkGround);
		}
	}
}

void CMap::Dig(int x1, int y1, int x2, int y2)
{
	if(x2 < x1)
	{
		int tmp = x2;
		x2 = x1;
		x1 = tmp;
	}
	if(y2 < y1)
	{
		int tmp = y2;
		y2 = y1;
		y1 = tmp;
	}
	while(x1 < 0)
		x1++;
	while(x1 >= m_width)
		x1--;
	while(x2 < 0)
		x2++;
	while(x2 >= m_width)
		x2--;
	while(y1 < 0)
		y1++;
	while(y1 >= m_height)
		y1--;
	while(y2 < 0)
		y2++;
	while(y2 >= m_height)
		y2--;
		
	for(int x = x1; x <= x2; x++)
	{
		for(int y = y1; y <= y2; y++)
		{
			m_tiles[x+y*m_width].canWalk = true;
		}
	}
}

void CMap::CreateRoom(bool first, int x1, int y1, int x2, int y2) {
	Dig (x1,y1,x2,y2);
	if ( first ) 
	{
		// put the player in the first room
		CEngine::GetInstance()->GetPlayer()->SetPos((x1+x2)/2,(y1+y2)/2);
	} 
	else 
	{
		TCODRandom *rng=TCODRandom::getInstance();
		if ( rng->getInt(0,3)==0 ) 
		{
		  CEngine::GetInstance()->PushActor(new CActor((x1+x2)/2,(y1+y2)/2,'@',
																	 TCODColor::yellow));
		}
	}
}

int CMap::GetWidth()
{
	return m_width;
}

int CMap::GetHeight()
{
	return m_height;
}
