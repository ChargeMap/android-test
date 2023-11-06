# android-test

Ce projet a pour but d'être transmis aux candidats postulant pour un poste de développeur Android chez Chargemap.

L'objectif est de créer un projet simple qui respecte certaines contraintes.

Objectifs :
- Implémenter une liste de cellule avec des informations.
	- La cellule doit au moins contenir une image (provenant d'un appel HTTP), un titre et un sous-titre.
      - OK
	- La liste doit contenir au moins 50 éléments.
      - OK
- Lors du clic sur une cellule, ouvrir un nouveau controller pour afficher des informations détaillées concernant la cellule.
	- Le nouveau écran doit contenir une donnée (image ou texte, peu importe) provenant d'un appel API HTTPS.
      - OK
	- Utiliser des StateFlow/MutableStateFlow pour afficher des données en temps réelle
      - OK
- Tests unitaires sur les `viewModels`
  - Partiel : comme le vm ici est mono-responsabilité, il est testé avec l'appel API.
- Tests unitaires sur l'appel API
  - OK
- Le sujet est libre.
  - Récupération d'une liste de voiture et affichage des détails disponibles.
- Le design est libre.
  - OK
- Forker le projet depuis github -> ChargeMap/android-test
  - OK
- Créer une pull request
  - OK

Contraintes :
- Utiliser Jetpack Compose pour les vues
  - OK
- Respecter l'architecture MVVM
  - OK

Bonus :
- Gérer une vue différente pour tablette pour être plus adapté
  - OK : CarList - affichage en grid pour tablet et en list pour smartphone
- Gérer un dark mode
  - OK : basé sur les préférences systèmes
Livrable :
- Le projet doit être compilable et executable sur un émulateur
  - OK
- Le livrable doit contenir le dossier `.git` pour analyser l'utilisation de GIT
  - La pull request permet cette analyse

Versions:
- Android Studio : Android Studio Giraffe | 2022.3.1 Patch 1
- Kotlin : 1.9.0
- Gradle : 8.1.1