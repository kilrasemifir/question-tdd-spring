package exercice.question.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.reset;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.platform.commons.annotation.Testable;

import com.jayway.jsonpath.Option;
import com.sun.net.httpserver.Authenticator.Result;

import exercice.question.dto.NouvelleQuestionDTO;
import exercice.question.dto.ReponseUtilisateur;
import exercice.question.dto.ResultatQuestion;
import exercice.question.entities.QuestionEntity;
import exercice.question.entities.Reponse;
import exercice.question.exceptions.QuestionNotFoundException;
import exercice.question.repositories.QuestionRepository;
import exercice.question.repositories.fake.FakeQuestionRepository;
import exercice.question.services.impl.QuestionServiceImpl;

@Testable
public class TestQuestionServiceImpl {

	private QuestionRepository  repository  = new FakeQuestionRepository();
	private QuestionService questionService = new QuestionServiceImpl(repository);
	
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	@DisplayName("Lors ce qu'un utilisateur creer une nouvelle question:")
	class TestNouvelleQuestion {
		
		NouvelleQuestionDTO nouvelleQuestion = new NouvelleQuestionDTO();
		String titre = "test titre";
		
		@BeforeAll
		public void beforeAll() {
			nouvelleQuestion.setQuestion(titre);
			nouvelleQuestion.getReponses().add("reponse1");
			nouvelleQuestion.getReponses().add("reponse2");
			nouvelleQuestion.getIdDesReponseValide().add(0);
		}
		
		@Test
		@DisplayName("Le tritre d'une question est egale au titre de la nouvelle question")
		public void test_titre_nouvelleQuestion() {
			QuestionEntity question = questionService.nouvelleQuestion(nouvelleQuestion);
			assertEquals(titre, question.getTitre());
			
		}
		
		@Test
		@DisplayName("Question possede autant de reponse que nouvelleQuestion")
		public void test_reponse_nombre() {
			QuestionEntity question = questionService.nouvelleQuestion(nouvelleQuestion);
			assertEquals(nouvelleQuestion.getReponses().size(), question.getReponses().size());
			
		}
		
		@Test
		@DisplayName("Les reponses de l'utilisateur son les titre des reponse.")
		public void test_titre_reponse() {
			QuestionEntity question = questionService.nouvelleQuestion(nouvelleQuestion);
			for (int i=0; i < question.getReponses().size(); i++) {
				
				List<Reponse> reponsesQuestion = question.getReponses();
				Reponse reponseI = reponsesQuestion.get(i);
				
				assertEquals(nouvelleQuestion.getReponses().get(i), reponseI.getTitre());

			}
		}
		
		@Test
		@DisplayName("L(utilisateur peut donner les reponses valides.")
		public void test_voalide_reponse() {
			QuestionEntity question = questionService.nouvelleQuestion(nouvelleQuestion);
			
			for (Integer indexReponseValide : nouvelleQuestion.getIdDesReponseValide()) {
				assertEquals(true, question.getReponses().get(indexReponseValide).isValide());
			}
		}
		
		@Test
		@DisplayName("Les reponses qui ne sont pas valides sont invalide.")
		public void test_invalide_reponse() {
			QuestionEntity question = questionService.nouvelleQuestion(nouvelleQuestion);
			int indexReponse = 0;
			for (Reponse reponse : question.getReponses()) {
				boolean estValide = nouvelleQuestion
						.getIdDesReponseValide()
						.contains(indexReponse);
				assertEquals(estValide, reponse.isValide());
				indexReponse++;
			}
		}
		
		@Test
		@DisplayName("L'utilisateur peut sauvegarder une question")
		public void test_sauvegarde() {
			QuestionEntity question = questionService.sauvegarderQuestion(nouvelleQuestion);
			
			Optional<QuestionEntity> optional = repository.findById(question.getId());
			
			assertEquals(true, optional.isPresent());
			assertEquals(nouvelleQuestion.getQuestion(), question.getTitre());
 		}
		
		
		@Test
		@DisplayName("L'utilisateur peut trouver une question grace a spn id")
		public void test_trouverParId() {
			QuestionEntity questionSauvegarder = questionService.sauvegarderQuestion(nouvelleQuestion);
			
			QuestionEntity questionTrouver = questionService.trouverQuestionParId(questionSauvegarder.getId());
			
			assertEquals(questionSauvegarder, questionTrouver);
 		}
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	@DisplayName("Un utilisateur peut envoyer ses reponses pour avoir un resultat")
	class TestResultatUtilisateur {
		
		private QuestionEntity question = new QuestionEntity();
		ReponseUtilisateur reponse = new ReponseUtilisateur();
		
		@BeforeAll
		public void totoALaPlage() {
			Reponse reponse1 = Reponse.builder()
					.titre("Reponse valide")
					.valide(true)
					.build();
			Reponse reponse2 = Reponse.builder()
					.titre("Reponse invalide")
					.valide(false)
					.build();
			question = QuestionEntity.builder()
					.titre("Titre test")
					.reponses(List.of( reponse1 , reponse2))
					.build();
			question = repository.save(question);
			
			
			reponse.setQuestionId(question.getId());
			reponse.setReponses(List.of(0));
			
		}
		
		@Test
		@DisplayName("Si l'id de la question est valide, alors le resultat n'est pas null")
		public void test_resultatNonNull() {
			assertNotNull(questionService.verificationDesReponses(reponse));
			
		}
		
		@Test
		@DisplayName("Si l'id de la question n'est pas valide, il y a une ExceptionLeve")
		public void test_resultatThrowExxception() {
			
			ReponseUtilisateur reponseInvalide = new ReponseUtilisateur();
			reponseInvalide.setQuestionId(-234564323);
			
			assertThrows(QuestionNotFoundException.class, () -> {
				questionService.verificationDesReponses(reponseInvalide);
			});
		}
		
		@Test
		@DisplayName("Si les reponses de l'utilisateurs sont bonnes alors il en est notifié")
		public void test_bonneReponses() {
			
			ResultatQuestion resultat = questionService.verificationDesReponses(reponse);
			
			assertTrue(resultat.isBonneReponse());
		}
		
		@Test
		@DisplayName("Si les reponses de l'utilisateurs sont mauvaises alors il en est notifié")
		public void test_mauvaisesReponses() {
			ReponseUtilisateur mauvaiseReponse = new ReponseUtilisateur();
			mauvaiseReponse.setQuestionId(question.getId());
			mauvaiseReponse.setReponses(List.of(1));
			
			ResultatQuestion resultat = questionService.verificationDesReponses(mauvaiseReponse);
			
			assertFalse(resultat.isBonneReponse());
		}
	}
	
}
