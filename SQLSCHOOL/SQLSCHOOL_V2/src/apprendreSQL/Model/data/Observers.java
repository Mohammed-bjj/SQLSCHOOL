package apprendreSQL.Model.data;


public interface Observers {
	default void notifyEventEleve(String tokenImage, String token) {};
	default void notifyEventProf(String tokenImage, String token) {};
}
