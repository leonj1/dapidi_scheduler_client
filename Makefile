all: integration

clean:
	mvn clean

compile: clean
	mvn package

docker: compile
	docker build -t dapidi/scheduler_agent:0.1 .

integration: docker
	./integration/test.sh

#test: integration
#	python ./integration/test.py

