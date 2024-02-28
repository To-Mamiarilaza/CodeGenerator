### Les remarques trouvée aucours du développement :
- Le database context peut être écraser en cas de regénération descendant
- Le nom du classe va être comme le nom du table même si il y a un "s" a la fin
- Lors du processus de generation, on doit générer tous les models interdépendant ( Foreign Key ), ensuite tous on ne peut générer un controller ou un view si le model de cette entité n'est pas générer.
- Le output path du projet ne doit pas se terminé avec un "/"
- On peut utiliser curl pour generer le projet 

### Avenir :
- On n'efface pas tous le contenue mais on append comme dans git si on regénère
- Gestion des erreurs depuis le view
- Changement des o dans le include en premier lettre du colonne
- Menu dynamique pour le navigation
- Regler problème jakarta ou javax dans le controller spring
- Simplifier le plus que possible les paramètres
- Changement en service des fonctions JPA
- On doit trouver une autre manière de coordonner les models, views, controller, sns pour faciliter leurs relations
- Catch the main exception output 
- On doit aussi generer le App.js et bien placer les routers
- On doit travailler pour que ca marche avec mysql : " Information base de donnees, Dialect JPA, URL Connection ".

### Changement non assurées :
- J'ai enlevé le "\n" dans le DAOAnnotations / fields pour enlever l'espacement dans le code
- J'ai enlevé le "\n" dans le FKEntityStateParameters pour mieux gérer l'affichage

### Erreurs ignorées :
- Generation d'un controller avec un foreign key vers un model non généré
- Toutes les repository doivent se placer dans un meme package pour le moment ( Problème au niveau de l'import controller )
- Affichage si valeur null ou si valeur foreign key null, ( Problème au niveau du select list, l'elements selectionné ne s'affiche plus pour .net)
- On doit mettre en variable les classOutputPath et les viewOutputPath pour chaque framework
- Input react et le checkbox

### Generation du projet de base :
#### SPRING :
1. Terminal : "mvn archetype:generate -DgroupId=mg.mamiarilaza -DartifactId=demo -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false"
2. Update pom.xml
3. Build with : "mvn clean install"

##### Get the zip form repository
curl https://start.spring.io/starter.zip \
  -d language=java \
  -d javaVersion=17 \
  -d bootVersion=3.2.3 \
  -d groupId=mg.mamiarilaza \
  -d artifactId=demo \
  -d type=maven-project \
  -d name=demo \
  -d packageName=mg.mamiarilaza.demo \
  -o demo.zip

##### unzip file 
unzip demo.zip -d demo
