package apprendreSQL.Model.analysisTypeMetier.syntax.particular;

class Paire<T> {
	private T token;
	private T imageToken;
	public Paire (T token, T imageToken){
		this.token      = token;
		this.imageToken = imageToken;
	}
	
	public T getToken() {
		return token;
	}
	
	public T getImageToken() {
		return imageToken;
	}
	
	public void display() {
		System.out.print("( "+token+" , "+imageToken+" )");
	}
	
	public boolean containToken(T tokenIm) {
		return this.imageToken == tokenIm;
	}
	
	
}