module Civitas
    class Casilla_Juez < Casilla
        
        @@carcel
        def initialize(numCasillaCarcel)
            super('Juez')
            @@carcel=numCasillaCarcel
        end

        def recibeJugador(actual, todos)
        
            if jugadorCorrecto(actual, todos)
                informe(actual, todos)
                todos[actual].encarcelar(@@carcel)
            end    
        
        end

        def toString
            return 'Casilla del juez.'
        end

    end
end