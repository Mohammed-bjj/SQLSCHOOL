package apprendreSQL.Model.analysisTypeMetier.semantic;

import java.util.List;

import apprendreSQL.Model.data.Factory;

/**
 * 
 * @author ACER
 *      Cette classe a comme responsabilité d'évaluer la requete de l'élève
 */
public class Semantique{
	
	private String requete;
	private List<String> reponse ;
	private String requete_prof = "select * from film";  

	
	public Semantique (String q)  {
	}
	
   /**
    *  mis à jour de l'attrubut representant la requête de l'élève
    * @param q  
//    */
//	public void  setRequete(String q) { 
//		 requete=q;
//	}
	 
//	private void init()  {
////		base.connect();
////		base.commande("DROP TABLE IF EXISTS film");
////		base.commande("create table film(mId int, title text, int year);");
////		base.commande("insert into film values(101,'Gone with the wind',1939);");
////		base.commande("insert into film values(102,'Star wars',1977);");
////		base.commande("insert into film values(103,'The sound of music',1965);");
//	}
	
	/**
	 * 
	 * @return la requête de l'élève
	 */
	public String Getrequete() { 
			return requete;
	}	
	
	/**
	 *  mis à jour de l'attribut requete_prof representant la requette de prof
	 * @param sql  
	 */
	public void Setrequete_prof(String sql) {
		requete_prof = sql;
	}
	
	/*
	 *    renvoie la requette de prof 
	 */
	public String Getrequete_prof() {
		return requete_prof;
	}
	
//	
//	public DataBase getBase() {
//		return base;
//	}

	/**
	 * 
//	 * @throws SQLException    affiche les tuples d'une table
//	 */
//	public void afficheReponse() throws SQLException {
//		for( String el:(base.getTuples(this.requete))) {
//			System.out.println(el);
//		}
//	}
	
	/**
	 * 
	 * @return evalue sémantiquement la requete du l'elève 
	 * @throws Exception
	 */
//	public boolean evalue() throws Exception {
//		if(requete.equalsIgnoreCase(requete_prof)){ return true;}
//		else
//		 { 
//			if( base.ExistTable(base.tableRequete(requete))){
//				
//				List<String> r1= new ArrayList();
//				List<String> r2= new ArrayList();
//							 
//				r1= base.getTuples(requete);
//				r2= base.getTuples(requete_prof);
//					
//				if(base.NombreTuples(requete)==base.NombreTuples(requete_prof)) {
//					return r2.containsAll(r1);
//				}else return false;
//			}
//			else return false;
//		 }
//	}
	
	/*

	public boolean evalue() throws Exception {
	if(base.connect() && base.ExistTable(base.tableRequete(requete))){
        return base.getTuples(requete).containsAll(base.getTuples(requete_prof));  											
		}
			return false;
		}*/
	
}
