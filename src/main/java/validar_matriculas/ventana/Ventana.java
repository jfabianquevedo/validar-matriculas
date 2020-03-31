package validar_matriculas.ventana;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import validar_matriculas.logica.logicaGeneral;

public class Ventana extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Choice desplegable;
	private JButton abrirArchivo;
	private JFrame ventana;
	private String camara = "";
	private List<String> listaMatriculas = new ArrayList<String>();
	private HashMap<String, String> matriculasEncontradas = new HashMap<String, String>();

	public Ventana() {
		super(); // usamos el contructor de la clase padre JFrame
		configurarVentana(); // configuramos la ventana
		inicializarComponentes(); // inicializamos los atributos o componentes
		inicializarLista(); // inicializar lista desplegable

	}

	private void configurarVentana() {
		this.setTitle("Validar Carga"); // colocamos titulo a la ventana
		this.setSize(310, 210); // colocamos tamanio a la ventana (ancho, alto)
		this.setLocationRelativeTo(null); // centramos la ventana en la pantalla
		this.setLayout(null); // no usamos ningun layout, solo asi podremos dar posiciones a los componentes
		this.setResizable(false); // hacemos que la ventana no sea redimiensionable
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // hacemos que cuando se cierre la ventana termina todo
																// proceso
	}

	private void inicializarComponentes() {
		// creamos los componentes
		final logicaGeneral general = new logicaGeneral();
		desplegable = new Choice();
		abrirArchivo = new JButton();
		// configuramos los componentes
		abrirArchivo.setText("Cargar Archivo");
		abrirArchivo.setBounds(50, 50, 120, 30);
		abrirArchivo.setLocation(90, 70);
		desplegable.setLocation(130,30);
		// adicionamos los componentes a la ventana
		this.add(desplegable);
		this.add(abrirArchivo);

		abrirArchivo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				long start = System.currentTimeMillis();
				JFileChooser archivo = new JFileChooser();
				archivo.showOpenDialog(ventana);
				File file = archivo.getSelectedFile();
				camara = desplegable.getSelectedItem();
				listaMatriculas = general.leerArchivo(file, camara);
				matriculasEncontradas = general.BuscarMatriculas(listaMatriculas);
				general.matriculasNoCargadas(matriculasEncontradas, listaMatriculas);
				long end = System.currentTimeMillis();
				System.out.println("finalizo la consultas de: " + listaMatriculas.size() + " matriculas " + " en "
						+ TimeUnit.MILLISECONDS.toSeconds(end - start) + " segundos");

			}
		});
	}

	private void inicializarLista() {
		desplegable.add("03");
		desplegable.add("04");
		desplegable.add("06");
		desplegable.add("07");
		desplegable.add("08");
		desplegable.add("09");
		desplegable.add("10");
		desplegable.add("11");
		desplegable.add("17");
		desplegable.add("20");
		desplegable.add("21");
		desplegable.add("24");
		desplegable.add("26");
		desplegable.add("27");
		desplegable.add("36");
		desplegable.add("37");
		desplegable.add("38");
		desplegable.add("39");
		desplegable.add("43");
		desplegable.add("46");
		desplegable.add("55");

	}

	public static void main(String[] args) {

		new ClassPathXmlApplicationContext("config.xml");
		Ventana ventana = new Ventana();
		ventana.setVisible(true);

	}

}
