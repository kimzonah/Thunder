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

##########################################

-- user 초기 데이터
-- Inserting data into the thunder_db.User table
INSERT INTO `thunder_db`.`User` (`id`, `password`, `name`, `favorite`, `image`, `org_image`) VALUES
('user1', 'password1', '롤링썬더', '러닝', '16d7467b-f867-4ec4-9b1f-d8c175d8de5d_image1.jpg', 'image1.jpg'),
('user2', 'password2', '푸바옹', '축구/풋살', '6171edb9-9d4d-46a0-a1b4-48456f343d7f_image2.jpg', 'image2.jpg'),
('user3', 'password3', '봉명동메시', '축구/풋살', '9babe3dd-63d4-4e66-b665-5d68b2677d54_image3.jpg', 'image3.jpg'),
('user4', 'password4', '김조나', '사이클링', '4092354d-5cfe-4013-a776-fe22ded6b003_image4.jpg', 'image4.jpg'),
('user5', 'password5', '봉봉', '야구', '09f2d700-5eb1-4b19-a6ba-26c9bea2fd5d_image5.png', 'image5.png'),
('user6', 'password6', '치코리타', '클라이밍', '2fac1465-cc41-4bcf-b5ad-71437ccced65_image6.JPG', 'image6.JPG'),
('user7', 'password7', '침착맨', '탁구', '1826fdfe-8829-4c27-8c7e-1d2cd56e2bae_image7.PNG', 'image7.PNG'),
('user8', 'password8', '나오린디', '볼링', 'acee6f67-17d6-40a0-8c59-5d4e238a3bac_image8.jpg', 'image8.jpg'),
('user9', 'password9', '하드혜', '기타', '76cd20c2-f01e-469e-94c7-2f6f98732deb_image9.jpg', 'image9.jpg');

select * from user;

-- Schedule 데이터를 삽입합니다.
INSERT INTO `thunder_db`.`Schedule` (`manager_id`, `title`, `category`, `scale`, `image`, `org_image`,`description`, `date_time`, `address_name`, `longitude`, `latitude`, `chat_url`)
VALUES
-- user1 (러닝)
('user1', '러닝 함께 해요!', '러닝', 5,'b755a391-6d96-4d1d-8fdb-efa5ab3dd0fd_2024022901258_0.jpg','2024022901258_0.jpg', '주말 아침 러닝 함께 하실 분 구합니다. 초보자 환영! 다 같이 건강하게 달려봐요. 즐겁게 운동합시다!', '2024-05-25 07:00:00', '샘머리공원 () * 대전 서구 둔산동 1379', '127.384869750369', '36.3567244475154', 'https://open.kakao.com/o/gd0bAitg'),
('user1', '자전거 타기 모임', '사이클링', 6,'ec3f65eb-b28b-4a18-9da4-ab5ffae29c5f_99EE79505B60443B2D.jpeg','99EE79505B60443B2D.jpeg', '안녕하세요! 주말에 함께 사이클을 즐길 멤버들을 모집합니다. 대전 서구 한밭수목원에서 출발하여 다양한 경로를 달릴 예정입니다. 초보자도 환영하며, 각자의 페이스에 맞춰 자유롭게 달릴 수 있습니다. 운동 후에는 스트레칭과 간단한 간식을 즐기며 서로의 경험을 나눌 예정입니다. 많은 참여 부탁드립니다!', '2024-05-26 08:00:00', '엑스포시민광장 (대전 서구 둔산대로 169) * 대전 서구 만년동 396', '127.38808571649619', '36.368065219673205', 'https://open.kakao.com/o/gd0bAitg'),

-- user2 (축구/풋살)
('user2', '주말 풋살 경기 모집', '축구/풋살', 10, '61d1b4cb-2ce7-475b-99df-25d064e2461f_mosaK9IpRU.jpeg','mosaK9IpRU.jpeg','주말에 풋살 경기 함께 하실 분들 구합니다. 초보자도 환영합니다! 다 같이 재미있게 운동해요. 함께 승리의 기쁨을 나눠요! 경기도 후에 맛있는 음식 먹어요!', '2024-05-27 16:00:00', '남선공원 종합체육관 풋살장 (대전 서구 남선로 66) * 대전 서구 탄방동 1084', '127.397991198843', '36.3462303652179', 'https://open.kakao.com/o/gd0bAitg'),
('user2', '테니스 파트너 구합니다', '테니스', 2, '4e59132b-fa07-436f-9606-665076e43c27_thumbnail_230912_73.jpg','thumbnail_230912_73.jpg','테니스 같이 치실 분 구해요. 재미있게 운동해요! 초보자도 환영입니다. 함께 즐거운 시간을 보내요!', '2024-05-28 09:00:00', '충남대학교 테니스장2 (대전 유성구 대학로 99) * 대전 유성구 궁동 220','127.343378601824', '36.3747373824309', 'https://open.kakao.com/o/gd0bAitg'),

