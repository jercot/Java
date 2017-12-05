DROP TABLE Animal;
DROP TABLE Status;
DROP TABLE Person;
DROP TABLE Location;

CREATE TABLE Animal
			(animalID	smallint	NOT NULL,
			age		smallint,
			type		VARCHAR(25),
			gender		VARCHAR(1),
			description	VARCHAR(150),
			breed		VARCHAR(25),
			name		VARCHAR(20),
			picture		VARCHAR(50),
			category	VARCHAR(10),
PRIMARY KEY (animalID));

CREATE TABLE Status
			(animalID	smallint	NOT NULL,
			neutered	VARCHAR(1),
			chipped		VARCHAR(1),
			vaccinated	VARCHAR(1),
			status		VARCHAR(25),
			reserved	VARCHAR(1),
			adoptionDate	DATE,
PRIMARY KEY (animalID));

CREATE TABLE Person
			(animalID	smallint	NOT NULL,
			name		VARCHAR(50),
			address		VARCHAR(50),
			phone		VARCHAR(50),
			email		VARCHAR(50));


CREATE TABLE Location
			(animalID	smallint	NOT NULL,
			date		DATE,
			location	VARCHAR(25),
PRIMARY KEY (animalID));