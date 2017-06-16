import javax.swing.*;//merge actualiza el archivo más reciente y solo lo hara el propietario de l arepo
import java.awt.*;
import java.awt.event.*;

import java.net.Socket;
import java.io.*;

class Cliente extends JFrame implements ActionListener{
	Socket usuario;
	DataOutputStream flujoS;
	DataInputStream flujoE;

	JButton b1, b2, b3, ba1, ba2, bacerca, bru1, bru2, brecU1, brecU2, bzt1, bzt2, bzt3, bzt4, bcr1, bcr2, blr, beR1, beR2, beR3, bP, bP2, bar1, bar2;
	JButton bzt5, bzt6, bzt7, bCn1, bCn2, ba3, ba4, bPu1, bPu2;
	JRadioButton check, check1;

	JFrame menu, auten, acerca, ventana, crear, editarRepo, pass, pass2, listRepo, regUser, mensaje, rec, archivoF, cClone, cCloneg,pushF;
	JTextArea areaArchivo;
	
	JTextField ip, npuerto, usser, cR1, eR1, eR2, ru1, ru2, ipr, npuertor, usserr, correo, repoPush, r1, nr, arch_nom, repo_clone, dir, in_rep;
	JTextField arch_push,repo_push;
	JPasswordField passw, p1, p2, ru3;
	JFileChooser file;


	private static final long serialVersionUID = 123;

