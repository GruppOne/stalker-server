-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Apr 16, 2020 alle 15:41
-- Versione del server: 10.1.37-MariaDB
-- Versione PHP: 7.2.12

USE `stalker-rdb`;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stalker-rdb`
--

--
-- Dump dei dati per la tabella `organizations`
--

INSERT INTO `Organization` (`name`, `description`) VALUES
('GruppOne', 'Organizzazione del gruppo 1 per il progetto di Ingegneria del Software'),
('Amici della Torre Archimede', 'Organizzazione di prova, è un gruppo di amici di Informatica ☺️👨🏻‍💻👩🏿‍💻');

--
-- Dump dei dati per la tabella `place`
--
SET @g = 'POLYGON((45.408351 11.886792,45.408164 11.886736,45.408115 11.887098,45.408351 11.886792))';
SET @h = 'POLYGON((45.408194 11.886379,45.408074 11.886312,45.408063 11.88635,45.408021 11.886304,45.408051 11.886208,45.407978 11.88617,45.407938 11.886283,45.407857 11.886275,45.407859 11.886347,45.407935 11.88635,45.407906 11.886535,45.407859 11.886532,45.407846 11.886092,45.407711 11.886106,45.407718 11.886583,45.407703 11.886878,45.408051 11.886988,45.408074 11.88691,45.408106 11.886926,45.40813 11.886819,45.408194 11.886379))';
SET @j = 'POLYGON((45.411561 11.887471, 45.411224 11.887326,45.411111 11.887785,45.411561 11.887471))';
INSERT INTO `Place` (`organizationId`, `name`, `position`) VALUES
(1, 'Aule Luzzati', ST_PolygonFromText(@g)),
(1, 'Complesso Paolotti', ST_PolygonFromText(@h)),
(2, 'Torre Archimede', ST_PolygonFromText(@j));

--
-- Dump dei dati per la tabella `placedata`
--

INSERT INTO `PlaceData` (`id`, `address`, `city`, `zipcode`, `state`) VALUES
(1, 'Via Paolotti', 'Padova', '35131', 'Italia'),
(2, 'Via Paolotti', 'Padova', '35131', 'Italia'),
(3, 'Via Trieste', 'Padova', '35131', 'Italia');

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `User` (`email`, `password`) VALUES
('mariotest01@gmail.com', 'f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352'),
('giorgiotest02@hotmail.it', '95f9d376cada5e12dac91abb345c8d1d53f30fd7469a144796d547ca73db1ceb05aadbd7ba0d4ea36c57e5e5c30098d13140beb93d306b0bf73d25a9a5281cbd'),
('mariatest03@live.it', 'ba191a9e86125cacf7dfe63e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba438d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531'),
('barbaratest04@alice.it', '86cb25a23ac98ac634130b4b76308f42f56ed05fac97c11b19f3e142581bdfbee5813adf7d3408cb318fbac869efe70944aedfd7c8e90df587d19f34d7578525'),
('filippotest05@virgilio.it', '3d6f76dfaa46a99ae8eab36e3da56ec60fbd6a60e8c8ff17d0d15f1395fdfff4b3e178053639bf47d0976bf329e7620178a1e3f28d965112c4cc24cc315de028');

--
-- Dump dei dati per la tabella `userdata`
--

INSERT INTO `UserData` (`userId`, `firstName`, `lastName`, `birthDate`) VALUES
(1, 'Mario', 'Rossi', '1960-04-16'),
(2, 'Giorgio', 'Bianchi', '1983-11-21'),
(3, 'Maria', 'Verdi', '1987-08-14'),
(4, 'Barbara', 'Gialli', '1965-01-15'),
(5, 'Filippo', 'Azzurri', '1999-06-25');

--
-- Dump dei dati per la tabella `Connection`
--

INSERT INTO `Connection` (`organizationId`, `userId`, `createdDate`) VALUES
(2, 4, '2020-02-07 16:31:38'),
(1, 1, '2020-03-17 16:31:38'),
(2, 3, '2020-04-24 14:33:50'),
(2, 1, '2020-02-04 16:35:23');

--
-- Dump dei dati per la tabella `OrganizationRole`
--

INSERT INTO `OrganizationRole` (`organizationId`, `userId`, `administratorType`, `createdDate`) VALUES
(2, 3, 4, '2020-02-07 16:31:38'),
(1, 2, 3, '2020-03-17 16:31:38'),
(2, 4, 2, '2020-04-24 14:33:50'),
(2, 2, 1, '2020-02-04 16:35:23');

COMMIT;
