CREATE TABLE `auth`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `birthday` DATETIME NULL,
  `address` VARCHAR(100) NULL,
  `phone` VARCHAR(20) NULL,
  `email` VARCHAR(45) NOT NULL,
  `base_salary` DECIMAL NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);
