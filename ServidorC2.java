import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;

class Servidor extends Thread{
	DataInputStream entrada;
	DataOutputStream salida;
	int op;
	Socket usuario;
	Usuario usser;

	Servidor(){}

	Servidor(Socket us){
		this.usuario = us;
	}

	public void run(){

		try{
			entrada = new DataInputStream(usuario.getInputStream());
			salida = new DataOutputStream(usuario.getOutputStream());
			entrada.available();

			while(usuario.isConnected()){ //mientras el usuario se mantenga activo
				op  = entrada.readInt();

				switch(op){

					case 1: //quiere cargar un archivo
							String a = entrada.readUTF();//nombre archivo
							String re = entrada.readUTF();//repo
							try{
								if(checkPrivado(re) == true){ //si es privado y mete bien la contraseña, o si  es publico regresa true
									System.out.println(a);
									System.out.println(re);
									mandaArchivo(a,re); //Los UTF los ocuparemos para los nombres de archivos
								}
							}catch(RepoInvalida riCarga){
								//
							}
							break;

					case 2://quiere guardar un archivo PUSH
							String repA = entrada.readUTF();

							try{
								validarRepo(repA);
								salida.writeBoolean(true);
								boolean r = recibeArchivo(repA);
								salida.writeBoolean(true); //avisamos que se realizo con exitos

							}catch(DireccionInvalida dir){
								salida.writeBoolean(false);
							}
							break;

					case 3: //quiere crear una repo nueva
							String nrepo_n =  entrada.readUTF(); //nombre
							try{
								validarRepo(nrepo_n);
								salida.writeBoolean(false); //la repo no es vaida porque ya existe
								System.out.println("Error al crear repo de "+usser.getNombre());
							}

							catch(DireccionInvalida diri){
								salida.writeBoolean(true);//el repo no existe, por lo tanto es valida

								if (entrada.readBoolean()==true) {//si lo quire privado
									boolean creado = crearRepo(nrepo_n,entrada.readUTF());//leemos la contraseña
									salida.writeBoolean(creado); //avisamos a cliente si se creo su repo
									System.out.println("El usuario "+usser.getNombre()+" ha creado un repositorio privado: "+nrepo_n);
									
								}
								else{
									boolean creado = crearRepo(nrepo_n);
									salida.writeBoolean(creado);//avisamos a cliente si se hizo todo correctamente
									if (creado ==true) {
										System.out.println("El usuario "+usser.getNombre()+" ha creado un repositorio publico: "+nrepo_n);
									}
									else
										System.out.println("No se pudo crear el repositorio"+nrepo_n);
									
								}

							}
							break;

					case 4: //iniciar sesion
							try{
								String n_us = entrada.readUTF();
								String contra = validaUsuario(n_us);//hasta qui llega el try si no existe usuario
								System.out.println(contra);

								salida.writeBoolean(true);//el usuario si existe
								String c_recibida = entrada.readUTF();

								if (contra.equals(c_recibida)) { //la contrasena es correcta
									crearUsuario(n_us, c_recibida);
									salida.writeBoolean(true);
								}
								else{
									salida.writeBoolean(false);
								}
								
							}catch(UsuarioInvalido noExiste){
								salida.writeBoolean(false);
							}
							break;

					case 5: //va a registrar un usuario
							registrarUsuario(entrada.readUTF(), entrada.readUTF(), entrada.readUTF());
							break;

					case 6://##########hace clone
							System.out.println("Usuario: "+usser.getNombre()+" quiere copiar un repositorio");
							String rep = entrada.readUTF();

							try{
								validarRepo(rep);
								System.out.println("EL repositorio de "+usser.getNombre()+" fue encontrado encontrado: "+rep);
								salida.writeBoolean(true);
								copiarRepo(rep);

							}catch(DireccionInvalida de){
								System.out.println("El repositorio de"+usser.getNombre()+" no fue encontrado: "+rep);
								salida.writeBoolean(false);
							}
							break;

					case 7://editar nombre de repo 																		#check privado
							String repo = entrada.readUTF();
							String nom = entrada.readUTF();

							try{
								validarRepo(repo);	
								editarRepo(repo, nom);
								salida.writeBoolean(true);

							}catch(DireccionInvalida de){
								System.out.println("Repo no encontrada. "+repo);
								salida.writeBoolean(false);
							}
							break;

					case 8://editar acceso
							String repoc = entrada.readUTF();
							System.out.println(repoc);
							try{
								validarRepo(repoc);	
								salida.writeBoolean(true);//si existe repo
								System.out.println("Si pasa");
								if(entrada.readBoolean() == true){//si se quiere hacer privado
									String pc = entrada.readUTF();
									System.out.println("privado: " + pc);
									Modelo.hacerPrivado(repoc,pc);
									

								}else{//si no se quiere hacer privado
									System.out.println("publico");
									Modelo.hacerPublico(repoc);
								}

							}catch(DireccionInvalida de){
								System.out.println("Repo no encontrada. "+repoc);
								salida.writeBoolean(false);
							}
							finally{
								break;
							}

					case 9: //quiere la direccion de memoria de repositorios
							enviaDireccion();
							break;

					case 10://va a hacer un innit
							String rep1 = entrada.readUTF();
							try{
								validarRepo(rep1);
								salida.writeBoolean(true);
								//creaInnit();		La comente porque falta el metodo ********************************
				
							}catch(IOException ioe){
								salida.writeBoolean(false);
							}catch(DireccionInvalida diInnit){
								
								salida.writeBoolean(false);
							}
							break;
					case 11://PULL
							String repoPull =  entrada.readUTF();

							try{
								validarRepo(repoPull);
								salida.writeBoolean(true);
								pull(repoPull);

							}catch(DireccionInvalida de){
								System.out.println("Repo no encontrada. "+repoPull);
								salida.writeBoolean(false);
							}
							break;
					case 13://commit
						String repoCommit = entrada.readUTF();
						if (guardarCommit(repoCommit) == true){
							salida.writeBoolean(true);
						}else{
							salida.writeBoolean(false);
						}
						break;
					
					case 14: //aplca Merge
							String no_rep= entrada.readUTF();//nombre del rep
							try{
								validarRepo(no_rep);
								salida.writeBoolean(true);
								boolean f = usuarioPropietarioR(no_rep);
								System.out.println("b - " + f);
								salida.writeBoolean(f);//revisa si el usuario es propietario
								if (hacerMerge(no_rep) == true){
									salida.writeBoolean(true);
								}else{
									salida.writeBoolean(false);
								}
								//que regrese un booleano para decirle que se hizo bien
							}catch(DireccionInvalida diI){
								salida.writeBoolean(false);
							}
							break;
					case 40:
							listarRepos();
							break;

					default:
							break;

				}
			}
			entrada.close();

		}catch(SocketException se){
			System.out.println("El usuario" + usser.getNombre()+" se desconecto");

		}catch(IOException e){
			System.out.println("Error en el flujo de datos con usuario");
		}
		
	}

