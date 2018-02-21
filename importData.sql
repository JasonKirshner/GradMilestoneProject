use capstonetracker;

-- Updates RITDeadlines
Insert INTO ritdeadlines VALUES(2175, '2018-1-23', '2018-5-10');

-- Updates status
Insert INTO status Values(1,'Proposal Submitted', '');
Insert INTO status Values(2,'Proposal Approved', '');
Insert INTO status Values(3,'Defense Scheduled', '');
Insert INTO status Values(4,'Defense Passed', '');

-- Updates Person
INSERT INTO person VALUES (1, 'bla3695', 'Brajon', 'Andrews', 'd9b5f58f0b38198293971865a14074f59eba3e82595becbe86ae51f1d9f1f65e','bla3695@g.rit.edu', '2017-5-09', 'IST', 'CIT', 'Student');
INSERT INTO person VALUES (2, 'jlk2918', 'Jason', 'Kirshner', 'd9b5f58f0b38198293971865a14074f59eba3e82595becbe86ae51f1d9f1f65e','jlk2918@g.rit.edu', '2017-5-09', 'IST', 'WMC', 'Student');
INSERT INTO person VALUES (3, 'spg9807', 'Spencer', 'Green', 'd9b5f58f0b38198293971865a14074f59eba3e82595becbe86ae51f1d9f1f65e','spg9807@g.rit.edu', '2017-5-09', 'IST', 'WMC', 'Student');
INSERT INTO person VALUES (4, 'gll1872', 'Gabe', 'Landau', 'd9b5f58f0b38198293971865a14074f59eba3e82595becbe86ae51f1d9f1f65e','gll1872@g.rit.edu', '2017-5-09', 'IST', 'WMC', 'Student');
INSERT INTO person (PersonID, UserName, FirstName, LastName, UserPassword, Email, Department, RoleName) 
VALUES (5, 'mjfics', 'Michael', 'Floeser', 'd9b5f58f0b38198293971865a14074f59eba3e82595becbe86ae51f1d9f1f65e','mjfics@g.rit.edu', 'IST', 'Professor');
INSERT INTO person (PersonID, UserName, FirstName, LastName, UserPassword, Email, Department, RoleName)
VALUES (6, 'blhist', 'Betty', 'Hillman', 'd9b5f58f0b38198293971865a14074f59eba3e82595becbe86ae51f1d9f1f65e','blhist@g.rit.edu', 'IST', 'Advisor');

-- Updates Project
Insert INTO project Values(1, 'capstonetracker', 'database', 'Practice project', 2175, '2019-5-09', 20, 87); 
Insert INTO project Values(2, 'capstonetracker', 'database', 'Practice project', 2175, '2019-5-09', 20, 87); 
Insert INTO project Values(3, 'capstonetracker', 'database', 'Practice project', 2175, '2019-5-09', 20, 87); 
Insert INTO project Values(4, 'capstonetracker', 'database', 'Practice project', 2175, '2019-5-09', 20, 87);

-- Updates PersonProject
INSERT INTO personproject Values(1,1,'Student');
INSERT INTO personproject Values(2,2,'Student');
INSERT INTO personproject Values(3,3,'Student');
INSERT INTO personproject Values(4,4,'Student');
INSERT INTO personproject Values(5,1,'Professor');
INSERT INTO personproject Values(5,2,'Professor');
INSERT INTO personproject Values(5,3,'Professor');
INSERT INTO personproject Values(5,4,'Professor');
INSERT INTO personproject Values(6,2,'Advisor');

-- Updates Project Status
Insert INTO projectstatus Values(1, 1, '2017-11-25', 'Work in progress');

Insert INTO projectstatus Values(1, 2, '2017-11-25', 'Work in progress');
Insert INTO projectstatus Values(2, 2, '2017-11-30', 'Work in progress');

Insert INTO projectstatus Values(1, 3, '2017-11-25', 'Work in progress');
Insert INTO projectstatus Values(2, 3, '2017-11-30', 'Work in progress');
Insert INTO projectstatus Values(3, 3, '2017-12-11', 'Work in progress');

Insert INTO projectstatus Values(1, 4, '2017-11-25', 'Work in progress');
Insert INTO projectstatus Values(2, 4, '2017-11-30', 'Work in progress');
Insert INTO projectstatus Values(3, 4, '2017-12-11', 'Work in progress');
Insert INTO projectstatus Values(4, 4, '2017-12-23', 'Work in progress');
