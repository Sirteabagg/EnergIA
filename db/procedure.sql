
DROP PROCEDURE IF EXISTS get_average_consumption_by_building;
DELIMITER $$

CREATE PROCEDURE get_average_consumption_by_building(IN date_start DATE, IN date_end DATE)
BEGIN
    SELECT building_id, AVG(value) AS average_consumption
    FROM consommations
    WHERE timestamp BETWEEN date_start AND date_end
    GROUP BY building_id;
END;

DELIMITER ;


DROP PROCEDURE IF EXISTS get_error_outliers_measure_by_sensor;
DELIMITER $$
CREATE PROCEDURE get_error_outliers_measure_by_sensor()
BEGIN
    SELECT
        s.name,
        e.temperature,
        e.humidity,
        e.power_consumption
       
    FROM Error e JOIN Sensor s ON e.sensor_id = s.sensor_id;
    
END $$

DELIMITER ;


DROP PROCEDURE IF EXISTS get_buildings_with_overconsumption;
DELIMITER $$
CREATE PROCEDURE get_buildings_with_overconsumption()
BEGIN
    SELECT b.name
    FROM Measure m JOIN Building b ON m.building_id = b.building_id
    GROUP BY m.building_id
    HAVING AVG(power_consumption) > (SELECT AVG(power_consumption) FROM Measure);
END;$$

DELIMITER ;

DROP PROCEDURE IF EXISTS get_building_info_by_id;
DELIMITER $$
CREATE PROCEDURE get_building_info_by_id(IN p_building_id INT)
BEGIN
    SELECT
        timestamp,
        temperature,
        humidity,
        power_consumption
    FROM Measure 
   	WHERE building_id = p_building_id
   	ORDER BY timestamp ASC;
   
END $$

DELIMITER ;
