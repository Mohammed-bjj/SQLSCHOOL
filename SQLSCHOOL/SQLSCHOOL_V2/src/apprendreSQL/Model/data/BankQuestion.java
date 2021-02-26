package apprendreSQL.Model.data;

import java.util.List;

public class BankQuestion {
	
	private List<Question> questions;
	public BankQuestion() {
		questions = Factory.makeList();
	}
	
	
	
	
	public Integer size() {
		return questions.size();
	}

	public void addQuestion(Question question) {
		if(question != null)
			questions.add(question);
	}
	
	/**
	 * 
	 * @param id
	 * @return question object indexé par son id
	 */
	public Question findQuestionByID(int id) {
		   return 	questions.get(id);

	}

	
	
	
}
