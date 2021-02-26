
package apprendreSQL.Model.data;

import java.util.List;

/**
 * This class represents a Question object.
 *
 */
public class Question {

	private String id;
	private String question;
	private List<String> reponses;
	private List<String> scripts1;
	private List<String> scripts2;
	

	
	public Question() {
	}

	public String getId() {
		return id;
	}
    
	public void setId(String id) {
		this.id = id;
	}
	
	public String getReponseA(int i) {
		return reponses.get(i);
	}
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getReponses() {
		return reponses;
	}

	public void setReponses(List<String> reponses) {
		this.reponses = reponses;
	}

	public List<String> getScripts1() {
		return scripts1;
	}

	public void setScripts1(List<String> scripts1) {
		this.scripts1 = scripts1;
	}

	public List<String> getScripts2() {
		return scripts2;
	}

	public void setScripts2(List<String> scripts2) {
		this.scripts2 = scripts2;
	}
	
	
	

}
