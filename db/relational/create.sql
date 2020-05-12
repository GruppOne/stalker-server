-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 16, 2020 alle 13:09
-- Versione del server: 10.1.37-MariaDB
-- Versione PHP: 7.2.12
-- FIXME should dump this from mysql!

SET
  SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

SET
  AUTOCOMMIT = 0;

START TRANSACTION;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */
;

/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */
;

/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */
;

/*!40101 SET NAMES utf8mb4 */
;

-- --------------------------------------------------------
--
-- Database: `stalker-rdb`
--
CREATE DATABASE IF NOT EXISTS `stalker-rdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `stalker-rdb`;

-- --------------------------------------------------------
--
-- Struttura della tabella `AdministratorType`
--
CREATE TABLE `AdministratorType` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

--
-- Dump dei dati per la tabella `AdministratorType`
--

INSERT INTO `AdministratorType` (`id`, `name`, `role`) VALUES
(1, 'Admin', 'ROLE_ADMIN'),
(2, 'Manager', 'ROLE_MANAGER'),
(3, 'Owner', 'ROLE_OWNER'),
(4, 'Viewer', 'ROLE_VIEWER');

-- --------------------------------------------------------
--
-- Struttura della tabella `Connection`
--
CREATE TABLE `Connection` (
  `organizationId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `createdDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `LdapConfiguration`
--
CREATE TABLE `LdapConfiguration` (
  `id` int(11) NOT NULL,
  `host` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` char(64) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `OrganizationRole`
--
CREATE TABLE `OrganizationRole` (
  `organizationId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `administratorType` int(11) NOT NULL,
  `createdDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `Organization`
--
CREATE TABLE `Organization` (
  `id` int(11) NOT NULL,
  `name` varchar(75) NOT NULL,
  `description` tinytext NOT NULL,
  `ldapConf` int(11) NULL DEFAULT NULL,
  `createdDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` TIMESTAMP NULL DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `PlaceData`
--
CREATE TABLE `PlaceData` (
  `id` int(11) NOT NULL,
  `address` varchar(150) NOT NULL,
  `city` varchar(75) NOT NULL,
  `zipcode` varchar(10) NOT NULL,
  `state` varchar(50) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `Place`
--
CREATE TABLE `Place` (
  `id` int(11) NOT NULL,
  `organizationId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `position` polygon NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `UserData`
--
CREATE TABLE `UserData` (
  `userId` int(11) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `birthDate` date NOT NULL,
  `createdDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` TIMESTAMP NULL DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `UserLog`
--
CREATE TABLE `UserLog` (
  `userId` int(11) NOT NULL,
  `ip` varchar(50) NOT NULL,
  `createdDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------
--
-- Struttura della tabella `User`
--
CREATE TABLE `User` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` char(128) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

--
-- Indici per le tabelle scaricate
--
--
-- Indici per le tabelle `AdministratorType`
--
ALTER TABLE
  `AdministratorType`
ADD
  PRIMARY KEY (`id`);

--
-- Indici per le tabelle `Connection`
--
ALTER TABLE
  `Connection`
ADD
  KEY `organizationId` (`organizationId`),
ADD
  KEY `userId` (`userId`);

--
-- Indici per le tabelle `LdapConfiguration`
--
ALTER TABLE
  `LdapConfiguration`
ADD
  PRIMARY KEY (`id`);

--
-- Indici per le tabelle `OrganizationRole`
--
ALTER TABLE
  `OrganizationRole`
ADD
  KEY `organizationId` (`organizationId`),
ADD
  KEY `userId` (`userId`),
ADD
  KEY `administratorType` (`administratorType`);

--
-- Indici per le tabelle `Organization`
--
ALTER TABLE
  `Organization`
ADD
  PRIMARY KEY (`id`),
ADD
  KEY `ldapConf` (`ldapConf`);

--
-- Indici per le tabelle `PlaceData`
--
ALTER TABLE
  `PlaceData`
ADD
  KEY `id` (`id`);

--
-- Indici per le tabelle `Place`
--
ALTER TABLE
  `Place`
ADD
  PRIMARY KEY (`id`),
ADD
  KEY `organizationId` (`organizationId`);

--
-- Indici per le tabelle `UserData`
--
ALTER TABLE
  `UserData`
ADD
  KEY `userId` (`userId`);

--
-- Indici per le tabelle `UserLog`
--
ALTER TABLE
  `UserLog`
ADD
  KEY `userId` (`userId`);

--
-- Indici per le tabelle `User`
--
ALTER TABLE
  `User`
ADD
  PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--
--
-- AUTO_INCREMENT per la tabella `AdministratorType`
--
ALTER TABLE
  `AdministratorType`
MODIFY
  `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `LdapConfiguration`
--
ALTER TABLE
  `LdapConfiguration`
MODIFY
  `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Organization`
--
ALTER TABLE
  `Organization`
MODIFY
  `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `Place`
--
ALTER TABLE
  `Place`
MODIFY
  `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `User`
--
ALTER TABLE
  `User`
MODIFY
  `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Limiti per le tabelle scaricate
--
--
-- Limiti per la tabella `Connection`
--
ALTER TABLE
  `Connection`
ADD
  CONSTRAINT `Connection_ibfk_1` FOREIGN KEY (`organizationId`) REFERENCES `Organization` (`id`),
ADD
  CONSTRAINT `Connection_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

--
-- Limiti per la tabella `OrganizationRole`
--
ALTER TABLE
  `OrganizationRole`
ADD
  CONSTRAINT `OrganizationRole_ibfk_1` FOREIGN KEY (`organizationId`) REFERENCES `Organization` (`id`),
ADD
  CONSTRAINT `OrganizationRole_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `User` (`id`),
ADD
  CONSTRAINT `OrganizationRole_ibfk_3` FOREIGN KEY (`administratorType`) REFERENCES `AdministratorType` (`id`);

--
-- Limiti per la tabella `Organization`
--
ALTER TABLE
  `Organization`
ADD
  CONSTRAINT `Organization_ibfk_1` FOREIGN KEY (`ldapConf`) REFERENCES `LdapConfiguration` (`id`);

--
-- Limiti per la tabella `PlaceData`
--
ALTER TABLE
  `PlaceData`
ADD
  CONSTRAINT `PlaceData_ibfk_1` FOREIGN KEY (`id`) REFERENCES `Place` (`id`);

--
-- Limiti per la tabella `Place`
--
ALTER TABLE
  `Place`
ADD
  CONSTRAINT `Place_ibfk_1` FOREIGN KEY (`organizationId`) REFERENCES `Organization` (`id`);

--
-- Limiti per la tabella `UserData`
--
ALTER TABLE
  `UserData`
ADD
  CONSTRAINT `UserData_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

--
-- Limiti per la tabella `UserLog`
--
ALTER TABLE
  `UserLog`
ADD
  CONSTRAINT `UserLog_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */
;

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */
;

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */
;
