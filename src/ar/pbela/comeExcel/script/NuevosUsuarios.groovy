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
		def _idPais = "to_number($id_pais)"
		def _idIdioma = "to_number($id_idioma)" 
		def _legacy = "decode(replace(lower('$legacy'), ' ', ''), 'sp', 1, 0)"
		def _isid = "replace(lower('$isid'), ' ', '')"
		def _apellido = "'$apellido_usuario'"
		def _nombre = "'$nombre_usuario'"
		def _email = "replace('$email', ' ', '')"
		def _an8 = "replace($an8, ' ', '')"
		def _goa = "to_number($goa)"
		def _goaPromocional = 0
		def _codigoCentroCosto = "replace($cc, ' ', '')"
		def _idCentroCosto = "(select id_centro_costo from cnf_centro_costo" +
			" where codigo_centro_costo = ${_codigoCentroCosto} and id_pais = ${_idPais})"
		def _isidAprobador = "replace(lower('$aprobador'), ' ', '')"
		def _idAprobador = "(select id_usuario from usr_usuario where isid = ${_isidAprobador})"
		def _firmaElectronica = 1
		def _esViaticante = 0
		def _accesoARed = 1
		def _activo = 1
		def _esRetirado = 0
		
//		println "${_idCentroCosto}"
		
		
		_sql.execute(
		"insert into usr_usuario_temp" + 
		"  (id_pais, id_centro_costo, id_aprobador, isid, codigo_address_book, email," +
		"  firma_electronica, acceso_red, es_rep_ventas, es_viaticante, activo, nombre_usuario," +
		"  apellido_usuario, goa, goa_promocional, es_retirado, id_idioma, isid_aprobador," +
		"  codigo_centro_costo) " +
		" values" +
		"  (${_idPais}, ${_idCentroCosto}, ${_idAprobador}, ${_isid}, ${_an8}, ${_email}, ${_firmaElectronica}," +
		"  ${_accesoARed}, ${_legacy}, ${_esViaticante}, ${_activo}, ${_nombre}, ${_apellido}, ${_goa}, ${_goaPromocional}," +
		"  ${_esRetirado}, ${_idIdioma}, ${_isidAprobador}, ${_codigoCentroCosto})")
	}

_sql.commit()
println "Finito"