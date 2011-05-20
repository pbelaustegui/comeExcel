import org.apache.poi.hssf.record.LabelSSTRecord;

import ar.pbela.comeExcel.extract.excel.ExcelBuilder;
import groovy.sql.Sql;

def _dbUrl = "jdbc:oracle:thin:@wimp.merck.com:25881:WIMP"
def _dbDriver = "oracle.jdbc.driver.OracleDriver"
def _dbUser = "expensys_admin"
def _dbPass = "temp123"
def _sql = Sql.newInstance(_dbUrl, _dbUser, _dbPass, _dbDriver)
def _excelPath = "C:\\java\\proyectos\\expensys\\elHoy\\Creacion_Usuarios_20-05-2011.xls"


//atenti al piojo que las columnas del excel tienen que tener los nombres que aparecen subrayados
new ExcelBuilder(_excelPath).eachLine([labels:true]) {
		def _idTarjeta = "$tt"
		def _idPais = "to_number($id_pais)"
		def _isid = "replace(lower('$isid'), ' ', '')"
		def _activo = "'S'"
		def _idBanco = 25
		
//		println "${_idCentroCosto}"
		
		
		_sql.execute(
		"insert into usr_usuario_tarjeta_temp" + 
		"  (id_tarjeta, id_pais, isid, activo, id_banco)" +
		" values" +
		"  (${_idTarjeta}, ${_idPais}, ${_isid}, ${_activo}, ${_idBanco})")
	}

_sql.commit()
println "Finito"