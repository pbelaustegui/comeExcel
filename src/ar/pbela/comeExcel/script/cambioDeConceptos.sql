declare

	cursor movimientos is
		select distinct m.id_movimiento, m.id_concepto, copia.ID_ORIGINAL, copia.ID_COPIA
			from usr_usuario u
			inner join cnf_centro_costo cc on u.id_centro_costo = cc.id_centro_costo
			inner join cnf_division div on cc.id_division = div.id_division
			and div.id_division = 542
			inner join cambio_centro_costo ccc on u.id_usuario = ccc.id_usuario and u.id_centro_costo <> ccc.id_cc_nuevo
			inner join exp_reporte r on u.id_usuario = r.id_usuario and r.id_estado not in(400)
			inner join exp_movimiento m on r.id_reporte = m.id_reporte
			inner join temp_conceptos_copiados copia on m.id_concepto = copia.id_copia;
	
	movimiento movimientos%rowtype;

	
begin
	open movimientos;
	loop
		fetch movimientos into movimiento;
		exit when movimientos%NOTFOUND;
		
		update exp_movimiento set id_concepto = movimiento.id_original
			where id_movimiento = movimiento.id_movimiento;
		
		
	end loop;
	close movimientos;
end;