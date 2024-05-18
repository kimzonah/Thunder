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


INSERT INTO User (id, password, name, favorite, image, org_image) VALUES
('jihye', 'password1', '지혜', '농구', 'image_path1', 'org_image_path1'),
('minho', 'password2', '민호', '축구/풋살', 'image_path2', 'org_image_path2'),
('sunwoo', 'password3', '선우', '야구', 'image_path3', 'org_image_path3'),
('jaehyun', 'password4', '재현', '등산', 'image_path4', 'org_image_path4'),
('soyeon', 'password5', '소연', '러닝', 'image_path5', 'org_image_path5'),
('junho', 'password6', '준호', '배드민턴', 'image_path6', 'org_image_path6'),
('yejin', 'password7', '예진', '테니스', 'image_path7', 'org_image_path7'),
('yonghoon', 'password8', '용훈', '탁구', 'image_path8', 'org_image_path8'),
('mira', 'password9', '미라', '볼링', 'image_path9', 'org_image_path9'),
('hyewon', 'password10', '혜원', '당구', 'image_path10', 'org_image_path10'),
('jisoo', 'password11', '지수', '클라이밍', 'image_path11', 'org_image_path11'),
('hwanhee', 'password12', '환희', '사이클링', 'image_path12', 'org_image_path12'),
('jiwon', 'password13', '지원', '기타', 'image_path13', 'org_image_path13'),
('sungjin', 'password14', '성진', '농구', 'image_path14', 'org_image_path14');

SELECT * FROM user;

INSERT INTO Schedule (manager_id, title, category, scale, description, date_time, address_name, longitude, latitude, chat_url) VALUES

