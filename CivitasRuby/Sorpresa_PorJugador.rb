module Civitas
    class Sorpresa_PorJugador < Sorpresa
        public_class_method :new
        
        def initialize(valor,texto)
            
            super(texto)
            @valor=valor
        
        end

        def aplicarAJugador(actual, todos)
            if jugadorCorrecto(actual,todos)
                
                informe(actual, todos)
                pagar=Sorpresa_PagarCobrar.new(-@valor, 'El jugador al que se le aplica la sorpresa ha de pagar el importe que se indica.')
                cobrar=Sorpresa_PagarCobrar.new(@valor*(todos.size-1), 'El jugador al que se le aplica la sorpresa recibe el importe que se indica.')
              
                for i in (0..todos.size-1)
                    if i!=actual
      
                    pagar.aplicarAJugador(i, todos)
              
                  end
      
                end
      
                cobrar.aplicarAJugador(actual, todos)
            end
      
        end

        def toString
            return @texto
        end
        

    end
end