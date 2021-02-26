package apprendreSQL.Model.analysisTypeMetier.syntax.particular;



abstract class Tokens implements Cloneable {
	
	protected TypePArtie typePArtie;
	
	static enum TypePArtie {STATIC, PERMUTABLE};
	public abstract void addToken(String imageToken, String token) ;
	public abstract boolean isEmpty() ;
	protected abstract boolean contain(String token) ;		
	public abstract boolean isExist(String token) ;
	public abstract void display();
	public abstract String getToken();
	public abstract String getTokenImage();
	public abstract void remove(String token);
	public abstract Paire<String> getCoupleToken(String token);
	public abstract int size();
	public TypePArtie getTypeToken() {
		return typePArtie;
	}
	@Override
	protected Tokens clone() throws CloneNotSupportedException {
		return (Tokens) super.clone();
	}

}