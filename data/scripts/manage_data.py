import pandas as pd
import numpy as np

def clean_data():
    # Load data
    # 1. Chargement du dataset brut
    df = pd.read_csv('data/raw/energy_data_global.csv')

    # 2. Génération des tables de référence Building et Sensor
    # Bâtiment
    building_names = np.sort(df["building_id"].unique())
    df_building = pd.DataFrame({
        "building_id": range(len(building_names)),
        "name": building_names
    })
    df_building.to_csv('data/processed/building.csv', index=False)

    # Sensor
    sensor_names = np.sort(df["sensor_id"].unique())
    df_sensor = pd.DataFrame({
        "sensor_id": range(len(sensor_names)),
        "name": sensor_names
    })
    df_sensor.to_csv('data/processed/sensor.csv', index=False)

    # 3. Nettoyage du jeu de données principal
    df = df.dropna()
    df = df[df['power_consumption'] >= 0]
    df = df[df['temperature'] >= 0]

    # Suppression des outliers sur la puissance : filtre interquartile
    Q1 = df['power_consumption'].quantile(0.25)
    Q3 = df['power_consumption'].quantile(0.75)
    IQR = Q3 - Q1
    upper_limit = Q3 + 1.5 * IQR
    df = df[df['power_consumption'] < upper_limit]

    # 4. Remplacement des identifiants par les ID numériques
    # Building
    mapping_building = dict(zip(df_building['name'], df_building['building_id']))
    df['building_id'] = df['building_id'].replace(mapping_building)
    # Sensor
    mapping_sensor = dict(zip(df_sensor['name'], df_sensor['sensor_id']))
    df['sensor_id'] = df['sensor_id'].replace(mapping_sensor)


    # 5. Sauvegarde du jeu de données nettoyé et prêt
    df.to_csv('data/processed/clean_global.csv', index=False)
