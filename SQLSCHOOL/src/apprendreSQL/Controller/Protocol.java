package apprendreSQL.Controller;

/**
 * 
 * @author equipe de dev 
 * @version 2.0
 * Cette inferface représente un protocol d'échange entre la vue et le controlleur
 *
 */
public interface Protocol {

	default void sendIsKeyWord(String word) {};
	default void sendMessage(String msg) {};
	
}
