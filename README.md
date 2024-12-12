# README - Projet Dice

## Description
Le projet **Dice** est une application développée avec Spring Boot permettant de simuler des lancés de dés, de stocker les résultats dans une base de données H2 et d'exposer des endpoints REST pour interagir avec ces données.

---

## Configuration et Lancement

1. **Prérequis :**
   - Java 17+.
   - Maven ou Gradle.
   - Un environnement configuré pour exécuter des applications Spring Boot.


2. **Accès à l'application :**
   - L'application est disponible sur le port **8081**.
   - Les endpoints REST sont accessibles via `http://localhost:8081`.

---

## Base de Données (H2)

### Accès à la Base de Données
La base de données H2 est configurée en mémoire et accessible pendant l'exécution de l'application.

- **Console H2 :**
  - URL : [http://localhost:8081/h2-console](http://localhost:8081/h2-console)
  - Informations de connexion :
    - **JDBC URL** : `jdbc:h2:mem:testdb`
    - **Nom d'utilisateur** : `sa`
    - **Mot de passe** : password

### Structure de la Base de données

#### Table `DICE_ROLL_LOG`
Enregistre les informations des lancés de dés.

| **Colonne**    | **Type**       | **Description**                     |
|-----------------|----------------|-------------------------------------|
| `ID`           | `BIGINT`       | Identifiant unique (clé primaire). |
| `DICE_COUNT`   | `INTEGER`      | Nombre de dés lancés.              |
| `TIMESTAMP`    | `TIMESTAMP`    | Date et heure du lancé.            |

#### Table `DICE_ROLL_LOG_RESULTS`
Enregistre les résultats des dés pour chaque lancé.

| **Colonne**         | **Type**       | **Description**                              |
|----------------------|----------------|----------------------------------------------|
| `RESULTS`           | `INTEGER`     | Résultat individuel d’un dé.                |
| `DICE_ROLL_LOG_ID`  | `BIGINT`      | Référence vers `DICE_ROLL_LOG` (clé étrangère). |

---

## Fonctionnalités Principales du Code

### 1. Classe `Dice`
- Cette classe représente un dé.
- La méthode `roll` gènère un nombre aléatoire entre 1 et 6.
- Annotée avec `@Component` pour permettre son injection automatique dans d'autres composants.

### 2. Classe `DiceRollLog`
- Représente une entité JPA pour stocker les informations des lancés de dés.
- Utilise l'annotation `@Entity` pour indiquer qu'elle est mappée à une table dans la base de données.
- Les résultats des dés sont stockés sous forme de collection grâce à `@ElementCollection`.

### 3. Service `DiceService`
- Gère la logique métier des lancés de dés.
- Méthode `rollDices` :
  - Simule plusieurs lancés de dés.
  - Enregistre les résultats et le nombre de dés dans la base via `DiceRepository`.

### 4. Contrôleur `DiceController`
- Expose les endpoints REST :
  - **GET `/rollDice`** : Lance un seul dé.
  - **GET `/rollDice/{X}`** : Lance `X` dés.
  - **GET `/diceLogs`** : Récupère l'historique des lancés.
- Utilise un repository pour enregistrer les données dans la base.

### 5. Repository `DiceRepository`
- Interface Spring Data JPA pour interagir avec la table `DICE_ROLL_LOG` dans la base.

---

## Endpoints REST

| **Méthode** | **Endpoint**          | **Description**                                      |
|-------------|-----------------------|----------------------------------------------------|
| `GET`       | `/rollDice`           | Lance un seul dé et retourne le résultat.         |
| `GET`       | `/rollDice/{X}`       | Lance `X` dés et retourne leurs résultats.        |
| `GET`       | `/diceLogs`           | Retourne l'historique des lancés de dés.          |

---
