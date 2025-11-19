import csv
import pymysql

import sys
import os
sys.path.append(os.path.abspath('data/scripts'))

import manage_data

manage_data.clean_data()

# Connexion à MySQL
conn = pymysql.connect(host="localhost", user="root", password="root", database="EnergIA")
cur = conn.cursor()

csv_out_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../data/processed/'))


files = {"Building": csv_out_path+"building.csv", "Sensor": csv_out_path+"sensor.csv", "Measure":csv_out_path + "clean_global.csv"}
for file in files:
    with open(files[file], "r", newline='') as csvfile:
        reader = csv.reader(csvfile)
        header = next(reader)
        print(header)
        print(file)
        columns = ", ".join(header)                
        placeholders = ", ".join(["%s"] * len(header)) 
        for row in reader:
            row = [None if v == '' else v for v in row]
            sql = f"""
                INSERT INTO {file}
                ({columns})
                VALUES ({placeholders})
            """
            print(row)
            try:
                cur.execute(sql, row)
            except pymysql.err.IntegrityError as e:
                print(f"Skipping duplicate: {row} ({e})")
        conn.commit()





cur.close()
conn.close()
