-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 16, 2020 alle 13:09
-- Versione del server: 10.1.37-MariaDB
-- Versione PHP: 7.2.12
SET
  SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

SET
  AUTOCOMMIT = 0;

START TRANSACTION;

SET
  time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */
;

/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */
;

/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */
;

/*!40101 SET NAMES utf8mb4 */
;

-- FIXME THIS FILE DUMP DOES NOT CREATE THE DATABASE

--
-- Database: `stalker-db`
--
-- --------------------------------------------------------
--
-- Struttura della tabella `admintype`
--
CREATE TABLE `admintype` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `connections`
--
CREATE TABLE `connections` (
  `organization_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `ldapconfiguration`
--
CREATE TABLE `ldapconfiguration` (
  `id` int(11) NOT NULL,
  `host` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` char(64) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `organizationrole`
--
CREATE TABLE `organizationrole` (
  `organization_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `admin_type` int(11) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `organizations`
--
CREATE TABLE `organizations` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` tinytext,
  `ldap_conf` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date` timestamp NULL DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `placedata`
--
CREATE TABLE `placedata` (
  `id` int(11) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `zipcode` varchar(10) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `place`
--
CREATE TABLE `place` (
  `id` int(11) NOT NULL,
  `organization_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `position` linestring DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `userdata`
--
CREATE TABLE `userdata` (
  `user_id` int(11) NOT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date` timestamp NULL DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `userlog`
--
CREATE TABLE `userlog` (
  `user_id` int(11) NOT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `users`
--
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` char(64) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

--
-- Indici per le tabelle scaricate
--
--
-- Indici per le tabelle `admintype`
--
ALTER TABLE
  `admintype`
ADD
  PRIMARY KEY (`id`);

--
-- Indici per le tabelle `connections`
--
ALTER TABLE
  `connections`
ADD
  KEY `organization_id` (`organization_id`),
ADD
  KEY `user_id` (`user_id`);

--
-- Indici per le tabelle `ldapconfiguration`
--
ALTER TABLE
  `ldapconfiguration`
ADD
  PRIMARY KEY (`id`);

--
-- Indici per le tabelle `organizationrole`
--
ALTER TABLE
  `organizationrole`
ADD
  KEY `organization_id` (`organization_id`),
ADD
  KEY `user_id` (`user_id`),
ADD
  KEY `admin_type` (`admin_type`);

--
-- Indici per le tabelle `organizations`
--
ALTER TABLE
  `organizations`
ADD
  PRIMARY KEY (`id`),
ADD
  KEY `ldap_conf` (`ldap_conf`);

--
-- Indici per le tabelle `placedata`
--
ALTER TABLE
  `placedata`
ADD
  KEY `id` (`id`);

--
-- Indici per le tabelle `place`
--
ALTER TABLE
  `place`
ADD
  PRIMARY KEY (`id`),
ADD
  KEY `organization_id` (`organization_id`);

--
-- Indici per le tabelle `userdata`
--
ALTER TABLE
  `userdata`
ADD
  KEY `user_id` (`user_id`);

--
-- Indici per le tabelle `userlog`
--
ALTER TABLE
  `userlog`
ADD
  KEY `user_id` (`user_id`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE
  `users`
ADD
  PRIMARY KEY (`id`);

--
-- Limiti per le tabelle scaricate
--
--
-- Limiti per la tabella `connections`
--
ALTER TABLE
  `connections`
ADD
  CONSTRAINT `connections_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
ADD
  CONSTRAINT `connections_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Limiti per la tabella `organizationrole`
--
ALTER TABLE
  `organizationrole`
ADD
  CONSTRAINT `organizationrole_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
ADD
  CONSTRAINT `organizationrole_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
ADD
  CONSTRAINT `organizationrole_ibfk_3` FOREIGN KEY (`admin_type`) REFERENCES `admintype` (`id`);

--
-- Limiti per la tabella `organizations`
--
ALTER TABLE
  `organizations`
ADD
  CONSTRAINT `organizations_ibfk_1` FOREIGN KEY (`ldap_conf`) REFERENCES `ldapconfiguration` (`id`);

--
-- Limiti per la tabella `placedata`
--
ALTER TABLE
  `placedata`
ADD
  CONSTRAINT `placedata_ibfk_1` FOREIGN KEY (`id`) REFERENCES `place` (`id`);

--
-- Limiti per la tabella `place`
--
ALTER TABLE
  `place`
ADD
  CONSTRAINT `place_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`);

--
-- Limiti per la tabella `userdata`
--
ALTER TABLE
  `userdata`
ADD
  CONSTRAINT `userdata_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Limiti per la tabella `userlog`
--
ALTER TABLE
  `userlog`
ADD
  CONSTRAINT `userlog_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */
;

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */
;

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */
;
