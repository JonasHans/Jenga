JCC = javac
JFLAGS = -g

default: Tower.class Jenga.class BlockCoords.class Layer.class Block.class

Tower.class: Tower.java
	$(JCC) $(JFLAGS) Tower.java

Jenga.class: Jenga.java
	$(JCC) $(JFLAGS) Jenga.java

BlockCoords.class: BlockCoords.java
	$(JCC) $(JFLAGS) BlockCoords.java

Layer.class: Layer.java
	$(JCC) $(JFLAGS) Layer.java

Block.class: Block.java
	$(JCC) $(JFLAGS) Block.java

clean: 
	$(RM) *.class