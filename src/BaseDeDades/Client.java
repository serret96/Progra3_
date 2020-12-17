package BaseDeDades;

public class Client {
	private double latc;
	private double lonc;
	
	public Client (double lc, double lonc) {
		this.latc = lc;
		this.lonc = lonc;
	}
	
	public void modificarUbicacio (double lat, double lon) {
		this.latc = lat;
		this.lonc = lon;
	}
	
	public double getLatc() {
		return latc;
	}
	
	public double getLonc() {
		return lonc;
	}

	public void setLatc(double latc) {
		this.latc = latc;
	}

	public void setLonc(double lonc) {
		this.lonc = lonc;
	}
}
