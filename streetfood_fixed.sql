-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: streetfood_db
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `admin_credentials`
--

DROP TABLE IF EXISTS `admin_credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_credentials` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_credentials`
--

LOCK TABLES `admin_credentials` WRITE;
/*!40000 ALTER TABLE `admin_credentials` DISABLE KEYS */;
INSERT INTO `admin_credentials` VALUES (1,'nagarajdhanush88@gmail.com','dhanush','Dhanush Admin','2026-03-17 07:06:28');
/*!40000 ALTER TABLE `admin_credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorites` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `vendor_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_fav` (`customer_id`,`vendor_id`),
  KEY `vendor_id` (`vendor_id`),
  CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
INSERT INTO `favorites` VALUES (2,11,5,'2026-03-12 10:47:50'),(3,13,3,'2026-03-13 14:51:17'),(4,17,1,'2026-03-13 15:32:32'),(5,34,1,'2026-03-17 04:55:02'),(6,34,2,'2026-03-17 04:55:56'),(7,34,3,'2026-03-17 04:55:59'),(8,34,4,'2026-03-17 04:56:01'),(9,32,2,'2026-03-22 02:04:58'),(10,32,1,'2026-03-22 02:15:15'),(11,32,3,'2026-03-22 02:15:24'),(12,41,1,'2026-03-27 00:24:52');
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_items`
--

