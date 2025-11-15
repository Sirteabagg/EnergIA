CREATE TABLE Building (
    building_id INT PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE Sensor (
    sensor_id INT PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE Measure (
    id INT PRIMARY KEY AUTO_INCREMENT,
    timestamp VARCHAR(25),
    temperature FLOAT,
    humidity FLOAT,
    power_consumption FLOAT,
    building_id INT,
    sensor_id INT,
    FOREIGN KEY (building_id) REFERENCES Building(building_id),
    FOREIGN KEY (sensor_id) REFERENCES Sensor(sensor_id)
);

CREATE TABLE Error (
    id INT PRIMARY KEY AUTO_INCREMENT,
    timestamp VARCHAR(25),
    temperature FLOAT,
    humidity FLOAT,
    power_consumption FLOAT,
    building_id INT,
    sensor_id INT,
    FOREIGN KEY (building_id) REFERENCES Building(building_id),
    FOREIGN KEY (sensor_id) REFERENCES Sensor(sensor_id)
);