package exercice.question.repositories.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import exercice.question.entities.QuestionEntity;

import exercice.question.repositories.QuestionRepository;

@Repository
public class FakeQuestionRepository implements QuestionRepository {

	private List<QuestionEntity> questions = new ArrayList<>();
	
	@Override
	public QuestionEntity save(QuestionEntity entity) {
		entity.setId(questions.size());
		this.questions.add(entity);
		return entity;
	}

	@Override
	public Optional<QuestionEntity> findById(long id) {
		for (QuestionEntity question : questions) {
			if (question.getId() == id) return Optional.of(question);
		}
		return Optional.empty();
	}

}
