module  Civitas
    class Controlador
        def initialize(juego, vista)
            @juego=juego
            @vista=vista
        end
        def juega
            @vista.setCivitasJuego(@juego)
            while ! @juego.finalDelJuego
                @vista.actualizarVista
                @vista.pausa
                operacion=@juego.siguientePaso
                @vista.mostrarSiguienteOperacion(operacion)
                if operacion != OperacionesJuego::PASAR_TURNO
                    @vista.mostrarEventos
                end
                if !@juego.finalDelJuego
                    case operacion

                    when OperacionesJuego::COMPRAR
                        if @vista.comprar == Respuestas::SI
                            @juego.comprar
                        end
                        
                        @juego.siguientePasoCompletado(operacion)

                    when OperacionesJuego::GESTIONAR
                        @vista.gestionar
                        lista_GestionesInmobiliarias = [GestionesInmobiliarias::VENDER,GestionesInmobiliarias::HIPOTECAR,GestionesInmobiliarias::CANCELAR_HIPOTECA,GestionesInmobiliarias::CONSTRUIR_CASA,GestionesInmobiliarias::CONSTRUIR_HOTEL,GestionesInmobiliarias::TERMINAR]
                        
                        oper_inm = OperacionInmobiliaria.new(lista_GestionesInmobiliarias[@vista.getGestion],@vista.getPropiedad)
                        
                        case oper_inm.gestion

                        when GestionesInmobiliarias::VENDER
                            @juego.vender(oper_inm.numPropiedad)
                        when GestionesInmobiliarias::HIPOTECAR
                            @juego.hipotecar(oper_inm.numPropiedad)
                        when GestionesInmobiliarias::CANCELAR_HIPOTECA
                            @juego.cancelarHipoteca(oper_inm.numPropiedad)
                        when GestionesInmobiliarias::CONSTRUIR_CASA
                            @juego.construirCasa(oper_inm.numPropiedad)
                        when GestionesInmobiliarias::CONSTRUIR_HOTEL
                            @juego.construirHotel(oper_inm.numPropiedad)
                        else
                            @juego.siguientePasoCompletado(operacion)
                        end

                    when OperacionesJuego::SALIR_CARCEL
                        if @vista.salirCarcel==SalidasCarcel::TIRANDO
                            @juego.salirCarcelTirando
                        else
                            @juego.salirCarcelPagando
                        end
                        @juego.siguientePasoCompletado(operacion)
                    end
                end

            end
            @juego.ranking
        end
    end
end