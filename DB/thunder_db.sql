-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema thunder_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema thunder_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `thunder_db`;
CREATE SCHEMA IF NOT EXISTS `thunder_db` DEFAULT CHARACTER SET utf8 ;
USE `thunder_db` ;


-- select * from category;
-- -----------------------------------------------------
-- Table `thunder_db`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thunder_db`.`User` (
  `id` VARCHAR(20) NOT NULL,
  `password` VARCHAR(40) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `favorite` VARCHAR(15) NOT NULL,
  `image` VARCHAR(255) NULL,
  `org_image` VARCHAR(255) NULL,
  PRIMARY KEY (`id`)
	)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thunder_db`.`Schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thunder_db`.`Schedule` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `manager_id` VARCHAR(20) NOT NULL,
  `title` VARCHAR(60) NOT NULL,
  `category` VARCHAR(15) NOT NULL,
  `scale` INT NOT NULL DEFAULT 4,
  `image` VARCHAR(255) NULL,
  `org_image` VARCHAR(255) NULL,
  `description` TEXT NOT NULL,
  `date_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `address_name` VARCHAR(100) NOT NULL,
  `longitude` VARCHAR(40) NOT NULL,
  `latitude` VARCHAR(40) NOT NULL,
  `chat_url` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `manager_idx` (`manager_id` ASC) VISIBLE,
  CONSTRAINT `fk_schedule_user`
    FOREIGN KEY (`manager_id`)
    REFERENCES `thunder_db`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
     )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `thunder_db`.`UserSchedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thunder_db`.`UserSchedule` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `schedule_id` INT NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `schedule_id_idx` (`schedule_id` ASC) VISIBLE,
  CONSTRAINT `fk_userschedule_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `thunder_db`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_userschedule_schedule`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `thunder_db`.`Schedule` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `thunder_db`.`Album`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thunder_db`.`Album` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `image` VARCHAR(255) NOT NULL,
  `org_image` VARCHAR(255) NOT NULL,
  `schedule_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `schedule_id_idx` (`schedule_id` ASC) VISIBLE,
  CONSTRAINT `fk_album_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `thunder_db`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_album_schedule`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `thunder_db`.`Schedule` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thunder_db`.`Board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thunder_db`.`Board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(40) NOT NULL,
  `content` TEXT NOT NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `schedule_id` INT NOT NULL,
  `comment_cnt` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `schedule_id_idx` (`schedule_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `thunder_db`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_board_schedule`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `thunder_db`.`Schedule` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thunder_db`.`Friend`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thunder_db`.`Friend` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `from_user_id` VARCHAR(20) NOT NULL,
  `to_user_id` VARCHAR(20) NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `from_user_id_idx` (`from_user_id` ASC) VISIBLE,
  INDEX `to_user_id_idx` (`to_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_friend_from_user`
    FOREIGN KEY (`from_user_id`)
    REFERENCES `thunder_db`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_friend_to_user`
    FOREIGN KEY (`to_user_id`)
    REFERENCES `thunder_db`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thunder_db`.`Comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thunder_db`.`Comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NOT NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `content` TEXT NOT NULL,
  `reg_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `board_id_idx` (`board_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `thunder_db`.`Board` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `thunder_db`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- select * from userschedule;

-- INSERT INTO user VALUES ('ssafy', '1234', '김싸피', '배드민턴', 'aaaaaa');
-- INSERT INTO user VALUES ('aaa','aaa','박싸피','축구', 'ㅠㅠㅠㅠ');
-- select * from user;

-- INSERT INTO schedule VALUES (0, 'ssafy', '번개요~', '배드민턴', 5, 'ㅁㅁㅁㅁ','모이셈',now(), 'aaa','a','a','aa');
-- select * from schedule;

-- INSERT INTO friend VALUES (0, 'ssafy', 'aaa',0);
-- select * from friend;

-- INSERT INTO board VALUES (0, '공지합니다', 'ㅇㅇ','ssafy', now(), now(), 1);
-- select * from board;

-- INSERT INTO comment VALUES (0, 1,'ssafy','dd', now());
-- select * from comment;

-- INSERT INTO album VALUES (0,'ssafy','dd', 1);
-- select * from album;