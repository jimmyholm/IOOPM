#ifndef LINKEDLIST_H
#define LINKEDLIST_H
#include <stdlib.h>
#include <string.h> // For memcpy

typedef struct sLinkedList sLinkedList;
typedef struct sListIterator sListIterator;
// Initialize a linked list
void listInitialize(sLinkedList** List, size_t ElementSize, void (*Erasefun)(void*));

// Add a copy of the given data to the end of the list.
void listPushBack(sLinkedList* List, void* Data);

// Add a copy of the given data to the front of the list.
void listPushFront(sLinkedList* List, void* Data);

// Add a copy of the given data into the list at the given iterator position.
void listInsert(sListIterator* Iterator, void* Data);

// Erase the element pointed to by Iterator
void listErase(sListIterator* Iterator);

// Return the data held by an iterator.
void* listGet(sListIterator* Iterator);

// Initialize an iterator to the head of the list
void listHead(sLinkedList* List, sListIterator** It);

// Return the number of elements in a list
size_t listSize(sLinkedList* List);

// Return 1 is the list is empty, 0 if not
int listEmpty(sLinkedList* List);

// Clear the list
void listClear(sLinkedList* List);

// Advance an iterator to the next element in a list.
void listIteratorNext(sListIterator* Iterator);

// Return 1 if list iterator has reached the end of its list.
int listIteratorEnd(sListIterator* Iterator);
#endif
