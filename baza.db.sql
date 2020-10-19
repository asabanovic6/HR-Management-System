BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "employees" (
	"employee_id"	INTEGER,
	"employee_name"	TEXT,
	"email"	TEXT,
	"hire_date"	TEXT,
	"department_id"	INTEGER,
	"manager_id"	INTEGER,
	"job_id"	INTEGER,
	"salary"	INTEGER,
	"cmp"	REAL,
	"expire_date"	TEXT,
	PRIMARY KEY("employee_id"),
	FOREIGN KEY("department_id") REFERENCES "departments"("department_id"),
	FOREIGN KEY("job_id") REFERENCES "jobs"("job_id")
);
CREATE TABLE IF NOT EXISTS "departments" (
	"department_id"	INTEGER,
	"department_name"	TEXT,
	"manager_id"	INTEGER,
	"location_id"	INTEGER,
	PRIMARY KEY("department_id"),
	FOREIGN KEY("manager_id") REFERENCES "employees"("employee_id"),
	FOREIGN KEY("location_id") REFERENCES "locations"("location_id")
);
CREATE TABLE IF NOT EXISTS "jobs" (
	"job_id"	INTEGER,
	"job_title"	TEXT,
	"min_salary"	INTEGER,
	"max_salary"	INTEGER,
	PRIMARY KEY("job_id")
);
CREATE TABLE IF NOT EXISTS "locations" (
	"location_id"	INTEGER,
	"city"	TEXT,
	PRIMARY KEY("location_id")
);
INSERT INTO "employees" VALUES (1,'Amina Sabanovic','asabanovi6@etf.unsa.ba','2010-10-05 00:00',1,0,1,1500,NULL,'2020-10-05 00:00');
INSERT INTO "employees" VALUES (2,'Hana Veladzic','hanaveladzic@gmail.com','2011-10-10 00:00',1,1,2,1000,0.2,'2016-10-05 00:00');
INSERT INTO "employees" VALUES (3,'Adnan Tomic ','adnant98@live.com','2011-09-18 00:00',1,1,3,1200,0.1,'2016-10-05 00:00');
INSERT INTO "employees" VALUES (4,'Nina Skopljak','snina1@etf.unsa.ba','2015-03-11 00:00',2,0,1,1700,0.1,'2030-10-05 00:00');
INSERT INTO "employees" VALUES (5,'Davor Sekulic','dsekulic3@etf.unsa.ba','2019-10-18 00:00',2,4,3,1300,NULL,'2025-10-05 00:00');
INSERT INTO "employees" VALUES (6,'Sandra Zec','zecsandra1@gmail.com','2019-10-18 00:00',2,4,3,1300,0.1,'2025-10-05 00:00');
INSERT INTO "employees" VALUES (7,'Mario Drmac','dmario1@gmail.com','2019-10-20 00:00',2,4,4,1000,NULL,'2025-10-05 00:00');
INSERT INTO "employees" VALUES (8,'Nijaz Šabanović','nSabanovic@gmail.com','2020-10-05 00:00',3,0,1,1000,0.1,'2030-10-05 00:00');
INSERT INTO "departments" VALUES (1,'Restoran',1,1);
INSERT INTO "departments" VALUES (2,'Kafić',4,2);
INSERT INTO "departments" VALUES (3,'Lounge',8,1);
INSERT INTO "jobs" VALUES (1,'Menadžer',1200,2000);
INSERT INTO "jobs" VALUES (2,'Kuhar',1000,1700);
INSERT INTO "jobs" VALUES (3,'Šanker',1000,1500);
INSERT INTO "jobs" VALUES (4,'Čistać',900,1300);
INSERT INTO "locations" VALUES (1,'Sarajevo');
INSERT INTO "locations" VALUES (2,'Mostar');
COMMIT;
