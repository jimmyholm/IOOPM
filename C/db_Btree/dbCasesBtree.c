#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "dbCasesBtree.h"

static void readline(char *dest, int n, FILE *source){
  fgets(dest, n, source);
  int len = strlen(dest);
  if(dest[len-1] == '\n')
    dest[len-1] = '\0';
}

typedef struct btree{
  char *key;
  char *value;
  struct btree *left;
  struct btree *right;
} *btree;

btree btree = NULL;

btree createBranch (char* key, char* value){
    btree newBranch = malloc(sizeof(struct btree));
    newBranch->key = malloc(strlen(key) + 1);
    strcpy(newBranch->key, key);
    newBranch->value = malloc(strlen(value) + 1);
    strcpy(newBranch->value, value);
    newBranch->left = NULL;
    newBranch->right = NULL; 
    return newBranch;  
  }

int insert (char* key, char* value){
  returnExtededResult = insertExtended(key, value, btree);
  if (returnExtendedResult == NULL){
    return 0;
  }
  btree = returnExtendedResult;
  return 1;
}

btree insertExtended (char* key, char* value, btree* cursor){
  if (strcmp(key, cursor->key) == 0){
    return NULL;
  }
  if (strcmp(key, cursor->key) > 0){
    if (cursor->right == NULL){
      cursor->right = createBranch(key, value);
      return cursor;      
    }        
    cursor->right = insertExtended(key, value, cursor->right);
    return cursor;
  }
  if (cursor->left == NULL){
    cursor->left = createBranch(key, value);
    return cursor;      
  }
  cursor->left = insertExtended(key, value, cursor->left);
  return cursor;
}

int readDatabase (char* filename){
  printf("Loading database \"%s\"...\n\n", filename);
  FILE *database = fopen(filename, "r");
  char bufferkey[128];
  char buffervalue[128];
  while(!(feof(database))){
    readline(bufferkey, 128, database);
    readline(buffervalue, 128, database);
    Btree = insert(, newBranch);
  }
  return 0;
}

char* query (char* key){
  btree cursor = btree;
  while (cursor != 0){
    if (strcmp(key, cursor->key) == 0){
      return cursor->value;
    }
  if (strcmp(key, cursor->key) > 0){
    cursor = cursor->right;
    query(key);
  }
  cursor = cursor->left;
  query(key);
  return NULL;
}

int update (char* key, char* value){
  updateExtededResult = updateExtended(key, value, btree);
  if (returnExtendedResult == NULL){
    return 0;
  }
  btree = returnExtendedResult;
  return 1;
}

btree updateExtended (char* key, char* value, btree* cursor){
  if (strcmp(key, cursor->key) == 0){
    cursor->value = value;
    return cursor;
  }
  if (strcmp(key, cursor->key) > 0){
    if (cursor->right == NULL){
      return NULL;      
    }
    cursor->right = updateExtended(key, value, cursor->right);
    return cursor;
  }
  if (cursor->left == NULL){
    return NULL;     
  }
  cursor->left = updateExtended(key, value, cursor->left);
  return cursor;
}
 


 char* delete (char* key){
   btree deleteExtendedResult = deleteExtended(key, btree);
   if (deleteExtendedResult == NULL) {return NULL;}
   return deleteExtendedResult->value;
 } 

 btree deleteExtended (char* key, btree cursor){
   if (strcmp(key, cursorkey) == 0){
     if (cursor->left == NULL && cursor->right == NULL){casecursor = 1;}
     if (cursor->left == NULL && cursor->right != NULL){casecursor = 2;}
     if (cursor->left != NULL && cursor->right == NULL){casecursor = 3;}
     if (cursor->left != NULL && cursor->right != NULL){casecursor = 4;}
     switch (casecursor){
       
     case 1:
       cursor = NULL;
       break;   
     case 2:
       cursor = cursor->right;
       break;
     case 3:
       cursor = cursor->left;
       break;
     case 4:
       btree mostLeft (btree updateTree){

         if (cursor->left == NULL){
           return cursor;
         }
         mostLeft(cursor->left);
       }
       
       btree mostRight (btree updateTree, btree rightState){
         
         if (updateTree == NULL){ 
           free mostLeft (rightState);
           updateTree = rightState;
         }
         mostright (updateTree->right, rightState);
         return updateTree;
       }
       
       btree leftState = cursor->left;
       btree rightState = cursor->right;
       cursor = mostLeft(cursor->right);
       cursor->left = leftState;
       cursor->right = mostRight (cursor->right, rightState);
       break;
     }
     return cursor->value;
   }
   if (strcmp(key, cursorkey) > 0)
     deleteExtended(key, cursor->right);
   return cursor;
 }
deleteExtended(key, cursor->left);
 return cursor;
}

void print (){
  void printExtended(btree);
}

typedef struct btreelist{
  btree *node;
  struct btreelist *next;
} *btreelist;

btreelist cursorlist = NULL;

void printExtended (btreelist btreelist){
  btree cursor = btree;
  if (cursor->left == NULL && cursor->right == NULL){

  cursorlist->node = btree;
if cursor






 
















