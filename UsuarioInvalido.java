class UsuarioInvalido extends Exception{
	UsuarioInvalido(){
		super("Datos de usuario invalidos.");
	}
	UsuarioInvalido(int opc){
		super("Este usuario ya existe");
	}
}