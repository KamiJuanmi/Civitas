module Civitas

    class Casilla_Calle < Casilla
        attr_reader :titulo

        def initialize(titulo)
            super(titulo.nombre)
            @titulo=titulo
            @importe=titulo.precioCompra
        end

        def recibeJugador(actual,todos)
            if jugadorCorrecto(actual, todos)
            
                informe(actual, todos)
                jugador=todos[actual]

                if !@titulo.tienePropietario
                    
                    jugador.puedeComprarCasilla
                else
                    titulo.tramitarAlquiler(jugador)
                end
            end
        end

        def toString
            
            ret=@nombre + ' de tipo calle '
            ret+=@titulo.toString
            return ret
            
        end

    end
    
end