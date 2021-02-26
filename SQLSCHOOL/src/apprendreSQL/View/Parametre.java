package apprendreSQL.View;

import java.awt.Color;


/**
 *  Interface a comme responsabilité de paramétrer l'interface Graphique
 * @author mohammed
 *
 */
public interface Parametre {
	
	// Longueru et largeur de la fenêtre
	final int WEIGHT = 600, HEIGHT = 600;
	// Titre de la fenêtre
	final String  TITLE_FRAME = "*** APPRENDRE SQL ***";
	// Titre de footer
	final String TITLE_HEADER = "VERSION TEST";
	
	// largeur et longeur des entrée et sorties
	final int WEIGHT_TEXTAREA = 400, HEIGHT_TEXTAREA = 400;
	
	// Titre des entrées et sortie
	final String TITLE_INPUT = "CONSOLE ", TITLE_OUTPUT = "CORRECTION";
	
	// color
	final Color HCOLOR = new Color(89, 91, 82);
}
