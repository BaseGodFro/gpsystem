-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: comp5590_assessment_2_group_12b
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `UserID` int NOT NULL,
  PRIMARY KEY (`UserID`),
  CONSTRAINT `admins_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1),(7);
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `AppointmentID` int NOT NULL AUTO_INCREMENT,
  `PatientID` int DEFAULT NULL,
  `DoctorID` int DEFAULT NULL,
  `AppointmentDate` date DEFAULT NULL,
  `Notes` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`AppointmentID`),
  KEY `PatientID` (`PatientID`),
  KEY `DoctorID` (`DoctorID`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`PatientID`) REFERENCES `patients` (`UserID`),
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`DoctorID`) REFERENCES `doctors` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (1,1,1,'2024-03-10','Patient and Doctor were same person - very confusing'),(2,3,2,'2024-05-05','Patient has cancer'),(3,1,1,'2024-04-05','Follow-up checkup for Patient 1 with Doctor 1'),(4,5,2,'2024-04-06','Annual physical examination for Patient 5 with Doctor 2'),(5,3,6,'2024-04-07','Consultation for medication review for Patient 3 with Doctor 6'),(6,4,1,'2024-04-08','Routine dental cleaning for Patient 4 with Doctor 1'),(7,1,2,'2024-04-09','Vaccination appointment for Patient 1 with Doctor 2'),(8,1,6,'2024-04-04','Check in with Dr Wilson'),(9,1,2,'2024-07-04','A JULY TO REMEMBER'),(10,1,2,'2024-07-04','');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `UserID` int NOT NULL,
  `Qualifications` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  CONSTRAINT `doctors_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,NULL),(2,'None'),(6,'Made of medicine');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicalhistories`
--

DROP TABLE IF EXISTS `medicalhistories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicalhistories` (
  `MedicalHistoryID` int NOT NULL AUTO_INCREMENT,
  `PatientID` int DEFAULT NULL,
  `Symptoms` varchar(50) DEFAULT NULL,
  `Treatment` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MedicalHistoryID`),
  KEY `PatientID` (`PatientID`),
  CONSTRAINT `medicalhistories_ibfk_1` FOREIGN KEY (`PatientID`) REFERENCES `patients` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalhistories`
--

LOCK TABLES `medicalhistories` WRITE;
/*!40000 ALTER TABLE `medicalhistories` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicalhistories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `NotificationID` int NOT NULL AUTO_INCREMENT,
  `UserID` int DEFAULT NULL,
  `Message` varchar(300) DEFAULT NULL,
  `NotificationDate` date DEFAULT NULL,
  `NotificationRead` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`NotificationID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,7,'Hello!','2024-05-05',1),(2,7,'Hello!','2024-05-05',1),(4,7,'YOOOO','2024-04-05',1),(5,1,'Hello','2024-04-05',1),(6,1,'Test','2024-04-05',1),(7,1,'You have just booked an appointment with Dr Dr. Wilson','2024-04-05',1),(8,6,'A new appointment has just been booked with you with mworb0001','2024-04-05',0),(9,1,'You have just booked an appointment with Dr. Johnson on 2024-07-04','2024-04-05',1),(10,2,'A new appointment has just been booked with you with mworb0001','2024-04-05',1),(11,1,'You have just booked an appointment with Dr. Johnson on 2024-07-04','2024-04-05',1),(12,2,'A new appointment has just been booked with you with mworb0001','2024-04-05',1),(13,2,'Recorded a visit: Michael Davis (on 2024-04-06)','2024-04-05',1),(14,2,'Recorded a visit: Michael Davis (on 2024-04-06)','2024-04-05',1),(15,2,'Edited a visit: Michael Davis (on 2024-04-06)','2024-04-05',1),(16,1,'Recorded a visit: Emily Brown (on 2024-04-08)','2024-04-05',1),(17,2,'Recorded a visit: Max Worby (on 2024-04-09)','2024-04-05',1),(18,1,'Recorded a visit: Max Worby (on 2024-03-10)','2024-04-05',1),(19,1,'Recorded a visit: Max Worby (on 2024-03-10)','2024-04-05',1),(20,1,'Recorded a visit: Max Worby (on 2024-03-10)','2024-04-05',1),(21,1,'Recorded a visit: Max Worby (on 2024-03-10)','2024-04-05',1),(22,2,'Recorded a visit: Michael Davis (on 2024-04-06)','2024-04-05',1),(29,1,'You have been reassigned to Dr. Johnson.','2024-04-05',1),(30,1,'You have been reassigned to Dr. Worby.','2024-04-05',1),(31,1,'You have been reassigned to Dr. Johnson.','2024-04-05',1),(32,7,'Hello Tom!','2024-04-05',1);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `UserID` int NOT NULL,
  `PrimaryCarePhysicianID` int DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  KEY `PrimaryCarePhysicianID` (`PrimaryCarePhysicianID`),
  CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`),
  CONSTRAINT `patients_ibfk_2` FOREIGN KEY (`PrimaryCarePhysicianID`) REFERENCES `doctors` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (5,1),(1,2),(3,2),(4,6);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(20) DEFAULT NULL,
  `Surname` varchar(20) DEFAULT NULL,
  `DateOfBirth` date DEFAULT NULL,
  `Photo` blob,
  `Username` varchar(10) DEFAULT NULL,
  `Password` varchar(25) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `AGE` int DEFAULT NULL,
  `GENDER` varchar(1) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Max','Worby','2004-05-10',NULL,'mworb0001','password123','mw737@kent.ac.uk',19,'m','011111 1111'),(2,'Alice','Johnson','1995-08-12',NULL,'ajohn0001','pass1234','alice.johnson@example.com',29,'F','+1234567890'),(3,'John','Smith','1988-04-25',NULL,'jsmit0001','password123','john.smith@example.com',36,'M','+1987654321'),(4,'Emily','Brown','2000-11-03',NULL,'ebrow0001','emilyPass','emily.brown@example.com',24,'F','+1122334455'),(5,'Michael','Davis','1992-07-18',NULL,'mdavi0001','mdavispass','michael.davis@example.com',32,'M','+1555666777'),(6,'Sarah','Wilson','1985-12-30',NULL,'swils0001','sarahWpass','sarah.wilson@example.com',39,'F','+1444333222'),(7,'Tom','Roberts','1990-03-15',NULL,'trob0001','tomsPass','tom.roberts@example.com',32,'M','+1654321987'),(8,'Sophia','Lee','1996-09-21',NULL,'slee0001','sophiaPass','sophia.lee@example.com',25,'F','+1122334455'),(9,'Daniel','Garcia','1982-06-08',NULL,'dgarc0001','danielPass','daniel.garcia@example.com',39,'M','+1789456123'),(10,'Olivia','Martinez','1998-12-03',NULL,'omart0001','oliviaPass','olivia.martinez@example.com',23,'F','+1987654321'),(11,'James','Hernandez','1987-05-29',NULL,'jhern0001','jamesPass','james.hernandez@example.com',34,'M','+1888777666'),(12,'alice','smith','2000-01-01',NULL,'asmit0001','passsword','alicesmith@google.com',24,'f','099981918'),(13,'tony','brown','2001-05-01',NULL,'tbrow0001','tonypass','tony@brown.com',23,'m','44431242');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visits`
