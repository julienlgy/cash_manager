##
## EPITECH PROJECT, 2019
## Makefile JAVA
## BOOTSTRAP CASH MANAGER
## Julien Legay
##

NAME	=	CashManager_bootstrap.jar

test:
	mvn test

package:
	mvn package
	cp ./target/bootstrap_cash*.jar $(NAME)

build:
	mvn compile

deps:
	mvn dependency:resolve

clean:
	mvn clean
	rm -f $(NAME)

all:	clean
	make package
	chmod +x $(NAME)
