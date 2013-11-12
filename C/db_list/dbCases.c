#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "dbCases.h"

static void readline(char *dest, int n, FILE *source){
  fgets(dest, n, source);
  int len = strlen(dest);
  if(dest[len-1] == '\n')
    dest[len-1] = '\0';
}


typedef struct node{
  char *key;
  char *value;
  struct node *next;
} *Node;

Node list = NULL;

int readDatabase (char* filename){
  printf("Loading database \"%s\"...\n\n", filename);
  FILE *database = fopen(filename, "r");
  char buffer[128];

  while(!(feof(database))){
    Node newNode = malloc(sizeof(struct node));
    readline(buffer, 128, database);
    newNode->key = malloc(strlen(buffer) + 1);
    strcpy(newNode->key, buffer);
    readline(buffer, 128, database);
    newNode->value = malloc(strlen(buffer) + 1);
    strcpy(newNode->value, buffer);
    newNode->next = list;
    list = newNode;
  }
  return 0;
}

char* query (char* key){
  int found = 0;
  Node cursor = list;
  while(!found && cursor != NULL){
    if(strcmp(key, cursor->key) == 0){
      found = 1;
    }else{
      cursor = cursor->next;
    }
  }
  if(!found){
    return NULL;
  }
  return cursor->value;
}

int update (char* key, char* newValue){
  int found = 0;
  Node cursor = list;
  while(!found && cursor != NULL){
    if(strcmp(key, cursor->key) == 0){
      free(cursor->value);
      cursor->value = malloc(strlen(newValue) + 1);
      strcpy(cursor->value, newValue);
        found = 1;
    }
    cursor = cursor->next;
  }
  if(!found){
    return 0;
  }
  return 1;
}

int insert (char* key, char* value){
  int found = 0;
  Node cursor = list;
  while(!found && cursor != NULL){
    if(strcmp(key, cursor->key) == 0){
    return 0;
      found = 1;
    }
    cursor = cursor->next;
  }
  if(!found){
  }
  Node newNode = malloc(sizeof(struct node));
  newNode->key = malloc(strlen(key) + 1);
  newNode->value = malloc(strlen(value) + 1);
  strcpy(newNode->key, key);
  strcpy(newNode->value, value);
  newNode->next = list;
  list = newNode;
 
 return 1;
}

char* delete (char* key){
  int found = 0;
  Node cursor = list;
  Node prev = NULL;
  char* value = NULL;
  while(!found && cursor != NULL){
    if(strcmp(key, cursor->key) == 0){
      value = cursor->value;
      if(prev == NULL){ // Delete first node
        list = cursor->next;
      }else{
        prev->next = cursor->next;
      }
      found = 1;
    }else{
      prev = cursor;
      cursor = cursor->next;
    }
  }
  if(!found){
    return NULL;  }
  return value;
}

void print (){
 Node cursor = list;
  while(cursor != NULL){
    puts(cursor->key);
    puts(cursor->value);
    cursor = cursor->next;
  }
}
