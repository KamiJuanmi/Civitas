module Civitas
    class Sorpresa_IrCasilla < Sorpresa
        public_class_method :new
        def initialize(tablero,valor,texto)

            super(texto)
            @tablero=tablero
            @valor=valor
            
        end

        def aplicarAJugador(actual, todos)

            if jugadorCorrecto(actual,todos)
                informe(actual,todos)
                casillaActual=todos[actual].numCasillaActual
                tirada=@tablero.calcularTirada(casillaActual,@valor)
                todos[actual].moverACasilla(@tablero.nuevaPosicion(casillaActual,tirada))
                @tablero.getCasilla(@valor).recibeJugador(actual, todos)
            end

        end

        def toString
            return @texto
        end
    end
end