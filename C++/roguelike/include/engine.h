#ifndef ROGUELIKE_ENGINE_H
#define ROGUELIKE_ENGINE_H
#include "libtcod.hpp"
#include "map.h"
#include "actor.h"

class CEngine
{
protected:
	TCODList<CActor*> m_actors;
	CActor* m_player;
	CMap*   m_map;
	CEngine();
	static CEngine* m_instance;
public:
	~CEngine();
	void Init();
	void Update();
	void Render() const;
	static CEngine* GetInstance();
	static void Shutdown();
	CActor* GetPlayer();
	void PushActor(CActor*);
};

#endif // ROGUELIKE_ENGINE_H
