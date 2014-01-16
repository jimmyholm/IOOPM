#include "CActor.h"

CActor::CActor(int x, int y, int ch, const TCODColor &color) : x(x), y(y), ch(ch), col(color)
{
    //ctor
}

CActor::~CActor()
{
    //dtor
}


void CActor::Render()
{
    TCODConsole::root->setChar(x, y, ch);
    TCODConsole::root->setCharForeground(x, y, col);
}
