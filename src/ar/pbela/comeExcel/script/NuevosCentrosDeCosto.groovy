import org.apache.poi.hssf.record.LabelSSTRecord;

import ar.pbela.comeExcel.extract.excel.ExcelBuilder;
import groovy.sql.Sql;

def _dbUrl = "jdbc:oracle:thin:@wimp.merck.com:25881:WIMP"
def _dbDriver = "oracle.jdbc.driver.OracleDriver"
def _dbUser = "expensys_admin"
def _dbPass = "temp123"
def _sql = Sql.newInstance(_dbUrl, _dbUser, _dbPass, _dbDriver)
def _excelPath = "C:\\java\\proyectos\\expensys\\elHoy\\caso11173236\\nuevosCentrosDeCostoEspejo.xls"


//atenti al piojo que las columnas del excel tienen que tener los nombres que aparecen subrayados
new ExcelBuilder(_excelPath).eachLine([labels:true]) {
        def _division = "(select id_division from cnf_division where nombre_division = '$division')"
        def _nombreCC = "'$nombre_centro_costo'"
        def _codigoCC = "$codigo"
        def _idPais = "to_number($id_pais)"
		def _tipo = 200
		
        
        println "${_division + ' ' + _nombreCC + ' ' + _codigoCC + ' ' + _idPais}"
        
        
        _sql.execute(
        "insert into cnf_centro_costo (id_division, id_pais, id_tipo_concepto_centro_costo," +
        "   codigo_centro_costo, nombre_centro_costo, activo, id_responsable)" +
        " values" +
        "  (${_division}, ${_idPais}, ${_tipo}, ${_codigoCC}, ${_nombreCC}, 1, 13055)")
    }

_sql.commit()
println "Finito"