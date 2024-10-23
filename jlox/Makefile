# Variables
SRC = $(wildcard *.java)
CLASSFILES = $(SRC:.java=.class)
MAINCLASS = Main

# Compiler and flags
JAVAC = javac
JAVA = java
JFLAGS = -g

# Default target to compile and run
all: compile run

# Compile Java files
compile: $(CLASSFILES)

%.class: %.java
	$(JAVAC) $(JFLAGS) $<

# Run the main program
run:
	$(JAVA) $(MAINCLASS)

# Clean compiled files
clean:
	rm -f *.class
