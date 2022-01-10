-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema online_store
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema online_store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `online_store` DEFAULT CHARACTER SET utf8 ;
USE `online_store` ;

-- -----------------------------------------------------
-- Table `online_store`.`Publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`Publisher` (
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NULL,
  `phone` CHAR(12) NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `online_store`.`Book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`Book` (
  `ISBN` CHAR(14) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `noOfCopies` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `publicationYear` YEAR NULL,
  `threshold` INT NOT NULL,
  `publisherName` VARCHAR(45) NOT NULL,
  `image` MEDIUMTEXT NULL,
  `categoryName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ISBN`),
  INDEX `publicationYear_idx` (`publicationYear` ASC),
  INDEX `title_idx` (`title` ASC),
  INDEX `fk_Book_1_idx` (`publisherName` ASC),
  CONSTRAINT `fk_Book_1`
    FOREIGN KEY (`publisherName`)
    REFERENCES `online_store`.`Publisher` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `online_store`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`User` (
  `firstName` VARCHAR(70) NULL,
  `lastName` VARCHAR(70) NULL,
  `userName` VARCHAR(70) NOT NULL,
  `address` VARCHAR(70) NULL,
  `password` VARCHAR(200) NULL,
  `email` VARCHAR(70) NULL,
  `role` INT NULL,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  PRIMARY KEY (`userName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `online_store`.`BookAuthors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`BookAuthors` (
  `ISBN` CHAR(14) NOT NULL,
  `authorName` INT NOT NULL,
  PRIMARY KEY (`ISBN`, `authorName`),
  UNIQUE INDEX `index2` (`ISBN` ASC),
  UNIQUE INDEX `index3` (`authorName` ASC),
  CONSTRAINT `fk_BookAuthors_Book`
    FOREIGN KEY (`ISBN`)
    REFERENCES `online_store`.`Book` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `online_store`.`Orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`Orders` (
  `orderId` INT NOT NULL AUTO_INCREMENT,
  `noOfCopies` INT NULL,
  `ISBN` CHAR(14) NULL,
  PRIMARY KEY (`orderId`),
  INDEX `fk_Orders_Book_idx` (`ISBN` ASC),
  CONSTRAINT `fk_Orders_Book`
    FOREIGN KEY (`ISBN`)
    REFERENCES `online_store`.`Book` (`ISBN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `online_store`.`CreditCard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`CreditCard` (
  `serialNumber` INT NOT NULL,
  `expirationDate` DATE NOT NULL,
  PRIMARY KEY (`serialNumber`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `online_store`.`CheckOut`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`CheckOut` (
  `userName` VARCHAR(70) NOT NULL,
  `ISBN` CHAR(14) NOT NULL,
  `noOfCopies` INT NOT NULL,
  `date` DATE NULL,
  INDEX `fk_CheckOut_ISBN_idx` (`ISBN` ASC),
  PRIMARY KEY (`userName`),
  CONSTRAINT `fk_CheckOut_ISBN`
    FOREIGN KEY (`ISBN`)
    REFERENCES `online_store`.`Book` (`ISBN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CheckOut_1`
    FOREIGN KEY (`userName`)
    REFERENCES `online_store`.`User` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
USE `online_store`;

DELIMITER $$
USE `online_store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `online_store`.`Book_BEFORE_UPDATE` BEFORE UPDATE ON `Book` FOR EACH ROW
BEGIN
if NEW.noOfCopies < 0 then
    set @message_text = concat("Can't update with negative value.");
    signal sqlstate '45000' set message_text = @message_text;
end if;
END$$

USE `online_store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `online_store`.`Book_AFTER_UPDATE` AFTER UPDATE ON `Book` FOR EACH ROW
BEGIN
if NEW.noOfCopies < NEW.threshold then 
	INSERT INTO Orders(ISBN,noOfCopies) VALUES (NEW.ISBN,100) ; 
end if ; 
END$$

USE `online_store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `online_store`.`Orders_BEFORE_DELETE` BEFORE DELETE ON `Orders` FOR EACH ROW
BEGIN
UPDATE Book AS B SET B.noOfCopies = B.noOfCopies + OLD.noOfCopies WHERE B.ISBN = OLD.ISBN  ; 
END$$

USE `online_store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `online_store`.`CheckOut_BEFORE_INSERT` BEFORE INSERT ON `CheckOut` FOR EACH ROW
BEGIN
declare cnt INT ;
SELECT 
    noOfCopies
INTO cnt FROM
    Book
WHERE
    ISBN = NEW.ISBN; 
if cnt < NEW.noOfCopies then
 set @message_text = concat('Not enough books! , we have only ', cnt ,' book(s).');
    signal sqlstate '45000' set message_text = @message_text;
end if ; 
END$$

USE `online_store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `online_store`.`CheckOut_AFTER_INSERT` AFTER INSERT ON `CheckOut` FOR EACH ROW
BEGIN
UPDATE Book AS B SET B.noOfCopies = B.noOfCopies - NEW.noOfCopies WHERE B.ISBN = NEW.ISBN ; 
END$$


DELIMITER ;
