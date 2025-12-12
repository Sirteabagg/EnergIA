package com.example.api.DTO;

import com.example.api.dao.MeasureDAO;
import com.example.api.dto.BuildingAvgPeriodDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour BuildingAvgPeriodDTO")
class BuildingAvgPeriodDTOTest {

    @Test
    @DisplayName("Devrait créer un BuildingAvgPeriodDTO avec des dates et une liste de mesures")
    void shouldCreateBuildingAvgPeriodDTOWithDatesAndMeasures() {
        // Arrange
        List<MeasureDAO.AverageConsumption> measures = Arrays.asList(
               new MeasureDAO.AverageConsumption("B001", 100),
                new MeasureDAO.AverageConsumption("B002", 10.f)
        );

        // Act
        BuildingAvgPeriodDTO dto = new BuildingAvgPeriodDTO(
                "2024-01-01",
                "2024-01-31",
                measures
        );

        // Assert
        assertEquals("2024-01-01", dto.getStartDate());
        assertEquals("2024-01-31", dto.getEndDate());
        assertEquals(2, dto.getAverageConsumption().size());
        assertSame(measures, dto.getAverageConsumption());
    }

    @Test
    @DisplayName("Devrait gérer une liste vide de mesures")
    void shouldHandleEmptyMeasuresList() {
        // Arrange & Act
        BuildingAvgPeriodDTO dto = new BuildingAvgPeriodDTO(
                "2024-01-01",
                "2024-01-31",
                Collections.emptyList()
        );

        // Assert
        assertTrue(dto.getAverageConsumption().isEmpty());
    }

    @Test
    @DisplayName("Devrait gérer des dates nulles")
    void shouldHandleNullDates() {
        // Arrange & Act
        BuildingAvgPeriodDTO dto = new BuildingAvgPeriodDTO(
                null,
                null,
                Collections.emptyList()
        );

        // Assert
        assertNull(dto.getStartDate());
        assertNull(dto.getEndDate());
    }

    @Test
    @DisplayName("Devrait gérer une liste null de mesures")
    void shouldHandleNullMeasuresList() {
        // Arrange & Act
        BuildingAvgPeriodDTO dto = new BuildingAvgPeriodDTO(
                "2024-01-01",
                "2024-01-31",
                null
        );

        // Assert
        assertNull(dto.getAverageConsumption());
    }
}

