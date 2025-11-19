import sys
import os
import pandas as pd
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '../')))

from manage_data import clean_data

def setup_module(module):
    """Setup: Crée des fichiers de test pour simuler les données brutes."""
    raw_data_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../data/raw/energy_data_global.csv'))
    os.makedirs(os.path.dirname(raw_data_path), exist_ok=True)
    
    data = {
        'building_id': ['B001', 'B002', 'B003'],
        'sensor_id': ['S01', 'S02', 'S03'],
        'value': [100, 200, 300]
    }
    pd.DataFrame(data).to_csv(raw_data_path, index=False)

def teardown_module(module):
    """Teardown: Supprime les fichiers générés après les tests."""
    processed_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../data/processed/'))
    raw_data_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../data/raw/energy_data_global.csv'))
    
    if os.path.exists(raw_data_path):
        os.remove(raw_data_path)
    
    if os.path.exists(processed_path):
        for file in os.listdir(processed_path):
            os.remove(os.path.join(processed_path, file))

def test_clean_data():
    """Test principal pour la fonction clean_data."""
    clean_data()

    processed_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../data/processed/'))

    # Vérifie si les fichiers de sortie existent
    assert os.path.exists(os.path.join(processed_path, 'building.csv'))
    assert os.path.exists(os.path.join(processed_path, 'sensor.csv'))
    assert os.path.exists(os.path.join(processed_path, 'clean_global.csv'))

    # Vérifie le contenu des fichiers générés
    building_df = pd.read_csv(os.path.join(processed_path, 'building.csv'))
    sensor_df = pd.read_csv(os.path.join(processed_path, 'sensor.csv'))
    clean_df = pd.read_csv(os.path.join(processed_path, 'clean_global.csv'))

    assert len(building_df) == 3
    assert len(sensor_df) == 3
    assert len(clean_df) == 3

    assert 'building_id' in clean_df.columns
    assert 'sensor_id' in clean_df.columns