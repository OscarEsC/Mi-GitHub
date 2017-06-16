import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
import java.util.GregorianCalendar;
import java.util.Calendar;


class Modelo{//modelo

	static String validaUsuario(String us) throws UsuarioInvalido{
		StringTokenizer st;
		String u;
		boolean v = false;

		try{
			File usuarios = new File("usuarios.txt");
			BufferedReader busca = new BufferedReader(new FileReader(usuarios));
			while(usuarios.canRead()){
				st = new StringTokenizer(busca.readLine(),"-");
				u = st.nextToken();

				if (us.equals(u)){
					v = true;
					return st.nextToken(); //retorna la contrasena
				}
				else{
					continue;
				}
			}

		}catch(IOException e){
			System.out.println( e);

		}catch(NoSuchElementException e){
			e.printStackTrace();

		}finally{
			if(v == false){
				throw new UsuarioInvalido();
				//return "None";
			}
		}

		return "None";
	}

	static void buscaRep(String repo) throws DireccionInvalida{
		File repos = new File("repositorios");
		File dir_r = new File(repos.getAbsolutePath()+"\\"+repo);

		if(dir_r.exists() != true)  //si no existe este repo
			throw new DireccionInvalida();
	}

	static void mandarRepo(String repo, Socket usuario){
		int i, nbytes;
		File ar;
		FileInputStream leerA;
		DataOutputStream salida;
		byte[] buffer;

		File repos = new File("repositorios");
		File dir_r = new File(repos.getAbsolutePath()+"\\"+repo);
		File[] archivos = dir_r.listFiles();

		try{
			salida = new DataOutputStream(usuario.getOutputStream());
			salida.writeInt(archivos.length);
			System.out.println("mando no de archivos " + archivos.length);

			for (i=0; i<archivos.length;i++){
				if (archivos[i].isFile()) {
						ar = new File(dir_r.getAbsolutePath(), archivos[i].getName());
						leerA = new FileInputStream(ar);
						salida.writeInt((int)ar.length());
						buffer = new byte[(int)ar.length()];
						nbytes = leerA.read(buffer,0,(int)ar.length());
						salida.writeUTF(archivos[i].getName());
						salida.write(buffer,0,nbytes);
						leerA.close();
						salida.flush();
				}
			}
			//salida.close();

		}catch(IOException ioex){
			System.out.println("Error al mandar archivo. mandarRepo");
		}
	}

	static void agregarUsuario(String n, String p, String m) throws UsuarioInvalido{
		try{
			File usuarios = new File("usuarios.txt");
			if (usuarios.exists()!=true) {
				usuarios.createNewFile();
			}
			//Modelo.validaUsuario(n,p);//buscamos si ya existe el usuario
			//como no hay excepcion, quiere decir que el usuario ya existe
			throw new UsuarioInvalido(1);

		}catch(IOException e){
			e.printStackTrace();

		}catch(UsuarioInvalido Ui){//si manda excepcion el usuario no existe en la base de datos
			try{
				PrintWriter guarda = new PrintWriter(new BufferedWriter(new FileWriter(new File("usuarios.txt"),true)));
				guarda.println(n+"-"+p+"-"+m);
				guarda.close();

			}catch(IOException e){
				System.out.println("Error al escribir usuario");
			}
		}
	}

