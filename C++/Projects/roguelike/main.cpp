
#include "libtcod.hpp"
#include "CGame.h"
CGame* Instance = CGame::GetInstance();
int main()
{
    Instance->Initialize(80, 50, "Test!", false);
    Instance->StartMainLoop();
    CGame::ShutDown();

    return 0;
}
