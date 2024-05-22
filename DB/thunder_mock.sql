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


INSERT INTO User (id, password, name, favorite) VALUES
('jihye', 'password1', '지혜', '농구'),
('minho', 'password2', '민호', '축구/풋살'),
('sunwoo', 'password3', '선우', '야구'),
('jaehyun', 'password4', '재현', '등산'),
('soyeon', 'password5', '소연', '러닝'),
('junho', 'password6', '준호', '배드민턴'),
('yejin', 'password7', '예진', '테니스'),
('yonghoon', 'password8', '용훈', '탁구'),
('mira', 'password9', '미라', '볼링'),
('hyewon', 'password10', '혜원', '당구'),
('jisoo', 'password11', '지수', '클라이밍'),
('hwanhee', 'password12', '환희', '사이클링'),
('jiwon', 'password13', '지원', '기타'),
('sungjin', 'password14', '성진', '농구');

SELECT * FROM user;

select count(*) from user;

INSERT INTO Schedule (manager_id, title, category, scale, description, date_time, address_name, longitude, latitude, chat_url) VALUES

('jihye', '풋살 모임', '축구/풋살', 10, '안녕하세요! 주말에 함께 축구를 즐길 멤버들을 모집합니다. 초보자부터 숙련자까지 환영하며, 대전 서구 남선공원 종합체육관에서 경기를 진행할 예정입니다. 함께 땀 흘리며 스트레스를 풀고, 경기 후에는 간단한 식사와 음료를 즐기며 친목을 다져보아요. 많은 참여 부탁드립니다!', '2024-05-25 15:00:00', '남선공원 종합체육관 풋살장 (대전 서구 남선로 66) * 대전 서구 탄방동 1084', '127.397991198843', '36.3462303652179', NULL),
('jihye', '등산 모임', '등산', 8, '안녕하세요! 함께 등산을 떠날 멤버들을 모집합니다. 대전의 계족산에서 초보자도 부담 없이 참여할 수 있는 코스로 진행됩니다. 등산을 통해 몸과 마음을 재충전하고, 새로운 사람들과의 교류를 통해 즐거운 시간을 보내고 싶으신 분들, 지금 바로 신청해 주세요!', '2024-05-10 08:00:00', '보문산 () * 대전 중구 문화동 산 18-1', '127.42185539539828', '36.29619458682548', NULL),
('minho', '야구 모임', '야구', 15, '매주 수요일 오후에 야구를 즐기는 모임', '2024-05-25 18:00:00', '스트라이크존 대전둔산구장 (대전 서구 둔산로 18) * 대전 서구 둔산동 1081', '127.375728497456', '36.3518871992669', NULL),
('jaehyun', '러닝 모임', '러닝', 20, '안녕하세요! 주말 러닝 모임에 함께할 러닝 파트너를 모집합니다. 대전 서구 한밭수목원에서 각자의 페이스에 맞춰 자유롭게 달릴 수 있습니다. 운동 후에는 스트레칭과 간단한 간식을 즐기며 서로의 경험을 나눌 예정입니다. 많은 참여 부탁드립니다!', '2024-06-10 07:00:00', '샘머리공원 () * 대전 서구 둔산동 1379', '127.384869750369', '36.3567244475154', NULL),
('soyeon', '테니스 모임', '테니스', 8, '안녕하세요! 함께 테니스를 즐길 멤버들을 모집합니다. 대전 서구의 테니스장에서 초보자부터 숙련자까지 모두 환영합니다. 함께 라켓을 잡고 코트에서 즐거운 시간을 보내고, 운동 후에는 간단한 다과를 즐기며 친목을 다져보아요. 많은 관심과 참여 부탁드립니다!', '2024-06-15 10:00:00', '충남대학교 테니스장2 (대전 유성구 대학로 99) * 대전 유성구 궁동 220', '127.343378601824', '36.3747373824309', NULL),
('junho', '볼링 모임', '볼링', 6, '안녕하세요! 주말에 함께 볼링을 즐길 멤버들을 모집합니다. 초보자부터 숙련자까지 환영하며, 대전 서구의 볼링장에서 모임을 가질 예정입니다. 스트라이크의 짜릿함과 함께 즐거운 시간을 보내고 싶으신 분들, 지금 바로 신청해 주세요! 볼링 후에는 간단한 다과를 즐기며 친목을 다질 예정입니다. 많은 참여 부탁드립니다!', '2024-06-20 13:00:00', '매드블럭볼링장 (대전 유성구 대학로 60) * 대전 유성구 봉명동 612-4', '127.343981401194', '36.3587704570829', NULL),
('yejin', '탁구 모임', '탁구', 8, '안녕하세요! 탁구를 함께 즐길 멤버들을 모집합니다. 대전 서구 탄방동 체육관에서 모임을 가질 예정이며, 초보자도 환영합니다. 기본적인 탁구 기술부터 함께 연습하며 실력을 키워보아요. 운동 후에는 시원한 음료와 함께 자유롭게 대화를 나눌 시간도 가질 예정입니다. 많은 참여 부탁드립니다!', '2024-06-25 19:00:00', '씅튜브탁구클럽 (대전 유성구 한밭대로 414) * 대전 유성구 궁동 482-3', '127.345067265116', '36.3614474192426', NULL),
('yonghoon', '당구 모임', '당구', 4, '일요일 오후에 당구 치는 모임', '2024-06-30 16:00:00', '당구장 I', '130.5678', '37.7890', NULL),
('hyewon', '사이클링 모임', '사이클링', 12, '안녕하세요! 주말에 함께 사이클을 즐길 멤버들을 모집합니다. 대전 서구 한밭수목원에서 출발하여 다양한 경로를 달릴 예정입니다. 초보자도 환영하며, 각자의 페이스에 맞춰 자유롭게 달릴 수 있습니다. 운동 후에는 스트레칭과 간단한 간식을 즐기며 서로의 경험을 나눌 예정입니다. 많은 참여 부탁드립니다!', '2024-07-10 08:00:00', '엑스포시민광장 (대전 서구 둔산대로 169) * 대전 서구 만년동 396', '127.38808571649619', '36.368065219673205', NULL),
('hwanhee', '농구 모임', '농구', 10, '매주 토요일 오후에 농구 치는 모임', '2024-07-20 17:00:00', '농구장 M', '132.5678', '37.4321', NULL),
('sungjin', '야구 모임', '야구', 15, '토요일 오후에 야구 치는 모임', '2024-07-30 16:00:00', '야구장 O', '133.5678', '37.3456', NULL),
('sunwoo', '야구 모임', '야구', 12, '주말 오후 야구 모임', '2024-05-27 14:00:00', '야구장 P', '134.1234', '37.5678', NULL),
('sunwoo', '배드민턴 모임', '배드민턴', 8, '안녕하세요! 대전 서구 탄방동 체육관에서 배드민턴 동호회 멤버를 모집합니다. 초보자도 환영하며, 기본적인 라켓 사용법부터 경기 룰까지 함께 배울 수 있습니다. 운동 후에는 시원한 음료와 함께 자유로운 대화 시간을 가질 예정이니 많은 관심과 참여 부탁드립니다!', '2024-06-01 19:00:00', '한밭대학교 종합운동장 (대전 유성구 동서대로 125) * 대전 유성구 덕명동 16-1', '127.301240592964', '36.3536914765031', NULL),
('sunwoo', '등산 모임', '등산', 6, '주중 산행 모임', '2024-06-07 07:00:00', '산 X', '135.1234', '37.2468', NULL),
('jiwon', '풋살 모임', '축구/풋살', 10, '안녕하세요! 주말에 함께 축구를 즐길 멤버들을 모집합니다. 초보자부터 숙련자까지 환영하며, 대전 서구 남선공원 종합체육관에서 경기를 진행할 예정입니다. 함께 땀 흘리며 스트레스를 풀고, 경기 후에는 간단한 식사와 음료를 즐기며 친목을 다져보아요. 많은 참여 부탁드립니다!', '2024-06-05 21:00:00', '한밭풋살구장 (대전 유성구 현충원로 364) * 대전 유성구 구암동 498-9', '127.31134127472413', '36.3588239175646', NULL),
('jiwon', '테니스 모임', '테니스', 6, '주말 오전 테니스 즐기는 모임', '2024-06-10 10:00:00', '테니스장 S', '135.5678', '37.6543', NULL),
('jiwon', '볼링 모임', '볼링', 8, '평일 오후 볼링 모임', '2024-06-17 15:00:00', '볼링장 Y', '136.1234', '37.8765', NULL),
('mira', '클라이밍 모임', '클라이밍', 8, '주말 암벽등반 모임', '2024-06-15 12:00:00', '클라이밍장 T', '136.1234', '37.8765', NULL),
('mira', '사이클링 모임', '사이클링', 10, '평일 아침 사이클링 모임', '2024-06-20 07:00:00', '자전거길 U', '136.5678', '37.4321', NULL),
('mira', '야구 모임', '야구', 6, '평일 야구 모임', '2024-06-27 18:00:00', '야구장 Z', '137.1234', '37.9876', NULL),
('jisoo', '등산 모임', '등산', 6, '일요일 산행 모임', '2024-06-25 09:00:00', '산 V', '137.1234', '37.9876', NULL),
('jisoo', '탁구 모임', '탁구', 8, '평일 저녁 탁구 모임', '2024-06-30 18:00:00', '탁구장 W', '137.5678', '37.6543', NULL),
('jisoo', '농구 모임', '농구', 10, '주말 농구 모임', '2024-07-05 17:00:00', '농구장 AA', '138.1234', '37.8765', NULL);

-- DELETE FROM schedule
-- WHERE id=21 OR id=22 OR id=23;

SELECT * FROM schedule;

select count(DISTINCT manager_id) from schedule;

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
('jisoo', 18, 1), ('jisoo', 21, 1), ('jisoo', 22, 1), ('jisoo', 23, 1),
('hwanhee', 10, 1), ('hwanhee', 2, 1), ('hwanhee', 4, 0),
('jiwon', 3, 1), ('jiwon', 15, 1), ('jiwon', 16, 1), ('jiwon', 17, 1),
('sungjin', 8, 1), ('sungjin', 9, 1), ('sungjin', 11, 1);

SELECT * FROM userSchedule;



select * from friend;
INSERT INTO friend
VALUES(0, 'minho', 'jihye', 1), (0, 'sunwoo', 'jihye', 0), (0, 'jihye', 'jaehyun', 0), (0, 'hyewon', 'jihye', 0),
(0, 'mira', 'jihye', 0), (0, 'jiwon', 'jihye', 0);