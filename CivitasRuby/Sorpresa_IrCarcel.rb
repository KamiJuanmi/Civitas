module Civitas
    class Sorpresa_IrCarcel < Sorpresa
        public_class_method :new
        
        def initialize(tablero)
            super('Vaya a la carcel.')
            @tablero=tablero
        end

        def aplicarAJugador(actual, todos)

            if jugadorCorrecto(actual,todos)
                informe(actual, todos)
                todos[actual].encarcelar(@tablero.numCasillaCarcel)
            end
        end

        def toString
            return 'Sorpresa que envia a la carcel.'
        end

    end
end