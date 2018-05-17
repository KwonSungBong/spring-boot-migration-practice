CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_at` datetime DEFAULT NULL,
  `is_used` bigint(1) NOT NULL DEFAULT '1',
  `full_path` varchar(100) DEFAULT NULL,
  `full_path_name` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcmtwvovxcdalvlgifxh6luun8` (`parent`) USING BTREE,
  CONSTRAINT `FKcmtwvovxcdalvlgifxh6luun8` FOREIGN KEY (`parent`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8;

CREATE TABLE `program` (
  `id` varchar(255) NOT NULL,
  `cast` varchar(255) DEFAULT NULL,
  `category1_name` varchar(255) DEFAULT NULL,
  `category2_name` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `grade` int(11) DEFAULT 0,
  `name` varchar(255) DEFAULT NULL,
  `original_name` varchar(255) DEFAULT NULL,
  `product_country` varchar(255) DEFAULT NULL,
  `product_year` int(11) NOT NULL,
  `production` varchar(255) DEFAULT NULL,
  `summary` text,
  `category` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2wep6dkcpotc85hj9vcubsrll` (`category`),
  CONSTRAINT `FK2wep6dkcpotc85hj9vcubsrll` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category(`name`, `is_used`) value("PROGRAM", 1);
UPDATE category SET `full_path`=id, `full_path_name`=`name` WHERE `name`="PROGRAM" AND `parent` IS NULL;

INSERT INTO category(`name`, `is_used`) value("SEGMENT", 1);
UPDATE category SET `full_path`=id, `full_path_name`=`name` WHERE `name`="SEGMENT" AND `parent` IS NULL;

CREATE TABLE `segment` (
  `id` bigint auto_increment PRIMARY KEY,
  `configuration` varchar(4000) NOT NULL,
  `name` varchar(255) NULL,
  `description` varchar(255) NULL,
  `created_at` datetime default CURRENT_TIMESTAMP NOT NULL,
  `modified_at` datetime NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `segment_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `segment` bigint(20) NOT NULL,
  `category` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL default now(),
  `modified_at` datetime default NULL,
  PRIMARY KEY (`id`),
  KEY `fk_segment_category_idx1` (`segment`),
  CONSTRAINT `fk_segment_category_11` FOREIGN KEY (`segment`) REFERENCES `segment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_segment_category_22` FOREIGN KEY (`category`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

