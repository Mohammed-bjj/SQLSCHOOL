package apprendreSQL.Model.data;


public interface Observers {
	default void notifyEventEleve(String token) {};
	default void notifyEventProf(String token) {};
}
