package com.example.api.DTO;

import com.example.api.dao.MeasureDAO;
import com.example.api.dto.BuildingDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour BuildingDTO")
class BuildingDTOTest {

    @Test
    @DisplayName("Devrait créer un BuildingDTO avec un nom et des mesures")
    void shouldCreateBuildingDTOWithNameAndMeasures() {
        // Arrange
        List<MeasureDAO.Measure> measures = Arrays.asList(
                new MeasureDAO.Measure("12-12-2012", 0.0f,0.0f,
                        0.0f),
                new MeasureDAO.Measure("12-12-2012", 0.0f,0.0f,
                        0.0f)
        );

        // Act
        BuildingDTO dto = new BuildingDTO("Tour Eiffel", measures);

        // Assert
        assertEquals("Tour Eiffel", dto.getName());
        assertEquals(2, dto.getMeasures().size());
        assertSame(measures, dto.getMeasures());
    }

    @Test
    @DisplayName("Devrait gérer une liste vide de mesures")
    void shouldHandleEmptyMeasuresList() {
        // Arrange & Act
        BuildingDTO dto = new BuildingDTO("Bâtiment Vide", Collections.emptyList());

        // Assert
        assertTrue(dto.getMeasures().isEmpty());
    }

    @Test
    @DisplayName("Devrait gérer un nom null")
    void shouldHandleNullBuildingName() {
        // Arrange & Act
        BuildingDTO dto = new BuildingDTO(null, Collections.emptyList());

        // Assert
        assertNull(dto.getName());
    }

    @Test
    @DisplayName("Devrait gérer une liste null de mesures")
    void shouldHandleNullMeasuresList() {
        // Arrange & Act
        BuildingDTO dto = new BuildingDTO("Test Building", null);

        // Assert
        assertNull(dto.getMeasures());
    }
}
