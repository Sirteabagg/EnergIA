DROP TRIGGER IF EXISTS verifie_sensors;

DELIMITER //
CREATE TRIGGER verifie_sensors
BEFORE INSERT ON Measure
FOR EACH ROW
BEGIN
  DECLARE erreur TEXT DEFAULT NULL;

  IF NEW.humidity <= 0 OR NEW.humidity > 100 THEN
    SET erreur = 'Valeur d''humidité non valide';
  END IF;
  IF NEW.temperature <= 0 OR NEW.temperature > 40 THEN
    SET erreur = 'Valeur de température non valide';
  END IF;
  IF NEW.power_consumption <= 0 OR NEW.power_consumption > 1000 THEN
    SET erreur = 'Valeur de puissance non valide';
  END IF;

  IF erreur IS NOT NULL THEN
    INSERT INTO Error(timestamp,humidity, temperature, power_consumption, building_id, sensor_id)
      VALUES (NEW.timestamp, NEW.humidity, NEW.temperature, NEW.power_consumption, NEW.building_id, NEW.sensor_id);
  END IF;

END;
//
DELIMITER ;


