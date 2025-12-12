package com.example.api.DTO;

import com.example.api.dto.BuildingAverageDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour BuildingAverageDTO")
class BuildingAverageDTOTest {

    @Test
    @DisplayName("Devrait créer un BuildingAverageDTO avec toutes les valeurs")
    void shouldCreateBuildingAverageDTOWithAllValues() {
        // Arrange & Act
        BuildingAverageDTO dto = new BuildingAverageDTO(
                "Bâtiment A",
                65.5f,
                22.3f,
                150.8f
        );

        // Assert
        assertEquals("Bâtiment A", dto.getBuildingName());
        assertEquals(65.5f, dto.getAverageHumidity(), 0.01);
        assertEquals(22.3f, dto.getAverageTemperature(), 0.01);
        assertEquals(150.8f, dto.getAveragePowerConsumption(), 0.01);
    }

    @Test
    @DisplayName("Devrait gérer des valeurs nulles pour le nom du bâtiment")
    void shouldHandleNullBuildingName() {
        // Arrange & Act
        BuildingAverageDTO dto = new BuildingAverageDTO(null, 50.0f, 20.0f, 100.0f);

        // Assert
        assertNull(dto.getBuildingName());
    }

    @Test
    @DisplayName("Devrait gérer des valeurs de zéro")
    void shouldHandleZeroValues() {
        // Arrange & Act
        BuildingAverageDTO dto = new BuildingAverageDTO("Test", 0.0f, 0.0f, 0.0f);

        // Assert
        assertEquals(0.0f, dto.getAverageHumidity());
        assertEquals(0.0f, dto.getAverageTemperature());
        assertEquals(0.0f, dto.getAveragePowerConsumption());
    }

    @Test
    @DisplayName("Devrait gérer des valeurs négatives")
    void shouldHandleNegativeValues() {
        // Arrange & Act
        BuildingAverageDTO dto = new BuildingAverageDTO("Test", -5.0f, -10.0f, -20.0f);

        // Assert
        assertEquals(-5.0f, dto.getAverageHumidity());
        assertEquals(-10.0f, dto.getAverageTemperature());
        assertEquals(-20.0f, dto.getAveragePowerConsumption());
    }
}
