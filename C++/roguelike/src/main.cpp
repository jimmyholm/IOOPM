#include "libtcod.hpp"
#include "engine.h"
int main() 
{
	CEngine::GetInstance()->Init();
	while ( !TCODConsole::isWindowClosed() ) 
	{
		CEngine::GetInstance()->Update();
		CEngine::GetInstance()->Render();
		TCODConsole::root->flush();
	}
	CEngine::GetInstance()->Shutdown();
	return 0;
}
