#ifndef CACTOR_H
#define CACTOR_H
#include "libtcod.hpp"

class CActor
{
    public:
        CActor(int x, int y, const char* name, int ch, const TCODColor &color);
        virtual ~CActor();
        int Getx() { return x; }
        void Setx(int val) { x = val; }
        int Gety() { return y; }
        void Sety(int val) { y = val; }
        int Getch() { return ch; }
        void Setch(int val) { ch = val; }
        TCODColor Getcol() { return col; }
        void Setcol(TCODColor val) { col = val; }
        void Render();
        const char* name;
        void Update();
        bool MoveOrAttack(int x, int y);
    protected:
    private:
        int x;
        int y;
        int ch;
        TCODColor col;
};

#endif // CACTOR_H
