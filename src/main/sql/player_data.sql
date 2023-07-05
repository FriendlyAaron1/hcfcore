CREATE TABLE IF NOT EXITS `player_data` (
    `player_id` VARCHAR(100) NOT NULL UNIQUE,
    `balance` INT NOT NULL DEFAULT 0,
    `kills` INT NOT NULL DEFAULT 0
);