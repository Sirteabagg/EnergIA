package com.example.api.DTO;


import com.example.api.dao.MeasureDAO;
import com.example.api.dto.BuildingAverageDTO;
import com.example.api.dto.SummaryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour SummaryDTO")
class SummaryDTOTest {

    @Test
    @DisplayName("Devrait créer un SummaryDTO avec toutes les valeurs")
    void shouldCreateSummaryDTOWithAllValues() {
        // Arrange
        List<BuildingAverageDTO> buildingAverages = Arrays.asList(
                new BuildingAverageDTO("Bâtiment A", 60.0f, 20.0f, 100.0f),
                new BuildingAverageDTO("Bâtiment B", 55.0f, 22.0f, 120.0f)
        );

        // Act
        SummaryDTO dto = new SummaryDTO(
                5,
                25,
                3,
                57.5f,
                21.0f,
                110.0f,
                buildingAverages
        );

        // Assert
        assertEquals(5, dto.getBuildings());
        assertEquals(25, dto.getSensors());
        assertEquals(3, dto.getAnomalies());
        assertEquals(57.5f, dto.getAverageHumidityGlobal(), 0.01);
        assertEquals(21.0f, dto.getAverageTemperatureGlobal(), 0.01);
        assertEquals(110.0f, dto.getAveragePowerConsumptionGlobal(), 0.01);
        assertEquals(2, dto.getAverages().size());
        assertSame(buildingAverages, dto.getAverages());
    }

    @Test
    @DisplayName("Devrait gérer des valeurs de zéro")
    void shouldHandleZeroValues() {
        // Arrange & Act
        SummaryDTO dto = new SummaryDTO(
                0, 0, 0, 0.0f, 0.0f, 0.0f,
                Collections.emptyList()
        );

        // Assert
        assertEquals(0, dto.getBuildings());
        assertEquals(0, dto.getSensors());
        assertEquals(0, dto.getAnomalies());
        assertEquals(0.0f, dto.getAverageHumidityGlobal());
        assertEquals(0.0f, dto.getAverageTemperatureGlobal());
        assertEquals(0.0f, dto.getAveragePowerConsumptionGlobal());
        assertTrue(dto.getAverages().isEmpty());
    }

    @Test
    @DisplayName("Devrait gérer une liste vide de moyennes de bâtiments")
    void shouldHandleEmptyBuildingAveragesList() {
        // Arrange & Act
        SummaryDTO dto = new SummaryDTO(
                10, 50, 2, 60.0f, 22.0f, 150.0f,
                Collections.emptyList()
        );

        // Assert
        assertTrue(dto.getAverages().isEmpty());
    }

    @Test
    @DisplayName("Devrait gérer une liste null de moyennes de bâtiments")
    void shouldHandleNullBuildingAveragesList() {
        // Arrange & Act
        SummaryDTO dto = new SummaryDTO(
                10, 50, 2, 60.0f, 22.0f, 150.0f,
                null
        );

        // Assert
        assertNull(dto.getAverages());
    }

    @Test
    @DisplayName("Devrait gérer des valeurs négatives pour les compteurs")
    void shouldHandleNegativeCounters() {
        // Arrange & Act
        SummaryDTO dto = new SummaryDTO(
                -1, -5, -2, 50.0f, 20.0f, 100.0f,
                Collections.emptyList()
        );

        // Assert
        assertEquals(-1, dto.getBuildings());
        assertEquals(-5, dto.getSensors());
        assertEquals(-2, dto.getAnomalies());
    }
}
