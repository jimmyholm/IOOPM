.PHONY : release
.PHONY : debug
.PHONY : all
.PHONY : rebuild
.PHONY : rebuild_release
.PHONY : clean
release: DEBUG ?=n
RCFLAGS=-Wall -Wextra -I $(INCLUDE) -O2
CFLAGS=-Wall -Wextra -I $(INCLUDE) -ggdb
#LDFLAGS=-L $/lib
RLDLIBS=-ltcod -ltcodxx
LDLIBS=-ltcod_debug -ltcodxx_debug
TARGET=roguelike
OUT=./obj
SRC=./src
BIN=./bin
INCLUDE=./include
CC=g++
CPP=$(wildcard $(SRC)/*.cpp)
OBJ=$(CPP:$(SRC)/%.cpp=$(OUT)/%.o)
release : CFLAGS = $(RCFLAGS)
release : LDLIBS = $(RLDLIBS)
debug: all
	$(info )
	$(info Debug Build Performed.)
release: all
	$(info )
	$(info Release  Build Performed.)
all: $(OBJ)
	$(CC) $(CFLAGS) -o $(BIN)/$(TARGET) $(OBJ) $(LDFLAGS) $(LDLIBS)

$(OUT)/%.o: $(SRC)/%.cpp
	$(CC) -c -o $@ $(CFLAGS) $< 
# $(LDFLAGS) $(LDLIBS)

clean: 
	rm -rf $(OUT)/*
	rm -rf $(BIN)/$(TARGET)
	rm -rf ./*~
	rm -rf $(SRC)/*~
	rm -rf $(INCLUDE)/*~

rebuild: clean debug
	
rebuild_release: clean release
	
