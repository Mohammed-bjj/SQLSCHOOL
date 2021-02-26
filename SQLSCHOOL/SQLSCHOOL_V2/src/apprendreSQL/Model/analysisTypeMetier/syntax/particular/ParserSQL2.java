package apprendreSQL.Model.analysisTypeMetier.syntax.particular;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


import apprendreSQL.Controller.Controller;
import apprendreSQL.Model.analysisTypeMetier.syntax.general.ParseException;
import apprendreSQL.Model.analysisTypeMetier.syntax.general.ParserSQL;
import apprendreSQL.Model.analysisTypeMetier.syntax.particular.Tokens.TypePArtie;
import apprendreSQL.Model.data.Factory;
import apprendreSQL.Model.data.Observers;

/**
 * 
 *  Cette a comme responsabilité la compilation d'une requête relativement à une question
 *
 */


interface TokenFix {
	
	public static  boolean isFix(String token) {
		for(String tkn : tokenImage) {
			if(tkn.equals(token)) {
				return true;
			}
		}
		return false; 
	}
	
	public static boolean ignorer(String token) {
		for(String s : tokenIgnorer) {
			if(s.equals(token))
			{
				return true;
			}
		}
		return false;
	}
	String[] tokenIgnorer = {
		    "\",\"",
		    "\"\"\"",
		    "\"'\"",
	};
	
	String[] tokenImage = {
			"\"fun\"",
		    "\"ABORT\"",
		    "\"ACTION\"",
		    "\"ADD\"",
		    "\"AFTER\"",
		    "\"ALL\"",
		    "\"ALTER\"",
		    "\"ANALYZE\"",
		    "\"ATTACH\"",
		    "\"AUTOINCREMENT\"",
		    "\"'\"",
		    "\"BEFORE\"",
		    "\"BEGIN\"",
		    "\"BY\"",
		    "\"CASCADE\"",
		    "\"CASE\"",
		    "\"CAST\"",
		    "\"CHECK\"",
		    "\"COLLATE\"",
		    "\"\"\"",
		    "\"COLUMN\"",
		    "\"COMMIT\"",
		    "\"CONFLICT\"",
		    "\"CONSTRAINT\"",
		    "\"CREATE\"",
		    "\"CROSS\"",
		    "\"CURRENT_DATE\"",
		    "\"CURRENT_TIME\"",
		    "\"CURRENT_TIMESTAMP\"",
		    "\"DATABASE\"",
		    "\"DEFAULT\"",
		    "\"DEFERRABLE\"",
		    "\"DEFERRED\"",
		    "\"DELETE\"",
		    "\"DESC\"",
		    "\"DETACH\"",
		    "\"DISTINCT\"",
		    "\"DROP\"",
		    "\"EACH\"",
		    "\"ELSE\"",
		    "\"END\"",
		    "\"ESCAPE\"",
		    "\"EXCLUSIVE\"",
		    "\"EXPLAIN\"",
		    "\"FAIL\"",
		    "\"FOR\"",
		    "\"FOREIGN\"",
		    "\"FROM\"",
		    "\"FULL\"",
		    "\"GLOB\"",
		    "\"GROUP\"",
		    "\"HAVING\"",
		    "\"IF\"",
		    "\"IGNORE\"",
		    "\"IMMEDIATE\"",
		    "\"INDEX\"",
		    "\"INDEXED\"",
		    "\"INITIALLY\"",
		    "\"INSERT\"",
		    "\"INSTEAD\"",
		    "\"INTERSECT\"",
		    "\"INTO\"",
		    "\"KEY\"",
		    "\"LEFT\"",
		    "\"MATCH\"",
		    "\"NATURAL\"",
		    "\"OF\"",
		    "\"ORDER\"",
		    "\"PLAN\"",
		    "\"PRAGMA\"",
		    "\"PRIMARY\"",
		    "\"QUERY\"",
		    "\"RAISE\"",
		    "\"REFERENCES\"",
		    "\"REGEXP\"",
		    "\"REINDEX\"",
		    "\"RELEASE\"",
		    "\"RENAME\"",
		    "\"REPLACE\"",
		    "\"RESTRICT\"",
		    "\"RIGHT\"",
		    "\"ROLLBACK\"",
		    "\"ROW\"",
		    "\"SAVEPOINT\"",
		    "\"SELECT\"",
		    "\"SET\"",
		    "\"TABLE\"",
		    "\"TEMP\"",
		    "\"TEMPORARY\"",
		    "\"THEN\"",
		    "\"TO\"",
		    "\"TRANSACTION\"",
		    "\"TRIGGER\"",
		    "\"UNION\"",
		    "\"UNIQUE\"",
		    "\"UPDATE\"",
		    "\"USING\"",
		    "\"VACUUM\"",
		    "\"VALUES\"",
		    "\"VIEW\"",
		    "\"VIRTUAL\"",
		    "\"WHEN\"",
		    "\"WHERE\"",
		    "<BLOB>",
		    "\"(\"",
		    "\")\"",
		    "\";\"",
		    "\",\"",
		    "\".\"",
		  };
}
public class ParserSQL2 implements  Observers, ParserSQL {
	private Stack<Tokens> p_token_eleve;
	private Stack<Tokens> p_token_prof;
	private List<Stack<Tokens>>  list_p_token;
	private List<String> reponses;
	private ParserSQL parser;
	private Controller controller;
	private int numero_pile = 0;
	private Tokens pairToken = new TokensVariable();
	private Stack<Tokens> p_token_accepted;
	private Stack<Paire<String>> p_ID = new Stack<Paire<String>>();
	private boolean flag = false;

	
	
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
	public void notifyEventEleve(String token, String tokenImage) {
		if(!TokenFix.ignorer(tokenImage))
			this.p_token_eleve.add(new TokenFixe(token, tokenImage));
	}
	
