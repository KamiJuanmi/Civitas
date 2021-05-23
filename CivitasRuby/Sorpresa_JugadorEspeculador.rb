module Civitas
    class Sorpresa_JugadorEspeculador < Sorpresa
        public_class_method :new
        def initialize(fianza,texto)
            super(texto)
            @fianza=fianza
        end

        def aplicarAJugador(actual,todos)
            if jugadorCorrecto(actual,todos)
                informe(actual, todos)
                aux=JugadorEspeculador.nuevoEspeculador(todos[actual],@fianza)
                puts aux.nombre
                todos[actual]=aux

            end
        end

        def toString

            return @texto

        end
    end
end