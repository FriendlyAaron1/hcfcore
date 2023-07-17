CREATE TABLE IF NOT EXISTS `player_data` (
    `player_id` VARCHAR(100) NOT NULL UNIQUE,
    `balance` INT NOT NULL DEFAULT 500,
    `kills` INT NOT NULL DEFAULT 0
);