	static boolean guardaArchivo(String repo, DataInputStream entrada, String nus){ //crea un nuevo archivo que recibio Servidor
		String fecha;
		PrintWriter escribeL;
		Calendar fecha_act = GregorianCalendar.getInstance();
		try{
			FileOutputStream salida;
			byte[] buffer;
			StringTokenizer st = new StringTokenizer(entrada.readUTF(),".");//para cambiar versiones aqui se lee el nombre del archivo
			int nbytes;
			buffer = new byte[entrada.readInt()];
			nbytes = entrada.read(buffer);
			String nom = st.nextToken();
			System.out.println(nom.substring(nom.length()-2,nom.length()-1)+" : "+nom.charAt(nom.length()-1));
			if (nom.substring(nom.length()-2,nom.length()-1).equals("_")==true && Character.isDigit(nom.charAt(nom.length()-1))==true) {
				nom = nom.substring(0,nom.length()-1) +(Integer.parseInt(nom.substring(nom.length()-1, nom.length())) +1); //aumentos la version
			}
			else
				nom = nom+"_1";

			nom = nom+"."+st.nextToken();//le agregamos su extension
			File repos = new File("repositorios");
			File archrec = new File(repos.getAbsolutePath()+"\\"+repo, nom);

			if(archrec.exists()==false){
				archrec.createNewFile();
			}

			salida = new FileOutputStream(archrec);
			salida.write(buffer,0,nbytes);
			salida.close();

			System.out.println("pasa");
			BufferedReader busca = new BufferedReader(new FileReader(new File(repos.getAbsolutePath()+"\\"+repo,"colaboradores.txt")));
			if (nus.equals(busca.readLine())) {//si hace un push el autor de la repo
				System.out.println("entrada");
				File log = new File(repos.getAbsolutePath()+"\\"+repo,"log.txt");
				log.delete();
				log.createNewFile();
				escribeL = new PrintWriter(new BufferedWriter( new FileWriter(log,false)));
				escribeL.println("Archivo mas reciente: "+nom);
				escribeL.println("Autor: "+nus);
				fecha = fecha_act.getTime().toLocaleString();
				escribeL.println("Ultima actualizacion: "+fecha);
				escribeL.close();
			}
			else{
				File logo = new File(repos.getAbsolutePath()+"\\"+repo,"logo.txt");
				if (logo.exists()!=true) {
					logo.createNewFile();
				}
				escribeL = new PrintWriter(new BufferedWriter( new FileWriter(logo,false)));
				escribeL.println("Archivo mas reciente: "+nom);
				escribeL.println("Autor: "+nus);
				fecha = fecha_act.getTime().toLocaleString();
				escribeL.println("Ultima actualizacion: "+fecha);
				escribeL.close();

				File us = new File(repos.getAbsolutePath()+"\\"+repo,"colaboradores.txt");
				us.createNewFile();
				escribeL = new PrintWriter(new BufferedWriter(new FileWriter(us,true)));
				escribeL.println(nus);
				escribeL.close();

			}
			busca.close();
			
			return true;
			//archrec.close();

		}catch(IOException e){
			return false;
		}
	}

	static boolean buscaArchivo(DataOutputStream salida, String narchivo, String nrepo){
		int nbytes;
		byte[] buffer = new byte[1024];

		try{
			File archU = new File(new File("repositorios").getAbsolutePath()+"\\"+nrepo+"\\"+narchivo);
			FileInputStream entrada = new FileInputStream(archU);
			nbytes = entrada.read(buffer, 0, 1024);
			salida.writeUTF(archU.getName());
			salida.write(buffer, 0, nbytes);
			entrada.close();

			return true;
		}catch(IOException e){
			System.out.println("Error al abrir el archivo deseado.");

			return false;
		}
	}

	static boolean crearRepoUsuario(String r, DataInputStream entrada, String nus){ //#PUBLICO#antes se valida si existe con buscaRep
		int nb;
		String fecha;
		Calendar fecha_act = GregorianCalendar.getInstance();
		PrintWriter escribeL;
		try{
			byte[] buff = new byte[entrada.readInt()];//tamaño de bytes 
			nb = entrada.read(buff);

			File repos = new File("repositorios");
			if(repos.exists() != true){
				repos.mkdir();
			}
			File nueva_repo = new File(repos.getAbsolutePath()+"\\"+r);
			nueva_repo.mkdir();
			File innit = new File(nueva_repo,"innit.txt");
			if (innit.exists()!= true) {
				innit.createNewFile();
			}

			FileOutputStream escribe = new FileOutputStream(innit);
			escribe.write(buff,0,nb);
			escribe.close();

			File log = new File(nueva_repo.getAbsolutePath(),"log.txt");
			if (log.exists() != true) {
				log.createNewFile();
			}
			escribeL = new PrintWriter(new BufferedWriter( new FileWriter(log,false)));
			escribeL.println("Archivo mas reciente: innit.txt");
			escribeL.println("Autor: "+nus);
			fecha = fecha_act.getTime().toLocaleString();
			escribeL.println("Ultima actualizacion: "+fecha);
			escribeL.close();

			File us = new File(nueva_repo.getAbsolutePath(),"colaboradores.txt");
			us.createNewFile();
			escribeL = new PrintWriter(new BufferedWriter(new FileWriter(us,true)));
			escribeL.println(nus); //es el dueño del repo
			escribeL.close();

			return true; //avisa a servidor si se creo la repo y el archivo

		}catch(IOException iio){
			return false;
		}
	}

