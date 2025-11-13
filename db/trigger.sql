DELIMITER //
CREATE TRIGGER verifie_sensors
BEFORE INSERT ON Measure
FOR EACH ROW
BEGIN
  IF NEW.humidity <= 0 OR NEW.humidity > 100 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Valeur d''humidité non valide';
  END IF;
  IF NEW.temperature <= 0 OR NEW.temperature > 40 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Valeur de température non valide';
  END IF;
  IF NEW.power_consumption <= 0 OR NEW.power_consumption > 1000 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Valeur de puissance non valide';
  END IF;
END;
//
DELIMITER ;
