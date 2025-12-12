package com.example.api;

import com.example.api.dao.*;
import com.example.api.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Tests pour DataController")
public class DataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    @DisplayName("GET /data/summary devrait retourner le résumé avec status 200")
    public void testGetSummary() throws Exception {
        // Arrange
        List<Float> averages = Arrays.asList(60.5f, 21.3f, 120.0f);
        List<Integer> buildingIds = Arrays.asList(1, 2);
        List<Float> buildingAvg1 = Arrays.asList(65.0f, 22.0f, 130.0f);
        List<Float> buildingAvg2 = Arrays.asList(55.0f, 20.0f, 110.0f);

        ConnectionDAO mockConnection = mock(ConnectionDAO.class);

        // ✅ Mock statique de ConnectionDAO.getInstance
        try (MockedStatic<ConnectionDAO> connectionMock = mockStatic(ConnectionDAO.class);

             // ✅ Mock des constructeurs avec MockedConstruction
             MockedConstruction<BuildingDAO> buildingConstruction = mockConstruction(BuildingDAO.class,
                     (mock, context) -> {
                         when(mock.getNumberOfBuildings()).thenReturn(2);
                         when(mock.getAllIds()).thenReturn(buildingIds);
                         when(mock.getBuildingNameById(1)).thenReturn("Bâtiment A");
                         when(mock.getBuildingNameById(2)).thenReturn("Bâtiment B");
                     });

             MockedConstruction<SensorDAO> sensorConstruction = mockConstruction(SensorDAO.class,
                     (mock, context) -> {
                         when(mock.getNumberOfSensors()).thenReturn(10);
                     });

             MockedConstruction<ErrorDAO> errorConstruction = mockConstruction(ErrorDAO.class,
                     (mock, context) -> {
                         when(mock.getNumberAnomalies()).thenReturn(3);
                     });

             MockedConstruction<MeasureDAO> measureConstruction = mockConstruction(MeasureDAO.class,
                     (mock, context) -> {
                         when(mock.getAverageMeasures()).thenReturn(averages);
                         when(mock.getAverageMeasureByBuilding(1)).thenReturn(buildingAvg1);
                         when(mock.getAverageMeasureByBuilding(2)).thenReturn(buildingAvg2);
                     })) {

            connectionMock.when(() -> ConnectionDAO.getInstance(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act & Assert
            mockMvc.perform(get("/data/summary")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.buildings").value(2))
                    .andExpect(jsonPath("$.sensors").value(10))
                    .andExpect(jsonPath("$.anomalies").value(3))
                    .andExpect(jsonPath("$.averageHumidityGlobal").value(60.5))
                    .andExpect(jsonPath("$.averageTemperatureGlobal").value(21.3))
                    .andExpect(jsonPath("$.averagePowerConsumptionGlobal").value(120.0))
                    .andExpect(jsonPath("$.averages").isArray())
                    .andExpect(jsonPath("$.averages.length()").value(2));
        }
    }

    @Test
    @WithMockUser
    @DisplayName("GET /data/buildings/{id} devrait retourner un bâtiment avec status 200")
    public void testGetBuilding() throws Exception {
        // Arrange
        List<MeasureDAO.Measure> measures = Arrays.asList(
                mock(MeasureDAO.Measure.class),
                mock(MeasureDAO.Measure.class)
        );

        ConnectionDAO mockConnection = mock(ConnectionDAO.class);

        try (MockedStatic<ConnectionDAO> connectionMock = mockStatic(ConnectionDAO.class);
             MockedConstruction<BuildingDAO> buildingConstruction = mockConstruction(BuildingDAO.class,
                     (mock, context) -> {
                         when(mock.getBuildingNameById(1)).thenReturn("Tour Eiffel");
                     });
             MockedConstruction<MeasureDAO> measureConstruction = mockConstruction(MeasureDAO.class,
                     (mock, context) -> {
                         when(mock.getMeasuresByBuilding(1)).thenReturn(measures);
                     })) {

            connectionMock.when(() -> ConnectionDAO.getInstance(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act & Assert
            mockMvc.perform(get("/data/buildings/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Tour Eiffel"))
                    .andExpect(jsonPath("$.measures").isArray())
                    .andExpect(jsonPath("$.measures.length()").value(2));
        }
    }

    @Test
    @WithMockUser
    @DisplayName("GET /data/buildings/{id} avec ID invalide devrait gérer l'erreur")
    public void testGetBuildingWithInvalidId() throws Exception {
        ConnectionDAO mockConnection = mock(ConnectionDAO.class);

        try (MockedStatic<ConnectionDAO> connectionMock = mockStatic(ConnectionDAO.class);
             MockedConstruction<BuildingDAO> buildingConstruction = mockConstruction(BuildingDAO.class,
                     (mock, context) -> {
                         when(mock.getBuildingNameById(999))
                                 .thenThrow(new RuntimeException("Building not found"));
                     });
             MockedConstruction<MeasureDAO> measureConstruction = mockConstruction(MeasureDAO.class)) {

            connectionMock.when(() -> ConnectionDAO.getInstance(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act & Assert
            mockMvc.perform(get("/data/buildings/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
        }
    }

    @Test
    @WithMockUser
    @DisplayName("GET /data/data/building avec dates devrait retourner la période moyenne")
    public void testGetAverageConsumptionByBuilding() throws Exception {
        // Arrange
        List<MeasureDAO.AverageConsumption> averageConsumptions = Arrays.asList(
                mock(MeasureDAO.AverageConsumption.class),
                mock(MeasureDAO.AverageConsumption.class)
        );

        ConnectionDAO mockConnection = mock(ConnectionDAO.class);

        try (MockedStatic<ConnectionDAO> connectionMock = mockStatic(ConnectionDAO.class);
             MockedConstruction<MeasureDAO> measureConstruction = mockConstruction(MeasureDAO.class,
                     (mock, context) -> {
                         when(mock.getAverageConsumptionByBuilding("2024-01-01", "2024-01-31"))
                                 .thenReturn(averageConsumptions);
                     })) {

            connectionMock.when(() -> ConnectionDAO.getInstance(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act & Assert
            mockMvc.perform(get("/data/data/building")
                            .param("start", "2024-01-01")
                            .param("end", "2024-01-31")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.startDate").value("2024-01-01"))
                    .andExpect(jsonPath("$.endDate").value("2024-01-31"))
                    .andExpect(jsonPath("$.averageConsumption").isArray())
                    .andExpect(jsonPath("$.averageConsumption.length()").value(2));
        }
    }

    @Test
    @WithMockUser
    @DisplayName("GET /data/data/building/overconsumption devrait retourner les bâtiments en surconsommation")
    public void testGetOverconsumingBuildings() throws Exception {
        // Arrange
        List<String> overconsuming = Arrays.asList("Bâtiment A", "Bâtiment C");

        ConnectionDAO mockConnection = mock(ConnectionDAO.class);

        try (MockedStatic<ConnectionDAO> connectionMock = mockStatic(ConnectionDAO.class);
             MockedConstruction<BuildingDAO> buildingConstruction = mockConstruction(BuildingDAO.class,
                     (mock, context) -> {
                         when(mock.getOverconsummingBuilding()).thenReturn(overconsuming);
                     })) {

            connectionMock.when(() -> ConnectionDAO.getInstance(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act & Assert
            mockMvc.perform(get("/data/data/building/overconsumption")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0]").value("Bâtiment A"))
                    .andExpect(jsonPath("$[1]").value("Bâtiment C"));
        }
    }

    @Test
    @WithMockUser
    @DisplayName("GET /data/anomalies devrait retourner les capteurs avec anomalies")
    public void testGetAnomalies() throws Exception {
        // Arrange
        List<Integer> sensorIds = Arrays.asList(1, 2);
        List<MeasureDAO.Measure> measures1 = Arrays.asList(mock(MeasureDAO.Measure.class));
        List<MeasureDAO.Measure> measures2 = Arrays.asList(mock(MeasureDAO.Measure.class));

        ConnectionDAO mockConnection = mock(ConnectionDAO.class);

        try (MockedStatic<ConnectionDAO> connectionMock = mockStatic(ConnectionDAO.class);
             MockedStatic<MeasureDAO> measureStaticMock = mockStatic(MeasureDAO.class);
             MockedConstruction<SensorDAO> sensorConstruction = mockConstruction(SensorDAO.class,
                     (mock, context) -> {
                         when(mock.getAllIds()).thenReturn(sensorIds);
                         when(mock.getSensorNameById(1)).thenReturn("Capteur-001");
                         when(mock.getSensorNameById(2)).thenReturn("Capteur-002");
                     })) {

            connectionMock.when(() -> ConnectionDAO.getInstance(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            measureStaticMock.when(() -> MeasureDAO.getMeasuresBySensors(1)).thenReturn(measures1);
            measureStaticMock.when(() -> MeasureDAO.getMeasuresBySensors(2)).thenReturn(measures2);

            // Act & Assert
            mockMvc.perform(get("/data/anomalies")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].name").value("Capteur-001"))
                    .andExpect(jsonPath("$[1].name").value("Capteur-002"));
        }
    }

    @Test
    @WithMockUser
    @DisplayName("GET /data/summary devrait gérer les exceptions et retourner null")
    public void testGetSummaryWithException() throws Exception {
        ConnectionDAO mockConnection = mock(ConnectionDAO.class);

        try (MockedStatic<ConnectionDAO> connectionMock = mockStatic(ConnectionDAO.class);
             MockedConstruction<BuildingDAO> buildingConstruction = mockConstruction(BuildingDAO.class,
                     (mock, context) -> {
                         when(mock.getNumberOfBuildings())
                                 .thenThrow(new RuntimeException("Database error"));
                     });
             MockedConstruction<SensorDAO> sensorConstruction = mockConstruction(SensorDAO.class);
             MockedConstruction<ErrorDAO> errorConstruction = mockConstruction(ErrorDAO.class);
             MockedConstruction<MeasureDAO> measureConstruction = mockConstruction(MeasureDAO.class)) {

            connectionMock.when(() -> ConnectionDAO.getInstance(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act & Assert
            mockMvc.perform(get("/data/summary")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
        }
    }
}
