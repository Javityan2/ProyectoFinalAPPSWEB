package mx.itson.reporteciudadanopf.models

/**
 * Enum que representa las colonias disponibles en el sistema.
 * Cada valor tiene un nombre de visualización que se muestra en la interfaz de usuario.
 */
enum class Colonias(val displayName: String) {
    CASAS_100("100 CASAS"),
    TRECE_DE_JULIO("13 DE JULIO"),
    DIECIOCHO_DE_NOVIEMBRE("18 DE NOVIEMBRE"),
    VEINTIDOS_DE_NOVIEMBRE("22 DE NOVIEMBRE"),
    VEINTITRES_DE_MARZO("23 DE MARZO"),
    VEINTITRES_DE_NOVIEMBRE("23 DE NOVIEMBRE"),
    VEINTINUEVE_DE_NOVIEMBRE("29 DE NOVIEMBRE"),
    CINCO_DE_MAYO("5 DE MAYO"),
    ACACIAS("ACACIAS"),
    ADOLFO_DE_LA_HUERTA("ADOLFO DE LA HUERTA"),
    ADOLFO_LOPEZ_MATEOS("ADOLFO LÓPEZ MATEOS"),
    AEROCOMANDER("AEROCOMANDER"),
    AEROPUERTO("AEROPUERTO"),
    ALAMO("ALAMO"),
    AMERICAS("AMÉRICAS"),
    AMPLIACION_BUROCRATA("AMPLIACIÓN BURÓCRATA"),
    AMPLIACION_GIL_SAMANIEGO("AMPLIACIÓN GIL SAMANIEGO"),
    AMPLIACION_GOLFO_DE_CALIFORNIA("AMPLIACIÓN GOLFO DE CALIFORNIA"),
    AMPLIACION_GUADALUPE("AMPLIACIÓN GUADALUPE"),
    AMPLIACION_INDEPENDENCIA("AMPLIACIÓN INDEPENDENCIA"),
    AMPLIACION_MIGUEL_HIDALGO("AMPLIACIÓN MIGUEL HIDALGO"),
    ANTENA("ANTENA"),
    ANTORCHISTA("ANTORCHISTA"),
    ARANJUEZ("ARANJUÉZ"),
    ARCOS("ARCOS"),
    ARRECIFES("ARRECIFES"),
    ATARDECERES("ATARDECERES"),
    AURORA("AURORA"),
    BACUM("BÁCUM"),
    BAHIA("BAHÍA"),
    BAUGO("BAUGO"),
    BAUTECAS("BAUTECAS"),
    BELEM("BELEM"),
    BELLAVISTA("BELLAVISTA"),
    BICENTENARIO("BICENTENARIO"),
    BRISAS("BRISAS"),
    BUENOS_AIRES("BUENOS AIRES"),
    BUGAMBILIA("BUGAMBILIA"),
    BUROCRATA("BURÓCRATA"),
    CALICHI("CALICHI"),
    CAMPESTRE("CAMPESTRE"),
    CAMPO_DE_TIRO("CAMPO DE TIRO"),
    CAMPO_NUEVO("CAMPO NUEVO"),
    CANTERA("CANTERA"),
    CARACOL_PENINSULA("CARACÓL PENINSULA"),
    CARACOL_TURISTICO("CARACÓL TURÍSTICO"),
    CARLOS_ROMERO_D("CARLOS ROMERO D"),
    CASAS_BLANCAS("CASAS BLANCAS"),
    CASTILLO("CASTILLO"),
    CENTENARIO("CENTENARIO"),
    CENTINELA("CENTINELA"),
    CENTRO("CENTRO"),
    CERRO_GANDARENO("CERRO GANDAREÑO"),
    CHOYA("CHOYA"),
    CHUMAMPACO("CHUMAMPACO"),
    COLINAS("COLINAS"),
    COLINAS_DE_MIRAMAR("COLINAS DE MIRAMAR"),
    COLINAS_DEL_SOL("COLINAS DEL SOL"),
    COLONIA_CENTRO("COLONIA CENTRO"),
    COSTA_AZUL("COSTA AZUL"),
    COUNTRY_CLUB("COUNTRY CLUB"),
    CRESTON("CRESTÓN"),
    CUADRITA("CUADRITA"),
    DELICIAS("DELICIAS"),
    DIAMANTE("DIAMANTE"),
    DORADO("DORADO"),
    EJIDO_ALVARO_OBREGON("EJIDO ÁLVARO OBREGÓN"),
    EJIDO_FELIPE_ANGELES("EJIDO FELIPE ÁNGELES"),
    EJIDO_GRACIANO_SANCHEZ("EJIDO GRACIANO SÁNCHEZ"),
    EJIDO_LAZARO_CARDENAS("EJIDO LAZARO CARDENAS"),
    EJIDO_MARIANO_ESCOBEDO("EJIDO MARIANO ESCOBEDO"),
    EJIDO_NUEVO_SAN_FRANCISCO("EJIDO NUEVO SAN FRANCISCO"),
    EJIDO_PROFESOR_GRACIANO_SANCHEZ("EJIDO PROFESOR GRACIANO SÁNCHEZ"),
    EJIDO_SAN_FERNANDO("EJIDO SAN FERNANDO"),
    EJIDO_SANTA_MARIA("EJIDO SANTA MARÍA"),
    EJIDO_SONORA("EJIDO SONORA"),
    EMILIANO_ZAPATA("EMILIANO ZAPATA"),
    EMPALME("EMPALME"),
    ESTEBAN_BACA_CALDERON("ESTEBAN BACA CALDERÓN"),
    FATIMA("FÁTIMA"),
    FEMOSA("FEMOSA"),
    FLORES("FLORES"),
    FOVISSSTE("FOVISSSTE"),
    FRANCISCO_MARQUEZ("FRANCISCO MÁRQUEZ"),
    FUENTE_DE_PIEDRA("FUENTE DE PIEDRA"),
    FUENTES("FUENTES"),
    FUENTES_RODRIGUEZ("FUENTES RODRÍGUEZ"),
    GIL_SAMANIEGO("GIL SAMANIEGO"),
    GIL_SAMANIEGO_2("GIL SAMANIEGO 2"),
    GOLFO_DE_CALIFORNIA("GOLFO DE CALIFORNIA"),
    GOLONDRINAS("GOLONDRINAS"),
    GUADALUPE("GUADALUPE"),
    GUADALUPE_VICTORIA("GUADALUPE VICTORIA"),
    GUARIDA_DEL_TIGRE("GUARIDA DEL TIGRE"),
    GUASIMAS("GUASIMAS"),
    GUAYMAS_CENTRO("GUAYMAS CENTRO"),
    GUAYMAS_NORTE("GUAYMAS NORTE"),
    HUIRIBIS("HUIRIBIS"),
    HUIRIVIS("HUIRIVIS"),
    HUMBERTO_GUTIERREZ("HUMBERTO GUTIÉRREZ"),
    INDEPENDENCIA("INDEPENDENCIA"),
    INFONAVIT("INFONAVIT"),
    JACINTO_LOPEZ("JACINTO LÓPEZ"),
    JARDINES("JARDÍNES"),
    JUAN_FRANCISCO_PATRON_MARQUEZ("JUAN FRANCISCO PATRÓN MÁRQUEZ"),
    JUNTAS("JUNTAS"),
    LINDAVISTA("LINDAVISTA"),
    LOMA_BONITA("LOMA BONITA"),
    LOMA_DORADA("LOMA DORADA"),
    LOMA_LINDA("LOMA LINDA"),
    LOMAS_DE_COLOSIO("LOMAS DE COLOSIO"),
    LOMAS_DE_CORTES("LOMAS DE CORTÉS"),
    LOMAS_DE_FATIMA("LOMAS DE FÁTIMA"),
    LOMAS_DE_MIRAMAR("LOMAS DE MIRAMAR"),
    LOMAS_DE_SAN_CARLOS("LOMAS DE SAN CARLOS"),
    LOMAS_DEL_GANDARENO("LOMAS DEL GANDAREÑO"),
    LOMAS_DEL_MAR("LOMAS DEL MAR"),
    LOMAS_MIRAMAR("LOMAS MIRAMAR"),
    LOPEZ_MATEOS("LÓPEZ MATEOS"),
    LOS_LAGOS("LOS LAGOS"),
    LUIS_DONALDO_COLOSIO("LUIS DONALDO COLOSIO"),
    MALECON("MALECÓN"),
    MANGA_SAN_CARLOS("MANGA SAN CARLOS"),
    MANUEL_R_BOBADILLA("MANUEL R BOBADILLA"),
    MAR_DE_CORTES("MAR DE CORTÉS"),
    MARIANA("MARIANA"),
    MARSELLA("MARSELLA"),
    MICROONDAS("MICROONDAS"),
    MIGUEL_HIDALGO("MIGUEL HIDALGO"),
    MIRADOR("MIRADOR"),
    MIRAMAR("MIRAMAR"),
    MISA("MISA"),
    MISION_DEL_SOL("MISIÓN DEL SOL"),
    MISIONEROS("MISIONEROS"),
    MONTE_BELLO("MONTE BELLO"),
    MONTECARLO("MONTECARLO"),
    MONTELOLITA("MONTELOLITA"),
    MORENO("MORENO"),
    MURALLA("MURALLA"),
    NICOLAS_BRAVO("NICOLÁS BRAVO"),
    NIZA("NIZA"),
    NUEVO_HORIZONTE("NUEVO HORIZONTE"),
    NUEVO_PENASCO("NUEVO PEÑASCO"),
    OCOTILLO("OCOTILLO"),
    OCOTILLO_2("OCOTILLO 2"),
    OROZ("OROZ"),
    ORTIZ("ORTÍZ"),
    PALMAS("PALMAS"),
    PALO_VERDE("PALO VERDE"),
    PARAJE_VIEJO("PARAJE VIEJO"),
    PARQUE_INDUSTRIAL("PARQUE INDUSTRIAL"),
    PEDREGAL("PEDREGAL"),
    PEDREGAL_DE_QUIROGA("PEDREGAL DE QUIROGA"),
    PENINSULA("PENÍNSULA"),
    PERIODISTA("PERIODISTA"),
    PERLA_MARINA("PERLA MARINA"),
    PERLA_MARINA_2("PERLA MARINA 2"),
    PERLAS("PERLAS"),
    PESCADORES("PESCADORES"),
    PESQUERO("PESQUERO"),
    PETROLERA("PETROLERA"),
    PETROLEROS("PETROLEROS"),
    PINOS("PINOS"),
    PLAYA_DE_CORTES("PLAYA DE CORTÉS"),
    PLAYA_DE_MIRAMAR("PLAYA DE MIRAMAR"),
    PLAYA_VISTA_1("PLAYA VISTA 1"),
    PLAYA_VISTA_2("PLAYA VISTA 2"),
    PLAYITAS("PLAYITAS"),
    PLAYITAS_2("PLAYITAS 2"),
    PLAZAS("PLAZAS"),
    POPULAR("POPULAR"),
    POTAM("POTAM"),
    PRADERA_DORADA("PRADERA DORADA"),
    PRADERAS("PRADERAS"),
    PRADOS("PRADOS"),
    PUEBLO_DE_BELEM("PUEBLO DE BELÉM"),
    PUNTA_ARENA("PUNTA ARENA"),
    PUNTA_DE_AGUA("PUNTA DE AGUA"),
    PUNTA_DE_LASTRE("PUNTA DE LASTRE"),
    QUINTAS("QUINTAS"),
    RAHUN("RAHUN"),
    RANCHITO_CAMPESTRE("RANCHITO CAMPESTRE"),
    RASTRO("RASTRO"),
    RASTRO_CERRO("RASTRO CERRO"),
    RASTRO_PLAYA("RASTRO PLAYA"),
    REAL_DE_CORTES("REAL DE CORTÉS"),
    RENACIMIENTO("RENACIMIENTO"),
    RESBALON("RESBALÓN"),
    RESIDENCIAL_MARSELLA("RESIDENCIAL MARSELLA"),
    RINCON_DE_FATIMA("RINCÓN DE FÁTIMA"),
    RINCON_DEL_BURRO("RINCÓN DEL BURRO"),
    RIOS("RÍOS"),
    ROBLE("ROBLE"),
    RODRIGO_DE_TRIANA("RODRÍGO DE TRIANA"),
    RODRIGUEZ_ALCAINE("RODRÍGUEZ ALCAINE"),
    ROMERO_DE_CHAMPS("ROMERO DE CHAMPS"),
    SAHUARAL("SAHUARAL"),
    SAHUARI("SAHUARI"),
    SAHUARIPA("SAHUARIPA"),
    SALVACION("SALVACIÓN"),
    SAN_BERNARDO("SAN BERNARDO"),
    SAN_CARLOS_NUEVO_GUAYMAS("SAN CARLOS NUEVO GUAYMAS"),
    SAN_GERMAN("SAN GERMÁN"),
    SAN_GERONIMO("SAN GERÓNIMO"),
    SAN_GILBERTO("SAN GILBERTO"),
    SAN_JOSE("SAN JOSÉ"),
    SAN_JOSE_DE_GUAYMAS("SAN JOSÉ DE GUAYMAS"),
    SAN_MARCIAL("SAN MARCIAL"),
    SAN_MARINO("SAN MARINO"),
    SAN_SEBASTIAN("SAN SEBASTIÁN"),
    SAN_VICENTE("SAN VICENTE"),
    SANTA_CLARA("SANTA CLARA"),
    SANTA_FE("SANTA FE"),
    SANTA_MONICA("SANTA MÓNICA"),
    SECTOR_BAHIA("SECTOR BAHÍA"),
    SECTOR_CRESTON("SECTOR CRESTÓN"),
    SEXTA_SECCION("SEXTA SECCIÓN"),
    SIN_DOMICILIO_FIJO("SIN DOMICILIO FIJO"),
    SONORA("SONORA"),
    SUENO("SUENO"),
    TERMOELECTRICA("TERMOELÉCTRICA"),
    TETABIATE("TETABIATE"),
    TIERRA_BONITA("TIERRA BONITA"),
    TINAJAS("TINAJAS"),
    TORIN("TORIN"),
    TORRES("TÓRRES"),
    TRIUNFO_DE_SANTA_ROSA("TRIUNFO DE SANTA ROSA"),
    TULAR("TULAR"),
    VALIENTE("VALIENTE"),
    VALLE_BONITO("VALLE BONITO"),
    VALLE_DEL_MAR("VALLE DEL MAR"),
    VARAL("VARAL"),
    VERGELES("VERGELES"),
    VICAM("VICAM"),
    VILLA_SOFIA("VILLA SOFIA"),
    VILLA_ZARINA("VILLA ZARINA"),
    VILLAHERMOSA("VILLAHERMOSA"),
    VILLAS("VILLAS"),
    VILLAS_DE_MIRAMAR("VILLAS DE MIRAMAR"),
    VILLAS_DE_SAN_CARLOS("VILLAS DE SAN CARLOS"),
    VILLAS_DEL_PARQUE("VILLAS DEL PARQUE"),
    VILLAS_DEL_PUERTO_I("VILLAS DEL PUERTO I"),
    VILLAS_DEL_PUERTO_II("VILLAS DEL PUERTO II"),
    VILLAS_DEL_PUERTO_III("VILLAS DEL PUERTO III"),
    VILLAS_DEL_TULAR("VILLAS DEL TULAR"),
    VILLAS_TETAKAWI("VILLAS TETAKAWI"),
    VISTA_AZUL("VISTA AZUL"),
    VISTA_DORADA("VISTA DORADA"),
    YAQUI("YAQUI"),
    YUCATAN("YUCATÁN");

    companion object {
        /**
         * Obtiene el valor del enum a partir de su nombre de visualización.
         * Si no se encuentra, retorna CENTRO como valor por defecto.
         *
         * @param displayName El nombre de visualización de la colonia
         * @return El valor del enum correspondiente
         */
        fun fromDisplayName(displayName: String): Colonias {
            return values().find { it.displayName == displayName } ?: CENTRO
        }
    }
} 