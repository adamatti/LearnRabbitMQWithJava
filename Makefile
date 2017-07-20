deps:
	docker-compose rm -fv
	docker-compose up --build

run:
	./gradlew install
	cd build/install/LearnRabbit/bin;./LearnRabbit