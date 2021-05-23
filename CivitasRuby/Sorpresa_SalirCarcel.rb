module Civitas
    class Sorpresa_SalirCarcel < Sorpresa
        public_class_method :new
        
        def initialize(mazo)
            
            super('Queda libre de la carcel.')
            @mazo=mazo

        end

        def aplicarAJugador(actual, todos)
            if jugadorCorrecto(actual,todos)
                informe(actual, todos)
                obtiene=true
                i=0
                while i<todos.size-1 && !obtiene
                
                    if(todos[i].tieneSalvoconducto)
                
                        obtiene=false
                    
                    end
                    i+=1
                end
            
                if(obtiene)
                
                    todos[actual].obtenerSalvoconducto(self)
                    salirDelMazo
                
                end
            end
            
        end

        def salirDelMazo
            
            @mazo.inhabilitarCartaEspecial(self)
              
        end
          
        def usada
          
            @mazo.habilitarCartaEspecial(self)
          
        end

        def toString

            return 'Sorpresa de salva de la carcel.'

        end


    end
end