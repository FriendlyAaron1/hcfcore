CREATE TABLE IF NOT EXISTS `members` (
    `faction_id` VARCHAR(100) NOT NULL,
    `player_uuid` VARCHAR(100) NOT NULL UNIQUE,
    `rank` SMALLINT NOT NULL
)