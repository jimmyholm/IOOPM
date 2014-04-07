#include "actor.h"

CActor::CActor(int x, int y, short ch, TCODColor col) : m_x(x), m_y(y), m_ch(ch),
																												m_col(col)
{}

void CActor::SetPos(int x, int y)
{
	m_x = x;
	m_y = y;
}

void CActor::SetChar(short ch)
{
	m_ch = ch;
}

void CActor::SetColor(TCODColor col)
{
	m_col = col;
}

int CActor::GetPosX()
{
	return m_x;
}

int CActor::GetPosY()
{
	return m_y;
}

short CActor::GetChar()
{
	return m_ch;
}

TCODColor CActor::GetColor()
{
	return m_col;
}

void CActor::Render() const
{
	TCODConsole::root->setChar(m_x, m_y, m_ch);
	TCODConsole::root->setCharForeground(m_x, m_y, m_col);
}