	Cliente(){
		try{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		autenticacion();
	}



//	-----------------------------------		FUNCIONES DEL MENU PRINCIPAL 	-----------------------------------
	void autenticacion(){
		auten = new JFrame();
		Container panel = auten.getContentPane();
		auten.setTitle("Usuario");

		panel.setLayout(new FlowLayout());
		JLabel e1 = new JLabel("IP: ");
		ip = new JTextField("");
		ip.setColumns(10);
		
		JLabel e2 = new JLabel("Puerto: ");
		npuerto = new JTextField("");
		npuerto.setColumns(5);
		
		JLabel e3 = new JLabel("Usuario: ");
		usser = new JTextField("");
		usser.setColumns(10);

		ba1 = new JButton("Conectar");
		ba2 = new JButton("Salir");

		ba1.addActionListener(this);
		ba2.addActionListener(this);
		
		panel.add(e1);
		panel.add(ip);
		panel.add(e2);
		panel.add(npuerto);
		panel.add(e3);
		panel.add(usser);
		panel.add(ba1);
		panel.add(ba2);
		auten.pack();

		auten.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		auten.setVisible(true);
		auten.setSize(500, 150);
	}

	void acercaDe(){
		acerca = new JFrame();
		Container panel = acerca.getContentPane();
		acerca.setTitle("Acerca de...");
		panel.setLayout(new FlowLayout());

		JLabel e = new JLabel("* ** * ** * ** * ** * ** * ** * ** * ** *           MyGitHub           * ** * ** * ** * ** * ** * ** * ** * ** *");
		JLabel e1 = new JLabel("			MyGitHub   1.0.0			"); 
		JLabel e2 = new JLabel("MyGitHub SA CV, licencia GNU");
		JLabel e3 = new JLabel ("Karen Abril Robles Uribe, Oscar Espinosa Curiel, Aaron Enrique Mejia Ortiz, Edgar Hernandez");
		JLabel e4 = new JLabel ("Semestre 2017-1															.");
		bacerca = new JButton("Cerrar");

		bacerca.addActionListener(this);
		
		panel.add(e);
		panel.add(e1);
		panel.add(e2);
		panel.add(e3);
		panel.add(e4);
		panel.add(bacerca);
		acerca.pack();

		acerca.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		acerca.setVisible(true);
		acerca.setSize(550, 200);
	}

//	-----------------------------------		FUNCIONES EXTRAS AL INICIAR SESION 	-----------------------------------
	void registrarUsuario(){
		regUser = new JFrame();
		Container panel = regUser.getContentPane();
		regUser.setTitle("Registrar Usuario");
		panel.setLayout(new FlowLayout());
		
		JLabel e1 = new JLabel("Nombre: ");
		ru1 = new JTextField("");
		ru1.setColumns(15);
		
		JLabel e2 = new JLabel("Correo: ");
		ru2 = new JTextField("");
		ru2.setColumns(15);

		JLabel e3 = new JLabel("Password: ");
		ru3 = new JPasswordField("");
		ru3.setColumns(14);

		bru1 = new JButton("Registrar");
		bru2 = new JButton("Cancelar");

		bru1.addActionListener(this);
		bru2.addActionListener(this);

		panel.add(e1);
		panel.add(ru1);
		panel.add(e2);
		panel.add(ru2);
		panel.add(e3);
		panel.add(ru3);
		panel.add(bru1);
		panel.add(bru2);
		regUser.pack();

		regUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		regUser.setVisible(true);
		regUser.setSize(280, 200);
	}

	/*void recuperarUsuario(){
		rec = new JFrame();
		Container panel = rec.getContentPane();
		rec.setTitle("Recuperar Usuario");
		panel.setLayout(new FlowLayout());
		
		JLabel e1 = new JLabel("IP: ");
		ipr = new JTextField("");
		ipr.setColumns(10);
		
		JLabel e2 = new JLabel("Puerto: ");
		npuertor = new JTextField("");
		npuertor.setColumns(5);

		JLabel e3 = new JLabel("Usuario: ");
		usserr = new JTextField("");
		usserr.setColumns(10);

		JLabel e4 = new JLabel("Correo: ");
		correo = new JTextField("");
		correo.setColumns(10);

		brecU1 = new JButton("Recuperar");
		brecU2 = new JButton("Cerrar");

		brecU1.addActionListener(this);
		brecU2.addActionListener(this);

		panel.add(e1);
		panel.add(ipr);
		panel.add(e2);
		panel.add(npuertor);
		panel.add(e3);
		panel.add(usserr);
		panel.add(e4);		
		panel.add(correo);
		panel.add(brecU1);
		panel.add(brecU2);
		rec.pack();

		rec.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rec.setVisible(true);
		rec.setSize(650, 200);
	}*/

//	-------------------------------------------     ZONA DE TRABAJO     ------------------------------
	JButton bzt8;
	/*void menuZonaDeTrabajo(){
		zTrabajo = new JFrame();
		Container panel = zTrabajo.getContentPane();
		zTrabajo.setTitle("MyGitHub");
		panel.setLayout(new FlowLayout());

		bzt1 = new JButton("Crear nuevo repositorio");
		bzt2 = new JButton("Listar repositorio");
		bzt3 = new JButton("Editar repositorio");
		bzt4 = new JButton("Cerrar");


		bzt5 = new JButton("Push");
		bzt6 = new JButton("Pull");
		bzt7 = new JButton("Clone");
		bzt8 = new JButton("Merge");

		bzt1.addActionListener(this);
		bzt2.addActionListener(this);
		bzt3.addActionListener(this);
		bzt4.addActionListener(this);

		//--------------------------------------------
		bzt5.addActionListener(this);
		bzt6.addActionListener(this);
		bzt7.addActionListener(this);
		bzt8.addActionListener(this);

		panel.add(bzt5);
		panel.add(bzt6);
		panel.add(bzt7);
		panel.add(bzt8);

		//-------------------------------------------
		panel.add(bzt1);
		panel.add(bzt2);
		panel.add(bzt3);
		panel.add(bzt4);
		zTrabajo.pack();

		zTrabajo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		zTrabajo.setVisible(true);
		zTrabajo.setSize(350, 150);
	}*/

	JMenuItem mCrear, mListar, mEditar, mPush, mPull, mClone, mMerge, mAcercaDe; 
	JMenu crearr, listar;

	void menuPrincipal(){
		JFrame ventana = new JFrame("GitHub");
	    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //ventana.setLocationRelativeTo(null);

	    JMenuBar barra = new JMenuBar();

	    JMenu editarCa = new JMenu("Editar campos");
	    //archivo.setLayout(new GridLayout(3,3));
	    mCrear = new JMenuItem("Crear");
	    mListar = new JMenuItem("Listar");
	    mEditar = new JMenuItem("Editar");
	    mPush = new JMenuItem("Push");
	    mPull = new JMenuItem("Pull");
	    mClone = new JMenuItem("Clone");
	    mMerge = new JMenuItem("Merge");

	    editarCa.add(mListar);
	    editarCa.add(mCrear);
	    editarCa.add(mEditar);
	    editarCa.add(mPush);
	    editarCa.add(mPull);
	    editarCa.add(mClone);
	    editarCa.add(mMerge);
	    editarCa.addSeparator();

	    mCrear.addActionListener(this);
	    mListar.addActionListener(this);
	    mEditar.addActionListener(this);
	    mPush.addActionListener(this);
	    mPull.addActionListener(this);
	    mClone.addActionListener(this);
	    mMerge.addActionListener(this);

	    JMenu ayuda = new JMenu("Ayuda");  
	    mAcercaDe = new JMenuItem("Acerca de...");
	    
	    ayuda.add(mAcercaDe);
	    mAcercaDe.addActionListener(this);

	    barra.add(editarCa);
	    barra.add(ayuda);

	    ayuda.addSeparator();

	    ventana.setJMenuBar(barra);
	    ventana.add(barra, BorderLayout.NORTH);
	    ventana.pack();
	    ventana.setSize(1100, 700);
	    ventana.setVisible(true);
	}

//	-------------------------		FUNCIONES DEL MENU ZONA DE TRABAJO(CREAR,LISTAR Y EDITAR REPOSITORIO) 	--------------------------
	void crearRep(){
		crear = new JFrame();
		Container panel = crear.getContentPane();
		crear.setTitle("Crear repositorio");
		panel.setLayout(new FlowLayout());
		
		JLabel e1 = new JLabel("Nombre del repositorio: ");
		cR1 = new JTextField("");
		cR1.setColumns(10);

		JLabel e2 = new JLabel("Acceso: ");
		check = new JRadioButton("Privado", false);

		bcr1 = new JButton("Innit");
		bcr2 = new JButton("Cerrar");

		bcr1.addActionListener(this);
		bcr2.addActionListener(this);

		panel.add(e1);
		panel.add(cR1);
		panel.add(e2);
		panel.add(check);
		panel.add(bcr1);
		panel.add(bcr2);
		crear.pack();

		crear.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crear.setVisible(true);
		crear.setSize(400, 200);
	}

	void listarRep(String[] data){

		listRepo = new JFrame();
		Container panel = listRepo.getContentPane();
		listRepo.setTitle("Autenticacion");
		panel.setLayout(new FlowLayout());

		JLabel e1 = new JLabel("Repositorios: ");
		blr = new JButton("Cerrar");

		JList<String> l1 = new JList<String>(data);

		JScrollPane listScroller = new JScrollPane(l1);
		listScroller.setPreferredSize(new Dimension(250, 80));

		blr.addActionListener(this);
		
		l1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		l1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		l1.setVisibleRowCount(-1);
		
		listRepo.add(e1);
        listRepo.getContentPane().add(listScroller);
        listRepo.add(blr);
        listRepo.pack();

		listRepo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		listRepo.setVisible(true);
		listRepo.setSize(350, 550);
	}

	void editarRep(){
		editarRepo = new JFrame();
		Container panel = editarRepo.getContentPane();
		editarRepo.setTitle("Editar Repositorio");
		panel.setLayout(new FlowLayout());

		JLabel e1 = new JLabel("Nuevo nombre: ");
		JLabel e3 = new JLabel("Archivo a modificar: ");
		beR1 = new JButton("Cambiar Nombre");
		eR1 = new JTextField("");
		eR2 = new JTextField("");
		eR1.setColumns(15);
		eR2.setColumns(25);

		JLabel e2 = new JLabel("Tipo de acceso: ");
		beR2 = new JButton("Cambiar Acceso");
		check1 = new JRadioButton("Privado", false);

		beR3 = new JButton("Cerrar");

		beR1.addActionListener(this);
		beR2.addActionListener(this);
		beR3.addActionListener(this);

		panel.add(e3);
		panel.add(eR2);

		panel.add(e1);
		panel.add(eR1);
		panel.add(beR1);

		panel.add(e2);
		panel.add(beR2);
		panel.add(check1);
		panel.add(beR3);
		editarRepo.pack();

		editarRepo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editarRepo.setVisible(true);
		editarRepo.setSize(450, 200);
	}

//	-----------------------------------		Commit 	-----------------------------------
	JFrame commitF;
	JTextArea archivoCommit;
	JButton bCommit1, bCommit2; 
	String repoCommit;

	void commit(String repo){ //a string que le mandan debe ser el nombre del repo

		repoCommit = repo;

		commitF = new JFrame();
		Container panel = commitF.getContentPane();
		commitF.setTitle("Commit");
		panel.setLayout(new FlowLayout());

		JLabel e = new JLabel("Escribe la informacion sobre el commit en "+ repo);
		archivoCommit = new JTextArea(200,100);

		JScrollPane listScroller = new JScrollPane(archivoCommit);
		listScroller.setPreferredSize(new Dimension(750, 500));

		bCommit1 = new JButton("Guardar");
		bCommit2 = new JButton("Cancelar");

		bCommit1.addActionListener(this);
		bCommit2.addActionListener(this);
		
		panel.add(e);
        panel.add(listScroller);
        panel.add(bCommit1);
        panel.add(bCommit2);
        commitF.pack();

		commitF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		commitF.setVisible(true);
		commitF.setSize(1000, 800);
	}


//	-----------------------------------		FUNCIONES AUXILIARES (PASSWORD Y ARCHIVO)	-----------------------------------

	void password(){
		pass = new JFrame();
		Container panel = pass.getContentPane();
		panel.setLayout(new FlowLayout());

		JLabel e1 = new JLabel("Password: ");
		p1 = new JPasswordField("");
		p1.setColumns(10);
		bP = new JButton("Aceptar");

		bP.addActionListener(this);

		panel.add(e1);
		panel.add(p1);
		panel.add(bP);

		pass.pack();
		pass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pass.setVisible(true);
		pass.setSize(250, 150);
	}

	void password2(){
		pass2 = new JFrame();
		Container panel = pass2.getContentPane();
		panel.setLayout(new FlowLayout());

		JLabel e1 = new JLabel("Password: ");
		p2 = new JPasswordField("");
		p2.setColumns(10);
		bP2 = new JButton("Aceptar");

		bP2.addActionListener(this);

		panel.add(e1);
		panel.add(p2);
		panel.add(bP2);

		pass2.pack();
		pass2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pass2.setVisible(true);
		pass2.setSize(250, 150);
	}

	JFrame pass3;
	JPasswordField pass_3;
	JButton bPass3;

	void password3(){
		pass3 = new JFrame();
		Container panel = pass3.getContentPane();
		panel.setLayout(new FlowLayout());

		JLabel e1 = new JLabel("Password: ");
		pass_3 = new JPasswordField("");
		pass_3.setColumns(10);
		bPass3 = new JButton("Aceptar");

		bPass3.addActionListener(this);

		panel.add(e1);
		panel.add(pass_3);
		panel.add(bPass3);

		pass3.pack();
		pass3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pass3.setVisible(true);
		pass3.setSize(250, 150);
	}

	void archivo(){
		archivoF = new JFrame();
		Container panel = archivoF.getContentPane();
		archivoF.setTitle("innit");
		panel.setLayout(new FlowLayout());

		JLabel e = new JLabel("Escribe la informacion sobre le repositorio "+ cR1.getText());

		areaArchivo = new JTextArea(200,100);
		JScrollPane listScroller = new JScrollPane(areaArchivo);
		listScroller.setPreferredSize(new Dimension(750, 500));

		bar1 = new JButton("Guardar");
		bar2 = new JButton("Cancelar");

		bar1.addActionListener(this);
		bar2.addActionListener(this);
		
		panel.add(e);
        panel.add(listScroller);
        panel.add(bar1);
        panel.add(bar2);
        archivoF.pack();

		archivoF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		archivoF.setVisible(true);
		archivoF.setSize(1000, 800);
	}

//	-----------------------------------		COMANDOS REQUERIDOS 	-----------------------------------
	void cloneR(){	//Validar si el repo es privado
		cClone = new JFrame();
		Container panel = cClone.getContentPane();
		cClone.setTitle("clone");
		panel.setLayout( new FlowLayout());

		JLabel r = new JLabel("Repo a copiar: ");
		repo_clone = new JTextField("");
		repo_clone.setColumns(10);

		JLabel d = new JLabel("Guardar en: ");
		dir = new JTextField("");
		dir.setColumns(14);

		bCn1 = new JButton("Copiar");
		bCn2 = new JButton("Cancelar");

		bCn1.addActionListener(this);
		bCn2.addActionListener(this);

		panel.add(r);
		panel.add(repo_clone);
		panel.add(d);
		panel.add(dir);

		panel.add(bCn1);
		panel.add(bCn2);
		cClone.pack();

		cClone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cClone.setVisible(true);
		cClone.setSize(300,300);
	}

	void pushC(){
		file = new JFileChooser();
		file.setDialogTitle("Agregar...");
		int selec = file.showDialog(null, "Seleccionar");

		switch(selec) {
			case JFileChooser.APPROVE_OPTION:
				String rt = file.getSelectedFile().getAbsolutePath();

				pushF = new JFrame();
				Container panel = pushF.getContentPane();
				pushF.setTitle("Push");
				panel.setLayout( new FlowLayout());

				JLabel e2 = new JLabel("Nombre del repositorio donde se guardara: ");
				repo_push = new JTextField("");
				repo_push.setColumns(14);

				bPu1 = new JButton("Agregar");
				bPu2 = new JButton("Cancelar");

				bPu1.addActionListener(this);
				bPu2.addActionListener(this);

				panel.add(e2);
				panel.add(repo_push);

				panel.add(bPu1);
				panel.add(bPu2);
				pushF.pack();

				pushF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pushF.setVisible(true);
				pushF.setSize(300,300);

				break;

			case JFileChooser.CANCEL_OPTION:
				//dio click en cancelar o cerro la ventana
				break;

			case JFileChooser.ERROR_OPTION:
			 	//llega aqui si sucede un error
				break;
		}
	}

	JFrame pullF;
	JButton bpull1, bpull2;
	JTextField pull1,pull2;
	void pullM(){
		pullF = new JFrame("Pull");
		Container panel = pullF.getContentPane();
		pullF.setTitle("Pull");
		panel.setLayout(new FlowLayout());

		JLabel e1 = new JLabel("Ruta local:");
		pull1 = new JTextField("");
		pull1.setColumns(14);

		JLabel e2 = new JLabel("Nombre Repositorio");
		pull2 = new JTextField("");
		pull2.setColumns(10);

		bpull1 = new JButton("Pull");
		bpull2 = new JButton("Cerrar");

		bpull1.addActionListener(this);
		bpull2.addActionListener(this);

		panel.add(e1);
		panel.add(pull1);

		panel.add(e2);
		panel.add(pull2);

		panel.add(bpull1);
		panel.add(bpull2);


		pullF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pullF.setVisible(true);
		pullF.setSize(270, 150);
	}

	JFrame aplicaM;
	JTextField rep_m;
	JButton acp_m,canc_m;
	void merge(){
		aplicaM = new JFrame("Merge");
		Container panel = aplicaM.getContentPane();
		aplicaM.setTitle("Merge");
		aplicaM.setLayout(new FlowLayout());

		JLabel em = new JLabel("Repo a aplicar merge: ");
		rep_m = new JTextField("");
		rep_m.setColumns(10);

		acp_m = new JButton("Aceptar");
		canc_m = new JButton("Cancelar");

		acp_m.addActionListener(this);
		canc_m.addActionListener(this);

		panel.add(em);
		panel.add(rep_m);
		panel.add(acp_m);
		panel.add(canc_m);

		aplicaM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aplicaM.setVisible(true);
		aplicaM.setSize(270, 150);

	}

//	-----------------------------------		FUNCIONES AUXILIARES DE LOS COMANDOS 	-----------------------------------
	void descargarArch(int no_archivos, String dir_mem){

		File arc;
		FileOutputStream escritura;
		byte[] buffer;
		int n, nbytes;
		String nan="";

		try{
			for (int i=0;i<no_archivos; i++) {//el numero de archivos a copiar
				n = flujoE.readInt();//tamaño del arch
				buffer = new byte[n];
				nan = flujoE.readUTF(); //nombre archivo

				arc = new File(dir_mem,nan);
				arc.createNewFile();
				escritura = new FileOutputStream(arc);

				nbytes = flujoE.read(buffer);
				escritura.write(buffer,0,nbytes);
				escritura.close();
			}
			if (no_archivos==1) {//hizo pull
				mostrarMensaje("Se ha descargado el archivo "+nan+" a "+ dir_mem);
			}
			else//hizo clone
				mostrarMensaje("Se ha descargado el repositorio correctamente");
		}catch(IOException ioex){
			mostrarError("Error al descargar los archivos.");
			//cClone.dispose();
			//cloneR();
		}
		
	}

	void mandaPush(){
		byte[] buffer;
		FileInputStream leerA;
		int nb;
		try{
			flujoS.writeInt(2);//recibe push
			flujoS.writeUTF(repo_push.getText());//mandamos repo donde lo quiere guardar
			if (flujoE.readBoolean()==true) { //la repo si existe
				flujoS.writeUTF(file.getSelectedFile().getName());//mandamos el nombre del archivo a servidor
				leerA = new FileInputStream(file.getSelectedFile());//el archivo que quiere subir al servidor
				flujoS.writeInt((int)file.getSelectedFile().length());//le decimos al servidor(Modelo) el tamaño del archivo
				buffer = new byte[(int)file.getSelectedFile().length()];
				nb = leerA.read(buffer);
				flujoS.write(buffer,0,nb);
				flujoS.flush(); //solo por si las moscas, creo que los usamos mucho :v
				if (flujoE.readBoolean()==true) {
					mostrarMensaje("Se subio el archivo "+file.getSelectedFile().getName()+" con exito");
				}
				else
					mostrarError("No se logro subir el archivo "+file.getSelectedFile().getName());

			}
		}catch(IOException xx){
			mostrarError("Error em mandarPush");
		}
	}

	void guardarCRepo(){
		File arc;
		File dirR = new File(dir.getText()); //direccion donde quiere la copia
		if (dirR.exists()) {
			try{
				flujoS.writeInt(6);
				flujoS.writeUTF(repo_clone.getText());

				if (flujoE.readBoolean() == true) {
					File repC = new File(dir.getText()+repo_clone.getText());

					if (repC.exists()) {
						repC = new File(dir.getText()+repo_clone.getText()+"_copia");
						repC.mkdir();
					}
					else{
						repC.mkdir();
					}

					descargarArch(flujoE.readInt(),repC.getAbsolutePath()); // numero de archivos a descargar y donde se descargaran

				}
				else{
					mostrarError("Este repositorio no existe o no tienes acceso a el. Intenta de nuevo");
					cClone.dispose();
					cloneR();
				}
			}catch(IOException iex){
				mostrarError("Error al conectarse con servidor. Intenta de nuevo");
				cClone.dispose();
				cloneR();
			}
		}
		else{
			mostrarError("Ingresaste una direccion de memoria invalida. Intenta de nuevo.");
			cClone.dispose();
			cloneR();
		}
	}

	void mandaInnit(){
		byte[] buff;

		try{
				//Enviar datos de salida
				flujoS.writeInt(3);
				flujoS.writeUTF(cR1.getText());	//Para madar el nombre del repositorio nuevo
				if (flujoE.readBoolean()==true) {	//el repo es valido

					if (check.isSelected()) { //si es privado
						flujoS.writeBoolean(true);//avisamos a servidor que el repo sera privado
						flujoS.writeUTF(pass_3.getText());
						buff = new byte[areaArchivo.getText().length()];
						buff = areaArchivo.getText().getBytes();//pasamos lo que escribio a bytes
						flujoS.writeInt(areaArchivo.getText().length());
						flujoS.write(buff,0,areaArchivo.getText().length());

						if (flujoE.readBoolean() == true) { //servidor dice que se creo correctamente
							mostrarMensaje("El repositorio "+cR1.getText()+" se ha creado");
							archivoF.dispose();
						}
						else
							mostrarError("Error al crear el repositorio "+cR1.getText());
							archivoF.dispose();
					}

					else{
						flujoS.writeBoolean(false);
						buff = new byte[areaArchivo.getText().length()];
						buff = areaArchivo.getText().getBytes();//pasamos lo que escribio a bytes
						flujoS.writeInt(areaArchivo.getText().length());
						flujoS.write(buff,0,areaArchivo.getText().length());

						if (flujoE.readBoolean() == true) { //servidor dice que se creo correctamente
							mostrarMensaje("El repositorio "+cR1.getText()+" se ha creado");
							archivoF.dispose();
						}
						else
							mostrarError("Error al crear el repositorio "+cR1.getText());
							archivoF.dispose();
					}
				}
				else{
					mostrarError("Este repositorio no existe o no puedes acceder a el.");
					archivoF.dispose();
					archivo();
				}

			}catch(IOException ioe){
				mostrarMensaje("Erron en coneccion "+ioe.getMessage() );

			}catch(IllegalArgumentException iae){
				mostrarMensaje("Error en argumentos dados. Intenta de nuevo.");
			}
	}

	void mandaCommit(){
		byte[] buff;

		try{
			//Enviar datos de salida
			flujoS.flush();
			flujoS.writeInt(13);
			flujoS.writeUTF(repoCommit);	//Para madar el nombre del repositorio
		
			buff = new byte[archivoCommit.getText().length()];
			buff = archivoCommit.getText().getBytes();//pasamos lo que escribio a bytes
			flujoS.writeInt(archivoCommit.getText().length());
			flujoS.write(buff,0,archivoCommit.getText().length());

			if (flujoE.readBoolean() == true) { //servidor dice que se creo correctamente
				mostrarMensaje("El archivo "+repoCommit+" se ha creado");
				commitF.dispose();
			}
			else{
				mostrarError("Error al crear el archivo "+repoCommit);
				commitF.dispose();
			}

		}catch(IOException ioe){
			mostrarMensaje("Erron en coneccion "+ioe.getMessage() );

		}catch(IllegalArgumentException iae){
			mostrarMensaje("Error en argumentos dados. Intenta de nuevo.");
		}
	}

	void pull(){
		File carpeta = new File(pull1.getText());//ruta local
		if (carpeta.exists()){
			try{
				//Enviar datos de salida
				flujoS.writeInt(11);		
				flujoS.writeUTF(pull2.getText());//mandamos repo
				if (flujoE.readBoolean()==true) {//si la repo es valida
					descargarArch(1, carpeta.getAbsolutePath());//solo descarga el mas reciente
				}
			}catch(IOException ioe){
				mostrarError("Erron en coneccion "+ioe.getMessage() );

			}catch(IllegalArgumentException iae){
				mostrarError("Error en argumentos dados. Intenta de nuevo.");
			}
		}
		
	}

	void aplicaMerge(){
		try{
			flujoS.writeInt(14);
			flujoS.writeUTF(rep_m.getText()); //mandamos el repo
			if (flujoE.readBoolean()==true) {//la repo es valida
				if (flujoE.readBoolean()==true ) {//el usuario es propietario
					if (flujoE.readBoolean()==true){
						mostrarMensaje("Se realizo el merge de " + rep_m.getText());
					}else{
						mostrarError("Ocurrio un probema en la operacion");
					}
				}else{
					mostrarError("No puede realizar el merge de este repositorio si no es propietario");
				}
			}else{
				mostrarError("No existe el repositorio");
			}
		}catch(IOException merge){
			mostrarError("Error al enviar los datos");
		}

	}


	public void actionPerformed(ActionEvent e){ //Se ejecuta cada que haces click en un boton
//------------------- 	MENU PRINCIPAL 	 -------------------
		if(e.getSource() == mCrear)
        	crearRep();

    	if(e.getSource() == mListar){
    		try{

				flujoS.writeInt(40);
				String linea = flujoE.readUTF();
				String[] lista = linea.split("-");

				listarRep(lista);
			}catch(IOException eListar){
				mostrarError("Ocurrio un error");
			}
    	}
      
    	if(e.getSource() == mEditar){
    		editarRep();
    	}
        
      	if(e.getSource() == mPush)
        	pushC();

        if(e.getSource() == mPull){
    		pullM();
    	}
        
      	if(e.getSource() == mClone)
        	cloneR();

        if(e.getSource() == mMerge)
        	merge();

        if(e.getSource() == mAcercaDe)
        	acercaDe();

        if(e.getSource() == bacerca)
        	acerca.dispose();


//------------------- 	AUTENTICACION 	 -------------------
		if (e.getSource() == ba1){	//Iniciar sesion
			try{
				usuario = new Socket(ip.getText(),Integer.parseInt(npuerto.getText()));
				flujoS= new DataOutputStream(usuario.getOutputStream()); //Enviar datos de salida
				flujoE = new DataInputStream(usuario.getInputStream());
				
				flujoS.writeInt(4); //opcion de servidor que recibe al usuario.
				flujoS.writeUTF(usser.getText());

				if(flujoE.readBoolean() == true){ //el usuario si existe
					password2();
				}
				else{
					mostrarError("Usuario invalido. Intenta de nuevo");
					auten.dispose();
					autenticacion();
					registrarUsuario();
				}

			}catch(IOException iOE){
				mostrarError("Error al enviar datos al servidor. Intenta nuevamente.");
			
			}catch(IllegalArgumentException iae){
				mostrarError("Error en argumentos dados. intenta de nuevo.");
			}
		}

		if(e.getSource() == ba2){//cerrar autenticacion
			auten.dispose();		
		}

//------------------- 	REGISTRAR USUARIO 	 -------------------
		if (e.getSource() == bru1){//registrar usuario
			try{
				
				flujoS.writeInt(5);
				flujoS.writeUTF(ru1.getText());//nomb
				flujoS.writeUTF(ru3.getText());//pass
				flujoS.writeUTF(ru2.getText());//correo

				mostrarMensaje("Registro exitoso");
				regUser.dispose();

			}catch(IOException ioe){
				mostrarMensaje("Erron en autenticacion "+ioe.getMessage() );
			}
		}

		if (e.getSource() == bru2){
			regUser.dispose();
		}

//------------------- 	RECUPERAR CUENTA 	 -------------------
		if (e.getSource() == brecU1){
			try{
				usuario = new Socket(ipr.getText(), Integer.parseInt(npuertor.getText()));
				flujoS= new DataOutputStream(usuario.getOutputStream()); //Enviar datos de salida
				
				flujoS.writeInt(4); //opcion de servidor que recibe al usuario. aun no esta habilitada 	
				flujoS.writeUTF(usserr.getText());
				flujoS.writeUTF(correo.getText());

				//coneccion.close();
				mostrarMensaje("Recuperacion exitosa");
				rec.dispose();

			}catch(IOException ioe){
				mostrarMensaje("Erron en autenticacion "+ioe.getMessage() );

			}catch(IllegalArgumentException iae){
				mostrarMensaje("Error en argumentos dados. Intenta de nuevo.");
			}
		}

		if (e.getSource() == brecU2){
			rec.dispose();
		}

//------------------- 	MENU ZONA DE TRABAJO 	 -------------------
		/*if(e.getSource() == bzt1){ //crear nuevo repositorio
			crearRep();
		}

		if(e.getSource() == bzt2){ //listar repositorios
			
			try{

				flujoS.writeInt(40);
				String linea = flujoE.readUTF();
				String[] lista = linea.split("-");

				listarRep(lista);
			}catch(IOException eListar){
				mostrarError("Ocurrio un error");
			}
		}

		if(e.getSource() == bzt3){ //EDITAR REPO
			editarRep();
		}

		if(e.getSource() == bzt4){//CERRAR ZONA DE TRABAJO
			zTrabajo.dispose();
		}
*/
//------------------- 	CREAR REPOSITORIO 	 ------------------
		if(e.getSource() == bcr1){	//crear repositorio parte de bzt1
			if (check.isSelected()) {
				password3();
			}
			else
				archivo(); //innit de la repo nueva
		}

		if(e.getSource() == bcr2){
			crear.dispose();		
		}

//------------------- 	LISTAR REPOSITORIO 	 -------------------
		if (e.getSource() == blr){
			listRepo.dispose();
		}

//------------------- 	EDITAR REPOSITORIO 	 -------------------
		if(e.getSource() == beR1){//cambiar nombre repositorio

			try{		
				flujoS.writeInt(7);		
				flujoS.writeUTF(eR2.getText());  //Archivo a modificar
				flujoS.writeUTF(eR1.getText());	 //Nuevo nombre

				if (flujoE.readBoolean() == true){
					mostrarMensaje("Cambio realizado");
					editarRepo.dispose();
				}else{
					mostrarError("El repositorio no existe o el nuevo nombre ya esta en uso");
				}

			}catch(IOException ioe){
				mostrarError("Erron en editar nombre " + ioe.getMessage() );
			}			
		}

		if(e.getSource() == beR2){

			try{
				flujoS.writeInt(8); //mensaje a ServidorC
				flujoS.writeUTF(eR2.getText());

				if(flujoE.readBoolean() == true){//valida si existe el repo			
					if(check1.isSelected()){
						flujoS.writeBoolean(true);//le dice que quiere ser privado
						password();
					}else{
						flujoS.writeBoolean(false);
					}
					editarRepo.dispose();
				}else{
					mostrarError("No existe el repositorio");
				}
			}catch(IOException ioe){
				mostrarError("Error en editar acceso " + ioe.getMessage() );
			}
		}

		if(e.getSource() == beR3){
			editarRepo.dispose();
		}

//------------------- 	PASSWORD 1 , 2 y 3	 -------------------
		if (e.getSource()==bP) {
			
			try{
				DataOutputStream flujoSE = new DataOutputStream(usuario.getOutputStream());
				flujoSE.writeUTF(p1.getText());
				pass.dispose();

			}catch(IOException iOE){
				mostrarError("Error al enviar datos al servidor. Intenta nuevamente.");
			}
			
		}

		if(e.getSource() == bP2){
			try{
				DataInputStream flujoSE = new DataInputStream(usuario.getInputStream());
				flujoS.writeUTF(p2.getText());

				if (flujoE.readBoolean()==true) { //si la contrasena es valida
					auten.dispose();
					pass2.dispose();
					menuPrincipal();
				}
				else{
					mostrarError("Password incorrecto. Intenta de nuevo.");
					auten.dispose();
					pass2.dispose();
					autenticacion();
				}
			}catch(IOException iOE){
				mostrarError("Error al enviar datos al servidor. Intenta nuevamente.");
			}	
		}

		if (e.getSource() == bPass3) { //password para reponueva privada
			pass3.dispose();
			archivo();
		}

//------------------- 	ARCHIVO 	 -------------------
		if (e.getSource() == bar1){
			mandaInnit();
		}

		if (e.getSource() == bar2){
			archivoF.dispose();
		}
//------------------- 	COMMIT 	 -------------------
		if (e.getSource() == bCommit1){
			mandaCommit();
		}

		if (e.getSource() == bCommit2){
			commitF.dispose();
		}

//------------------- 	MENU DE COMANDOS 	 -------------------
		if (e.getSource() == bzt5){ //PUSH
			pushC();
		}

		if (e.getSource() == bzt6){//PULL
			pullM();
		}

		if (e.getSource() == bzt7) {//CLONE
			cloneR();
		}

		if (e.getSource() == bzt8) {//merge
			merge();
		}

//------------------- 	COMANDO CLONE 	 -------------------
		if (e.getSource() == bCn1){//############ va a hacer clone al repo
			cClone.dispose();
			guardarCRepo();
		}

		if (e.getSource() == bCn2){
			cClone.dispose();
		}

//------------------- 	COMANDO PUSH 	 -------------------

		if (e.getSource() == bPu1){
			pushF.dispose();
			mandaPush();
			commit(repo_push.getText());
		}

		if (e.getSource() == bPu2){
			pushF.dispose();
		}

//------------------- 	COMANDO PULL 	 -------------------
		if (e.getSource() == bpull1) {
			pull();
		}
		if (e.getSource() == bpull2) {
			pullF.dispose();
		}

//------------------- 	COMANDO MERGE 	 -------------------

		if (e.getSource() == acp_m) {
			aplicaMerge();
			aplicaM.dispose();
		}
		if (e.getSource() == canc_m) {
			aplicaM.dispose();
		}

//--------------------------	FUNCIONES EXTRAS AL INICIAR SESION NO IMPLEMENTADAS---------------------------
		if (e.getSource() == ba3){
			try{
				flujoS.writeInt(5);
				registrarUsuario();

			}catch(IOException i){
				mostrarMensaje("Error en ba3");
			}
		}

		if(e.getSource() == ba4){
			//recuperarUsuario();
		}
	}

	void mostrarMensaje(String mssg){
		JOptionPane.showMessageDialog(null, mssg);
	}

	void mostrarError(String mssg){
		JOptionPane.showMessageDialog(null, mssg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		new Cliente();
	}
}

