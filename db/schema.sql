CREATE TABLE Building (
    building_id INT PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE Sensor (
    sensor_id INT PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE Measure (
    id INT PRIMARY KEY,
    temperature FLOAT,
    humidity FLOAT,
    power_consumption FLOAT,
    id_building INT,
    id_sensor INT,
    FOREIGN KEY (id_building) REFERENCES Building(building_id),
    FOREIGN KEY (id_sensor) REFERENCES Sensor(sensor_id)
);

