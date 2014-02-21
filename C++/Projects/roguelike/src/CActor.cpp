#include "CActor.h"

CActor::CActor(int x, int y, const char* name, int ch, const TCODColor &color) : x(x), y(y), name(name), ch(ch), col(color)
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

void CActor::Update()
{

}

bool CActor::MoveOrAttack(int x, int y)
{
    if(CGame::GetInstance()->map->IsWall(x,y)) return false;
    for(CActor** it = CGame::GetInstance()->actors.begin(); it != CGame::GetInstance()->actors.end(); it++)
    {
        CActor* actor = *it;
        if(actor->Getx() == x && actor->Gety() == y)
        {
            if ( actor->x == x && actor->y ==y ) {
            printf("The %s laughs at your puny efforts to attack him!\n", actor->name);
            return false;
        }
    }
    this->x = x;
    this->y = y;
    return true;
}
