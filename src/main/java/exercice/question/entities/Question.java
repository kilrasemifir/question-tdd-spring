package exercice.question.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Une question contient:
 * - un titre (par exemple qu'est ce que java) et 
 * - des reponses par exemple: 
 * 		1.Un langage de programmation, 
 * 		2. Une ile paradisiaque, 
 * 		3. Une danse, 
 * 		4. Une vache
 * 	Les reponses peuvent etre valide ou invalide.
 * 
 */
@Data
public class Question {

	private long id;
	private String titre;
	private List<Reponse> reponses = new ArrayList<>();
	public void setId(int id) {
		this.id = id;		
	}
}
