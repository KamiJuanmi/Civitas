module Civitas
    class Sorpresa_PagarCobrar < Sorpresa
        public_class_method :new
        def initialize(valor,texto)
            super(texto)
            @valor=valor
        end

        def aplicarAJugador(actual, todos)

            if jugadorCorrecto(actual,todos)
                informe(actual, todos)
                todos[actual].modificarSaldo(@valor)
            end
        
        end

        def toString
            return @texto
        end
    end
end