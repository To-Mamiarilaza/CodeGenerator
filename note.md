### Les remarques trouvée aucours du développement :
- Le database context peut être écraser en cas de regénération descendant
- Le nom du classe va être comme le nom du table même si il y a un "s" a la fin
- Lors du processus de generation, on doit générer tous les models interdépendant ( Foreign Key ), ensuite tous on ne peut générer un controller ou un view si le model de cette entité n'est pas générer.
- Le output path du projet ne doit pas se terminé avec un "/"
- On peut utiliser curl pour generer le projet 
- Lors du generation du projet spring le nom du projet en minuscule est définie comme artifact
- Le primary key des tables doit etre justement id

### Avenir :
- On n'efface pas tous le contenue mais on append comme dans git si on regénère
- Simplifier le plus que possible les paramètres
- Changement en service des fonctions JPA
- On doit trouver une autre manière de coordonner les models, views, controller, sns pour faciliter leurs relations
- Catch the main exception output 
- On doit aussi generer le App.js et bien placer les routers
- On doit travailler pour que ca marche avec mysql : " Information base de donnees, Dialect JPA, URL Connection, Dependences ".
- Gestion version dotnet

### Changement non assurées :

### Erreurs ignorées :
- Generation d'un controller avec un foreign key vers un model non généré
- Toutes les repository doivent se placer dans un meme package pour le moment ( Problème au niveau de l'import controller )
- On doit mettre en variable les classOutputPath et les viewOutputPath pour chaque framework
- Input react et le checkbox

### A Faire prochainement :
- Checkbox input de react et gestion table avec s a la fin ( CORS )
- Preparation livraison
- Creation du projet react
