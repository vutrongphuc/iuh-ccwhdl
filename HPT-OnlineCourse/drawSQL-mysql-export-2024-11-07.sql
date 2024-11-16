CREATE TABLE `user`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_name` VARCHAR(50) NOT NULL,
    `full_name` VARCHAR(255) NOT NULL,
    `role` VARCHAR(10) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL
);
ALTER TABLE
    `user` ADD UNIQUE `user_user_name_unique`(`user_name`);
CREATE TABLE `course`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `yt_id` VARCHAR(11) NOT NULL,
    `course_name` VARCHAR(255) NOT NULL,
    `course_desc` TEXT NOT NULL,
    `duration` VARCHAR(15) NOT NULL,
    `author` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL
);
ALTER TABLE
    `course` ADD UNIQUE `course_yt_id_unique`(`yt_id`);
CREATE TABLE `enrollment`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `course_id` BIGINT NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    PRIMARY KEY(`user_id`)
);
ALTER TABLE
    `enrollment` ADD PRIMARY KEY(`course_id`);
ALTER TABLE
    `enrollment` ADD CONSTRAINT `enrollment_course_id_foreign` FOREIGN KEY(`course_id`) REFERENCES `course`(`id`);
ALTER TABLE
    `enrollment` ADD CONSTRAINT `enrollment_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);