class Usuario{

	private String nombre;
	private String pass;
	private String mail;

	Usuario(){}
	Usuario(String n, String p){
		this.nombre = n;
		this.pass = p;
	}
	Usuario(String n, String p, String c){
		this.nombre = n;
		this.pass = p;
		this.mail = c;
	}

	public String getNombre(){
		return this.nombre;
	}

	public String getPass(){
		return this.pass;
	}

	public String getMail(){
		return this.mail;
	}
}