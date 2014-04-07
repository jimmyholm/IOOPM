#ifndef ROGUELIKE_ACTOR_H
#define ROGUELIKE_ACTOR_H
#include "libtcod.hpp"
class CActor
{
private:
	int m_x, m_y;
	short m_ch;
	TCODColor m_col;
public:
	CActor(int, int, short, TCODColor);
	void      SetPos(int, int);
	void      SetChar(short);
	void      SetColor(TCODColor);
	int       GetPosX();
	int       GetPosY();
	short     GetChar();
	TCODColor GetColor();
	void      Render() const;
};

#endif // ROGUELIKE_ACTOR_H
