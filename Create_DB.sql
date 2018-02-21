DROP DATABASE IF EXISTS CapstoneTracker;

CREATE DATABASE CapstoneTracker;

USE CapstoneTracker;

CREATE Table RITDeadlines(
	Term INT UNSIGNED,
	AddDropDate DATE,
	GradesDue DATE,
	
	PRIMARY KEY(Term)
);

CREATE TABLE Status(
	StatusID INT UNSIGNED AUTO_INCREMENT,
	Name VARCHAR(50),
	Description TEXT,
	
	PRIMARY KEY(StatusID)
);

CREATE TABLE Person(
	PersonID INT UNSIGNED AUTO_INCREMENT,
	UserName CHAR(7),
	FirstName VARCHAR(50),
	LastName VARCHAR(50),
	UserPassword CHAR(64),
	Email VARCHAR(50),
	GraduationDate DATE,
	Department VARCHAR(50),
	Major VARCHAR(50),
	RoleName VARCHAR(50),
	
	PRIMARY KEY(PersonID)
);

CREATE TABLE Project(
	ProjectID INT UNSIGNED AUTO_INCREMENT,
	Name VARCHAR(50),
	ProjectType VARCHAR(50),
	Description TEXT,
	StartTerm INT UNSIGNED,
	Deadline DATE,
	PlagiarismScore INT,
	Grade INT,
	
	PRIMARY KEY(ProjectID),
	FOREIGN KEY(StartTerm) REFERENCES RITDeadlines(Term)
);

CREATE TABLE PersonProject(
	PersonID INT UNSIGNED,
	ProjectID INT UNSIGNED,
	Role VARCHAR(50),
	
	FOREIGN KEY(PersonID) REFERENCES Person(PersonID),
	FOREIGN KEY(ProjectID) REFERENCES Project(ProjectID)
);

CREATE TABLE ProjectStatus(
	StatusID INT UNSIGNED,
	ProjectID INT UNSIGNED,
	DateGiven DATE,
	Comments TEXT,
	
	FOREIGN KEY(StatusID) REFERENCES Status(StatusID),
	FOREIGN KEY(ProjectID) REFERENCES Project(ProjectID)
);