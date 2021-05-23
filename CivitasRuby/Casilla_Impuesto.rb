module Civitas
    class Casilla_Impuesto < Casilla

        attr_reader :importe

        def initialize(nombre,cantidad)
            super(nombre)
            @importe=cantidad
        end

        def recibeJugador(actual, todos)
        
            if jugadorCorrecto(actual, todos)
                informe(actual, todos)
                todos[actual].pagaImpuesto(@importe)
            end

        end

        def toString
            
            ret=@nombre + ' de tipo impuesto con precio: '
            ret+=@importe.to_s
            return ret
            
        end

    end
end