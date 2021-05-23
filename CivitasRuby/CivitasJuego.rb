module Civitas

    class CivitasJuego

        def initialize(nombres)
        
            @jugadores=[]
            for i in (0..nombres.length - 1)
                jug=Jugador.new(nombres[i])
                @jugadores<<jug
            end

            @gestorEstados=Gestor_estados.new
            @estado=@gestorEstados.estado_inicial

            @indiceJugadorActual=Dado.instance.quienEmpieza(nombres.length)
            @mazo=MazoSorpresas.new
            inicializarTablero(@mazo)
            inicializarMazoSorpresas(@tablero)
        end

        def inicializarTablero(mazo)
        
            @mazo=mazo
            
            @tablero=Tablero.new(rand 1..10)

            casilla1=  Casilla_Sorpresa.new( 'Sorpresa 1',mazo)
            casilla2= Casilla_Sorpresa.new( 'Sorpresa 2',mazo)
            casilla3= Casilla_Sorpresa.new( 'Sorpresa 3',mazo)
 
            titulo1= TituloPropiedad.new('Propiedad 1', 100, 1.1, 50, 150, 75)
            titulo2= TituloPropiedad.new('Propiedad 2', 200, 1.3, 70, 130, 60)
            titulo3= TituloPropiedad.new('Propiedad 3', 175, 1.07, 45, 170, 80)
            titulo4= TituloPropiedad.new('Propiedad 4', 95, 1.12, 55, 115, 55)
            casilla4= Casilla_Calle.new(titulo1)
            casilla5= Casilla_Calle.new(titulo2)
            casilla6= Casilla_Calle.new(titulo3)
            casilla7= Casilla_Calle.new(titulo4)      

            casilla8= Casilla_Impuesto.new('Impuesto',250)

            @tablero.añadeCasilla(casilla1)
            @tablero.añadeCasilla(casilla2)
            @tablero.añadeCasilla(casilla3)
            @tablero.añadeCasilla(casilla4)
            @tablero.añadeJuez
            @tablero.añadeCasilla(casilla5)
            @tablero.añadeCasilla(casilla6)
            @tablero.añadeCasilla(casilla7)
            @tablero.añadeCasilla(casilla8)


        end

        def inicializarMazoSorpresas(tablero)

            @mazo.alMazo(Sorpresa_JugadorEspeculador.new(500,'El jugador va a cambiar a especulatore'))  
=begin 
            @mazo.alMazo(Sorpresa_PorJugador.new(1000,'El jugador al que se le aplica esta sorpresa recibe la cantidad indicada.'))
            @mazo.alMazo(Sorpresa_PorJugador.new(-1000,'El jugador al que se le aplica esta sorpresa recibe la cantidad indicada.'))

            @mazo.alMazo(Sorpresa_PagarCobrar.new(1500, 'El jugador al que se le aplica esta sorpresa recibe la cantidad indicada.'))
            @mazo.alMazo(Sorpresa_PagarCobrar.new(-2000, 'El jugador al que se le aplica esta sorpresa paga la cantidad indicada.'))

            numCas_rand1=rand (0..10)
            numCas_rand2=rand (0..10)
            
            while numCas_rand1 == @tablero.numCasillaCarcel 
                numCas_rand1=rand(0..10)
            end

            while numCas_rand2 == @tablero.numCasillaCarcel
                numCas_rand2=rand(0..10)
            end

            @mazo.alMazo(Sorpresa_IrCasilla.new(@tablero, numCas_rand1,'Vaya a la casilla indicada'))
            @mazo.alMazo(Sorpresa_IrCasilla.new(@tablero ,numCas_rand1, 'Vaya a la casilla indicada'))
            @mazo.alMazo(Sorpresa_IrCasilla.new(@tablero,@tablero.numCasillaCarcel, 'Vaya a la carcel'))

            @mazo.alMazo(Sorpresa_PorCasaHotel.new(250, 'EL jugador al que se le aplica esta sorpresa recibe el importe indicado por cada casa y hotel que posea.'))
            @mazo.alMazo(Sorpresa_PorCasaHotel.new(-250, 'EL jugador al que se le aplica esta sorpresa recibe el importe indicado por cada casa y hotel que posea.'))

            @mazo.alMazo(Sorpresa_SalirCarcel.new(@mazo))

            @mazo.alMazo(Sorpresa_IrCarcel.new(@tablero))   
            
=end
            
        end

        def contabilizarPasosPorSalida(jugadorActual)
        
            while(@tablero.getPorSalida > 0)
                jugadorActual.pasaPorSalida
            end
        end

        def  pasarTurno 
        
            if @indiceJugadorActual < @jugadores.length-1
                @indiceJugadorActual+=1
            else
                @indiceJugadorActual=0
            end

        end

        def siguientePasoCompletado(operacion)
    
            @estado=@gestorEstados.siguiente_estado(getJugadorActual, @estado, operacion)
        
        end

        def getJugadorActual
            
            return @jugadores[@indiceJugadorActual]

        end

        def getCasillaActual

            casilla=@tablero.casillas[getJugadorActual.numCasillaActual] 
            return casilla
        
        end


        def construirCasa(ip)
        
            return getJugadorActual.construirCasaJug(ip)
        
        end

        def construirHotel(ip)
        
            return getJugadorActual.construirHotelJug(ip)
        
        end

        def vender(ip)
        
            return getJugadorActual.vender(ip)
        
        end

        def hipotecar(ip)
        
            return getJugadorActual.hipotecar(ip)
        
        end

        def cancelarHipoteca(ip)
            
            return getJugadorActual.cancelarHipoteca(ip)
        
        end

        def salirCarcelPagando

            return getJugadorActual.salirCarcelPagando
        
        end

        def salirCarcelTirando

            return getJugadorActual.salirCarcelTirando

        end

        def finalDelJuego

            it=@jugadores.size-1
            ret=false
            i=0

            while i < it && !ret
                ret=@jugadores[i].enBancarrota
                i+=1
            end
        
        end

        def infoJugadorTexto

            return @jugadores[@indiceJugadorActual].toString

        end

        def ranking

            ret=@jugadores

            ret.sort {|x,y| x<=>y}

            return ret            

        end

        def avanzaJugador

            jugadorActual=getJugadorActual
            posicionActual=jugadorActual.numCasillaActual

            tirada=Dado.instance.tirar

            posicionNueva=@tablero.nuevaPosicion(posicionActual, tirada)
            
            casilla=@tablero.getCasilla(posicionNueva)

            contabilizarPasosPorSalida(jugadorActual)
            
            jugadorActual.moverACasilla(posicionNueva)
            casilla.recibeJugador(@indiceJugadorActual, @jugadores)

            contabilizarPasosPorSalida(jugadorActual) 
        end

        def siguientePaso

            jugadorActual=@jugadores[@indiceJugadorActual]
            operacion=@gestorEstados.operaciones_permitidas(jugadorActual, @estado)

            if operacion==OperacionesJuego::PASAR_TURNO
                pasarTurno
                siguientePasoCompletado(operacion)
            else 

                if operacion==OperacionesJuego::AVANZAR 
                    avanzaJugador
                    siguientePasoCompletado(operacion)
                end
            end

            return operacion
        end

        def comprar

            jugadorActual=@jugadores[@indiceJugadorActual]
            numCasillaActual=jugadorActual.numCasillaActual
            
            casilla=@tablero.getCasilla(numCasillaActual)
            titulo=casilla.titulo

            return jugadorActual.comprar(titulo)
        end

        private :avanzaJugador, :contabilizarPasosPorSalida, :inicializarMazoSorpresas, :inicializarTablero, :pasarTurno

    end
end