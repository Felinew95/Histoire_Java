package moteur.traitement;

public abstract class Traitement {
	private String nomTraitement;
	
	public Traitement(String nomTraitement) {
		this.nomTraitement=nomTraitement;
	}
	
	public String getNom() {
		return nomTraitement;
	}
	
	public void setNom(String nom) {
		this.nomTraitement=nom;
	}
	
	public String toString() {
		return "Ceci est un traitement de " + getNom();
	}
	
	




}
