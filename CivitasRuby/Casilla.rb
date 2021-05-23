module Civitas

    class Casilla
        attr_reader :nombre
        
        def initialize(nombre)
            @nombre=nombre
        end

        def informe(actual, todos)
        
            Diario.instance.ocurre_evento('El jugador ' + todos[actual].nombre + ' ha caido en la casilla ' + toString)    

        end

        def jugadorCorrecto(actual, todos)
        
            return actual>=0 && actual<todos.length
        
        end
        
        def recibeJugador(actual, todos)

            if jugadorCorrecto(actual, todos)
                informe(actual, todos)
            end 
        end
        
        def toString
            ret=@nombre + ' de tipo descanso'
            return ret
        end

        protected :informe 

    end
end