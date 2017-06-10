-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema android2k17
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema android2k17
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `android2k17` DEFAULT CHARACTER SET utf8 ;
USE `android2k17` ;

-- -----------------------------------------------------
-- Table `android2k17`.`BAR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`BAR` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`BAR` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NOT NULL,
  `address` VARCHAR(1023) NOT NULL,
  `latitude` DECIMAL(10,8) NOT NULL,
  `longitude` DECIMAL(11,8) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`CUSTOMER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`CUSTOMER` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`CUSTOMER` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NULL,
  `year_of_birth` INT UNSIGNED NOT NULL,
  `email` VARCHAR(1024) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`EVENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`EVENT` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`EVENT` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `BAR_ID` INT UNSIGNED NOT NULL,
  `title` VARCHAR(511) NOT NULL,
  `description` TEXT NULL,
  `start_datetime` DATETIME NOT NULL,
  `end_datetime` DATETIME NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_EVENT_BAR1_idx` (`BAR_ID` ASC),
  CONSTRAINT `fk_EVENT_BAR1`
    FOREIGN KEY (`BAR_ID`)
    REFERENCES `android2k17`.`BAR` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`ITEM_SIZE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`ITEM_SIZE` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`ITEM_SIZE` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `size_description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`ITEM_CATEGORY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`ITEM_CATEGORY` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`ITEM_CATEGORY` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`GLOBAL_MENU_ITEM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`GLOBAL_MENU_ITEM` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`GLOBAL_MENU_ITEM` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`MENU_ITEM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`MENU_ITEM` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`MENU_ITEM` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `BAR_ID` INT UNSIGNED NOT NULL,
  `ITEM_CATEGORY_ID` INT UNSIGNED NOT NULL,
  `GLOBAL_MENU_ITEM_ID` INT UNSIGNED NULL,
  `menu_item_name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_MENU_ITEM_BAR1_idx` (`BAR_ID` ASC),
  INDEX `fk_MENU_ITEM_ITEM_CATEGORY1_idx` (`ITEM_CATEGORY_ID` ASC),
  INDEX `fk_MENU_ITEM_GLOBAL_MENU_ITEM1_idx` (`GLOBAL_MENU_ITEM_ID` ASC),
  CONSTRAINT `fk_MENU_ITEM_BAR1`
    FOREIGN KEY (`BAR_ID`)
    REFERENCES `android2k17`.`BAR` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MENU_ITEM_ITEM_CATEGORY1`
    FOREIGN KEY (`ITEM_CATEGORY_ID`)
    REFERENCES `android2k17`.`ITEM_CATEGORY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MENU_ITEM_GLOBAL_MENU_ITEM1`
    FOREIGN KEY (`GLOBAL_MENU_ITEM_ID`)
    REFERENCES `android2k17`.`GLOBAL_MENU_ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`INGREDIENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`INGREDIENT` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`INGREDIENT` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ingredient_name` VARCHAR(256) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`ITEM_ADDITION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`ITEM_ADDITION` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`ITEM_ADDITION` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `addition_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `name_UNIQUE` (`addition_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`MEASUREMENT_UNIT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`MEASUREMENT_UNIT` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`MEASUREMENT_UNIT` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`DELIVERY_PLACE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`DELIVERY_PLACE` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`DELIVERY_PLACE` (
  `ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`BAR_HAS_DELIVERY_PLACE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`BAR_HAS_DELIVERY_PLACE` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`BAR_HAS_DELIVERY_PLACE` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `floor_number` INT UNSIGNED NOT NULL,
  `BAR_ID` INT UNSIGNED NOT NULL,
  `DELIVERY_PLACE_ID` INT UNSIGNED NOT NULL,
  INDEX `fk_BAR_HAS_DELIVERY_PLACE_BAR1_idx` (`BAR_ID` ASC),
  PRIMARY KEY (`ID`),
  INDEX `fk_BAR_HAS_DELIVERY_PLACE_DELIVERY_PLACE1_idx` (`DELIVERY_PLACE_ID` ASC),
  CONSTRAINT `fk_BAR_HAS_DELIVERY_PLACE_BAR1`
    FOREIGN KEY (`BAR_ID`)
    REFERENCES `android2k17`.`BAR` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_BAR_HAS_DELIVERY_PLACE_DELIVERY_PLACE1`
    FOREIGN KEY (`DELIVERY_PLACE_ID`)
    REFERENCES `android2k17`.`DELIVERY_PLACE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`CUSTOMER_ORDER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`CUSTOMER_ORDER` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`CUSTOMER_ORDER` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` INT UNSIGNED NOT NULL,
  `paid` TINYINT(1) NOT NULL DEFAULT 0,
  `process_status` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `BAR_HAS_DELIVERY_PLACE_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `BAR_HAS_DELIVERY_PLACE_ID`),
  INDEX `fk_CUSTOMER_ORDER_CUSTOMER1_idx` (`CUSTOMER_ID` ASC),
  INDEX `fk_CUSTOMER_ORDER_BAR_HAS_DELIVERY_PLACE1_idx` (`BAR_HAS_DELIVERY_PLACE_ID` ASC),
  CONSTRAINT `fk_CUSTOMER_ORDER_CUSTOMER1`
    FOREIGN KEY (`CUSTOMER_ID`)
    REFERENCES `android2k17`.`CUSTOMER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CUSTOMER_ORDER_BAR_HAS_DELIVERY_PLACE1`
    FOREIGN KEY (`BAR_HAS_DELIVERY_PLACE_ID`)
    REFERENCES `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`ORDER_ITEM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`ORDER_ITEM` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`ORDER_ITEM` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `MENU_ITEM_ID` INT UNSIGNED NOT NULL,
  `CUSTOMER_ORDER_ID` INT UNSIGNED NOT NULL,
  `order_item_name` VARCHAR(511) NOT NULL,
  `total_price` FLOAT NOT NULL,
  `rating` INT UNSIGNED NULL,
  `quantity` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_ORDER_ITEM_MENU_ITEM1_idx` (`MENU_ITEM_ID` ASC),
  INDEX `fk_ORDER_ITEM_CUSTOMER_ORDER1_idx` (`CUSTOMER_ORDER_ID` ASC),
  CONSTRAINT `fk_ORDER_ITEM_MENU_ITEM1`
    FOREIGN KEY (`MENU_ITEM_ID`)
    REFERENCES `android2k17`.`MENU_ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDER_ITEM_CUSTOMER_ORDER1`
    FOREIGN KEY (`CUSTOMER_ORDER_ID`)
    REFERENCES `android2k17`.`CUSTOMER_ORDER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`QUEUED_GROUP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`QUEUED_GROUP` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`QUEUED_GROUP` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `on_pause` TINYINT(1) NOT NULL DEFAULT 0,
  `queue_time` DATETIME NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`EVENT_ADD_DISCOUNT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`EVENT_ADD_DISCOUNT` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`EVENT_ADD_DISCOUNT` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ITEM_OF_SIZE_ID` INT UNSIGNED NOT NULL,
  `EVENT_ID` INT UNSIGNED NOT NULL,
  `discount` FLOAT NOT NULL,
  PRIMARY KEY (`ID`, `ITEM_OF_SIZE_ID`, `EVENT_ID`),
  INDEX `fk_EVENT_ADD_DISCOUNT_EVENT1_idx` (`EVENT_ID` ASC),
  CONSTRAINT `fk_EVENT_ADD_DISCOUNT_EVENT1`
    FOREIGN KEY (`EVENT_ID`)
    REFERENCES `android2k17`.`EVENT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`CUSTOMER_PREFER_GLOBAL_MENU_ITEM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`CUSTOMER_PREFER_GLOBAL_MENU_ITEM` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`CUSTOMER_PREFER_GLOBAL_MENU_ITEM` (
  `GLOBAL_MENU_ITEM_ID` INT UNSIGNED NOT NULL,
  `CUSTOMER_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`GLOBAL_MENU_ITEM_ID`, `CUSTOMER_ID`),
  INDEX `fk_CUSTOMER_PREFER_GLOBAL_MENU_ITEM_CUSTOMER1_idx` (`CUSTOMER_ID` ASC),
  CONSTRAINT `fk_CUSTOMER_PREFER_GLOBAL_MENU_ITEM_GLOBAL_MENU_ITEM1`
    FOREIGN KEY (`GLOBAL_MENU_ITEM_ID`)
    REFERENCES `android2k17`.`GLOBAL_MENU_ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CUSTOMER_PREFER_GLOBAL_MENU_ITEM_CUSTOMER1`
    FOREIGN KEY (`CUSTOMER_ID`)
    REFERENCES `android2k17`.`CUSTOMER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`MENU_ITEM_HAS_INGREDIENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`MENU_ITEM_HAS_INGREDIENT` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`MENU_ITEM_HAS_INGREDIENT` (
  `MENU_ITEM_ID` INT UNSIGNED NOT NULL,
  `INGREDIENT_ID` INT UNSIGNED NOT NULL,
  `quantity` VARCHAR(45) NULL,
  PRIMARY KEY (`MENU_ITEM_ID`, `INGREDIENT_ID`),
  INDEX `fk_MENU_ITEM_HAS_INGREDIENT_INGREDIENT1_idx` (`INGREDIENT_ID` ASC),
  CONSTRAINT `fk_MENU_ITEM_HAS_INGREDIENT_MENU_ITEM1`
    FOREIGN KEY (`MENU_ITEM_ID`)
    REFERENCES `android2k17`.`MENU_ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MENU_ITEM_HAS_INGREDIENT_INGREDIENT1`
    FOREIGN KEY (`INGREDIENT_ID`)
    REFERENCES `android2k17`.`INGREDIENT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`MENU_ITEM_HAS_SIZE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`MENU_ITEM_HAS_SIZE` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`MENU_ITEM_HAS_SIZE` (
  `MENU_ITEM_ID` INT UNSIGNED NOT NULL,
  `ITEM_SIZE_ID` INT UNSIGNED NOT NULL,
  `price` FLOAT NOT NULL,
  INDEX `fk_ITEM_OF_SIZE_MENU_ITEM1_idx` (`MENU_ITEM_ID` ASC),
  PRIMARY KEY (`MENU_ITEM_ID`, `ITEM_SIZE_ID`),
  CONSTRAINT `fk_ITEM_OF_SIZE_ITEM_SIZE1`
    FOREIGN KEY (`ITEM_SIZE_ID`)
    REFERENCES `android2k17`.`ITEM_SIZE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ITEM_OF_SIZE_MENU_ITEM1`
    FOREIGN KEY (`MENU_ITEM_ID`)
    REFERENCES `android2k17`.`MENU_ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`ORDER_IN_GROUP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`ORDER_IN_GROUP` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`ORDER_IN_GROUP` (
  `CUSTOMER_ORDER_ID` INT UNSIGNED NOT NULL,
  `QUEUED_GROUP_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`CUSTOMER_ORDER_ID`, `QUEUED_GROUP_ID`),
  INDEX `fk_ORDER_IN_GROUP_QUEUED_GROUP1_idx` (`QUEUED_GROUP_ID` ASC),
  CONSTRAINT `fk_ORDER_IN_GROUP_CUSTOMER_ORDER1`
    FOREIGN KEY (`CUSTOMER_ORDER_ID`)
    REFERENCES `android2k17`.`CUSTOMER_ORDER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDER_IN_GROUP_QUEUED_GROUP1`
    FOREIGN KEY (`QUEUED_GROUP_ID`)
    REFERENCES `android2k17`.`QUEUED_GROUP` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`MENU_ITEM_HAS_ADDITION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`MENU_ITEM_HAS_ADDITION` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`MENU_ITEM_HAS_ADDITION` (
  `MENU_ITEM_ID` INT UNSIGNED NOT NULL,
  `ITEM_ADDITION_ID` INT UNSIGNED NOT NULL,
  `price` FLOAT NOT NULL DEFAULT 0,
  PRIMARY KEY (`MENU_ITEM_ID`, `ITEM_ADDITION_ID`),
  INDEX `fk_ITEM_OF_ADDITION_ITEM_ADDITION1_idx` (`ITEM_ADDITION_ID` ASC),
  CONSTRAINT `fk_ITEM_OF_ADDITION_MENU_ITEM1`
    FOREIGN KEY (`MENU_ITEM_ID`)
    REFERENCES `android2k17`.`MENU_ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ITEM_OF_ADDITION_ITEM_ADDITION1`
    FOREIGN KEY (`ITEM_ADDITION_ID`)
    REFERENCES `android2k17`.`ITEM_ADDITION` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`CUSTOMER_PREFER_MENU_ITEM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`CUSTOMER_PREFER_MENU_ITEM` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`CUSTOMER_PREFER_MENU_ITEM` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `MENU_ITEM_ID` INT UNSIGNED NOT NULL,
  `CUSTOMER_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `MENU_ITEM_ID`, `CUSTOMER_ID`),
  INDEX `fk_CUSTOMER_PREFER_MENU_ITEM_MENU_ITEM1_idx` (`MENU_ITEM_ID` ASC),
  INDEX `fk_CUSTOMER_PREFER_MENU_ITEM_CUSTOMER1_idx` (`CUSTOMER_ID` ASC),
  CONSTRAINT `fk_CUSTOMER_PREFER_MENU_ITEM_MENU_ITEM1`
    FOREIGN KEY (`MENU_ITEM_ID`)
    REFERENCES `android2k17`.`MENU_ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CUSTOMER_PREFER_MENU_ITEM_CUSTOMER1`
    FOREIGN KEY (`CUSTOMER_ID`)
    REFERENCES `android2k17`.`CUSTOMER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`OPENING_HOUR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`OPENING_HOUR` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`OPENING_HOUR` (
  `BAR_ID` INT UNSIGNED NOT NULL,
  `day_of_week` INT NOT NULL,
  `time_open` TIME NOT NULL,
  `working_time` INT NOT NULL,
  PRIMARY KEY (`BAR_ID`, `day_of_week`, `time_open`),
  INDEX `fk_OPENING_HOUR_BAR1_idx` (`BAR_ID` ASC),
  CONSTRAINT `fk_OPENING_HOUR_BAR1`
    FOREIGN KEY (`BAR_ID`)
    REFERENCES `android2k17`.`BAR` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`BAR_TABLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`BAR_TABLE` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`BAR_TABLE` (
  `DELIVERY_PLACE_ID` INT UNSIGNED NOT NULL,
  `number` INT NULL,
  PRIMARY KEY (`DELIVERY_PLACE_ID`),
  CONSTRAINT `fk_BAR_TABLE_DELIVERY_PLACE1`
    FOREIGN KEY (`DELIVERY_PLACE_ID`)
    REFERENCES `android2k17`.`DELIVERY_PLACE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `android2k17`.`BAR_COUNTER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `android2k17`.`BAR_COUNTER` ;

CREATE TABLE IF NOT EXISTS `android2k17`.`BAR_COUNTER` (
  `DELIVERY_PLACE_ID` INT UNSIGNED NOT NULL,
  `name` VARCHAR(255) NULL,
  PRIMARY KEY (`DELIVERY_PLACE_ID`),
  CONSTRAINT `fk_BAR_TABLE_DELIVERY_PLACE10`
    FOREIGN KEY (`DELIVERY_PLACE_ID`)
    REFERENCES `android2k17`.`DELIVERY_PLACE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `android2k17`.`BAR`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`BAR` (`ID`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES (1, 'BAR BELLISSIMO', 'Questo è il bar più bello della storia dei bar!', 'Via dei bei bar 45', 46.06802028, 11.14992142);
INSERT INTO `android2k17`.`BAR` (`ID`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES (2, 'BAR BRUTTO', 'Bar brutto', 'Via butti 55', 46.0672552, 11.12101110);

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`CUSTOMER`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`CUSTOMER` (`ID`, `username`, `year_of_birth`, `email`, `password`) VALUES (1, 'mario', 1990, 'mario@gmail.com', 'mario');
INSERT INTO `android2k17`.`CUSTOMER` (`ID`, `username`, `year_of_birth`, `email`, `password`) VALUES (2, 'giulia', 1991, 'giulia@gmail.com', 'giulia');

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`ITEM_SIZE`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`ITEM_SIZE` (`ID`, `size_description`) VALUES (1, 'piccola');
INSERT INTO `android2k17`.`ITEM_SIZE` (`ID`, `size_description`) VALUES (2, 'media');
INSERT INTO `android2k17`.`ITEM_SIZE` (`ID`, `size_description`) VALUES (3, 'grande');

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`ITEM_CATEGORY`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`ITEM_CATEGORY` (`ID`, `category_name`) VALUES (1, 'Birre');
INSERT INTO `android2k17`.`ITEM_CATEGORY` (`ID`, `category_name`) VALUES (2, 'Vini');
INSERT INTO `android2k17`.`ITEM_CATEGORY` (`ID`, `category_name`) VALUES (3, 'Bevande analcoliche');
INSERT INTO `android2k17`.`ITEM_CATEGORY` (`ID`, `category_name`) VALUES (4, 'Cocktails');
INSERT INTO `android2k17`.`ITEM_CATEGORY` (`ID`, `category_name`) VALUES (5, 'Cocktails analcolici');
INSERT INTO `android2k17`.`ITEM_CATEGORY` (`ID`, `category_name`) VALUES (6, 'Caffetteria');

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`GLOBAL_MENU_ITEM`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (1, 'Coca cola');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (2, 'Birra Guinnes');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (3, 'Pinot Grigio');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (4, 'Thè alla pesca');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (5, 'Birra chiara');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (6, 'Thè al limone');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (7, 'Vino rosso della casa');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (8, 'Vino bianco della casa');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (9, 'Caffè');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (10, 'Birra Rossa');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (11, 'Birra Scura');
INSERT INTO `android2k17`.`GLOBAL_MENU_ITEM` (`ID`, `name`) VALUES (12, 'Birra Weizen');

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`MENU_ITEM`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`MENU_ITEM` (`ID`, `BAR_ID`, `ITEM_CATEGORY_ID`, `GLOBAL_MENU_ITEM_ID`, `menu_item_name`, `description`) VALUES (1, 1, 3, 1, 'CocaCola', 'buona coca 1');
INSERT INTO `android2k17`.`MENU_ITEM` (`ID`, `BAR_ID`, `ITEM_CATEGORY_ID`, `GLOBAL_MENU_ITEM_ID`, `menu_item_name`, `description`) VALUES (2, 2, 3, 1, 'Coca-cola', 'buona coca 2');
INSERT INTO `android2k17`.`MENU_ITEM` (`ID`, `BAR_ID`, `ITEM_CATEGORY_ID`, `GLOBAL_MENU_ITEM_ID`, `menu_item_name`, `description`) VALUES (3, 1, 1, 5, 'Birra chiara', 'bella birra');
INSERT INTO `android2k17`.`MENU_ITEM` (`ID`, `BAR_ID`, `ITEM_CATEGORY_ID`, `GLOBAL_MENU_ITEM_ID`, `menu_item_name`, `description`) VALUES (4, 1, 1, 12, 'Birra Weizen', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`INGREDIENT`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`INGREDIENT` (`ID`, `ingredient_name`) VALUES (1, 'Alcool');

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`ITEM_ADDITION`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`ITEM_ADDITION` (`ID`, `addition_name`) VALUES (1, 'ghiaccio');
INSERT INTO `android2k17`.`ITEM_ADDITION` (`ID`, `addition_name`) VALUES (2, 'limone');
INSERT INTO `android2k17`.`ITEM_ADDITION` (`ID`, `addition_name`) VALUES (3, 'arancia');
INSERT INTO `android2k17`.`ITEM_ADDITION` (`ID`, `addition_name`) VALUES (4, 'temp. ambiente');

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`DELIVERY_PLACE`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`DELIVERY_PLACE` (`ID`) VALUES (1);
INSERT INTO `android2k17`.`DELIVERY_PLACE` (`ID`) VALUES (2);
INSERT INTO `android2k17`.`DELIVERY_PLACE` (`ID`) VALUES (3);
INSERT INTO `android2k17`.`DELIVERY_PLACE` (`ID`) VALUES (4);
INSERT INTO `android2k17`.`DELIVERY_PLACE` (`ID`) VALUES (5);
INSERT INTO `android2k17`.`DELIVERY_PLACE` (`ID`) VALUES (6);

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`BAR_HAS_DELIVERY_PLACE`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (1, 1, 1, 1);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (2, 1, 1, 2);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (3, 1, 1, 3);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (4, 1, 1, 4);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (5, 1, 1, 5);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (6, 1, 1, 6);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (7, 1, 2, 1);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (8, 1, 2, 2);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (9, 1, 2, 3);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (10, 1, 2, 4);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (11, 1, 2, 5);
INSERT INTO `android2k17`.`BAR_HAS_DELIVERY_PLACE` (`ID`, `floor_number`, `BAR_ID`, `DELIVERY_PLACE_ID`) VALUES (12, 1, 2, 6);

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`MENU_ITEM_HAS_INGREDIENT`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`MENU_ITEM_HAS_INGREDIENT` (`MENU_ITEM_ID`, `INGREDIENT_ID`, `quantity`) VALUES (3, 1, '5');
INSERT INTO `android2k17`.`MENU_ITEM_HAS_INGREDIENT` (`MENU_ITEM_ID`, `INGREDIENT_ID`, `quantity`) VALUES (4, 1, '6');

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`MENU_ITEM_HAS_SIZE`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`MENU_ITEM_HAS_SIZE` (`MENU_ITEM_ID`, `ITEM_SIZE_ID`, `price`) VALUES (3, 1, 3.50);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_SIZE` (`MENU_ITEM_ID`, `ITEM_SIZE_ID`, `price`) VALUES (3, 2, 5.00);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_SIZE` (`MENU_ITEM_ID`, `ITEM_SIZE_ID`, `price`) VALUES (3, 3, 8.00);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_SIZE` (`MENU_ITEM_ID`, `ITEM_SIZE_ID`, `price`) VALUES (1, 3, 2.50);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_SIZE` (`MENU_ITEM_ID`, `ITEM_SIZE_ID`, `price`) VALUES (2, 3, 3.00);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_SIZE` (`MENU_ITEM_ID`, `ITEM_SIZE_ID`, `price`) VALUES (4, 2, 5.50);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_SIZE` (`MENU_ITEM_ID`, `ITEM_SIZE_ID`, `price`) VALUES (4, 3, 10.00);

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`MENU_ITEM_HAS_ADDITION`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`MENU_ITEM_HAS_ADDITION` (`MENU_ITEM_ID`, `ITEM_ADDITION_ID`, `price`) VALUES (1, 1, 0);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_ADDITION` (`MENU_ITEM_ID`, `ITEM_ADDITION_ID`, `price`) VALUES (1, 2, 0.5);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_ADDITION` (`MENU_ITEM_ID`, `ITEM_ADDITION_ID`, `price`) VALUES (1, 3, 0.5);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_ADDITION` (`MENU_ITEM_ID`, `ITEM_ADDITION_ID`, `price`) VALUES (2, 1, 0);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_ADDITION` (`MENU_ITEM_ID`, `ITEM_ADDITION_ID`, `price`) VALUES (2, 2, 0);
INSERT INTO `android2k17`.`MENU_ITEM_HAS_ADDITION` (`MENU_ITEM_ID`, `ITEM_ADDITION_ID`, `price`) VALUES (2, 3, 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`OPENING_HOUR`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`OPENING_HOUR` (`BAR_ID`, `day_of_week`, `time_open`, `working_time`) VALUES (1, 5, '18:00:00', 8);
INSERT INTO `android2k17`.`OPENING_HOUR` (`BAR_ID`, `day_of_week`, `time_open`, `working_time`) VALUES (1, 6, '18:00:00', 8);
INSERT INTO `android2k17`.`OPENING_HOUR` (`BAR_ID`, `day_of_week`, `time_open`, `working_time`) VALUES (1, 7, '18:00:00', 6);
INSERT INTO `android2k17`.`OPENING_HOUR` (`BAR_ID`, `day_of_week`, `time_open`, `working_time`) VALUES (2, 5, '20:00:00', 4);
INSERT INTO `android2k17`.`OPENING_HOUR` (`BAR_ID`, `day_of_week`, `time_open`, `working_time`) VALUES (2, 6, '21:00:00', 4);
INSERT INTO `android2k17`.`OPENING_HOUR` (`BAR_ID`, `day_of_week`, `time_open`, `working_time`) VALUES (2, 7, '20:00:00', 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`BAR_TABLE`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`BAR_TABLE` (`DELIVERY_PLACE_ID`, `number`) VALUES (2, 1);
INSERT INTO `android2k17`.`BAR_TABLE` (`DELIVERY_PLACE_ID`, `number`) VALUES (3, 2);
INSERT INTO `android2k17`.`BAR_TABLE` (`DELIVERY_PLACE_ID`, `number`) VALUES (4, 3);
INSERT INTO `android2k17`.`BAR_TABLE` (`DELIVERY_PLACE_ID`, `number`) VALUES (5, 4);
INSERT INTO `android2k17`.`BAR_TABLE` (`DELIVERY_PLACE_ID`, `number`) VALUES (6, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `android2k17`.`BAR_COUNTER`
-- -----------------------------------------------------
START TRANSACTION;
USE `android2k17`;
INSERT INTO `android2k17`.`BAR_COUNTER` (`DELIVERY_PLACE_ID`, `name`) VALUES (1, 'Bancone');

COMMIT;

