package com.example.api.DTO;

import com.example.api.dao.MeasureDAO;
import com.example.api.dto.SensorDTO;
import com.example.api.model.Measure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour SensorDTO")
class SensorDTOTest {

    @Test
    @DisplayName("Devrait créer un SensorDTO avec un nom et des mesures")
    void shouldCreateSensorDTOWithNameAndMeasures() {
        MeasureDAO.Measure measure1 = new MeasureDAO.Measure("12-12-2012", 0.0f,0.0f,
                0.0f);
        MeasureDAO.Measure measure2 = new MeasureDAO.Measure("12-12-2012", 0.0f,0.0f,
                0.0f);
        MeasureDAO.Measure measure3 = new MeasureDAO.Measure("12-12-2012", 0.0f,0.0f,
                0.0f);
        // Arrange
        List<MeasureDAO.Measure> measures = List.of(measure1, measure2, measure3);

        // Act
        SensorDTO dto = new SensorDTO("Capteur-001", measures);

        // Assert
        assertEquals("Capteur-001", dto.getName());
        assertEquals(3, dto.getMeasures().size());
        assertSame(measures, dto.getMeasures());
    }

    @Test
    @DisplayName("Devrait gérer une liste vide de mesures")
    void shouldHandleEmptyMeasuresList() {
        // Arrange & Act
        SensorDTO dto = new SensorDTO("Capteur-002", Collections.emptyList());

        // Assert
        assertEquals("Capteur-002", dto.getName());
        assertTrue(dto.getMeasures().isEmpty());
    }

    @Test
    @DisplayName("Devrait gérer un nom null")
    void shouldHandleNullName() {
        // Arrange & Act
        SensorDTO dto = new SensorDTO(null, Collections.emptyList());

        // Assert
        assertNull(dto.getName());
    }

    @Test
    @DisplayName("Devrait gérer une liste null de mesures")
    void shouldHandleNullMeasuresList() {
        // Arrange & Act
        SensorDTO dto = new SensorDTO("Capteur-003", null);

        // Assert
        assertNull(dto.getMeasures());
    }
}
