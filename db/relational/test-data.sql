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

INSERT INTO `Organization`(`name`, `description`, `createdDate`)
VALUES(
    'GruppOne',
    'Organizzazione del gruppo 1 per il progetto di Ingegneria del Software',
    '2020-01-01 01:01:01'
),(
    'Amici della Torre Archimede',
    'Organizzazione di prova, è un gruppo di amici di Informatica 🙊😡❤️',
    '2020-01-01 01:01:01'
);

--
-- Dump dei dati per la tabella `place`
--
INSERT INTO `Place` (`organizationId`, `name`, `color`, `maxConcurrentUsers`, `address`, `city`, `zipcode`, `state`)
VALUES(
    1,
    'Aule Luzzatti',
    '#e64a19',
    100,
    'Via Paolotti',
    'Padova',
    '35131',
    'Italia'
),(
    1,
    'Complesso Paolotti',
    '#283593',
    100,
    'Via Paolotti',
    'Padova',
    '35131',
    'Italia'
),(
    2,
    'Torre Archimede',
    '#c8e6c9',
    100,
    'Via Trieste',
    'Padova',
    '35131',
    'Italia'
);

--
-- Dump dei dati per la tabella `placeposition`
--
SET @place_one = 'POLYGON((45.408351 11.886792,45.408164 11.886736,45.408115 11.887098,45.408351 11.886792))';
SET @place_two = 'POLYGON((45.408194 11.886379,45.408074 11.886312,45.408063 11.88635,45.408021 11.886304,45.408051 11.886208,45.407978 11.88617,45.407938 11.886283,45.407857 11.886275,45.407859 11.886347,45.407935 11.88635,45.407906 11.886535,45.407859 11.886532,45.407846 11.886092,45.407711 11.886106,45.407718 11.886583,45.407703 11.886878,45.408051 11.886988,45.408074 11.88691,45.408106 11.886926,45.40813 11.886819,45.408194 11.886379))';
SET @place_three = 'POLYGON((45.411561 11.887471, 45.411224 11.887326,45.411111 11.887785,45.411561 11.887471))';

INSERT INTO `PlacePosition` (`id`, `position`) VALUES
(1, ST_PolygonFromText(@place_one)),
(2, ST_PolygonFromText(@place_two)),
(3, ST_PolygonFromText(@place_three));

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `User` (`email`, `password`) VALUES
('mariotest01@gmail.com', SHA2('Mario01!',512)),
('giorgiotest02@hotmail.it', SHA2('Giorgio02!',512)),
('mariatest03@live.it', SHA2('Maria03!',512)),
('barbaratest04@alice.it', SHA2('Barbara04!',512)),
('filippotest05@virgilio.it', SHA2('Filippo05!',512));

--
-- Dump dei dati per la tabella `userdata`
--

INSERT INTO `UserData` (`userId`, `firstName`, `lastName`, `birthDate`, `createdDate`) VALUES
(1, 'Mario', 'Rossi', '1960-04-16', '2020-01-01 01:01:01'),
(2, 'Giorgio', 'Bianchi', '1983-11-21', '2020-01-01 01:01:01'),
(3, 'Maria', 'Verdi', '1987-08-14', '2020-01-01 01:01:01'),
(4, 'Barbara', 'Gialli', '1965-01-15', '2020-01-01 01:01:01'),
(5, 'Filippo', 'Azzurri', '1999-06-25', '2020-01-01 01:01:01');

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