	synchronized boolean recibeArchivo(String repA){
		return Modelo.guardaArchivo(repA,entrada,usser.getNombre());
	}

	synchronized void mandaArchivo(String narchivo,String nrepo){//por si al buscar se quiere cambiar el nombre
		boolean t = Modelo.buscaArchivo(salida,narchivo,nrepo);
	}

	synchronized void registrarUsuario(String nombre, String pass, String correo){
		try{
			Modelo.agregarUsuario(nombre,pass,correo);
			System.out.println("se guardo el usuario "+ nombre);

		}catch(UsuarioInvalido us){
			System.out.println(us);
		}	
	}

	boolean crearRepo(String repo){ //repo publica
		return Modelo.crearRepoUsuario(repo,entrada,usser.getNombre());//retonamos lo que regrese Modelo.crearRepoUsuario
	}

	boolean crearRepo(String repo, String pass){//repo privada
		return Modelo.crearRepoUsuario(repo,pass,entrada,usser.getNombre());
	}

	void crearUsuario(String nombre, String pass){
		usser = new Usuario(nombre,pass);
		System.out.println("El usuario "+usser.getNombre()+" ha establecido conexion.");
	}

	void validarRepo(String repo) throws DireccionInvalida{
		Modelo.buscaRep(repo);
	}

	void enviaDireccion(){
		Modelo.enviarDireccion(salida);
	}

	synchronized void copiarRepo(String rep){
		Modelo.mandarRepo(rep, usuario);
	}

	String validaUsuario(String nom) throws UsuarioInvalido{ //crea un un objeto usuario que va ainteractuar en ese momento.
			return Modelo.validaUsuario(nom);
	}

	synchronized void listarRepos(){
		Modelo.listarRepos(salida, usser.getNombre());
		
	}

	void pull(String repo){
		Modelo.pull(repo, salida);
	}

	synchronized void editarRepo(String repo, String nom) throws DireccionInvalida{
		Modelo.editarRepo(repo,nom);
	}

	boolean usuarioPropietarioR(String nom_rep){
		return Modelo.usuarioPropietarioR(nom_rep, usser.getNombre());
	}

	boolean hacerMerge(String no_rep){
		return Modelo.hacerMerge(no_rep);
	}

	boolean checkPrivado(String r) throws RepoInvalida{

		File repos = new File("repositorios");
		File ruta = new File(repos.getAbsolutePath()+"\\"+r);
		File lock	= new File(ruta.getAbsolutePath() + "\\privado.txt" );

		if(ruta.exists() == true){
			if(lock.exists() == true){

				return true;
			}else{
				return false;
			}

		}else{
			throw new RepoInvalida();
		}

	}

	boolean guardarCommit(String repoCommit){
		return Modelo.guardarCommit(repoCommit, entrada);
	}
}