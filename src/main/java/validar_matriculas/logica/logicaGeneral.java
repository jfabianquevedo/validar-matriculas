package validar_matriculas.logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import validar_matriculas.dao.conexion;
import validar_matriculas.dao.consultas;

public class logicaGeneral {

	static Connection con;
	private PrintWriter printWriter;
	private FileWriter fileWriter;

	public logicaGeneral() {
		try {
			fileWriter = new FileWriter("C:\\Validar Carga\\log\\resultado.txt");
			printWriter = new PrintWriter(fileWriter);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<String> leerArchivo(File file, String camara) {
		System.out.println("Leyendo Archivo ... ");
		String matricula = "";
		List<String> listaMatriculas = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while ((matricula = br.readLine()) != null) {
				matricula = matricula.substring(0, 8).trim();
				matricula = matricula + "-" + camara;
				listaMatriculas.add(matricula);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaMatriculas;
	}

	public HashMap<String, String> BuscarMatriculas(List<String> listaMat) {
		System.out.println("Buscando Matriculas en IRIS");
		HashMap<String, String> matriculasResultado = new HashMap<String, String>();
		String query;
		String matriculas = "";
		try {
			con = conexion.getConexionDataSource().getConnection();
			query = consultas.buscarMatricula;
			int contador = 0;
			for (String matricula : listaMat) {
				contador = contador + 1;
				matriculas = matriculas + "'" + matricula + "',";
				if (contador == 1000) {
					matriculas = matriculas.substring(0, matriculas.length() - 1);
					PreparedStatement ps = con.prepareStatement(query.replace("XX", matriculas));
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						matriculasResultado.put(rs.getString(1), rs.getString(2));
					}
					contador = 0;
					matriculas = "";
				}
			}
			if (contador > 0) {
				matriculas = matriculas.substring(0, matriculas.length() - 1);
				PreparedStatement ps = con.prepareStatement(query.replace("XX", matriculas));
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					matriculasResultado.put(rs.getString(1), rs.getString(2));
				}
				contador = 0;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return matriculasResultado;
	}

	public void matriculasNoCargadas(HashMap<String, String> matriculasEncontradas, List<String> listaMatriculas) {
		System.out.println("Buscando matriculas no cargadas");
		for (String string : listaMatriculas) {
			if (!matriculasEncontradas.containsKey(string)) {
				printWriter.println("Matricula no cargada; " + string);

			}
		}
		if (printWriter != null) {
			printWriter.close();
		}

	}
}
