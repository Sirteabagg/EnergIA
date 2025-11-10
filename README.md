```text
EnergIA/
├── README.md                  # Explications du projet, installation, usage
├── requirements.txt           # Dépendances Python (si besoin)
├── pom.xml                    # Dépendances Java/Spring Boot (si Maven)
├── .gitignore                 # Fichiers à ignorer dans Git
├── data/
│   ├── raw/                   # Données originales (CSV, Excel, etc.)
│   ├── processed/              # Données nettoyées/transformées
│   └── scripts/                # Scripts Python pour le traitement & nettoyage
├── db/
│   ├── schema.sql              # Script création des tables SQL
│   ├── queries.sql             # Requêtes SQL utiles (indicateurs, anomalies)
│   └── seed/                   # Données de test pour initialiser la base
├── api/
│   ├── src/                    # Code source Java (Spring Boot)
│   │   ├── main/
│   │   │   └── java/
│   │   └── test/
│   │       └── java/
│   ├── resources/
│   │   ├── application.properties     # Config API (connexion DB, ports...)
│   │   └── static/                    # Fichiers statiques éventuels
│   └── docs/
│       └── swagger/                   # Documentation API (Swagger, autres)
├── docs/
│   ├── cahier_des_charges.md   # Cahier des charges, specs fonctionnelles
│   ├── rapport_final.md        # Rapport technique final, choix, analyses
│   └── images/                 # Schémas, graphiques explicatifs
└── scripts/
    └── manage.py               # Script de gestion : run ETL, API, test...
```
