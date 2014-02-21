#ifndef CGAME_H
#define CGAME_H
#include "libtcod.hpp"
#include "CActor.h"
#include "CMap.h"

class CGame
{
    public:
        virtual ~CGame();
        int Initialize(int width, int height, const char* title, bool fullscreen);
        int StartMainLoop();
        static int ShutDown();
        static CGame* GetInstance();
        void Update();
        const int FOVRadius;
    protected:
    private:
    friend class CMap;
        bool computeFOV;
        static CGame* Instance;
        CGame();
        CMap* map;
        TCODList<CActor*> actors;
        CActor* player;
};

#endif // CGAME_H
