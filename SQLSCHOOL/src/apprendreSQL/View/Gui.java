package apprendreSQL.View;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import apprendreSQL.Controller.Controller;
import apprendreSQL.Controller.Protocol;
import apprendreSQL.Model.data.Factory;
import apprendreSQL.Model.data.Question;


public class Gui extends JFrame implements Protocol {


	private static final long serialVersionUID = 1L;

   
	
	// ********************************************************************************************//
	
	// ******************************** VARIABLES  ***********************************************// 
	
	private JPanel panBody, panHeader, panParent, panQuestion;
	private Button btn_execute, btn_next, btn_preceden;
	private JTextPane input, ouput;
	private JLabel title_version, question;
	private StyledDocument document_input, document_ouput;
	private Style style;
	
	
	private Controller controller;
	private Question que;
	
	// ********************************************************************************************//

	
	public Gui(Controller contro){
		controller = contro ;
		que = contro.getQuestions();
	}
	
	
	/**
	 *  Cette méthode affiche les erreurs lexical et syntaxique d'une requête 
	 */
	@Override
	public void sendMessage(String msg) {
		ouput.setText(null);
		style = document_ouput.addStyle("",null);
		StyleConstants.setForeground(style, Color.RED);
		try {
			document_ouput.insertString(document_ouput.getLength(),msg, style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *  
	 */
	public void display()
	{
		reset_Frame();
		create_Container();
//		listner();
	}
	

	/**
	 * 
	 * @return une 
	 */
	public Button getButton() {
			return btn_execute;
	}

	
	
	
	/**
	 * 
	 * @return 
	 */
	public String getText()
	{	
		return input.getText();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//******************************************  Fonctionnalité à developper dans les prochaines version du projet *************/
//	private void isKeyWord(String[] text) 
//	{
//		int size = text.length - 1;
//		String word  = text[size];
//		style = input.addStyle("",null);
// 		if(word.equals("select") || word.equals("from") ){
//			try {
//				if(size == 0) document_input.remove(0, 7);
//				else 
//				{
//					int z = (text[size].length() * 2 );
//					document_input.remove(z + 1, z/2);
//					
//				}
//				StyleConstants.setForeground(style, Color.RED);
//				document_input.insertString(document_input.getLength(),text[size] , style);
//				System.out.println(input.getText());
//				StyleConstants.setForeground(style, Color.WHITE);
//				document_input.insertString(document_input.getLength()," " , style);
//			} catch (BadLocationException e) {} 
//		} 
//		
// 		
//	}
//	
//	private void listner() {
//		input.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyReleased(KeyEvent e) {
//				super.keyReleased(e);
//				if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_SEMICOLON) {
//					String mots[] = input.getText().split(" ");
//					if(mots[mots.length -1].contains(";"))
//					{
//						String m[] = mots[mots.length -1].split(" ");
//						m = mots[mots.length -1].split(";");
//						
//						 System.out.println("m : "+ m.length);
//						 isKeyWord(m);
//					}
//					isKeyWord(mots);
//				}
//				
//		
//			}
//		});	
//	}
//	/**
//	 *   La vue envoi une requete au controlleur que lui à son tour l'envoi au Model pour s'assurer est ce mot est 
//	 *   un mot clè { SELECT, FROM ......}
//	 */
//	@Override
//	public void sendIsKeyWord(String word) {
//		controller.sendIsKeyWord(word);
//	}
//	
//	
	
	
	
	
	
	
	//************************************ Contruction de la fenêtre ********************************************/
	
	
	/**
	 * [1]  methode réinitialise les paramétres de la fenêtre  
	 */
	private void reset_Frame() {
		setTitle(Parametre.TITLE_FRAME);
		setSize(Parametre.WEIGHT, Parametre.HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(Factory.makeBorderLayout());
	}
	
	/**
	 *  Création du conteneur 
	 */
	private void create_Container()
	{	
		// Conteneur
		panParent = (JPanel) getContentPane();
		
		create_Header();
		create_Body();
		create_Footer();		
	
		panParent.add(panHeader, BorderLayout.SOUTH);
		panParent.add(panQuestion, BorderLayout.NORTH);
		panParent.add(panBody, BorderLayout.CENTER);
		// raffraichir l'ecran 
		revalidate();
		repaint();
	}
	
	/**
	 *   creation de header de l'interface 
	 */
	private void create_Footer()
	{		
		panHeader = Factory.makePanel();
		panHeader.setBackground(Parametre.HCOLOR);
		panHeader.setPreferredSize(Factory.makeDimension(0, 80));
		
		title_version =   Factory.makeJLabel(Parametre.TITLE_HEADER);
		title_version.setForeground(Color.black);
		title_version.setBackground(Color.YELLOW);
		title_version.setPreferredSize(Factory.makeDimension(189, 40));
		title_version.setOpaque(true);
		title_version.setHorizontalAlignment(SwingConstants.CENTER);
		title_version.setBorder(BorderFactory.createLineBorder(Color.black, 5, true));
	      // Insertion dans le panel Header
		panHeader.add(title_version);
	}
	
	
	private void create_Header() {
		panQuestion = Factory.makePanel();
		panQuestion.setBackground(Color.CYAN);
		panQuestion.setPreferredSize(new Dimension(0,80));


		panQuestion.setLayout(new FlowLayout());
		
		question =     Factory.makeJLabel(que.getQuestion());
		btn_next =     Factory.makeButton("next");
		btn_preceden = Factory.makeButton("precedent");
		
		btn_next.setBackground(Color.BLACK);
		btn_preceden.setBackground(Color.BLACK);
		btn_next.setForeground(Color.YELLOW);
		btn_preceden.setForeground(Color.YELLOW);
		
		
		panQuestion.add(btn_preceden);
		panQuestion.add(question);
		panQuestion.add(btn_next);
	
	}
	/**
	 *   creation du corps de l'interface 
	 */
	private void create_Body()
	{
		panBody = Factory.makePanel();
		panBody.setLayout(Factory.makeFlowLayout());	
		panBody.setLayout(new FlowLayout(FlowLayout.CENTER,10,90));
		panBody.setBackground(Factory.makeColor(203, 247, 93));
		panBody.setPreferredSize(Factory.makeDimension(0, 100));
		
		Border line = BorderFactory.createLineBorder(Factory.makeColor(162, 181, 200), 10);
	
		input = Factory.makeJTextPane();
		ouput = Factory.makeJTextPane();
		document_input = input.getStyledDocument();
		document_ouput = ouput.getStyledDocument();

		btn_execute = Factory.makeButton("Exécute");
		btn_execute.setBackground(Color.BLACK);
		btn_execute.setForeground(Color.WHITE);
		input.setPreferredSize(Factory.makeDimension(Parametre.WEIGHT_TEXTAREA, Parametre.HEIGHT_TEXTAREA));
		input.setForeground(Color.YELLOW);
		input.setBorder(BorderFactory.createTitledBorder(line, Parametre.TITLE_INPUT));

		input.setFont(new Font(Font.SERIF,Font.BOLD, 25));
		input.setBackground(Color.BLUE);
	
				
		ouput.setEditable(false);
		ouput.setPreferredSize(Factory.makeDimension(Parametre.WEIGHT_TEXTAREA , Parametre.HEIGHT_TEXTAREA + 50));
		ouput.setBorder(BorderFactory.createTitledBorder(line, Parametre.TITLE_INPUT));
		
		panBody.add(input);
		panBody.add(btn_execute);
		panBody.add(ouput);
	}


	public Button getButtonExu() {
		return btn_execute;
	}
	public Button getButtonNext() {
		return btn_next;
	}
	
	public Button getButtonPrecedent() {
		return btn_preceden;
	}
	
	public Question getQuestion() {
		return que;
	}
	public void setQuestion(Question qu) {
		this.que = qu;
		this.question.setText(qu.getQuestion());
		input.setText(null);
		ouput.setText(null);
	}


}
