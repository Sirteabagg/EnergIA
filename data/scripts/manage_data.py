import os
import pandas as pd
import numpy as np



def clean_data():
    # Load data
    # 1. Chargement du dataset brut
    csv_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../../data/raw/energy_data_global.csv'))
    csv_out_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../../data/processed/'))
    df  = pd.read_csv(csv_path)

    # 2. Génération des tables de référence Building et Sensor
    # Bâtiment
    building_names = np.sort(df["building_id"].unique())
    df_building = pd.DataFrame({
        "building_id": range(len(building_names)),
        "name": building_names
    })
    df_building.to_csv(csv_out_path + 'building.csv', index=False)

    # Sensor
    sensor_names = np.sort(df["sensor_id"].unique())
    df_sensor = pd.DataFrame({
        "sensor_id": range(len(sensor_names)),
        "name": sensor_names
    })
    df_sensor.to_csv(csv_out_path + 'sensor.csv', index=False)

    

    # 4. Remplacement des identifiants par les ID numériques
    # replace Building_id by there id in buidling.csv B001 = 0, B002 = 1, B003 = 2 etc
    mapping_dict = dict(zip(df_building['name'], df_building['building_id']))

    df['building_id'] = df['building_id'].replace(mapping_dict)

    # replace Sensor_id by there id in sensor.csv S01 = 0, S02 = 1, S03 = 2 etc
    mapping_dict = dict(zip(df_sensor['name'], df_sensor['sensor_id']))

    df['sensor_id'] = df['sensor_id'].replace(mapping_dict)

    # 5. Sauvegarde du jeu de données nettoyé et prêt
    df.to_csv(csv_out_path+"clean_global.csv", index=False) 