	static boolean crearRepoUsuario(String repo, String pass, DataInputStream entrada, String nus){//#PRIVADO# antes se valida se existe con buscaRep
		int nb;
		String f;
		PrintWriter escribeL;
		Calendar fecha_act = GregorianCalendar.getInstance();
		try{
				byte[] buff = new byte[entrada.readInt()]; //tamaño de bytes del innit
				nb = entrada.read(buff);

				File repos = new File("repositorios");
				if(repos.exists() != true){
					repos.mkdir();
				}

				File nueva_repo = new File(repos.getAbsolutePath()+"\\"+repo);
				nueva_repo.mkdir();
				hacerPrivado(repo, pass);

				File innit = new File(nueva_repo.getAbsolutePath(),"innit.txt");
				if (innit.exists()!=true) {
					innit.createNewFile();
				}
				FileOutputStream escribe = new FileOutputStream(innit);
				escribe.write(buff,0,nb);
				escribe.close();


				File log = new File(nueva_repo.getAbsolutePath(),"log.txt");
				if (innit.exists() != true) {
					log.createNewFile();
				}
				escribeL = new PrintWriter(new BufferedWriter( new FileWriter(log,false)));
				escribeL.println("Archivo mas reciente: innit.txt");
				escribeL.println("Autor: "+nus);
				f = fecha_act.getTime().toLocaleString();
				escribeL.println("Ultima actualizacion: "+f);
				escribeL.close();

				File us = new File(nueva_repo.getAbsolutePath(),"colaboradores.txt");
				us.createNewFile();
				escribeL = new PrintWriter(new BufferedWriter(new FileWriter(us,true)));
				escribeL.println(nus); //es el dueño del repo
				escribeL.close();

				return true; //avisa a servidor si se creo correctamente

			}catch(IOException iio){
				return false;
			}
	}

