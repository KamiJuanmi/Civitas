module Civitas
    class Casilla_Sorpresa < Casilla
        def initialize(nombre,mazo)
            super(nombre)
            @mazo=mazo
        end

        def recibeJugador(actual,todos)

            if jugadorCorrecto(actual, todos)
                @sorpresa=@mazo.siguiente
                informe(actual, todos)
                @sorpresa.aplicarAJugador(actual, todos)
            end
        end

        def toString
            ret=@nombre + ' de tipo sorpresa: '
            ret+=@sorpresa.toString
            return ret
        end

    end
end