Objectif
Développement d'une mini application pour la gestion d'utilisateurs.
Livrables : deux modules à livrer sur Github
 
1. Partie Front : en Angular (Choix libre sur le Framework de composants UI)
2. Partie Back : Application Java avec la stack technique suivante : 
- Java 17
- Spring Boot
- Sprig Web MVC
- PostgreSQL
- Choix libres pour la couche applicatif qui permet le requêtage des données
Description du besoin
L'application propose les fonctionnalités suivantes :
1. Affichage de la liste des utilisateurs enregistrés dans un tableau
2. Pour chaque utilisateur on affiche les colonnes suivantes : id, prénom, nom, email et actions 
(modification et suppression)
3. On peut effectuer une recherche textuelle (à implémenter côté front) basée sur le nom ou le prénom
4. Possibilité de créer un nouvel utilisateur en saisissant 3 champs obligatoires : nom, prénom et email. Si 
l'email renseigné est déjà utilisé, un message d'erreur doit s'afficher en dessous du champ email 
indiquant que cet email est déjà utilisé par un autre utilisateur
5. Possibilité de modifier le nom ou le prénom de l'utilisateur
6. Possibilité de supprimer un utilisateur
La maquette suivante décrit l'interface utilisateur.
Les caractéristiques visuelles (Les couleurs, les icônes...etc.) sont données à titre indicatif