-- user3 (축구/풋살)
('user3', '평일 저녁 축구 모임', '축구/풋살', 10, '8f5a4216-2e9f-4e13-816a-e2a1d86d4510_20231226_ndII8B.jpg','20231226_ndII8B.jpg','평일 저녁에 축구 하실 분 구해요. 같이 운동해요! 초보자도 환영합니다. 즐겁게 축구합시다!', '2024-05-29 19:00:00', '한밭풋살구장 (대전 유성구 현충원로 364) * 대전 유성구 구암동 498-9', '127.31134127472413', '36.3588239175646', 'https://open.kakao.com/o/gd0bAitg'),
('user3', '볼링 모임', '볼링', 4, 'a92a07bf-d580-41d1-9126-c660d7fb7f00_다운로드.jpeg','다운로드.jpeg','볼링 함께 하실 분 구합니다. 즐겁게 볼링 쳐요! 초보자도 환영입니다. 함께 즐거운 시간을 보내요! 점수 대결도 해봐요!', '2024-05-30 18:00:00', '매드블럭볼링장 (대전 유성구 대학로 60) * 대전 유성구 봉명동 612-4', '127.343981401194', '36.3587704570829', 'https://open.kakao.com/o/gd0bAitg'),
('user3', '등산 동호회', '등산', 5,'9a6c029b-bf97-4605-a3c5-eda5f95c5d95_image_readtop_2020_40491_15788910574049889.jpeg','image_readtop_2020_40491_15788910574049889.jpeg', '주말에 등산 함께 하실 분 구합니다. 건강하게 산책해요! 초보자도 환영합니다. 함께 자연을 만끽해요!', '2024-06-01 06:00:00', '보문산 () * 대전 중구 문화동 산 18-1', '127.42185539539828', '36.29619458682548', 'https://open.kakao.com/o/gd0bAitg'),

-- user5 (야구)
('user5', '야구 경기 팀원 모집', '야구', 9,'ca07706a-7f03-4b47-ad2c-d25780af746e_3731660748_vilEU1Mq_1.jpg','3731660748_vilEU1Mq_1.jpg', '스크린 야구 함께 하실 팀원 구합니다. 경험 있으신 분들도 좋고 처음 해보시는 분도 환영합니다. 재미있게 경기해요!', '2024-05-20 14:00:00', '스트라이크존 대전둔산구장 (대전 서구 둔산로 18) * 대전 서구 둔산동 1081', '127.375728497456', '36.3518871992669', 'https://open.kakao.com/o/gd0bAitg'), -- 과거 날짜
('user5', '농구 팀원 모집', '농구', 10,'8c6d72bf-4a42-4a8e-96ed-1c525e60b2c2_images.jpeg','images.jpeg', '농구 함께 하실 팀원 구합니다. 재미있게 운동해요! 초보자도 환영합니다. 다 같이 즐겁게 뛰어요!', '2024-05-23 17:00:00', '한밭대학교 종합운동장 (대전 유성구 동서대로 125) * 대전 유성구 덕명동 16-1', '127.301240592964', '36.3536914765031', 'https://open.kakao.com/o/gd0bAitg'),
('user5', '당구 모임', '당구', 4,'df624d90-a67e-4b9f-9dd5-c05751288c32_Billiards_balls.jpg','Billiards_balls.jpg', '당구 치실 분 구해요. 초보자도 환영합니다! 재미있게 당구 쳐요. 함께 즐거운 시간을 보내요.', '2024-05-24 20:00:00', '죠이24시당구장 (대전 유성구 온천로 53) * 대전 유성구 봉명동 537-6', '127.344034448253', '36.3556342152849', 'https://open.kakao.com/o/gd0bAitg'),

-- user6 (클라이밍)
('user6', '클라이밍 파트너 구합니다', '클라이밍', 3, '730696fb-d4fc-4be3-9593-096d08141b7d_IMG_8154.jpg','IMG_8154.jpg','클라이밍 함께 하실 분 구해요. 초보자 환영합니다! 같이 재미있게 운동해요. 안전하게 즐깁시다!', '2024-05-28 10:00:00', '어웨이크클라이밍 타임월드점 (대전 서구 둔산로 18) * 대전 서구 둔산동 1081', '127.37578430341091', '36.3644297151604', 'https://open.kakao.com/o/gd0bAitg'),

