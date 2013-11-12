#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "dbCases.h"

void readline(char *dest, int n, FILE *source){
  fgets(dest, n, source);
  int len = strlen(dest);
  if(dest[len-1] == '\n')
    dest[len-1] = '\0';
}

int main(int argc, char *argv[]){
  char buffer[128];
  char newValueBuffer[128];
  if (argc < 2){
    puts("Usage: db [FILE]");
    return -1;
  }
  puts("Welcome to");
  puts(" ____    ____       ");
  puts("/\\  _`\\ /\\  _`\\     ");
  puts("\\ \\ \\/\\ \\ \\ \\L\\ \\   ");
  puts(" \\ \\ \\ \\ \\ \\  _ <\\ ");
  puts("  \\ \\ \\_\\ \\ \\ \\L\\ \\ ");
  puts("   \\ \\____/\\ \\____/ ");
  puts("    \\/___/  \\/___/  ");
  puts("");
  
  // Read the input file
  readDatabase(argv[1]);

  // Main loop
  int choice = -1;
  while(choice != 0){
    puts("Please choose an operation");
    puts("1. Query a key");
    puts("2. Update an entry");
    puts("3. New entry");
    puts("4. Remove entry");
    puts("5. Print database");
    puts("0. Exit database");
    printf("? ");
    scanf("%d", &choice);
    while(getchar() != '\n'); // Clear stdin
    switch(choice){

    case 1: // Query
      printf("Enter key: ");
      readline(buffer, 128, stdin);
      puts("Searching database...\n");
      char* queryResult = query(buffer);
      if (queryResult == NULL){  
        printf("Could not find an entry matching key \"%s\"!\n", buffer);
        break;}
      puts("Found entry:");
      printf("queryResult: %s\nvalue: %s\n", buffer, queryResult);
      break;
      
    case 2: // Update
      printf("Enter key: ");
      readline(buffer, 128, stdin);
      printf("Enter new value: ");
      readline(newValueBuffer, 128, stdin);      
      puts("Searching database...\n");
      int updatedDb = update(buffer, newValueBuffer);
      if (updatedDb == 0){
        printf("Could not find an entry matching key \"%s\"!\n", buffer);
        break;        
      }
      if (updatedDb == 1){
        puts("Value inserted successfully!");
      }
      
      break;
      
    case 3: // Insert
      printf("Enter key: ");
      readline(buffer, 128, stdin);
      printf("Enter value: ");
      readline(newValueBuffer, 128, stdin);
      puts("Searching database for duplicate keys...");
      int insertReturn = insert (buffer, newValueBuffer);
      if (insertReturn == 0){
        printf("key \"%s\" already exists!\n", buffer);
        break;
      }      
      puts("Key is unique!\n");
      puts("Entry inserted successfully:");
      printf("key: %s\nvalue: %s\n", buffer, newValueBuffer);
      break;





    case 4: // Delete
      printf("Enter key: ");
      readline(buffer, 128, stdin);
      puts("Searching database...\n");
      char* deleteReturn = delete (buffer);
        if (deleteReturn == NULL){
          printf("Could not find an entry matching key \"%s\"!\n", buffer);
          break;
        }
      printf("Deleted the following entry:\nkey: %s\nvalue: %s\n", buffer, deleteReturn);
      break;
      

    case 5: // Print database
      print();
      break;

    case 0: // Exit
      puts("Good bye!");
      break;

    default:
      // Please try again
      puts("Could not parse choice! Please try again");
    }
    puts("");
  }
  return 0;
}
