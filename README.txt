aqui se desarrollan unos interactivos para la visualizacion de un foro

además se incluye  un ejemplo de libreria para processing.org

y una aplicación que genera a partir de un video, una composición destinada 
al corte y montaje

el paquete codigodelaimagen.base es una especie de core

el paquete codigodelaimagen.base_tmp es una especie de core transitorio es decir hasta que no se use en más de 2 proyectos no se pasa al core

una fila tiene columnas

las columnas son de una fila (ManyToOne NOT ManyToMany)

las celdas son de una columna de una fila

cada fila tiene una fila parent
cada columna tiene una columna parent
cada celda tiene una celda parent

cada celda tiene un comentario

cada comentario es de un autor

un autor puede tener muchos comentarios

un autor es de un equipo



la reticula tiene un numero de filas

todas las filas tienen el mismo numero de columnas (esto para visualizar el nivel de discusión)