-- user7 (탁구)
('user7', '탁구 치실 분 구해요!', '탁구', 2, 'a4491856-eed4-4552-9cda-30785c81cae7_14825_15836_5842.jpg','14825_15836_5842.jpg','탁구 함께 하실 분 구합니다. 즐겁게 운동해요! 초보자도 환영합니다. 함께 즐거운 시간을 보내요.', '2024-05-29 18:00:00', '씅튜브탁구클럽 (대전 유성구 한밭대로 414) * 대전 유성구 궁동 482-3', '127.345067265116', '36.3614474192426', 'https://open.kakao.com/o/gd0bAitg'),
('user7', '러닝 모임', '러닝', 5,'753d5347-fbd6-4dca-95fc-5ddab8984432_2474663.jpg','2474663.jpg', '러닝 함께 하실 분 구합니다. 초보자 환영! 다 같이 건강하게 달려봐요. 즐겁게 운동합시다!', '2024-05-31 06:00:00', '보라매공원 () * 대전 서구 둔산동 1544', '127.38485318071415', '36.353584810306714', 'https://open.kakao.com/o/gd0bAitg'),
('user7', '사이클링 모임', '사이클링', 4,'9db32ddd-3690-4149-9200-e107b70e4a30_1599095705876.jpg','1599095705876.jpg', '사이클링 함께 하실 분 구해요. 즐겁게 운동해요! 초보자 환영합니다. 다 같이 건강하게 달려봐요!', '2024-05-15 18:00:00', '유성천자전거길 () * 대전 유성구 봉명동', '127.34564720770109', '36.359558683597214', 'https://open.kakao.com/o/gd0bAitg'); -- 과거 날짜

select * from schedule;

-- UserSchedule 데이터 삽입
INSERT INTO `thunder_db`.`UserSchedule` (`user_id`, `schedule_id`, `status`)
VALUES
('user1', 1, 1),
('user1', 2, 1),
('user2', 3, 1),
('user2', 4, 1),
('user3', 5, 1),
('user3', 6, 1),
('user3', 7, 1),
('user5', 8, 1),
('user5', 9, 1),
('user5', 10, 1),
('user6', 11, 1),
('user7', 12, 1),
('user7', 13, 1),
('user7', 14, 1);

-- UserSchedule 데이터 삽입
INSERT INTO `thunder_db`.`UserSchedule` (`user_id`, `schedule_id`, `status`)
VALUES
('user2', 2, 1),  -- user2는 번개에 소속된 상태
('user3', 2, 0),  -- user3는 번개에 신청 상태
('user4', 2, 0);  -- user4는 번개에 신청 상태

-- UserSchedule 데이터 삽입
INSERT INTO `thunder_db`.`UserSchedule` (`user_id`, `schedule_id`, `status`)
VALUES ('user1', 14, 1), ('user1',9,1);

select * from userschedule;

select * from album;

-- Friend 데이터 삽입
INSERT INTO `thunder_db`.`Friend` (`from_user_id`, `to_user_id`, `status`)
VALUES
('user1', 'user2', 1),  -- user1과 user2는 친구 상태
('user5', 'user1', 1),  -- user1과 user7은 친구 상태
('user9', 'user1', 1),  -- user1과 user9는 친구 상태
('user1', 'user3', 0),  -- user1이 user3에게 친구 요청을 보낸 상태
('user1', 'user5', 0),  -- user1이 user4에게 친구 요청을 보낸 상태
('user4', 'user1', 0),  -- user5가 user1에게 친구 요청을 보낸 상태
('user6', 'user1', 0);  -- user6가 user1에게 친구 요청을 보낸 상태


-- Board 데이터 삽입
INSERT INTO `thunder_db`.`Board` (`title`, `content`, `user_id`, `schedule_id`)
VALUES
('환영합니다! 자전거 타기 모임에 오신 것을 환영합니다', 
 '안녕하세요! 이번 자전거 타기 모임에 참여하게 되어 정말 기쁩니다. 함께 자전거를 타면서 좋은 시간을 보낼 수 있기를 기대합니다. 모두 즐겁게 타고 서로의 경험을 나누면서 건강하게 운동합시다. 곧 만나길 기대할게요!', 
 'user1', 
 2);

select * from board;

-- Comment 데이터 삽입
INSERT INTO `thunder_db`.`Comment` (`board_id`, `user_id`, `content`)
VALUES
(1, 'user2', '지난번 자전거 타기 모임이 정말 좋았어요! 이번 모임도 정말 기대됩니다. 모두 함께 즐겁게 달려봅시다!');

-- Board의 comment_cnt 증가
UPDATE `thunder_db`.`Board`
SET `comment_cnt` = `comment_cnt` + 1
WHERE `id` = 1;

select * from comment;

-- Album 데이터 삽입
INSERT INTO `thunder_db`.`Album` (`user_id`, `image`, `org_image`, `schedule_id`)
VALUES
('user1', '8fe40dcb-5de6-4226-a965-70d8e92006e0_caption.jpg', 'caption.jpg', 14);