--

DROP TABLE IF EXISTS `visits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visits` (
  `VisitID` int NOT NULL AUTO_INCREMENT,
  `AppointmentID` int DEFAULT NULL,
  `Details` varchar(500) DEFAULT NULL,
  `Prescriptions` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`VisitID`),
  KEY `AppointmentID` (`AppointmentID`),
  CONSTRAINT `visits_ibfk_1` FOREIGN KEY (`AppointmentID`) REFERENCES `appointments` (`AppointmentID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visits`
--

LOCK TABLES `visits` WRITE;
/*!40000 ALTER TABLE `visits` DISABLE KEYS */;
INSERT INTO `visits` VALUES (1,1,'Patient reported mild fever and sore throat. Throat swab taken for culture.','Prescribed antibiotics and throat lozenges.'),(2,2,'Patient presented with abdominal pain and nausea. Suspected case of food poisoning.','Prescribed antiemetic medication and advised to stay hydrated.'),(3,3,'Patient visited for a regular diabetes checkup. Blood glucose levels within target range.','No changes in medication. Advised on dietary modifications.'),(4,4,'Patient complained of joint pain and stiffness, especially in the knees. X-rays ordered for further evaluation.','Prescribed nonsteroidal anti-inflammatory drugs (NSAIDs) and referred to a rheumatologist.'),(5,5,'Patient came in for a follow-up after recent surgery. Wound healing progressing well.','Prescribed pain medication and advised on wound care regimen.'),(14,1,'2','2'),(15,4,'Patient had eye pain','Glasses');
/*!40000 ALTER TABLE `visits` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-05 16:07:21
