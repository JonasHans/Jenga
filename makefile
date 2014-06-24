JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Tower.java \
	Block.java \
	BlockCoords.java \
	Jenga.java \
	Layer.java \
	Move.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class