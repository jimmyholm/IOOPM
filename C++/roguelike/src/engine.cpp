#include "engine.h"


CEngine::CEngine()
{
}
CEngine::~CEngine()
{
	m_actors.clearAndDelete();
	delete m_map;
}

CEngine* CEngine::m_instance = NULL;

CEngine* CEngine::GetInstance()
{
	if(m_instance == NULL)
		m_instance = new CEngine();
	return m_instance;
}

void CEngine::Init()
{
	TCODConsole::initRoot(80,50,"libtcod C++ tutorial",false);
	m_player = new CActor(40, 25, '@', TCODColor::white);
	m_actors.push(m_player);
	m_map = new CMap(80, 45);
}

void CEngine::Update()
{
		TCOD_key_t Key;
		TCODSystem::checkForEvent(TCOD_EVENT_KEY_PRESS,&Key,NULL);
		int playerx = m_player->GetPosX();
		int playery = m_player->GetPosY();
		switch(Key.vk)
		{
		  case TCODK_UP:
				if(playerx > 0 && m_map->IsWall(playerx,playery-1))
					m_player->SetPos(playerx, playery-1); 
				break;
		  case TCODK_DOWN:
				if(playery < m_map->GetHeight()-1 && m_map->IsWall(playerx,playery+1))
					m_player->SetPos(playerx, playery+1); 
				break;
		  case TCODK_RIGHT:
				if(playerx < m_map->GetWidth()-1 && m_map->IsWall(playerx-1,playery))
					m_player->SetPos(playerx+1, playery); 
				break;
		  case TCODK_LEFT:
				if(playerx > 0 && m_map->IsWall(playerx+1,playery))
					m_player->SetPos(playerx-1, playery); 
				break;
		  default:
				break;
		}
}

void CEngine::Render() const
{
	TCODConsole::root->clear();
	m_map->Render();
	
	for(CActor** it = m_actors.begin();
			it != m_actors.end(); it++)
	{
		(*it)->Render();
	}
}

void CEngine::Shutdown()
{
	if(m_instance != 0)
		 delete m_instance;
	m_instance = 0;
}

CActor* CEngine::GetPlayer()
{
	return m_player;
}

void CEngine::PushActor(CActor* actor)
{
	if(actor == NULL)
		return;
	m_actors.push(actor);
}
