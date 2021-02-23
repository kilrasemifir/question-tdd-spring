# Exercice TDD en Spring
___
## Présentation

Le but de cet exercice est de créer un backend avec Spring en commençant par le service.
Nous allons utiliser J. Unit 5 pour faire nos testent unitaires.
___
## Prérequis ### Technologies
- JDK 11
- Spring Tool Suite 4 (STS4) avec le plugiciel EclEmma pour le coverage de code.

### Notions
- Spring boot les bases.
- Bases de developpement en Java
- Utilisation des testes.
- Notions de bases de TDD
___
## Initialiser le projet:

1. Dans un dossier correctement placé (par exemple ```D:/dev/spring```) cloner le projet depuis le repository github.
2. Importer le projet depuis STS4 (File > import... > Mavel > Existing Maven projets puis sélectionner le dossier contenant le pom.xml de votre projet et cliquer sur finish).
3. Supprimer le dossier```.Git``` pour utiliser votre propre repo git.
___
## Lancer l'analyse de coverage

Clique droit sur le projet > Coverage > JUnit Test
___
## Rappels du TDD
Le but du TDD est de développer une application en limitant au maximum le nombre de bugs en utilisant les tests unitaires.
Le principe est simple:
1. __[RED]__ Écrire un test puis le lancer pour voir qu'il ne passe pas. Si un test passe sans avoir besoin de modifier votre programme alors il n'est pas valide.
2. __[GREEN]__ Modifier votre code pour que le teste passe. Le But est de faire le code le plus simple possible. Il ne faut pas prévoir le futur. Ne développer que les éléments utiles pour faire passer le test.
3. __[REFACTOR]__ Le but est de chercher comment rendre le code plus simple à lire et plus facile a modifié. Par exemple changer le nom de vos variables, extraire les méthodes, ....

En suivant ce principe la programmation est plus simple.
Quand vous avez fini la phase de refacto vous pouvez faire un commit pour sauvegarder vos changements. le test suivant ne vous semble pas correct vous pouvez utiliser la commande ```git Stash``` pour revenir au dernier commis.
___
## Enoncé
___
### __Exercice 1:__ Les questions

#### __Exercice 1.1__

Écrire un test qui permet de transformer un objet du type NouvelleQuestion en Question. Il teste la méthode la ```Question Service ::nouvelle Question```.
Pour ce faire creer une nouvelle méthode annoter par l'annotation ```@test```.
Le premier test doit vérifier que la question creer porte le même titre que celui de NouvelleQuestion passé en paramètre.
Après avoir écrit le test lancer le coverage. Si le test passe _rouge (failure) alors écrire le code minimal qui permet de répondre au test.

#### __Exercie 1.2__

Écrire un autre test qui permet de transformer un objet du type NouvelleQuestion en Question. Il teste la méthode la `question Service : : nouvelle Question`.
Ce test vérifie que la question en sortie possède autant de réponses que la nouvelle Question passé en paramètre.
Puis faite le code minimal pour répondre à votre test.

#### __Exercice 1.3__

Écrire un test qui vérifie que les réponses de la question en sortie possèdent bien les mêmes titres que la paramètre `nouvelle question` puis répondre au test avec le code minimal.
Puis faite le code minimal pour répondre à votre test.

#### __Exercice 1.4__

Écrire un test qui vérifie que les réponses ayant leurs Ids dans la liste des Ids valides de `nouvelle Question` possédent l'attribut valide a vrai.Puis faite le code minimal pour répondre à votre test.
#### __Exercice 1.5__
Écrire un test qui vérifie que les réponses n'ayant pas leurs Ids dans la liste des Ids valides de `nouvelle Question` possédent l'attribut valide a faux.Puis faite le code minimal pour répondre à votre test.
___
### __Exercice 2:__ CRUD des questions.

#### __Exercice 2.1__
Écrire un test qui vérifie que la méthode Question Service ::sauvegarder Question sauvegarde bien la question dans le repository.Vous devez utiliser la méthode `Question Repository :: findbyid` du repository (donner en attribut de la classe de test) pour vérifier que la question a bien été sauvegarder.Cette méthode utilise la méthode `Question Service ::nouvelle Question` pour transformer `NouvelleQuestion` passer en paramètre en question.
Pour faire le test nous utilisons un Fake repository. C'est ce que l'on appelle un bouchon. Il permet de faire le test sans avoir besoin d'une base de données.
Puis faite le code minimal pour répondre à votre test.

#### __Exercice 2.2__

Ecrire un test qui verifie qu'apres avoir sauvegarder une nouvelle question vous pouvez utiliser la methode `QuestionService::trouverQuestionParId` pour recuperer la methode.
Puis faite le code minimal pour repondre a votre test.
___
### __Exercice 3:__ Verfication des reponses de l'utilisateur

Écrire des tests qui permettent de vérifier quelle réponse d'un utilisateur a une question.
#### __Exercice 3.1__
Le test vérifie que si l'utilisateur fournit un id de question valide alors la réponse n'est pas nulle.

#### __Exercice 3.2__

Créer une nouvelle class `QuestionNotFoundException` qui entend la class RuntimeException dans un nouveau package exceptions.
Écrire un test qui vérifie que si l'id de la question dans les résultats n'est pas valide alors la méthode leve une `QuestionNotFoundException`.

#### __Exercice 3.3__
Écrire un test qui vérifie que si l'utilisateur donne les bonnes réponses alors le résultat retourné à lattribut bonnereponse a vrai.

#### __Exercice 3.4__
Écrire un test qui vérifie l'inverse.
#### __Exercice 3.5__
Écrire un test qui vérifie que dans l'objet de retour, les toutes les bonnes réponses de l'utilisateur sont gardé dans le champ bonne Reponses.
#### __Exercice 3.6__
Écrire le test qui vérifie la même chose pour les mauvaises Reponses.

___
### Exercice 4
Creer un controller pour les questions qui utilise les methode du service.

Pour lier le service a spring creer une class annoté de l'annotation @Configuration et ayant un bean retournant @QuestionService et retournant un instance QuestionService Impl. Ce bean prend en parametre un Question repository pour faire l'injection.

