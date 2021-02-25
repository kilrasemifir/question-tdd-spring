package exercice.question.repositories;

import java.util.Optional;

import exercice.question.entities.QuestionEntity;

/**
 * Cette interface permet de sauvegarder une question.
 */
public interface QuestionRepository {
	
	/**
	 * Cette methode sauvegarde une nouvelle question.
	 * @param entity question a sauvegarder.
	 * @return la question avec son id.
	 */
	public QuestionEntity save(QuestionEntity entity);
	/**
	 * Cette methode retourne un {@link Optional} contenant une question portant l'id passé en parametre.
	 * @param id de la question a rechercher.
	 * @return Optional contenant la question portant l'id.
	 */
	public Optional<QuestionEntity> findById(long id);

}
