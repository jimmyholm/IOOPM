#ifndef BINOMIALHEAP_H
#define BINOMIALHEAP_H

#define BINOM_MINHEAP (char)0
#define BINOM_MAXHEAP (char)1

typedef struct sBinomHeap sBinomHeap;

/*! Initialize a binomial heap
 *  \param Heap a reference to a binomial heap instance pointer to be initialized, should be assigned 0 before this function is called.
 *  \param ElementSize the size of a stored element
 *  \param EraseFun pointer to an optional erasure function in charge of deinitializing an element before it's erased from the heap.
 *  \param HeapType determines whether the heap is to be treated as a min heap or max heap, using the macros BINOM_MINHEAP and BINOM_MAXHEAP
 *  \return 1 if initialization is successful, 0 otherwise.
 *  Initializes a NULL assigned binomial heap pointer and preparing it for use.
 */
int sBinHeapInit(sBinomHeap** Heap, size_t ElementSize, void (*EraseFun)(void*), char HeapType);

/*! Merge two heaps.
 *  \param Heap1 a reference to a binomial heap instance pointer to the first of the two heaps to be merged.
 *  \param Heap2 a reference to a binomial heap instance pointer to the second of the two heaps to be merged.
 *  Merges two heaps into a new heap. Upon successful completion of the function, Heap1 will contain the merged heap and Heap 2 will be NULL.
 */
void sBinHeapMerge(sBinomHeap** Heap1, sBinomHeap** Heap2);

/*! Copy data into the heap.
 *  \param Heap a binomial heap instance pointer to insert a copy of the given data into.
 *  \param Key the key value to be given to the new data node
 *  \param Data the data value to be copied into the heap
 *  Creates a copy of the given data and inserts it into the heap. Note that a \ecopy of the data is added and managed by the heap; the lifetime of the original data is \enot managed.
 */
void sBinHeapInsert(sBinomHeap* Heap, long Key, void* Data);

/*! Find the node with the lowest/highest key value in the heap, depending on the type of heap.
 *  \param Heap a binomial heap instance pointer to search in
 *  \return The data held by the node with the lowest key value.
 *  Searches through the heap for the node with the lowest key value.
 */
void* sBinHeapFindExtreme(sBinomHeap* Heap);

/*! Change the key value of a given node, while retaining the min/max-heap property.
 *  \param Heap a binomial heap instance pointer to the heap containing the element we wish to decrease 
 *  \param Key the key of the node to change
 *  \param NewKey the key of the node to change to. For a min-heap, the new key must be less than Key - for a max-heap the new key must be greater.
 *  \returns Returns 1 if the given node existed, and had its key changed - 0 otherwise.
 *  Alters the key value of a given node while retaining the heap properties.
 */
int sBinHeapChangeKey(sBinomHeap* Heap, long Key, long NewKey);

/*! Delete the node with the maximum/minimum key value depending on the heap type.
 *  \param Heap a binomial heap instance pointer to the heap that will have its extreme node deleted.
 *  Deletes the node with the extreme key value depending on the heap type. Calls upon the heap's EraseFun if available.
 */ 
void sBinHeapDeleteExtreme(sBinomHeap* Heap);

/*! Delete a node with the given key value
 *  \param Heap a binomial heap instance pointer to the heap that will have a given node deleted.
 *  \param Key the key value of the node to be deleted.
 *  Deletes a node with the given key value from the heap. Calls upon the heap's EraseFun if available.
 */
void sBinHeapDelete(sBinomHeap* Heap, long Key);

/*! Destroy a given heap.
 *  \param Heap reference to a binomial heap instance pointer to the heap to be destroyed.
 *  Deletes every node in the tree, releasing all kept resources and calling the EraseFun, if available, on each element in turn. Upon finishing, the given Heap pointer will point to NULL.
 */
void sBinHeapDestroy(sBinomHeap** Heap);
#endif // BINOMIALHEAP_H
