CREATE TABLE `users` (
    `id_user` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(100) UNIQUE NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `user` VARCHAR(20) UNIQUE NOT NULL,
    `password` VARCHAR(100) NOT NULL
);

CREATE TABLE `roles` (
    `id_role` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `role` VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE `privileges` (
    `id_privilege` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `privilege` VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE `roles_privileges` (
    `id_privilege` INT NOT NULL,
    `id_role` INT NOT NULL,
    PRIMARY KEY (`id_privilege`, `id_role`)
);

CREATE TABLE `users_roles` (
    `id_user` INT NOT NULL,
    `id_role` INT NOT NULL,
    PRIMARY KEY (`id_user`, `id_role`)
);

ALTER TABLE `users_roles` ADD FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);

ALTER TABLE `users_roles` ADD FOREIGN KEY (`id_role`) REFERENCES `roles` (`id_role`);

ALTER TABLE `roles_privileges` ADD FOREIGN KEY (`id_role`) REFERENCES `roles` (`id_role`);

ALTER TABLE `roles_privileges` ADD FOREIGN KEY (`id_privilege`) REFERENCES `privileges` (`id_privilege`);