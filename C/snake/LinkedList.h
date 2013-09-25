/**
 * @file LinkedList.h
 * @Author Jimmy Holm, Marcus Münger
 * @date September 25, 2013
 * @brief A Generic Linked List implementation in C
 */
#ifndef LINKEDLIST_H
#define LINKEDLIST_H
#include <stdlib.h>
#include <string.h> // For memcpy

/*!
 * Linked List structure
 */
typedef struct sLinkedList sLinkedList;
/*!
 * Generic Iterator for linked lists
 */
typedef struct sListIterator sListIterator;
/*! \brief Initialize a linked list
 * 
 * \param List a referehce to an uninitialized list pointer.
 * \param ElementSize the size of a list's stored data.
 * \param EraseFun a pointer to a function run on any element before its erasure
 * \return void
 * listInitialize is in charge of creating instances of a linked list and initializing its properties. The List parameter should point to null when passed, and will point to a valid, initialized list at the return of the function. The parameter ElementSize contains the size of a given data element, and all data passed to this list is assumed to be of this size. EraseFun allows for a special deconstructor function to be called on the list's elements upon erasure.
 * \remark The lifetime of the list is not maintained by the library; the user is responsible for freeing the List pointer when finished with it.
*/
void listInitialize(sLinkedList** List, size_t ElementSize, void (*EraseFun)(void*));

/*! \brief Insert a copy of the given data to the end of the list.
 *
 * \param List a pointer to a list previously initialized with listInitialized, into which Data is to be added.
 * \param Data a pointer to the data to be copied into the list.
 * \return void
 * listPushBack inserts a copy of the provided data into the list at the very end. Note that it's a \em copy of the Data parameter that is stored; the linked list does not maintain the lifetime of the original data passed.
 * \sa listPushFront(), listInsert() and listInitialize()
 */
void listPushBack(sLinkedList* List, void* Data);

/*! \brief Insert a copy of the given data to the front of the list.
 *
 * \param List a pointer to a list previously initialized with listInitialized, into which Data is to be added.
 * \param Data a pointer to the data to be copied into the list.
  * listPushFront inserts a copy of the provided data into the list at the very front. Note that it's a \em copy of the Data parameter that is stored; the linked list does not maintain the lifetime of the original data passed.
 * \sa listPushBack(), listInsert() and listInitialize()
 */
void listPushFront(sLinkedList* List, void* Data);

/*! \brief Insert a copy of the given data into the list at the given iterator position.
 *
 * \param Iterator pointer to an initialized iterator into a list, where the new element is to be inserted.
 * \param Data a pointer to the data to be copied into the list.
  * This functions inserts a copy of the provided data into the list in front of the current iterator position. Note that it's a \em copy of the Data parameter that is stored; the linked list does not maintain the lifetime of the original data passed.
 */
void listInsert(sListIterator* Iterator, void* Data);

/*! \brief Pop the first element of the list
 *
 * \param List a pointer to a list previously initialized by listInitialized, from which the first element is to be removed.
 * listPopFront removes the first element of the list, calling upon the list's erasure function if present.
 * \sa listPopBack(), listErase() and listInitialize()
 */
void listPopFront(sLinkedList* List);

/*! \brief Pop the last element of the list
 *
 * \param List a pointer to a list previously initialized by listInitialized, from which the final element is to be removed.
 * listPopBack removes the final element of the list, calling upon the list's erasure function if present.
 * \sa listPopFront(), listErase() and listInitialize()
 */
void listPopBack(sLinkedList* List);

/*! \brief Erase the element pointed to by Iterator
 *
 *\param Iterator an initialized iterator into a linked list.
 * listErase erases the element pointed to by the iterator, removing it from its list and calling upon the data's erasure function if present.
 *\sa listPopFront(), listPopBack(), and listHead()
 */
void listErase(sListIterator* Iterator);

/*! \brief Return the data held by an iterator.
 *
 * \param Iterator an initialized iterator into a linked list.
 * \return the data held by Iterator
 * listGet returns the data stored in the list element pointed to by Iterator.
 * \sa listHead()
 */
void* listGet(sListIterator* Iterator);

/*! \brief Initialize an iterator to the head of the list
 *
 * \param List a pointer to an initialized list.
 * \param It a reference to an uninitialized iterator pointer.
 * listHead initialises It to point at the first element of the given list.
 * \remark the lifetime of the iterator is not maintained by the library. The user is responsible for freeing an initialized iterator.
 */
void listHead(sLinkedList* List, sListIterator** It);

/*! \brief Return the number of elements in a list
 *
 * \param List a pointer to an initialized list
 * \return the number of elements in the given list
 * listSize returns the number of elements in the given list.
 */
size_t listSize(sLinkedList* List);

/*! \brief Check whether the list is empty or contains elements.
 *
 * \param List a pointer to an initialized list
 * \returns 1 if the list contains no elements or 0 otherwise
 * listEmpty returns a boolean integer based on whether the list is empty or contains elements.
 */
int listEmpty(sLinkedList* List);

/*! \brief Clear the list
 *
 * \param List a pointer to an initialized list
 * listClear calls listErase on every element in the list, calling upon the erasure function on each element if available.
 */
void listClear(sLinkedList* List);

/*! \brief Advance an iterator to the next element in a list.
 *
 * \param Iterator an initialized iterator into an initialized list.
 * After a call to listIteratorNext, Iterator will point to the next element in its associated list.
 */
void listIteratorNext(sListIterator* Iterator);

/*! Check whether or not an iterator is at the end of its list.
 *
 * \param Iterator an initialized iterator into an initialized list.
 * \return 1 if Iterator has reached the end of its list, 0 otherwise.
 * listIteratorEnd returns a boolean integer based on whether the given iterator has reached the end of its associated list.
 */
int listIteratorEnd(sListIterator* Iterator);
#endif
