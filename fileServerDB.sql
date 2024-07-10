# create database fileServer;
# create  table t_user (
#                          `id` int(8) NOT NULL,
#                          `username` varchar(80) DEFAULT NULL,
#                          `password` varchar(80) DEFAULT NULL,
#                          PRIMARY KEY (`id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# CREATE TABLE `t_files` (
#                            `id` int(8) NOT NULL AUTO_INCREMENT,
#                            `oldFileName` varchar(200) DEFAULT NULL,
#                            `newFileName` varchar(300) DEFAULT NULL,
#                            `ext` varchar(20) DEFAULT NULL,
#                            `path` varchar(300) DEFAULT NULL,
#                            `size` varchar(200) DEFAULT NULL,
#                            `type` varchar(120) DEFAULT NULL,
#                            `isImg` varchar(8) DEFAULT NULL,
#                            `downcounts` int(6) DEFAULT NULL,
#                            `uploadTime` datetime DEFAULT NULL,
#                            `userId` int(8) DEFAULT NULL,
#                            PRIMARY KEY (`id`),
#                            KEY `userId` (`userId`),
#                            CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
# ) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;