DROP TABLE IF EXISTS `menu_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `vendor_id` bigint NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `vendor_id` (`vendor_id`),
  CONSTRAINT `menu_items_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_items`
--

LOCK TABLES `menu_items` WRITE;
/*!40000 ALTER TABLE `menu_items` DISABLE KEYS */;
INSERT INTO `menu_items` VALUES (1,1,'Idli (2 pcs)',20,'Soft steamed rice cakes with sambar','Breakfast',1),(2,1,'Masala Dosa',45,'Crispy dosa with potato masala','Breakfast',1),(3,1,'Medu Vada',25,'Crispy lentil donuts with sambar','Breakfast',1),(4,1,'Mini Tiffin',60,'Idli + Vada + Dosa combo','Breakfast',1),(5,1,'Pongal',35,'Warm rice and lentil with ghee','Breakfast',1),(6,2,'Chicken Biryani',120,'Aromatic basmati rice with chicken','Biryani',1),(7,2,'Mutton Biryani',160,'Special Sunday mutton biryani','Biryani',0),(8,2,'Veg Biryani',80,'Fragrant rice with vegetables','Biryani',1),(9,2,'Raita',20,'Cool yogurt with cucumber','Sides',1),(10,3,'Parota (2 pcs)',30,'Flaky layered flatbread','Parota',1),(11,3,'Egg Parota',50,'Parota with egg scramble','Parota',1),(12,3,'Chicken Salna',80,'Spicy chicken curry for parota','Curry',1),(13,3,'Kothu Parota',70,'Shredded parota with eggs and spices','Parota',1),(14,4,'Sugarcane Juice',30,'Fresh pressed sugarcane with ginger','Juice',1),(15,4,'Mango Shake',60,'Fresh mango milkshake','Shakes',1),(16,4,'Coconut Water',25,'Fresh tender coconut water','Natural',1),(17,4,'Mixed Fruit Juice',50,'Seasonal mixed fruit juice','Juice',1),(18,5,'Bajji (5 pcs)',30,'Crispy batter fried vegetables','Snacks',1),(19,5,'Bonda (4 pcs)',25,'Spicy potato balls in batter','Snacks',1),(20,5,'Sundal',20,'Boiled chickpeas with coconut','Healthy',1),(21,5,'Murukku',30,'Crunchy spiral rice flour snack','Snacks',1),(22,6,'idli',20,'','breakfast',1),(24,14,'tea',10,'','',0),(25,14,'coffee',15,'','',1),(26,15,'biriyani',150,'','non veg',1),(27,18,'chicken chilli ',50,'all chillis are available and affordable price ','veg and non veg',1);
/*!40000 ALTER TABLE `menu_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `vendor_id` bigint NOT NULL,
  `customer_id` bigint NOT NULL,
  `rating` int DEFAULT NULL,
  `comment` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `approved` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_review` (`vendor_id`,`customer_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reviews_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,1,7,5,'Best idli in Erode! So soft and fresh!','2026-03-12 06:12:56',_binary ''),(2,2,7,4,'Biryani is really good. Slightly spicy but delicious!','2026-03-12 06:12:56',_binary ''),(3,3,7,5,'Late night parota cravings solved! Crispy and hot.','2026-03-12 06:12:56',_binary ''),(4,4,7,5,'Freshest juice ever. No sugar added, pure natural!','2026-03-12 06:12:56',_binary ''),(5,5,7,4,'Good evening snacks. Bajji was crispy and hot.','2026-03-12 06:12:56',_binary ''),(6,1,9,5,'idli is very nice','2026-03-12 03:56:44',_binary ''),(7,1,15,3,'sambar was not good','2026-03-13 09:50:01',_binary ''),(8,1,18,5,'idli nice to eat','2026-03-13 10:48:14',_binary ''),(9,1,34,5,'nice to eat','2026-03-17 03:29:57',_binary ''),(10,15,32,4,'all foods are very nice ','2026-03-22 02:04:08',_binary ''),(11,1,38,5,'nice\n','2026-03-23 03:58:50',_binary '');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Admin User','admin@streetfood.com','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','9999999999','ADMIN','2026-03-12 06:12:56'),(2,'Murugan Selvan','murugan@vendor.com','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','9876543210','VENDOR','2026-03-12 06:12:56'),(3,'Raja Kumar','raja@vendor.com','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','9876543211','VENDOR','2026-03-12 06:12:56'),(4,'Senthil Babu','senthil@vendor.com','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','9876543212','VENDOR','2026-03-12 06:12:56'),(5,'Lakshmi Devi','lakshmi@vendor.com','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','9876543213','VENDOR','2026-03-12 06:12:56'),(6,'Kumar Raj','kumar@vendor.com','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','9876543214','VENDOR','2026-03-12 06:12:56'),(7,'Test Customer','customer@test.com','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','9123456789','CUSTOMER','2026-03-12 06:12:56'),(8,'anbumani','an@gmail.com','$2a$10$KmSI.efVUit87XGV0YdHbe1CgaVwraqhvJdgyh8K.4y9lw.BGRbx2','7894563210','VENDOR','2026-03-12 09:24:57'),(9,'priya','pri@gmail.com','$2a$10$SeLrDxXxvBsPcog8kSvFce6zvir/Wz0dtql.L.qvI29ND3NTE/AYi','7894563214','CUSTOMER','2026-03-12 09:26:19'),(10,'dhanush','nagarajdhanush88@gmail.com','$2a$10$neXJhHOrUGL9.1hjOQP6JeC0p5y/IRLx16AMnIPb7MUMGOZcvlL9q','6374832278','VENDOR','2026-03-12 09:48:31'),(11,'ram','ram@gmail.com','$2a$10$T885GbdvTNFXPtW5f2cnE.gsuA8DaPBG8B0JlfNM6VPgigo/qlmC2',NULL,'CUSTOMER','2026-03-12 10:44:34'),(12,'ravi','ravi@gmail.com','$2a$10$8AIbVGuFUh9ijbBeFnlVrOTQz4D7PpzrPdTVg6RwNudIAbgjwheze',NULL,'CUSTOMER','2026-03-13 14:49:07'),(13,'raviram','ravi2@gmail.com','$2a$10$WhA2zrYqEMLnTngc7xKS/uu/TKm4aJrfLGUwXv/.4ktZlA5y7ks1.',NULL,'CUSTOMER','2026-03-13 14:50:57'),(14,'selvi','selvi@gmail.com','$2a$10$WVxWnB8Og.RaquE8JOnlT.i4jxv9jFPNNDLXBrT4vkgfb0caflOiq','6374830092','VENDOR','2026-03-13 14:57:45'),(15,'dhanu','dhanu@gmail.com','$2a$10$FFs1amaYd6.Q9RrSnMp7TuXu8JPij1QYziKOTweHM2Ea1flIZnYS.',NULL,'CUSTOMER','2026-03-13 14:58:48'),(16,'muni','mun@gmail.com','$2a$10$94JcQK77J9/woGN6BZn9QO7k0k59TUtJIUyeBghhAahxO/16j6GGS',NULL,'CUSTOMER','2026-03-13 15:21:53'),(17,'navin','navi@gmail.com','$2a$10$1h4awzYsKFigfE.a7HTqxeO6q76gpQ83u8Mlh754Z139L8z0E64Ju',NULL,'CUSTOMER','2026-03-13 15:29:39'),(18,'ram ','ram65@gmail.com','$2a$10$kihUCBHeOpjWosn7pCcRDu0Is7SjdAOn.f3KwJqxWru7.Mi1aPmPy',NULL,'CUSTOMER','2026-03-13 15:59:30'),(19,'ravi','ravi123@gmail.com','$2a$10$DlNSWnQboS/LmOkF1lRveeNqOw0R/GDDPxYI65sIzLZMuhhqQszDu',NULL,'CUSTOMER','2026-03-14 05:53:44'),(20,'navi','navi12@gmail.com','$2a$10$pGeP.64y1JYvLYNIhSe3SuZT3XxShPOJE/2rRlXn3ivrsS5QO1t6.',NULL,'CUSTOMER','2026-03-14 05:54:46'),(21,'mega','mega@gmail.com','$2a$10$VnQeQi5HM1FX.eZhVZJc2OxAWLb9YFFq1bSCS6BbB8VO/AQTz5CS6','70100056478','CUSTOMER','2026-03-14 06:02:51'),(22,'mega','mega123@gmail.com','$2a$10$JBbRJ2HhC6ryCu/R1PlHue/PCYa8YyNUgG/PXVRX3.l4qeLYYQ16e','70100056478','CUSTOMER','2026-03-14 06:15:23'),(23,'mini','muni8@gmail.com','$2a$10$rCFFzVbWv9RILINXBn7SxugUgYDRlV5Vu3cbcO.rG7IBeu7YqVfWm','7894561230','CUSTOMER','2026-03-14 06:23:06'),(24,'vasa','vasa@gmail.com','$2a$10$bdwD2hvPbAMYSLV3Qh9.geHZHcmcnmMiNVMjnoYS.qH.UY0.SQYFK','7894561230','CUSTOMER','2026-03-14 06:28:51'),(25,'vasa','vasan@gmail.com','$2a$10$1Z6lcjbADnezBpw8c99vqOt5C52PIJ.IQZc.jtrLlSeuxa.P.HM9e','7894561230','CUSTOMER','2026-03-14 06:35:54'),(26,'mega','mega0123@gmail.com','$2a$10$FLfFpeWHLSAfLIDme/8eb.D4Un.snC6gNm1lO0A7Se/meVZx9zL9m','70100056478','VENDOR','2026-03-14 06:54:06'),(27,'dhars','dhars@gmail.com','$2a$10$ddFMrckwCXwNhkjBSrF.iehpG3oo6ffMelXPZgavhmYjzETq4nM7m','7894561230','VENDOR','2026-03-14 07:05:03'),(28,'dana','dan@gmail.com','$2a$10$ZWBUuhDXEdUP17YiHUKP3Ogqyik9cjM7sZAcnm0svo9lxA.KAC802','321456789','VENDOR','2026-03-14 07:30:22'),(29,'naga','nag@gmail.com','$2a$10$nWUS.jDnxD/u6R3mL.YIueIe6y0gERvuTgvt1fLY2JtIqy5eSgDO.','7894561230','VENDOR','2026-03-14 07:44:46'),(30,'suba','suba@gmail.com','$2a$10$vIqdVgLaurEBrFWhAF4n5OxixiP9dWcobbFGYpaG9KhzVv5xMO2u.','7894561230','VENDOR','2026-03-16 14:02:18'),(31,'raffen','raffen@gmail.com','$2a$10$a7.LzIZTNzhbJTJH2A5bqebuTECrWIHYYmyKRuQd7y.ObFsK.PyJy',NULL,'CUSTOMER','2026-03-16 14:02:51'),(32,'raffen','raffen123@gmail.com','$2a$10$xXp1/xs.akIRsWcXwURJfec1FzBHnowWAPKEaNhhYPmMR5o7XTZxe',NULL,'CUSTOMER','2026-03-17 06:48:18'),(33,'vini','vini@gmail.com','$2a$10$3LrYXqugwC7OM1dd2Ww7Mug3ZaJ0GU9OFMuyhiLdtmScQZABclmIS','7894561230','VENDOR','2026-03-17 06:49:47'),(34,'priya','priya123@gmail.com','$2a$10$631XdgnVIEJtOji//GmNF.rbsEuszEF/sEkBymiX0P0m22M4Nuvnu',NULL,'CUSTOMER','2026-03-17 03:28:35'),(35,'priya1','priya23@gmail.com','$2a$10$9Otxyhrv9nN0kisy.tf3v.BwekfomFtQoEOnSATEBBLPZj9KyZLee','7894561230','VENDOR','2026-03-17 03:31:33'),(36,'nithya','nithya123@gnail.com','$2a$10$4devvJfePHqPvKWNdKGz7u2mQS6eOYgM8qfLYGDAYPe5fv0G55Zem',NULL,'CUSTOMER','2026-03-23 03:25:05'),(37,'nithya','nithya123@gmail.com','$2a$10$5LCyHw0kYJNcb6Ty42mIjOtpMn4xtCykDAqkZWwvXLiiOTs0QAW/S','1234567890','VENDOR','2026-03-23 03:28:16'),(38,'ramkumar','ramu@gmail.com','$2a$10$beWIK0z2E4Zs.7WFaFaPrezPXBZbWFVNbHapnuM0DOXNxAprTTOk.',NULL,'CUSTOMER','2026-03-23 03:51:50'),(39,'sri hari','sri@gmail.com','$2a$10$YWvJu3/lRB.mRZ0Ii2ZpOups/bedj2j44zo0Him/JMPBOrHM66Jda','7894561230','VENDOR','2026-03-24 22:15:27'),(40,'annachi','anna@gmail.com','$2a$10$XAywsKJdpKU4rXEe63/ctugFbo4AyJOS/I0qF/AkCgO732FXhK3e2','7894561230','VENDOR','2026-03-24 23:17:55'),(41,'dhanush','dhanush@gmail.com','$2a$10$ez4mNBJxNd8fT7/DQumY8evrW2dDCitZHn0BbIzV6X9YP/yBwmyAC',NULL,'CUSTOMER','2026-03-27 00:23:51');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendors`