('jihye', '풋살 모임', '축구/풋살', 10, '주말 풋살 모임', '2024-05-25 15:00:00', '풋살장 A', '127.1234', '37.5678', NULL),
('jihye', '등산 모임', '등산', 8, '산행을 즐기는 모임', '2024-05-30 08:00:00', '산 B', '127.5678', '37.9876', NULL),
('minho', '야구 모임', '야구', 15, '매주 수요일 오후에 야구를 즐기는 모임', '2024-06-02 18:00:00', '야구장 C', '127.7890', '37.2468', NULL),
('jaehyun', '러닝 모임', '러닝', 20, '매일 아침 7시에 함께하는 러닝 모임', '2024-06-10 07:00:00', '공원 E', '128.5678', '37.5432', NULL),
('soyeon', '테니스 모임', '테니스', 8, '주말 테니스 즐기는 모임', '2024-06-15 10:00:00', '테니스장 F', '129.1234', '37.9876', NULL),
('junho', '볼링 모임', '볼링', 6, '가벼운 주말 볼링 즐기는 모임', '2024-06-20 13:00:00', '볼링장 G', '129.5678', '37.6543', NULL),
('yejin', '탁구 모임', '탁구', 8, '평일 저녁 탁구를 즐기는 모임', '2024-06-25 19:00:00', '탁구장 H', '130.1234', '37.3456', NULL),
('yonghoon', '당구 모임', '당구', 4, '일요일 오후에 당구 치는 모임', '2024-06-30 16:00:00', '당구장 I', '130.5678', '37.7890', NULL),
('hyewon', '사이클링 모임', '사이클링', 12, '시간 맞는 주말에 사이클링 즐기는 모임', '2024-07-10 08:00:00', '자전거길 K', '131.5678', '37.6789', NULL),
('hwanhee', '농구 모임', '농구', 10, '매주 토요일 오후에 농구 치는 모임', '2024-07-20 17:00:00', '농구장 M', '132.5678', '37.4321', NULL),
('sungjin', '야구 모임', '야구', 15, '토요일 오후에 야구 치는 모임', '2024-07-30 16:00:00', '야구장 O', '133.5678', '37.3456', NULL),
('sunwoo', '야구 모임', '야구', 12, '주말 오후 야구 모임', '2024-05-27 14:00:00', '야구장 P', '134.1234', '37.5678', NULL),
('sunwoo', '배드민턴 모임', '배드민턴', 8, '평일 저녁 배드민턴 모임', '2024-06-01 19:00:00', '체육관 Q', '134.5678', '37.9876', NULL),
('sunwoo', '등산 모임', '등산', 6, '주중 산행 모임', '2024-06-07 07:00:00', '산 X', '135.1234', '37.2468', NULL),
('jiwon', '풋살 모임', '축구/풋살', 10, '평일 풋살 즐기는 모임', '2024-06-05 21:00:00', '풋살장 R', '135.1234', '37.2468', NULL),
('jiwon', '테니스 모임', '테니스', 6, '주말 오전 테니스 즐기는 모임', '2024-06-10 10:00:00', '테니스장 S', '135.5678', '37.6543', NULL),
('jiwon', '볼링 모임', '볼링', 8, '평일 오후 볼링 모임', '2024-06-17 15:00:00', '볼링장 Y', '136.1234', '37.8765', NULL),
('mira', '클라이밍 모임', '클라이밍', 8, '주말 암벽등반 모임', '2024-06-15 12:00:00', '클라이밍장 T', '136.1234', '37.8765', NULL),
('mira', '사이클링 모임', '사이클링', 10, '평일 아침 사이클링 모임', '2024-06-20 07:00:00', '자전거길 U', '136.5678', '37.4321', NULL),
('mira', '야구 모임', '야구', 6, '평일 야구 모임', '2024-06-27 18:00:00', '야구장 Z', '137.1234', '37.9876', NULL),
('jisoo', '등산 모임', '등산', 6, '일요일 산행 모임', '2024-06-25 09:00:00', '산 V', '137.1234', '37.9876', NULL),
('jisoo', '탁구 모임', '탁구', 8, '평일 저녁 탁구 모임', '2024-06-30 18:00:00', '탁구장 W', '137.5678', '37.6543', NULL),
('jisoo', '농구 모임', '농구', 10, '주말 농구 모임', '2024-07-05 17:00:00', '농구장 AA', '138.1234', '37.8765', NULL),
('jisoo', '과거 배드민턴 번개', '배드민턴', 4, 'path/to/past_badminton_image.jpg', 'path/to/past_badminton_org_image.jpg', '지난 배드민턴 번개', '2024-01-01 10:00:00', '서울특별시 강남구', '127.027621', '37.497942', 'http://chat.url/past_badminton');

SELECT * FROM schedule;

-- UserSchedule 목데이터 (30개)
INSERT INTO UserSchedule (user_id, schedule_id, status) VALUES
('jihye', 1, 1), ('jihye', 2, 1), ('jihye', 4, 0),
('minho', 3, 1), ('minho', 7, 0),
('sunwoo', 14, 1), ('sunwoo', 12, 1), ('sunwoo', 13, 1), ('sunwoo', 9, 1),
('jaehyun', 13, 0), ('jaehyun', 4, 1),
('soyeon', 18, 0), ('soyeon', 21, 1), ('soyeon', 5, 1),
('yejin', 1, 1), ('yejin', 4, 0), ('yejin', 7, 1),
('yonghoon', 3, 1), ('yonghoon', 7, 0), ('yonghoon', 8, 1),
('mira', 8, 1), ('mira', 9, 1), ('mira', 18, 1), ('mira', 19, 1), ('mira', 20, 1),
('hyewon', 13, 0), ('hyewon', 9, 1),
('jisoo', 18, 1), ('jisoo', 21, 1), ('jisoo', 22, 1), ('jisoo', 23, 1), ('jisoo', 24, 1)
('hwanhee', 10, 1), ('hwanhee', 2, 1), ('hwanhee', 4, 0),
('jiwon', 3, 1), ('jiwon', 15, 1), ('jiwon', 16, 1), ('jiwon', 17, 1),
('sungjin', 8, 1), ('sungjin', 9, 1), ('sungjin', 11, 1);

SELECT * FROM userSchedule;