#include "LinkedList.h"

typedef struct sListNode
{
  void* Data;
  struct sListNode* Next;
} sListNode;

typedef struct sLinkedList
{
  size_t Size;
  sListNode* Head;
  sListNode* Tail;
  size_t ElementSize;
  void (*EraseFun)(void*);
} sLinkedList;

typedef struct sListIterator
{
  sListNode** Prev;
  sListNode** Current;
  sListNode** Next;
  sLinkedList* List;
} sListIterator;

void listInitialize(sLinkedList** List, size_t ElementSize,void(*EraseFun)(void*))
{
  if(*List != NULL)
    return;
  *List = malloc(sizeof(sLinkedList));
  (*List)->Size = 0;
  (*List)->Head = NULL;
  (*List)->Tail = NULL;
  (*List)->ElementSize = ElementSize;
  (*List)->EraseFun = EraseFun;
}

void listPushBack(sLinkedList* List, void* Data)
{
  if(List == NULL)
    return;
  sListNode* Node = malloc(sizeof(sListNode));
  Node->Data = malloc(List->ElementSize);
  memcpy(Node->Data, Data, List->ElementSize);
  Node->Next = NULL;
  if(List->Head == NULL)
    List->Head = List->Tail = Node;
  else
  {
    List->Tail->Next = Node;
    List->Tail = Node;
  }
  List->Size++;
}

void listPushFront(sLinkedList* List, void* Data)
{
  if(List == NULL)
    return;
  sListNode* Node = malloc(sizeof(sListNode));
  memcpy(Node->Data, Data, List->ElementSize);
  Node->Next = List->Head;
  if(List->Head == NULL)
    List->Head = List->Tail = Node;
  else
    List->Head = Node;
  List->Size++;
}

void listInsert(sListIterator* Iterator, void* Data)
{
  if(*(Iterator->Current) == NULL)
    listPushBack(Iterator->List, Data);
  else
  {
    size_t ElemSize = Iterator->List->ElementSize;
    sListNode* Node = 0;
    Node = malloc(sizeof(sListNode));
    Node->Data = malloc(ElemSize);
    memcpy(Node->Data, Data, ElemSize);
    Node->Next = *(Iterator->Next);
    (*(Iterator->Current))->Next = Node;
  }
}

void listErase(sListIterator* Iterator)
{
  if(*(Iterator->Current) == NULL)
    return;
  sListNode* Node = (*(Iterator->Current));
  void* Data = Node->Data;
  free(Node);
  Node = NULL;
  if(Iterator->List->EraseFun != NULL)
    (*Iterator->List->EraseFun)(Data);
  free(Data);
  Data = NULL;
  Iterator->Current = Iterator->Next;
  Iterator->Next = &(*(Iterator->Next))->Next;  
  Iterator->List->Size--;
}

void* listGet(sListIterator* Iterator)
{
  if(*(Iterator->Current) == NULL)
    return 0;
  void* Data = (*(Iterator->Current))->Data;
  return Data;
}

void listHead(sLinkedList* List, sListIterator** It)
{
  if(It == NULL)
    return;
  if(*It == NULL)
  *It = malloc(sizeof(sListIterator));
  (*It)->List = List;
  (*It)->Prev = NULL;
  (*It)->Current = &(List->Head);
  (*It)->Next = &(List->Head->Next);
  return;
}

size_t listSize(sLinkedList* List)
{
  if(List == NULL)
    return 0;
  return List->Size;
}

int listEmpty(sLinkedList* List)
{
  if(List == NULL)
    return 0;
  return (List->Size == 0) ? 1 : 0;
}

void listClear(sLinkedList* List)
{
  if(List == NULL)
    return;
  sListIterator* it = 0;
  listHead(List, &it);
  while(!listIteratorEnd(it))
    listErase(it);
  free(it);
}

void listIteratorNext(sListIterator* Iterator)
{
  if(*(Iterator->Current) == NULL)
    return;
  Iterator->Prev = Iterator->Current;
  Iterator->Current = Iterator->Next;
  Iterator->Next = &((*(Iterator->Next))->Next);
}

int listIteratorEnd(sListIterator* Iterator)
{
  return(*Iterator->Current == NULL) ? 1 : 0;
}
