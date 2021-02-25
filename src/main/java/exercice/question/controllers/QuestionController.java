package exercice.question.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import exercice.question.dto.NouvelleQuestionDTO;
import exercice.question.dto.ReponseUtilisateur;
import exercice.question.dto.ResultatQuestion;
import exercice.question.entities.QuestionEntity;
import exercice.question.exceptions.QuestionNotFoundException;
import exercice.question.services.QuestionService;

@RestController
@CrossOrigin
@RequestMapping("questions")
public class QuestionController {

	@Autowired
	private QuestionService service;
	
	@GetMapping("{id}")
	public QuestionEntity findById(@PathVariable int id) {
		try {
			return this.service.trouverQuestionParId(id);
		} catch (QuestionNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("")
	public QuestionEntity save(@RequestBody NouvelleQuestionDTO dto) {
		return this.service.sauvegarderQuestion(dto);
	}
	
	@PostMapping("check")
	public ResultatQuestion check(@RequestBody ReponseUtilisateur reponse) {
		return this.service.verificationDesReponses(reponse);
	}
}
