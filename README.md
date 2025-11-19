# EnergIA

EnergIA est un projet conçu pour analyser et gérer les données énergétiques à l'aide d'une architecture combinant Python pour le traitement des données et Java/Spring Boot pour l'API backend. Ce projet inclut également une base de données SQL pour le stockage des données et des scripts pour l'analyse et la visualisation.

## Table des matières

- [Introduction](#introduction)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [Structure du projet](#structure-du-projet)
- [Contributeurs](#contributeurs)
- [Licence](#licence)

---

## Introduction

EnergIA vise à fournir une solution complète pour :

- Nettoyer et transformer les données énergétiques.
- Stocker les données dans une base SQL.
- Offrir une API REST pour accéder aux données.
- Générer des indicateurs et des visualisations pour l'analyse.

---

## Installation

### Prérequis

- **Python 3.10+** et `pip` pour les scripts de traitement des données.
- **Java 17+** et **Maven** pour l'API Spring Boot.
- **MySQL** pour la base de données.

### Étapes

1. Clonez le dépôt :

   ```bash
   git clone https://github.com/Sirteabagg/EnergIA.git
   cd EnergIA
   ```

2. Installez les dépendances Python :

   ```bash
   pip install -r requirements.txt
   ```

3. Configurez la base de données MySQL :

   - Créez une base de données `EngIA`.
   - Exécutez le script `db/schema.sql` pour créer les tables :
     ```bash
     mysql -u root -p EngIA < db/schema.sql
     ```

4. Compilez et démarrez l'API Spring Boot :
   ```bash
   cd api
   ./mvnw spring-boot:run
   ```

---

## Utilisation

### Lancer les scripts Python

- Nettoyage des données :
  ```bash
  python data/scripts/manage_data.py
  ```
- Analyse des indicateurs :
  Ouvrez et exécutez les notebooks dans `data/scripts/`.

### Accéder à l'API

- L'API est accessible à l'adresse : `http://localhost:8080`.
- Documentation Swagger : `http://localhost:8080/swagger-ui.html`.

### Tests

- Tests Python :
  ```bash
  pytest tests/
  ```
- Tests Java :
  ```bash
  cd api
  ./mvnw test
  ```

---

## Structure du projet

```text
EnergIA/
├── README.md                  # Explications du projet, installation, usage
├── requirements.txt           # Dépendances Python
├── pom.xml                    # Dépendances Java/Spring Boot
├── data/
│   ├── raw/                   # Données originales
│   ├── processed/             # Données nettoyées/transformées
│   └── scripts/               # Scripts Python pour le traitement
├── db/
│   ├── schema.sql             # Script création des tables SQL
│   ├── queries.sql            # Requêtes SQL utiles
│   └── seed/                  # Données de test
├── api/
│   ├── src/                   # Code source Java (Spring Boot)
│   ├── resources/             # Config API
│   └── docs/                  # Documentation API
├── docs/
│   ├── cahier_des charges.md  # Cahier des charges
│   ├── rapport_final.md       # Rapport technique
│   └── images/                # Schémas, graphiques
└── scripts/
    └── manage.py              # Script de gestion
```

---

## Contributeurs

- **Ethan** - Développeur principal
- **Sirteabagg** - Responsable du projet

---

## Licence

Ce projet est sous licence MIT. Consultez le fichier `LICENSE` pour plus d'informations.
