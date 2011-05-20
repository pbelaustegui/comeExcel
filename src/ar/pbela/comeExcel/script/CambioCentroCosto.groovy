import org.apache.poi.hssf.record.LabelSSTRecord;

import ar.pbela.comeExcel.extract.excel.ExcelBuilder;
import groovy.sql.Sql;

def _dbUrl = "jdbc:oracle:thin:@wimp.merck.com:25881:WIMP"
def _dbDriver = "oracle.jdbc.driver.OracleDriver"
def _dbUser = "expensys_admin"
def _dbPass = "temp123"
def _sql = Sql.newInstance(_dbUrl, _dbUser, _dbPass, _dbDriver)
def _excelPath = "C:\\java\\proyectos\\expensys\\elHoy\\lalala.xls"


//atenti al piojo que las columnas del excel tienen que tener los nombres que aparecen subrayados
new ExcelBuilder(_excelPath).eachLine([labels:true]) {
		def _idPais = 3000
		def _ord = "to_number($ord)"
		def _codigoCentroCosto = "replace($cc, ' ', '')"
		def _isid = "replace(lower('$user'), ' ', '')"
		def _idCentroCosto = "(select id_centro_costo from cnf_centro_costo" +
			" where codigo_centro_costo = ${_codigoCentroCosto} and id_pais = ${_idPais})"
		def _idUsuario = "(select id_usuario from usr_usuario where isid = ${_isid})"
		def _idCentroActual = "(select id_centro_costo from usr_usuario where id_usuario = ${_idUsuario})"
		
//		println "${_idCentroActual}"
		
		
		_sql.execute(
		"insert into cambio_centro_costo" + 
		"  (ord, isid, id_usuario, id_cc_actual, id_cc_nuevo, codigo_nuevo)" +
		" values" +
		"  (${_ord}, ${_isid}, ${_idUsuario}, ${_idCentroActual}, ${_idCentroCosto}, ${_codigoCentroCosto})")
	}

_sql.commit()
println "Finito"