CREATE TABLE IF NOT EXISTS `factions` (
    `id` VARCHAR(100) NOT NULL DEFAULT UUID() UNIQUE PRIMARY KEY,
    `faction_name` VARCHAR(20) NOT NULL UNIQUE,
    `dtr` FLOAT NOT NULL,
    `ally_faction_id` VARCHAR(100),
    `faction_hq_x` INT NOT NULL,
    `faction_hq_y` INT NOT NULL,
    `faction_hq_z` INT NOT NULL,

--  claims
    `corner_1_x` INT NOT NULL,
    `corner_1_z` INT NOT NULL,
    `corner_2_x` INT NOT NULL,
    `corner_2_z` INT NOT NULL,
--

    `datetime_created` TIMESTAMP DEFAULT NOW() NOT NULL
);