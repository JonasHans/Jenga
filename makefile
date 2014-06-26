JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Block.java \
	Layer.java \
	Tower.java \
	BlockCoords.java \
	Coords.java \
	GripperPos.java \
	JointVal.java \
	ReadTxt.java \
	InverseKinematics.java \
	Move.java \
	Jenga.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class