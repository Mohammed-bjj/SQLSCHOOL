package apprendreSQL.Model.analysisTypeMetier.syntax.general;

import java.io.InputStream;
import java.util.List;

import apprendreSQL.Controller.Controller;
import apprendreSQL.Model.data.Observers;

public interface ParserSQL {
	
	default  public void sqlStmtList()  throws ParseException {};
	default  public void ReInit(InputStream stream) {};
	default public void  registerObserver(Observers o) {};
	default public void setDestination(String desti) {};
	default public void updateReponses(List<String> repones) {};
	default public void setcontroller(Controller controller) {};
	default public void display() {};
	default public void reset() {};
	default  public void startAnalyseSemantic() throws  ParseException {};
	
	
}
