# android-test

Ce projet a pour but d'être transmis aux candidats postulant pour un poste de développeur Android chez Chargemap.

L'objectif est de créer un projet simple qui respecte certaines contraintes.

Objectifs :
- Implémenter une liste de cellule avec des informations.
	- La cellule doit au moins contenir une image (provenant d'un appel HTTP), un titre et un sous-titre.
	- La liste doit contenir au moins 50 éléments.
- Lors du clic sur une cellule, ouvrir un nouveau controller pour afficher des informations détaillées concernant la cellule.
	- Le nouveau écran doit contenir une donnée (image ou texte, peu importe) provenant d'un appel API HTTPS.
	- Utiliser des StateFlow/MutableStateFlow pour afficher des données en temps réelle
- Tests unitaires sur les `viewModels`
- Tests unitaires sur l'appel API
- Le sujet est libre.
- Le design est libre.
- Forker le projet depuis github -> ChargeMap/android-test
- Créer une pull request

Contraintes :
- Utiliser Jetpack Compose pour les vues
- Respecter l'architecture MVVM

Bonus :
- Gérer une vue différente pour tablette pour être plus adapté
- Gérer un dark mode

Livrable :
- Le projet doit être compilable et executable sur un émulateur
- Le livrable doit contenir le dossier `.git` pour analyser l'utilisation de GIT

Versions:
- Android Studio : Android Studio Giraffe | 2022.3.1 Patch 1
- Kotlin : 1.9.0
- Gradle : 8.1.1



# Explication 

L'application est structurée en multi-module pour permettre une bonne compartimentation des responsabilités : 
 * Chaque écran et dans son propre module feature
 * Dans le core vous retrouverez les parties domaines, network, data, et model.
 * L'app contient uniquement le Navgraph principal.

J'ai utilisé mon application personelle comme référence/base de travail. 

## Ecran
Les écrans ne sont pas dépendant des Vm pour permettre une utilisation dans un contexte différent. 
Le viewModel est injecter dans les fonctions préxifées par route. Ces fonctions sont appelées par deux méthodes d'extensions qui permettent de configurer la navigation.
Ce sont les seules fonctions visibles pour une feature.

## Core 
Le core n'a quasiement aucunes dépendances JVM. 

## Build-logic
Pour faciliter la configuration, plusieurs plugins ont été crééer dans le sous-projet build-logic. Ces plugins permettent la confifuration de Compose, Hilt, Applciation et Library.