	static void hacerPrivado(String r, String p){
		File repos = new File("repositorios");
		File ruta = new File(repos.getAbsolutePath()+"\\"+r);

		//si no es privado, lo hace privado, si es privado cambia la pass
		try{

			FileWriter fw = new FileWriter(ruta + "\\" + "privado.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(p);

			bw.close();
			fw.close();

		}catch(IOException e){
			System.out.println("Error al crear el repositorio privado.");
		}
	}

	static void hacerPublico(String r){
		File repos = new File("repositorios");
		File ruta = new File(repos.getAbsolutePath()+"\\"+r);
		File privado = new File(ruta + "\\" + "privado.txt");

		if (privado.exists() == true){
			privado.delete();
		}
	}

	static void eliminarRepoUsuario(String r){
		boolean eliminado;
		File repos = new File("repositorios");
		File el_repo = new File(repos.getAbsolutePath()+"\\"+r);

		if (el_repo.exists()== false){
			System.out.println("El repositorio no se puede eliminar.");
		}
		else{
			eliminado = el_repo.delete();
			System.out.println("El repositorio "+r+" se ha eliminado");
		}
	}

	static void listarRepos(DataOutputStream salida, String n){
		File dir = new File("repositorios");
		File[] repos = dir.listFiles();

		String lista = "";

		for (int i = 0; i < repos.length;i++){

			if( colaboraEnRepo(repos[i].toString(), n) == true){
				
				lista += repos[i].toString();
				lista+= "-";
			}//else no hace nada
		}
		lista = lista.replace("repositorios\\","");

		try{
			salida.writeUTF(lista);
		}catch(IOException e){
			System.out.println("Error al obtener lsita");
		}

	}

	static boolean colaboraEnRepo(String r, String n){
		File dir = new File(r);
		File colaboradores = new File(dir.getAbsolutePath() + "\\colaboradores.txt");

		System.out.println("n= " + n);
		System.out.println(r);
		System.out.println(colaboradores.toString());
		try{
			BufferedReader br = new BufferedReader(new FileReader(colaboradores));
			String linea;

			while((linea = br.readLine()) != null){
				if (linea.equals(n)){
					return true;
				}
			}
			return false;
		}catch(IOException colaboraEnRepo){
			System.out.println("Error al revisar coalboradores");
			return false;
		}
	}

	static void editarRepo(String repo, String nom) throws DireccionInvalida{
		File repos = new File("repositorios");
		File dir_r = new File(repos.getAbsolutePath()+"\\"+repo);
		File dir_n = new File(repos.getAbsolutePath()+"\\"+nom);

		if(dir_n.exists() != true){ //si no existe este repo
			dir_r.renameTo(dir_n);
		}else{
			throw new DireccionInvalida();
		}
	}

	static void enviarDireccion(DataOutputStream salida){
		File repos = new File("repositorios");
		
		try{
			salida.writeUTF(repos.getAbsolutePath());
		}catch(IOException ix){
			System.out.println("error en enviarDireccion.");
		}
	}

	static void pull(String repo,  DataOutputStream salida){
		int i,nbytes;
		File ar;
		BufferedReader leerBA;
		FileInputStream leerA;
		byte[] buffer;
		File repos = new File("repositorios");
		File dir_r = new File(repos.getAbsolutePath()+"\\"+repo+"\\log.txt");//abrimos el archivo log para obtener el más reciente
		
		try{
			leerBA = new BufferedReader(new FileReader(dir_r));
			String recienteA = leerBA.readLine();//la primer linea lee el archivo reciente.
			leerBA.close();
			StringTokenizer sR = new StringTokenizer(recienteA,":");//el segundo token es el nombre del archivo
			String a = sR.nextToken();
			a = sR.nextToken();//este es el archivo
			System.out.println(a);
			a = a.substring(1,a.length());//le quitamos un espacio que tiene al principio

			ar = new File(repos.getAbsolutePath()+"\\"+repo,a);
			leerA = new FileInputStream(ar);
			salida.writeInt((int)ar.length());//mandamos tamaño del archivo
		
			buffer = new byte[(int)ar.length()];
			nbytes = leerA.read(buffer,0,(int)ar.length());
			salida.writeUTF(ar.getName());//mandamos nombre de archivo
			salida.write(buffer,0,nbytes);//mandamos contenido del archivo
		
			leerA.close();
		}catch(IOException ioex){
			System.out.println("Error al mandar archivo. mandarRepo");
			ioex.printStackTrace();
		}
	}

	static boolean guardarCommit(String repoCommit, DataInputStream entrada){
		int nb;
		try{
		//leee el tamaño del commit
			byte[] buff = new byte[entrada.readInt()];//tamaño de bytes 
			nb = entrada.read(buff); //lee el commit
		//crea el commit
			File repo = new File("repositorios");
			File commit = new File(repo.getAbsolutePath() + "\\" + repoCommit + "\\commit.txt");

			if (commit.exists()!= true) {
				commit.createNewFile();
			}
		//escribe el commit
			FileOutputStream escribe = new FileOutputStream(commit);
			escribe.write(buff,0,nb);
			escribe.close();

			return true;

		}catch(IOException e){
			return false;
		}
	}

	static boolean usuarioPropietarioR(String r, String n){
		File repositorios = new File("repositorios");
		File usuarios = new File(repositorios.getAbsolutePath() + "\\" + r +"\\colaboradores.txt");
		try{
			if (usuarios.exists() == true){
				BufferedReader br = new BufferedReader(new FileReader(usuarios));
				String l = br.readLine();
				if (n.equals(l)){
					br.close();
					return true;
				}
				else{
					br.close();
					return false;
				}
			}
			else{
				return false;
			}
		}catch(IOException merge){
			return false;
		}
	}

	static boolean hacerMerge(String r){
		File repositorios = new File("repositorios");
		File log = new File(repositorios.getAbsolutePath() + "\\" + r + "\\log.txt");
		File logo = new File(repositorios.getAbsolutePath() + "\\" + r + "\\logo.txt");
		if (log.exists() == true && logo.exists() == true){
			log.delete();
			logo.renameTo(log);
			return true;
		}else{
			System.out.println("No existen los archivos log o logo");
			return false;
		}

	}
	
}