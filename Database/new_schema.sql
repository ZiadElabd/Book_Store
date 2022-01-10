-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema online_store
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema online_store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `online_store` DEFAULT CHARACTER SET utf8 ;
USE `online_store` ;

-- -----------------------------------------------------
-- Table `online_store`.`publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`publisher` (
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NULL DEFAULT NULL,
  `phone` CHAR(12) NULL DEFAULT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `online_store`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`book` (
  `ISBN` CHAR(14) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `noOfCopies` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `publicationYear` VARCHAR(45) NULL DEFAULT NULL,
  `threshold` INT NOT NULL,
  `publisherName` VARCHAR(45) NOT NULL,
  `image` MEDIUMTEXT NULL DEFAULT NULL,
  `categoryName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ISBN`),
  INDEX `publicationYear_idx` (`publicationYear` ASC) VISIBLE,
  INDEX `title_idx` (`title` ASC) VISIBLE,
  INDEX `fk_Book_1_idx` (`publisherName` ASC) VISIBLE,
  CONSTRAINT `fk_Book_1`
    FOREIGN KEY (`publisherName`)
    REFERENCES `online_store`.`publisher` (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `online_store`.`bookauthors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`bookauthors` (
  `ISBN` CHAR(14) NOT NULL,
  `authorName` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ISBN`, `authorName`),
  UNIQUE INDEX `index2` (`ISBN` ASC) VISIBLE,
  UNIQUE INDEX `index3` (`authorName` ASC) VISIBLE,
  CONSTRAINT `fk_BookAuthors_Book`
    FOREIGN KEY (`ISBN`)
    REFERENCES `online_store`.`book` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `online_store`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`user` (
  `firstName` VARCHAR(70) NULL DEFAULT NULL,
  `lastName` VARCHAR(70) NULL DEFAULT NULL,
  `userName` VARCHAR(70) NOT NULL,
  `address` VARCHAR(70) NULL DEFAULT NULL,
  `password` VARCHAR(200) NULL DEFAULT NULL,
  `email` VARCHAR(70) NULL DEFAULT NULL,
  `role` INT NULL DEFAULT NULL,
  PRIMARY KEY (`userName`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `online_store`.`checkout`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`checkout` (
  `userName` VARCHAR(70) NOT NULL,
  `ISBN` CHAR(14) NOT NULL,
  `noOfCopies` INT NOT NULL,
  `date` VARCHAR(70) NULL DEFAULT NULL,
  PRIMARY KEY (`userName`),
  INDEX `fk_CheckOut_ISBN_idx` (`ISBN` ASC) VISIBLE,
  CONSTRAINT `fk_CheckOut_1`
    FOREIGN KEY (`userName`)
    REFERENCES `online_store`.`user` (`userName`),
  CONSTRAINT `fk_CheckOut_ISBN`
    FOREIGN KEY (`ISBN`)
    REFERENCES `online_store`.`book` (`ISBN`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `online_store`.`creditcard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`creditcard` (
  `serialNumber` INT NOT NULL,
  `expirationDate` DATE NOT NULL,
  PRIMARY KEY (`serialNumber`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `online_store`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `online_store`.`orders` (
  `orderId` INT NOT NULL AUTO_INCREMENT,
  `noOfCopies` INT NULL DEFAULT NULL,
  `ISBN` CHAR(14) NULL DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  INDEX `fk_Orders_Book_idx` (`ISBN` ASC) VISIBLE,
  CONSTRAINT `fk_Orders_Book`
    FOREIGN KEY (`ISBN`)
    REFERENCES `online_store`.`book` (`ISBN`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;

USE `online_store`;

DELIMITER $$
USE `online_store`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `online_store`.`Book_AFTER_UPDATE`
AFTER UPDATE ON `online_store`.`book`
FOR EACH ROW
BEGIN
if NEW.noOfCopies < NEW.threshold then 
	INSERT INTO Orders(ISBN,noOfCopies) VALUES (NEW.ISBN,100) ; 
end if ; 
END$$

USE `online_store`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `online_store`.`Book_BEFORE_UPDATE`
BEFORE UPDATE ON `online_store`.`book`
FOR EACH ROW
BEGIN
if NEW.noOfCopies < 0 then
    set @message_text = concat("Can't update with negative value.");
    signal sqlstate '45000' set message_text = @message_text;
end if;
END$$

USE `online_store`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `online_store`.`CheckOut_AFTER_INSERT`
AFTER INSERT ON `online_store`.`checkout`
FOR EACH ROW
BEGIN
UPDATE Book AS B SET B.noOfCopies = B.noOfCopies - NEW.noOfCopies WHERE B.ISBN = NEW.ISBN ; 
END$$

USE `online_store`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `online_store`.`CheckOut_BEFORE_INSERT`
BEFORE INSERT ON `online_store`.`checkout`
FOR EACH ROW
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
CREATE
DEFINER=`root`@`localhost`
TRIGGER `online_store`.`Orders_BEFORE_DELETE`
BEFORE DELETE ON `online_store`.`orders`
FOR EACH ROW
BEGIN
UPDATE Book AS B SET B.noOfCopies = B.noOfCopies + OLD.noOfCopies WHERE B.ISBN = OLD.ISBN  ; 
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
