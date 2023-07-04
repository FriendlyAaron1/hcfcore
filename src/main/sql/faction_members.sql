CREATE TABLE IF NOT EXISTS `faction_members` (
    `faction_id` VARCHAR(100) NOT NULL,
    `player_uuid` VARCHAR(100) NOT NULL UNIQUE,

--    5 = leader, 4 = co-owner, 3 = officer, 0 = member
    `rank` SMALLINT NOT NULL DEFAULT 0
)