--

DROP TABLE IF EXISTS `vendors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `shop_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `category` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `photo_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `opening_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `closing_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_live` tinyint(1) DEFAULT '0',
  `avg_rating` double DEFAULT '0',
  `total_reviews` int DEFAULT '0',
  `price_range` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `owner_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `owner_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `owner_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `vendors_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendors`
--

LOCK TABLES `vendors` WRITE;
/*!40000 ALTER TABLE `vendors` DISABLE KEYS */;
INSERT INTO `vendors` VALUES (1,2,'Murugan Idli Shop','Best soft idlis in Erode since 1995.','Idli,Dosa,Vada,Pongal','Bus Stand Road, Erode','Erode',11.341,77.727,'https://images.unsplash.com/photo-1630383249896-424e482df921?w=600','06:00:00','11:00:00',1,4.7,6,'BUDGET','APPROVED','2026-03-12 06:12:56',NULL,NULL,NULL),(2,3,'Raja Biryani Center','Authentic Thalappakatti style biryani.','Biryani,Parcel','Perundurai Road, Erode','Erode',11.338,77.732,'https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=600','12:00:00','21:00:00',0,4,1,'MEDIUM','APPROVED','2026-03-12 06:12:56',NULL,NULL,NULL),(3,4,'Senthil Parota Kadai','Crispy flaky parotas with salna.','Parota,Salna,Egg Curry','Chithode Road, Erode','Erode',11.345,77.721,'https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=600','18:00:00','02:00:00',1,5,1,'BUDGET','APPROVED','2026-03-12 06:12:56',NULL,NULL,NULL),(4,5,'Lakshmi Fresh Juice','Fresh fruit juices. No artificial colors.','Juice,Shakes,Coconut Water','Gandhiji Road, Erode','Erode',11.342,77.729,'https://images.unsplash.com/photo-1546173159-315724a31696?w=600','08:00:00','20:00:00',1,5,1,'BUDGET','APPROVED','2026-03-12 06:12:56',NULL,NULL,NULL),(5,6,'Kumar Special Snacks','Evening snacks - Bajji, Bonda. All homemade.','Bajji,Bonda,Snacks,Sundal','EVN Road, Erode','Erode',11.34,77.735,'https://images.unsplash.com/photo-1601050690597-df0568f70950?w=600','16:00:00','21:00:00',0,4,1,'BUDGET','APPROVED','2026-03-12 06:12:56',NULL,NULL,NULL),(6,8,'anbumani Shop',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,'BUDGET','APPROVED','2026-03-12 09:24:57','anbumani','7894563210',NULL),(7,10,'dhanush Shop',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,'BUDGET','APPROVED','2026-03-12 09:48:31','dhanush','6374832278',NULL),(8,14,'selvi Shop',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,'BUDGET','APPROVED','2026-03-13 14:57:45','selvi','6374830092',NULL),(9,26,'mega juice',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,NULL,'APPROVED','2026-03-14 06:54:06','mega','70100056478','mega0123@gmail.com'),(10,27,'dhars biriyai',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,NULL,'APPROVED','2026-03-14 07:05:03','dhars','7894561230','dhars@gmail.com'),(11,28,'dana idli shop',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,NULL,'APPROVED','2026-03-14 07:30:22','dana','321456789','dan@gmail.com'),(12,29,'naga biriyani',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,NULL,'APPROVED','2026-03-14 07:44:46','naga','7894561230','nag@gmail.com'),(13,30,'suba tea stall',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,NULL,'APPROVED','2026-03-16 14:02:18','suba','7894561230','suba@gmail.com'),(14,33,'vini bakery',NULL,NULL,NULL,NULL,NULL,NULL,'http://localhost:8080/uploads/vendors/82756542-86e9-4ae3-aa78-f42daa7f14ef.png',NULL,NULL,1,0,0,NULL,'APPROVED','2026-03-17 06:49:47','vini','7894561230','vini@gmail.com'),(15,35,'priya biriyani shop','all foods are best quality and affordable price','veg','near new bus stand','bhavani',NULL,NULL,'http://localhost:8080/uploads/vendors/dfff0a0b-0eb2-4ad8-8168-324b83f07f65.png||http://localhost:8080/uploads/vendors/5db81faf-f888-4a58-88f7-f425f44fab98.png','08:01','20:02',1,4,1,NULL,'APPROVED','2026-03-17 03:31:33','priya1','7894561230','priya23@gmail.com'),(16,37,'nithya cafe',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,NULL,'APPROVED','2026-03-23 03:28:16','nithya','1234567890','nithya123@gmail.com'),(18,40,'annachi chilli kadai',NULL,NULL,'near sathi bus stand','sathi',NULL,NULL,'http://localhost:8080/uploads/vendors/178eff85-6e04-4378-bbd4-92f5454ee480.png','10:18','22:18',1,0,0,'MEDIUM','APPROVED','2026-03-24 23:17:55','annachi','7894561230','anna@gmail.com');
/*!40000 ALTER TABLE `vendors` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-27 13:52:24