	/**
	 *  notification envoyé par le parseur général concernant un proffeseur
	 */
	@Override
	public void notifyEventProf(String token, String tokenImage) {
		if(token.equals("fin")) {
			addQueryInStack();
		} else {
			try {
				addToken(token, tokenImage);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addPartyFix(String token, String tokenImage) throws CloneNotSupportedException {
		if(flag) 
			p_token_prof.add(pairToken.clone());
		p_token_prof.add(new TokenFixe(token, tokenImage));
	}
	private void addPartyVariable(String token, String tokenImage) {
		if(!flag) 
			pairToken = new TokensVariable();
		pairToken.addToken(token, tokenImage);
	}
	
	// methode traite les token selon leur nature positionnelle dans la requête 
	private void addToken(String token, String tokenImage) throws CloneNotSupportedException {
		if(TokenFix.isFix(tokenImage)) {
			if(!TokenFix.ignorer(tokenImage)) {
				addPartyFix(token, tokenImage);
				flag = false;
			}
		} else {
			addPartyVariable(token, tokenImage);
			flag = true;
		}
	}
	
	/**
	 *  ajoute une requête composé d'un ensemble de token dans une pile 
	 */
	@SuppressWarnings("unchecked")
	private void addQueryInStack() {
		p_token_prof.add(new TokenFixe("fin"));
		list_p_token.add((Stack<Tokens>) p_token_prof.clone());
		p_token_prof.removeAllElements();
	}
	/**
	 *  renitialisation des atttributs 
	 */	
	@Override
	public void reset() {
		numero_pile = 0;
		p_ID.removeAllElements();
		this.p_token_eleve.removeAllElements();
		this.p_token_prof.removeAllElements();
		this.p_token_accepted.removeAllElements();
		for(Stack<Tokens > s : list_p_token)
			s.removeAllElements();
	}
	
	/**
	 *  affichage en console  [  utile pour le test ]
	 */
	@Override
	public void display() {
		for(Tokens t :p_token_accepted)
		{
			t.display();
		}
	}
	
	int var;
	boolean isCorrect;
	private void consumeStaticToken(Paire<String> pair,  Stack<Tokens> p) {
		p.remove(p.firstElement());	
	}
	
	private void consumePermutableToken(Paire<String> pair , Stack<Tokens> p) {
		p.firstElement().remove(pair.getImageToken());
		if(p.firstElement().isEmpty()) 
			p.remove(p.firstElement());	
		if(pair.getImageToken().equals("<ID>")) {
			System.out.println("yes egal");
			p_ID.add(pair);
		}
	}
	
	/**
	 * 
	 * @param token  associé à son image   ( year , <ID> ) 
	 * @param tokenImage
	 * @return
	 */
	private boolean consume(String tokenImage) {
		isCorrect = false;
		var = 0;
		for(Stack<Tokens> pileToken : list_p_token){
			if(!pileToken.isEmpty()){
				Tokens tokenTmp = pileToken.firstElement();
				if(tokenTmp.contain(tokenImage)) {
					isCorrect = true;
					numero_pile = var;
					if(tokenTmp.getTypeToken().equals(TypePArtie.STATIC)) 					
						consumeStaticToken(tokenTmp.getCoupleToken(""), pileToken);
					 else 
						consumePermutableToken(tokenTmp.getCoupleToken(tokenImage), pileToken);		
				}
			}
			var++;
		}
		return isCorrect;
	}
	
	
	private void update() {
		
	}

	@Override
	public void sqlStmtList() throws ParseException {
		Tokens tmp;
		while(!p_token_eleve.isEmpty()) {
			tmp = p_token_eleve.firstElement();
			if(consume(tmp.getTokenImage())) {
				p_token_eleve.remove(p_token_eleve.firstElement());
				p_token_accepted.add(new TokenFixe(tmp.getToken(), tmp.getTokenImage()));
			} else {
				Tokens nextToken = list_p_token.get(numero_pile).firstElement();
				Tokens lastToken = p_token_accepted.lastElement();
				throw new ParseException(nextToken.getTokenImage(), lastToken.getToken(), numero_pile);
			}
		}		
	}
	
	private boolean isExistID(String token) {
		for(Paire<String> pair : p_ID) {
			if(pair.getToken().equals(token)) {
				return true;
			}
		}
		
		return false;
	}
	/**
	 *   analyse des sémantique 
	 */
	public void startAnalyseSemantic() throws  ParseException{
		for(Tokens token : p_token_accepted) {
			if(token.getTokenImage().equals("<ID>")) {
				if(!isExistID(token.getToken())) {
					throw new ParseException("error semantique  :=  \n   unkown : "+ token.getToken());
				}
			}
		}
	}
	
	
	

	
}

	
		
































	
	/*
	 *  cette classe représente la partie non permutable
	 */
	class TokenFixe extends Tokens {


		private Paire<String> pair;
		public TokenFixe(String token) {
			pair = new Paire<String>(token, "fin");
			typePArtie = TypePArtie.STATIC;
		}
		
		@Override
		public String getToken() {
			return pair.getToken();
		}


	

		public TokenFixe(String token, String tokenImage) {
			addToken(token, tokenImage);
			typePArtie = TypePArtie.STATIC;
		}
		
		@Override
		public void addToken(String token, String tokenImage) {
			pair = new Paire<String>(token, tokenImage);
		}
		
		@Override
		protected Tokens clone() throws CloneNotSupportedException {
			return (TokenFixe)super.clone();
		}
		
		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		protected boolean contain(String tokenImage) {	
			return this.pair.containToken(tokenImage);
		}

		@Override
		public boolean isExist(String token) {
			return false;
		}

		@Override
		public void display() {
			pair.display();
		}


		@Override
		public String getTokenImage() {
			return pair.getImageToken();
		}

		@Override
		public void remove(String token) {}

		@Override
		public Paire<String> getCoupleToken(String token) {
			return pair;
		}

		@Override
		public int size() {
			return 0;
		}
	}
	
	
	/**
	 * 
	 * @author 
	 * cette class represente la partie permutable
	 */
	class TokensVariable extends Tokens {
		
		private List<Paire<String>> tokens;
		
		public TokensVariable() {
			tokens = new ArrayList<>();
			typePArtie = TypePArtie.PERMUTABLE;
		}
		
		@Override
		public void addToken(String token, String tokenImage) {
			tokens.add(new Paire<String>(token, tokenImage));
		}

		@Override
		public boolean isEmpty() {
			return tokens.isEmpty();
		}

		@Override
		protected boolean contain(String tokenImage) {
			for(Paire<String> pair : tokens){
				if(pair.containToken(tokenImage)){
					return true;
				}
			}
			return false;
		}
		@Override
		protected Tokens clone() throws CloneNotSupportedException {
			return (TokensVariable)super.clone();
		}
		@Override
		public boolean isExist(String token) {
			return false;
		}

		@Override
		public void display() {
			for(Paire<String> pair : tokens) 
				pair.display();
		}

		@Override
		public String getToken() {
			return tokens.get(0).getImageToken();
		}

		@Override
		public String getTokenImage() {
			return tokens.get(0).getImageToken();
		}
		
		@Override
		public void remove(String tokenImage) {
			tokens.remove(getCoupleToken(tokenImage));
		}

		@Override   // to do pour l erreur syntaxique 
		public Paire<String> getCoupleToken(String tokenImage) throws IllegalArgumentException {
			for(Paire<String> pair : tokens){ 
				if(pair.containToken(tokenImage))
					return pair;
			}
			throw new IllegalArgumentException("");
		}

		@Override
		public int size() {
			return tokens.size();
		}
		

	}
	

	
//	/**
//	 * methode consume un token dans la requête de l'élève si les règles sont satisfaite
//	 *  les règles : respecte l'une des solutions proposés par le prof
//	 * @param token
//	 * @return
//	 */

//	
