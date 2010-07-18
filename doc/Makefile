GRAPHVIZ = $(foreach FILE,\
	$(wildcard ../src/docs/graphviz/*),\
	$(basename $(notdir $(FILE))).png)

all: graphviz

graphviz: $(GRAPHVIZ)

%.png: ../src/docs/graphviz/%.gv
	dot -Tpng -o$* $(.SOURCE)
%.png: ../src/docs/graphviz/%.py
	python $< | dot -Tpng -o$@
