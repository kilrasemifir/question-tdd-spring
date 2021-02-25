package exercice.question.services.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exercice.question.dto.NouvelleQuestionDTO;
import exercice.question.dto.ReponseUtilisateur;
import exercice.question.dto.ResultatQuestion;
import exercice.question.entities.QuestionEntity;
import exercice.question.entities.Reponse;
import exercice.question.exceptions.QuestionNotFoundException;
import exercice.question.repositories.QuestionRepository;
import exercice.question.services.QuestionService;

public class QuestionServiceImpl implements QuestionService {

	private QuestionRepository repository;
	
	public QuestionServiceImpl(QuestionRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public QuestionEntity nouvelleQuestion(NouvelleQuestionDTO nouvelleQuestion) {
		QuestionEntity question = new QuestionEntity();
		
		definitionDuTitre(nouvelleQuestion, question);
		
		generationDesReponses(nouvelleQuestion, question);
		
		validationDesReponses(nouvelleQuestion, question);
		
		return question;
	}

	/**
	 * @param nouvelleQuestion
	 * @param question
	 */
	private void definitionDuTitre(NouvelleQuestionDTO nouvelleQuestion, QuestionEntity question) {
		String titreDeLaQuestion = nouvelleQuestion.getQuestion();
		question.setTitre(titreDeLaQuestion);
	}

	/**
	 * @param nouvelleQuestion
	 * @param question
	 */
	private void generationDesReponses(NouvelleQuestionDTO nouvelleQuestion, QuestionEntity question) {
		for (String titreDeReponse : nouvelleQuestion.getReponses()) {
			Reponse reponse = new Reponse();
			reponse.setTitre(titreDeReponse);
			question.getReponses().add(reponse);
		}
	}

	/**
	 * @param nouvelleQuestion
	 * @param question
	 */
	protected void validationDesReponses(NouvelleQuestionDTO nouvelleQuestion, QuestionEntity question) {
		int indexReponse = 0;
		for (Reponse reponse : question.getReponses()) {
			boolean estValide = nouvelleQuestion
					.getIdDesReponseValide()
					.contains(indexReponse);
			
			reponse.setValide(estValide);
			indexReponse++;
		}
	} 

	@Override
	public ResultatQuestion verificationDesReponses(ReponseUtilisateur reponsesUtilisateur) {
		QuestionEntity question = this.trouverQuestionParId(reponsesUtilisateur.getQuestionId());
		ResultatQuestion resultat = new ResultatQuestion();
		List<Integer> idValides = new ArrayList<>();
		for(int idQuestion=0; idQuestion < question.getReponses().size(); idQuestion++) {
			if (question.getReponses().get(idQuestion).isValide()) {
				idValides.add(idQuestion);
			}
		}
		
		//Collections.sort(reponsesUtilisateur.getReponses());
		boolean valide = idValides.equals(reponsesUtilisateur.getReponses());
		resultat.setBonneReponse(valide);
		return resultat;
	}

	@Override
	public QuestionEntity trouverQuestionParId(long id) {
		return this.repository.findById(id).orElseThrow(()->{
			return new QuestionNotFoundException();
		});
	}

	@Override
	public QuestionEntity sauvegarderQuestion(NouvelleQuestionDTO nouvelleQuestion) {
		QuestionEntity question = this.nouvelleQuestion(nouvelleQuestion);
		return this.repository.save(question);
	}

}
