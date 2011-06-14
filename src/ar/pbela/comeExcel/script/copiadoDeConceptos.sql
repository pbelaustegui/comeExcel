declare
	i number;
	
	cursor divisiones is
		select * from cnf_division where nombre_division like 'PA_DIVERSIFICADOS';
	
	division divisiones%rowtype;
	
	cursor conceptos is
		select * from cnf_concepto where
		id_subsidiaria = 1100 
		and id_division = (select id_division from cnf_division where nombre_division = 'MHHD');
	
	concepto conceptos%rowtype;
	
begin
	open divisiones;
	loop
		fetch divisiones into division;
		exit when divisiones%NOTFOUND;
		open conceptos;
		loop
			fetch conceptos into concepto;
			exit when conceptos%NOTFOUND;
			insert into cnf_concepto (id_division, id_tipo_concepto, id_subsidiaria,
				nombre_concepto, cuenta, activo, separar_dias,
				requiere_ruc, codigo_nomina, requiere_atenciones,
				requiere_comentario, usa_cuenta_completa,
				tope_maximo_representante, tope_maximo_administrativo,
				id_concepto_representante, id_concepto_administrativo,
				requiere_tope_maximo, es_base_prestacional,
				separar_dias_sub, id_grupo, posee_guia, fijo, fake_id_concepto)
				values (division.id_division, concepto.id_tipo_concepto, concepto.id_subsidiaria,
				concepto.nombre_concepto, concepto.cuenta, concepto.activo, concepto.separar_dias,
				concepto.requiere_ruc, concepto.codigo_nomina, concepto.requiere_atenciones,
				concepto.requiere_comentario, concepto.usa_cuenta_completa,
				concepto.tope_maximo_representante, concepto.tope_maximo_administrativo,
				concepto.id_concepto_representante, concepto.id_concepto_administrativo,
				concepto.requiere_tope_maximo, concepto.es_base_prestacional,
				concepto.separar_dias_sub, concepto.id_grupo, concepto.posee_guia,
				concepto.fijo, concepto.fake_id_concepto)
				returning id_concepto into i;
			insert into temp_conceptos_copiados (id_original, id_copia, id_division)
			values (concepto.id_concepto, i, division.id_division);
		end loop;
		close conceptos;
	end loop;
	close divisiones;
end;



