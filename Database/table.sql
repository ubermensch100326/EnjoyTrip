-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `enjoytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `enjoytrip` ;

-- -----------------------------------------------------
-- Table `enjoytrip`.`sido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`sido` (
  `sido_code` INT NOT NULL,
  `sido_name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `enjoytrip`.`gugun`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`gugun` (
  `gugun_code` INT NOT NULL,
  `sido_code` INT NOT NULL,
  `gugun_name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`gugun_code`, `sido_code`),
  INDEX `gugun_to_sido_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `gugun_to_sido_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_info` (
  `attraction_info_id` INT NOT NULL,
  `type` INT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(50) NULL DEFAULT NULL,
  `zipcode` VARCHAR(50) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `first_image` VARCHAR(200) NULL DEFAULT NULL,
  `first_image2` VARCHAR(200) NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sido_code` INT NOT NULL,
  `gugun_code` INT NOT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `mlevel` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`attraction_info_id`),
  INDEX `attraction_to_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `attraction_to_gugun_code_fk_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `attraction_to_gugun_code_fk`
    FOREIGN KEY (`gugun_code`)
    REFERENCES `enjoytrip`.`gugun` (`gugun_code`),
  CONSTRAINT `attraction_to_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_description`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_description` (
  `attraction_info_id` INT NOT NULL,
  `homepage` VARCHAR(100) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  `telname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`attraction_info_id`),
  CONSTRAINT `attraction_detail_to_attraciton_id_fk`
    FOREIGN KEY (`attraction_info_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`attraction_info_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_detail` (
  `attraction_info_id` INT NOT NULL,
  `cat1` VARCHAR(3) NULL DEFAULT NULL,
  `cat2` VARCHAR(5) NULL DEFAULT NULL,
  `cat3` VARCHAR(9) NULL DEFAULT NULL,
  `create_time` DATETIME NULL DEFAULT NULL,
  `modify_time` DATETIME NULL DEFAULT NULL,
  `booktour` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`attraction_info_id`),
  CONSTRAINT `attraction_detail_to_basic_content_id_fk`
    FOREIGN KEY (`attraction_info_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`attraction_info_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `enjoytrip`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`user` (
  `user_id` VARCHAR(16) NOT NULL,
  `password` VARCHAR(16) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `email_id` VARCHAR(20) NOT NULL,
  `email_domain` VARCHAR(45) NOT NULL,
  `image_url` LONGTEXT NULL DEFAULT NULL,
  `birth_year` INT NOT NULL,
  `gender` VARCHAR(10) NULL DEFAULT NULL,
  `register_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `refresh_token` LONGTEXT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`plan_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`plan_info` (
  `plan_info_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `register_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` DATETIME NULL,
  `private` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`plan_info_no`),
  INDEX `board_to_member_user_id_fk` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_to_member_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`plan_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`plan_like` (
  `plan_info_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NOT NULL,
  `like_time` DATETIME NOT NULL,
  PRIMARY KEY (`plan_info_no`, `user_id`),
  INDEX `fk_board_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_board_has_user_board1_idx` (`plan_info_no` ASC) VISIBLE,
  CONSTRAINT `fk_board_has_user_board1`
    FOREIGN KEY (`plan_info_no`)
    REFERENCES `enjoytrip`.`plan_info` (`plan_info_no`),
  CONSTRAINT `fk_board_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`comment` (
  `comment_no` INT NOT NULL AUTO_INCREMENT,
  `plan_info_no` INT NOT NULL,
  `user_id` VARCHAR(16) NOT NULL,
  `content` VARCHAR(500) NOT NULL,
  `register_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_no`),
  INDEX `memo_to_board_article_no_fk` (`plan_info_no` ASC) VISIBLE,
  INDEX `memo_to_member_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `memo_to_board_article_no_fk`
    FOREIGN KEY (`plan_info_no`)
    REFERENCES `enjoytrip`.`plan_info` (`plan_info_no`),
  CONSTRAINT `memo_to_member_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`search_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`search_log` (
  `search_log_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL,
  `keyword` VARCHAR(100) NOT NULL,
  `search_time` DATETIME NOT NULL,
  PRIMARY KEY (`search_log_no`),
  INDEX `fk_search_log_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_search_log_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `enjoytrip`.`plan_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`plan_detail` (
  `plan_info_no` INT NOT NULL,
  `attraction_info_id` INT NOT NULL,
  `order` INT NOT NULL,
  `image_url` LONGTEXT NULL,
  INDEX `fk_board_plan_detail_attraction_info1_idx` (`attraction_info_id` ASC) VISIBLE,
  INDEX `fk_board_plan_detail_board_plan_info1_idx` (`plan_info_no` ASC) VISIBLE,
  PRIMARY KEY (`plan_info_no`, `attraction_info_id`),
  CONSTRAINT `fk_board_plan_detail_attraction_info1`
    FOREIGN KEY (`attraction_info_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`attraction_info_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_board_plan_detail_board_plan_info1`
    FOREIGN KEY (`plan_info_no`)
    REFERENCES `enjoytrip`.`plan_info` (`plan_info_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_like` (
  `attraction_info_id` INT NOT NULL,
  `user_id` VARCHAR(16) NOT NULL,
  `like_time` DATETIME NOT NULL,
  PRIMARY KEY (`attraction_info_id`, `user_id`),
  INDEX `fk_attraction_info_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_attraction_info_has_user_attraction_info1_idx` (`attraction_info_id` ASC) VISIBLE,
  CONSTRAINT `fk_attraction_info_has_user_attraction_info1`
    FOREIGN KEY (`attraction_info_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`attraction_info_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attraction_info_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
