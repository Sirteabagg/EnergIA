import pandas as pd
import numpy as np



def clean_data():
    # Load data
    # 1. Chargement du dataset brut
    df = df_origin = pd.read_csv('data/raw/energy_data_global.csv')

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

    # 4. Remplacement des identifiants par les ID numériques
    # replace Building_id by there id in buidling.csv B001 = 0, B002 = 1, B003 = 2 etc
    mapping_dict = dict(zip(df_building['name'], df_building['building_id']))

    df['building_id'] = df['building_id'].replace(mapping_dict)
    df_origin['building_id'] = df_origin['building_id'].replace(mapping_dict)


    # replace Sensor_id by there id in sensor.csv S01 = 0, S02 = 1, S03 = 2 etc
    mapping_dict = dict(zip(df_sensor['name'], df_sensor['sensor_id']))

    df['sensor_id'] = df['sensor_id'].replace(mapping_dict)
    df_origin['sensor_id'] = df_origin['sensor_id'].replace(mapping_dict)

    df_removed = (
        df_origin
        .merge(df, how='outer', indicator=True)
        .query("_merge == 'left_only'")
        .drop(columns=["_merge"])
    )


    
    # 5. Sauvegarde du jeu de données nettoyé et prêt
    df.to_csv('data/processed/clean_global.csv', index=False)
    df_removed.to_csv('data/processed/error_measure.csv', index=False)   
