#include "LinkedListTest.h"
#include <stdio.h>
void TestList(void)
{
  sLinkedList* List = 0;
  sListIterator* Iterator = 0;
  int errors = 0;
  int i = 1;
  puts("/tTesting linked list functionality\n");
  puts("Test one: Initialize list.");
  listInitialize(&List, sizeof(int),  NULL);
  printf("List initialized: %s\n", (List != NULL) ? "Yes": "No");
  if(List == NULL)
    errors++;
  puts("Testing pushBack twice. First element should be 1\n");
  listPushBack(List, (void*)&i);
  i++;
  listPushBack(List, (void*)&i);
  puts("Retreiving iterator to test first element of list.\n");
  listHead(List, &Iterator);
  i = *(int*)listGet(Iterator);
  printf("Value of first element is: %d\n", i);
  if(i != 1)
  {
    errors++;
    puts("Failed!\n");
  }
  else
    puts("Succeeded!\n");
  puts("Trying pushFront. First element should now be 3.\n");
  i = 3;
  listPushFront(List, (void*)&i);
  listHead(List, &Iterator);
  i = *(int*)listGet(Iterator);
  printf("Value of first element is: %d\n", i);
  if(i != 3)
  {
    errors++;
    puts("Failed!\n");
  }
  else
    puts("Succeeded!\n");
  puts("Erasing first element; new first element should once again be 1.\n");
  listErase(Iterator);
  i = *(int*)listGet(Iterator);
  printf("Value of first element: %d\n", i);
  if(i != 1)
  {
    errors++;
    puts("Failed!\n");
  }
  else
    puts("Succeeded!\n");
  puts("Size of list should now be 2.\n");
  i = listSize(List);
  printf("List size: %d\n", i);
  if(i != 2)
  {
    errors++;
    puts("Failed!\n");
  }
  else
    puts("Succeeded!\n");
  puts("Checking if list is empty.\n");
  i = listEmpty(List);
  printf("Is list empty: %s\n", (i == 1) ? "Yes" : "No");
  if(i == 1)
  {
    errors++;
    puts("Failed!\n");
  }
  else
    puts("Succeeded!\n");
  puts("Clearing list.\n");
  listClear(List);
  puts("Size of list should now be 0.\n");
  i = listSize(List);
  printf("Size of list: %d\n", i);
  if(i != 0)
  {
    errors++;
    puts("Failed!\n");
  }
  else
    puts("Succeeded!\n");
  puts("And list should reporty empty.\n");
  i = listEmpty(List);
  printf("Is list empty: %s\n", (i == 1) ? "Yes" : "No");
  if(i == 0)
  {
    errors++;
    puts("Failed!\n");
  }
  else
    puts("Succeeded!\n");
  puts("Testing insert and iterator advancement.");
  i = 0;
  listPushBack(List, (void*)&i);
  i = 4;
  listPushBack(List, (void*)&i);
  listHead(List, &Iterator);
  i = 1;
  listInsert(Iterator, (void*)&i);
  i = 2;
  listIteratorNext(Iterator);
  listInsert(Iterator, (void*)&i);
  i = 3;
  listIteratorNext(Iterator);
  listInsert(Iterator, (void*)&i);
  puts("Print readout should be: 0 1 2 3 4\n");
  listHead(List, &Iterator);
  while(!listIteratorEnd(Iterator))
  {
    printf("%d ", *(int*)listGet(Iterator));
    listIteratorNext(Iterator);
  }
  puts("\n");
  puts("Popping front. New readout should be: 1 2 3 4\n");
  listPopFront(List);
  listHead(List, &Iterator);
  while(!listIteratorEnd(Iterator))
  {
    printf("%d ", *(int*)listGet(Iterator));
    listIteratorNext(Iterator);
  }
  puts("\nList size should be 4.\n");
  i = listSize(List);
  printf("Size of list: %d\n", i);
  if(i != 4)
  {
    errors++;
    puts("Failed!\n");
  }
  else
    puts("Succeeded!\n");
  puts("Popping back. New readout should be: 1 2 3\n");
  listPopBack(List);
  listHead(List, &Iterator);
  while(!listIteratorEnd(Iterator))
  {
    printf("%d ", *(int*)listGet(Iterator));
    listIteratorNext(Iterator);
  }
  puts("\nList size should be 3.\n");
  i = listSize(List);
  printf("Size of list: %d\n", i);
  if(i != 3)
  {
    errors++;
    puts("Failed!\n");
  }
  else
    puts("Succeeded!\n");
  printf("Testing finished.\n %d errors found.\n\n", errors);
  listClear(List);
  free(List);
  free(Iterator);
}
