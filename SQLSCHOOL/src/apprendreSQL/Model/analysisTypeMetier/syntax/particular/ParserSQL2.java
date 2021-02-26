package apprendreSQL.Model.analysisTypeMetier.syntax.particular;

import java.util.List;
import java.util.Stack;

import apprendreSQL.Controller.Controller;
import apprendreSQL.Model.analysisTypeMetier.syntax.general.ParseException;
import apprendreSQL.Model.analysisTypeMetier.syntax.general.ParserSQL;
import apprendreSQL.Model.data.Factory;
import apprendreSQL.Model.data.Observers;

/**
 * 
 *  Cette a comme responsabilité la compilation d'une requête relativement à une question
 *
 */
public class ParserSQL2 implements  Observers, ParserSQL {
	// liste token pour la requête de l'élève
	private Stack<String> p_token_eleve;
	// liste token pour la requête d un prof
	private Stack<String> p_token_prof;
	// list de pile contenant les tokens du prof
	private List<Stack<String>>  list_p_token;
	// list de reponses de prof
	private List<String> reponses;
	// parseur général
	private ParserSQL parser;
	// controlle
	private Controller controller;
	private int numero_pile = 0;
	
	private Stack<String> p_token_accepted;

	
	
	public ParserSQL2() {
		initiation();
	}
	
	/**
	 *  setter 
	 */
	@Override
	public void setcontroller(Controller controller) {
			this.controller = controller;
	}
	
	/**
	 *  mis à jour de l'attribut reponses et automatiquement la reprise de l'analyse
	 */
	@Override
	public void updateReponses(List<String> repones) {
		this.reponses = repones;
		start();
	}
	
	/**
	 *  analyse de la requête du prof
	 */
	private void start() {
		try {
			for(String s : reponses) {
				parser.ReInit(Factory.translateToStream(s));
				parser.sqlStmtList();
			}
		} catch (ParseException e){
			controller.getView().sendMessage(" Requette prof  : "+e.getMessage());
		}
		
	}
	
	/**
	 *  initiation des attributes
	 */
	private void initiation() {
		
		this.p_token_eleve = Factory.makeStack();
		this.p_token_prof = Factory.makeStack();
		this.p_token_accepted = Factory.makeStack();
		
		this.list_p_token =  Factory.makeList();
		this.parser = Factory.makeParserSQL("general");
		this.parser.registerObserver(this);
		this.parser.setDestination("prof");
	}
	
	/**
	 *  notification envoyé par le parseur général concernant un elève
	 */
	@Override
	public void notifyEventEleve(String token) {
		this.p_token_eleve.add(token);
	}
	
	/**
	 *  notification envoyé par le parseur général concernant un proffeseur
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void notifyEventProf(String token) {
		System.out.println("prof "+ token);
		if(token.equals("fin")) {
			p_token_prof.add("fin");
			list_p_token.add((Stack<String>) p_token_prof.clone());
			p_token_prof.removeAllElements();
		} else {
			p_token_prof.add(token);
		}
	}
	
	/**
	 *  renitialisation des atttributs 
	 */	
	@Override
	public void reset() {
		this.p_token_eleve.removeAllElements();
		this.p_token_prof.removeAllElements();
		this.p_token_accepted.removeAllElements();
		for(Stack<String > s : list_p_token)
			s.removeAllElements();
	}
	
	/**
	 *  affichage en console
	 */
	@Override
	public void display() {
		for(Stack<String> d : list_p_token) {
			for(String a : d)
				System.out.println("--"+a);
			System.out.println("\n");
		}
	}
	
	/**
	 * methode consume un token dans la requête de l'élève si les règles sont satisfaite
	 *  les règles : respecte l'une des solutions proposés par le prof
	 * @param token
	 * @return
	 */
	private boolean consume(String token) {
		boolean isCorrect = false;
		int i = 0;
		System.out.println("Le token à consomé est : "+ token);
		for(Stack<String> p : list_p_token) {
			if(!p.isEmpty()) {
				System.out.println("automate num : "+i+" -> tete value "+ p.firstElement());
				if(p.firstElement().equals(token)) {
					p_token_accepted.add(token);
					p.remove(p.firstElement());
					numero_pile = i;
					isCorrect = true;
				}
			}
			i++;
		}
		System.out.println("isCorrest : "+isCorrect);
		return isCorrect;
	}
	
	@Override
	public void sqlStmtList() throws ParseException {
		while(!p_token_eleve.isEmpty()) {
			if(consume(p_token_eleve.firstElement())) {
				System.out.println("le token est consomé "+ p_token_eleve.firstElement());
				p_token_eleve.remove(p_token_eleve.firstElement());
			} else {
				System.out.println("pile numero : "+ numero_pile);
				throw new ParseException(list_p_token.get(numero_pile), p_token_accepted, p_token_eleve.firstElement());
			}
		}		
	}
	
	
	

	
		
	
	

}
