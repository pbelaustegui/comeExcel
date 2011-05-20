import ar.pbela.comeExcel.extract.excel.ExcelBuilder;
import groovy.sql.Sql

def foo = 2011
def sql = Sql.newInstance("jdbc:oracle:thin:@127.0.0.1:1521:XE", "expensys_admin",
                      "temp123", "oracle.jdbc.driver.OracleDriver")

//sql.eachRow("select * from exp_reporte where anyo_periodo = ${foo}") {
//    println "Gromit likes ${it.id_reporte}"
//}

new ExcelBuilder("C:\\java\\proyectos\\expensys\\elHoy\\Creacion_Usuarios_20-05-2011.xls").eachLine([labels:true]) {
	println "nombre: $NOMBRE idPais: $id_pais an8: '$an8'"
  }

//new ExcelBuilder("C:\\java\\proyectos\\expensys\\elHoy\\Centros_de_Costos_CARD_enviados_por_el_pais_parte1.xls").eachLine {
//	println "First column on row ${it.rowNum} = ${cell(0)}"
//  }