### Les remarques trouvée aucours du développement :
- Le database context peut être écraser en cas de regénération descendant
- Le nom du classe va être comme le nom du table même si il y a un "s" a la fin
- Lors du processus de generation, on doit générer tous les models interdépendant ( Foreign Key ), ensuite tous on ne peut générer un controller ou un view si le model de cette entité n'est pas générer.

### Avenir :
- On n'efface pas tous le contenue mais on append comme dans git si on regénère
- Gestion des erreurs depuis le view
- Changement des o dans le include en premier lettre du colonne
- Menu dynamique pour le navigation
- Regler problème jakarta ou javax dans le controller spring
- Simplifier le plus que possible les paramètres
- Changement en service des fonctions JPA
- On doit trouver une autre manière de coordonner les models, views, controller, sns pour faciliter leurs relations

### Changement non assurées :
- J'ai enlevé le "\n" dans le DAOAnnotations / fields pour enlever l'espacement dans le code
- J'ai enlevé le "\n" dans le FKEntityStateParameters pour mieux gérer l'affichage

### Erreurs ignorées :
- Generation d'un controller avec un foreign key vers un model non généré
- Toutes les repository doivent se placer dans un meme package pour le moment ( Problème au niveau de l'import controller )
- Affichage si valeur null ou si valeur foreign key null, ( Problème au niveau du select list, l'elements selectionné ne s'affiche plus pour .net)