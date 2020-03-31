package validar_matriculas.dao;

import javax.sql.DataSource;

public class conexion {

	static DataSource conexionDataSource;

	public static DataSource getConexionDataSource() {
		return conexionDataSource;
	}

	public static void setConexionDataSource(DataSource conexionDataSource) {
		conexion.conexionDataSource = conexionDataSource;
	}

}
