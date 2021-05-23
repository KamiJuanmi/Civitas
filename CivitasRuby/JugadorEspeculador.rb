module Civitas
    class JugadorEspeculador < Jugador

        @@FactorEspeculador=2
        @@CasasMax=@@FactorEspeculador*@@CasasMax
        @@HotelesMax=@@HotelesMax*@@FactorEspeculador

        def initialize(fianza)
            
            @fianza=fianza
            
        end

        private_class_method :new

        def self.nuevoEspeculador(jugador,fianza)

            nuevo=new(fianza)
            nuevo.copia(jugador)

            for i in (0...nuevo.propiedades.size-1)
                nuevo.propiedades[i].actualizaPropietarioPorConversion(nuevo)
            end

            return nuevo
            
        end

        def pagaImpuesto(cantidad)
        
            super(cantidad/2)
        end

        def debeSerEncarcelado 
            @encarcelado ? ret=false : ret= true 

            if !@encarcelado && !tieneSalvoConducto && saldo<= @fianza
                ret=true
            else 
                if tieneSalvoConducto
                    perderSalvoConducto
                    Diario.instance.ocurre_evento('El jugador ' + @nombre + ' se libra de la carcel.')
                    ret=false
                else
                    paga(@fianza)
                end
            end

            return ret
        end

        def getCasasMax
            @@CasasMax
        end

        def getHotelesMax
            @@HotelesMax
        end

        def toString

            salida = super
            salida+= 'Es un jugador especulador' + "\n"

            return salida
        end

    end
end