db: db.o dbCases.o 
	gcc -o db db.o dbCases.o 

db.o: db.c dbCases.h
	gcc -std=c99 -Wall -ggdb -c db.c

dbCases.o: dbCases.c dbCases.h
	gcc -std=c99 -Wall -ggdb -c dbCases.c
